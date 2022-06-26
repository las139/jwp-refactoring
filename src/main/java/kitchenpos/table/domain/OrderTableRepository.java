package kitchenpos.table.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findAllByIdIn(List<Long> ids);
    Optional<OrderTable> findByIdAndEmptyIsFalse(Long id);
    Optional<OrderTable> findByIdAndTableGroupIsNull(Long id);

    boolean existsAllByIdIn(List<Long> orderTables);
}
