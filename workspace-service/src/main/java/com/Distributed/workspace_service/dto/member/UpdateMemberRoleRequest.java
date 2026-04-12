package com.Distributed.workspace_service.dto.member;

import com.Distributed.common_lib.enums.ProjectRole;
//import com.webagent.projects.websmith.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
