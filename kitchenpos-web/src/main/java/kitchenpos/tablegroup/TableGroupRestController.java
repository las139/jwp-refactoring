package kitchenpos.tablegroup;

import java.net.URI;
import kitchenpos.dto.tablegroup.TableGroupResponse;
import kitchenpos.dto.tablegroup.TableGroupsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableGroupRestController {

    private final TableGroupService tableGroupService;

    public TableGroupRestController(final TableGroupService tableGroupService) {
        this.tableGroupService = tableGroupService;
    }

    @PostMapping("/api/table-groups")
    public ResponseEntity<TableGroupResponse> create(
        @RequestBody final TableGroupsRequest tableGroup) {
        final TableGroupResponse created = tableGroupService.create(tableGroup.toEntity());
        final URI uri = URI.create("/api/table-groups/" + created.getId());
        return ResponseEntity.created(uri)
            .body(created);
    }

    @DeleteMapping("/api/table-groups/{tableGroupId}")
    public ResponseEntity<Void> ungroup(@PathVariable final Long tableGroupId) {
        tableGroupService.ungroup(tableGroupId);
        return ResponseEntity.noContent()
            .build();
    }
}