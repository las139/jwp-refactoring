package kitchenpos.table.domain;

import static kitchenpos.helper.TableFixtures.빈_테이블_만들기;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단체 지정 관련 Domain 단위 테스트")
class TableGroupTest {

    @DisplayName("테이블들에 대한 단체지정을 등록한다.")
    @Test
    void groupingTables() {
        //given
        TableGroup tableGroup = new TableGroup(null, null);
        OrderTables orderTables = new OrderTables();
        orderTables.addOrderTable(빈_테이블_만들기());
        orderTables.addOrderTable(빈_테이블_만들기());

        //when
        tableGroup.groupingTables(orderTables, 2);

        //then
        assertThat(orderTables.getOrderTables().get(0).getTableGroup()).isNotNull();
        assertThat(orderTables.getOrderTables().get(1).getTableGroup()).isNotNull();
        assertThat(orderTables.getOrderTables().get(0).getEmpty().isOrderTable()).isTrue();
        assertThat(orderTables.getOrderTables().get(1).getEmpty().isOrderTable()).isTrue();
    }

    @DisplayName("테이블이 2개 미만인 경우 단체지정 할 수 없다.")
    @Test
    void groupingTables_less_then_two() {
        //given
        TableGroup tableGroup = new TableGroup(null, null);
        OrderTables orderTables = new OrderTables();
        orderTables.addOrderTable(빈_테이블_만들기());

        // when then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> tableGroup.groupingTables(orderTables,1));
    }

    @DisplayName("단체 지정을 해제 한다.")
    @Test
    void ungroupingTable(){
        //given
        TableGroup tableGroup = new TableGroup(null, null);
        OrderTables orderTables = new OrderTables();
        orderTables.addOrderTable(빈_테이블_만들기());
        orderTables.addOrderTable(빈_테이블_만들기());
        tableGroup.groupingTables(orderTables, 2);

        //when
        tableGroup.ungroupingTableGroup();

        //then
        assertThat(orderTables.getOrderTables().get(0).getTableGroup()).isNull();
        assertThat(orderTables.getOrderTables().get(1).getTableGroup()).isNull();
        assertThat(orderTables.getOrderTables().get(0).getEmpty().isOrderTable()).isTrue();
        assertThat(orderTables.getOrderTables().get(1).getEmpty().isOrderTable()).isTrue();
    }
}
