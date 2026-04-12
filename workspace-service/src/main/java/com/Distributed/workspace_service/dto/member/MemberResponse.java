package com.Distributed.workspace_service.dto.member;

import com.webagent.projects.websmith.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole role,
        Instant invitedAt
) {
}
