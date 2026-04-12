package com.Distributed.workspace_service.dto.project;

//import com.webagent.projects.websmith.dto.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
