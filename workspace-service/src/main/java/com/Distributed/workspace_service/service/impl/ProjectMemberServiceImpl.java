package com.Distributed.workspace_service.service.impl;

import com.Distributed.common_lib.dto.UserDto;
import com.Distributed.common_lib.error.ResourceNotFoundException;
import com.Distributed.common_lib.security.AuthUtil;
import com.Distributed.workspace_service.client.AccountClient;
import com.Distributed.workspace_service.dto.member.InviteMemberRequest;
import com.Distributed.workspace_service.dto.member.MemberResponse;
import com.Distributed.workspace_service.dto.member.UpdateMemberRoleRequest;
import com.Distributed.workspace_service.entity.Project;
import com.Distributed.workspace_service.entity.ProjectMember;
import com.Distributed.workspace_service.entity.ProjectMemberId;
import com.Distributed.workspace_service.mapper.ProjectMemberMapper;
import com.Distributed.workspace_service.repository.ProjectMemberRepository;
import com.Distributed.workspace_service.repository.ProjectRepository;
import com.Distributed.workspace_service.service.ProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {
    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
//    UserRepository userRepository;
    AuthUtil authUtil;
    AccountClient accountClient;
    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {
//        Long userId = authUtil.getCurrentUserId();
//        Project project = getAccessibleProjectById(projectId,userId);
        List<MemberResponse> memberResponseList = projectMemberRepository.findByIdProjectId(projectId)
                .stream()
                .map(projectMemberMapper::toProjectMemberResponseFromMember)
                .toList();
//        memberResponseList.add(projectMemberMapper.toProjectMemberResponseFromOwner(project.getOwner()));


        return memberResponseList;
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        UserDto invitee = accountClient.getUserByEmail(request.username()).orElseThrow(
                ()-> new ResourceNotFoundException("User", request.username())
        );
        if (invitee.id().equals(userId)){
            throw new RuntimeException("Cannot invite yourself");
        }
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.id());
        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Member already exists");
        }
        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);
        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public Void removeProjectMember(Long projectId, Long memberId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Member not found in project");
        }
        projectMemberRepository.deleteById(projectMemberId);
        return null;
    }

    public Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}
