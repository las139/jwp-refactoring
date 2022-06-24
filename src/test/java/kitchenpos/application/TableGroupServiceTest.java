package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.repository.OrderRepository;
import kitchenpos.domain.repository.OrderTableRepository;
import kitchenpos.domain.repository.TableGroupRepository;
import kitchenpos.dto.OrderTableRequest;
import kitchenpos.dto.OrderTableResponse;
import kitchenpos.dto.TableGroupRequest;
import kitchenpos.dto.TableGroupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest extends ServiceTest {

    @Mock
    private OrderRepository mockOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderTableRepository orderTableRepository;
    @Autowired
    private TableGroupRepository tableGroupRepository;
    @Autowired
    private TableGroupService tableGroupService;

    private OrderTable orderTable1;
    private OrderTable orderTable2;
    private TableGroupResponse tableGroupResponse;

    @BeforeEach
    void setUp() {
        super.setUp();
        orderTable1 = this.orderTableRepository.save(new OrderTable(0, true));
        orderTable2 = this.orderTableRepository.save(new OrderTable(0, true));
        tableGroupResponse = this.tableGroupService.create(new TableGroupRequest(OrderTableRequest.of(orderTable1, orderTable2)));
    }

    @Test
    @DisplayName("테이블 그룹이 정상적으로 생성된다.")
    void createTableGroup() {
        OrderTable orderTable1 = this.orderTableRepository.save(new OrderTable(0, true));
        OrderTable orderTable2 = this.orderTableRepository.save(new OrderTable(0, true));

        TableGroupResponse tableGroup = this.tableGroupService.create(new TableGroupRequest(OrderTableRequest.of(orderTable1, orderTable2)));

        assertThat(tableGroup.getId()).isNotNull();
        assertThat(tableGroup.getOrderTables()).hasSize(2);
        assertTrue(tableGroup.getOrderTables().stream().anyMatch(OrderTableResponse::isEmpty));
    }

    @Test
    @DisplayName("테이블 정보가 존재하지 않을 경우 예외를 던진다.")
    void createFail_orderTableNullOrEmpty() {
        OrderTable orderTable = new OrderTable(100L, null, 0, true);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> this.tableGroupService.create(new TableGroupRequest(Collections.emptyList())));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> this.tableGroupService.create(new TableGroupRequest(OrderTableRequest.of(orderTable))));
    }

    @Test
    @DisplayName("테이블 중 비어있지 않은 테이블이 존재할 경우 생성될 수 없다.")
    void createFail_orderTableEmptyStatus() {
        OrderTable orderTable = this.orderTableRepository.save(new OrderTable(4, false));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> this.tableGroupService.create(new TableGroupRequest(OrderTableRequest.of(orderTable))));
    }

    @Test
    @DisplayName("테이블 중 이미 테이블 그룹에 속한 테이블이 존재할 경우 생성될 수 없다.")
    void createFail_alreadyContainTableGroup() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> this.tableGroupService.create(new TableGroupRequest(OrderTableRequest.of(this.orderTable1))));
    }

    @Test
    @DisplayName("테이블 그룹을 해제한다.")
    void ungroup() {
        this.tableGroupService.ungroup(tableGroupResponse.getId());

        List<OrderTable> orderTables = this.orderTableRepository.findAll();
        assertTrue(orderTables.stream().anyMatch(orderTable -> orderTable.getTableGroup() == null));
    }

    @Test
    @DisplayName("테이블 중 식사중이거나 조리중인 테이블이 있다면 해제할 수 없다.")
    void ungroupFail() {
        TableGroupService service = new TableGroupService(mockOrderRepository, orderTableRepository, tableGroupRepository);
        when(this.mockOrderRepository.existsByOrderTableIdInAndOrderStatusIn(anyList(), anyList()))
            .thenReturn(true);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> service.ungroup(tableGroupResponse.getId()));
    }

}
