package com.Distributed.workspace_service.mapper;

import com.Distributed.workspace_service.dto.member.MemberResponse;
import com.Distributed.workspace_service.entity.ProjectMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {
//    MemberResponse toProjectMemberResponseFromOwner(User owner);

    @Mapping(target="userId", source="id.userId")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);
}
