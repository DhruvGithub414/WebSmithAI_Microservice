package com.Distributed.workspace_service.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectMemberId {
    Long projectId;
    Long userId;
}
