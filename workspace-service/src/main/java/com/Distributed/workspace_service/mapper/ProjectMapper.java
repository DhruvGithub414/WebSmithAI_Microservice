package com.Distributed.workspace_service.mapper;

import com.Distributed.common_lib.enums.ProjectRole;
import com.Distributed.workspace_service.dto.project.ProjectResponse;
import com.Distributed.workspace_service.dto.project.ProjectSummaryResponse;
import com.Distributed.workspace_service.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

// @JavaBean
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);

//    @Mapping(target = "projectName", source = "name")
    ProjectSummaryResponse toProjectSummaryResponse(Project project, ProjectRole role);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

}
