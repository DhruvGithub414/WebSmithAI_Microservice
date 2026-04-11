package com.Distributed.common_lib.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.Distributed.common_lib.enums.ProjectPermission.*;
//import static com.webagent.projects.websmith.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    EDITOR(Set.of(EDIT,VIEW,DELETE,VIEW_MEMBERS)),
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)),
    OWNER(Set.of(VIEW,EDIT,DELETE,MANAGE_MEMBERS,VIEW_MEMBERS));


    private final Set<ProjectPermission> permissions;
}
