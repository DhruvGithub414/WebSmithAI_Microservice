package com.Distributed.workspace_service.service;


import com.Distributed.workspace_service.dto.member.InviteMemberRequest;
import com.Distributed.workspace_service.dto.member.MemberResponse;
import com.Distributed.workspace_service.dto.member.UpdateMemberRoleRequest;

import java.util.List;

public interface ProjectMemberService {
    List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    Void removeProjectMember(Long projectId, Long memberId);
}
