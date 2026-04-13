package com.Distributed.workspace_service.controller;

import com.Distributed.workspace_service.dto.project.DeployResponse;
import com.Distributed.workspace_service.dto.project.ProjectRequest;
import com.Distributed.workspace_service.dto.project.ProjectResponse;
import com.Distributed.workspace_service.dto.project.ProjectSummaryResponse;
import com.Distributed.workspace_service.service.DeploymentService;
import com.Distributed.workspace_service.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final DeploymentService deploymentService;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects() {

        return ResponseEntity.ok(projectService.getUserProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectSummaryResponse> getProjectById(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(projectService.getUserProjectById(projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest request) {
        Long userId = 1L;
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Long userId = 1L;
        projectService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/deploy")
    public ResponseEntity<DeployResponse> deployProject(@PathVariable Long id){
        return ResponseEntity.ok(deploymentService.deploy(id));
    }

}

















