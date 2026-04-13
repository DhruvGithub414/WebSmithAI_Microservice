package com.Distributed.workspace_service.controller;


import com.Distributed.common_lib.dto.FileTreeDto;
import com.Distributed.workspace_service.service.ProjectFileService;
import com.Distributed.workspace_service.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/internal/v1/")
public class InternalWorkspaceController {

    private final ProjectService projectService;
    private final ProjectFileService projectFileService;

    @GetMapping("/projects/{projectId}/files/tree")
    public FileTreeDto getFileTree(@PathVariable Long projectId){

        return projectFileService.getFileTree(projectId);
    }

    @GetMapping("/projects/{projectId}/files/tree")
    public String getFileContent(@PathVariable Long projectId, @RequestParam String path){

        return projectFileService.getFileContent(projectId,path);

    }

}
