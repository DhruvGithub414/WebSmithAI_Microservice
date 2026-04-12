package com.Distributed.workspace_service.service.impl;

import com.Distributed.common_lib.dto.PlanDto;
import com.Distributed.common_lib.enums.ProjectRole;
import com.Distributed.common_lib.error.BadRequestException;
import com.Distributed.common_lib.error.ResourceNotFoundException;
import com.Distributed.common_lib.security.AuthUtil;
import com.Distributed.workspace_service.client.AccountClient;
import com.Distributed.workspace_service.dto.project.ProjectRequest;
import com.Distributed.workspace_service.dto.project.ProjectResponse;
import com.Distributed.workspace_service.dto.project.ProjectSummaryResponse;
import com.Distributed.workspace_service.entity.Project;
import com.Distributed.workspace_service.entity.ProjectMember;
import com.Distributed.workspace_service.entity.ProjectMemberId;
import com.Distributed.workspace_service.mapper.ProjectMapper;
import com.Distributed.workspace_service.repository.ProjectMemberRepository;
import com.Distributed.workspace_service.repository.ProjectRepository;
import com.Distributed.workspace_service.service.ProjectService;
import com.Distributed.workspace_service.service.ProjectTemplateService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;
    AuthUtil authUtil;
    ProjectTemplateService projectTemplateService;
    AccountClient accountClient;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        if(!canCreateProject()){
            throw new BadRequestException("User Cannot Create a New project with current Plan, Upgrade the Plan");
        }
        Long ownerUserId = authUtil.getCurrentUserId();
//        User owner = userRepository.findById(userId).orElseThrow(
//                () -> new ResourceNotFoundException("User", userId.toString())
//        );
        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();
        project = projectRepository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), ownerUserId);
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();

        projectMemberRepository.save(projectMember);

        projectTemplateService.initializeProjectFromTemplate(project.getId());

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
//        return projectRepository.findAllAccessibleByUser(userId)
//                .stream()
//                .map(projectMapper::toProjectSummaryResponse)
//                .collect(Collectors.toList());

        var projectsWithRoles = projectRepository.findAllAccessibleByUser(userId);
        return projectsWithRoles.stream()
                .map(p->projectMapper.toProjectSummaryResponse(p.getProject(), p.getRole()))
                .toList();
    }

    @Override
    @PreAuthorize("@security.canViewProject(#projectId)")
    public ProjectSummaryResponse getUserProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
//        Project project = getAccessibleProjectById(id,userId);

        ProjectRepository.ProjectWithRole projectWithRole = projectRepository.findAccessibleProjectByIdWithRole(projectId, userId)
                .orElseThrow(()->new BadRequestException("Project Not Found"));
        return projectMapper.toProjectSummaryResponse(projectWithRole.getProject(), projectWithRole.getRole());
    }

    @Override
    @PreAuthorize("@security.canEditProject(#id)")
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(id,userId);


        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.canDeleteProject(#id)")
    public void softDelete(Long id) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(id,userId);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    public Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findAccessibleProjectById(projectId, userId)
                .orElseThrow(()-> new ResourceNotFoundException("project ", projectId.toString()));
    }

    private boolean canCreateProject() {
        Long userId = authUtil.getCurrentUserId();
        if (userId == null) {
            return false;
        }
        PlanDto plan = accountClient.getCurrentSubscribedPlanByUser();

        int maxAllowed = plan.maxProjects();
        int ownedCount = projectMemberRepository.countProjectOwnedByUser(userId);

        return ownedCount < maxAllowed;
    }

}
