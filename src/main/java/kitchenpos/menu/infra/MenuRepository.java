package kitchenpos.menu.infra;

import kitchenpos.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("select count(m) from Menu m where m.id in (:ids)")
    int countByIdIn(@Param("ids") List<Long> menuIds);
}
