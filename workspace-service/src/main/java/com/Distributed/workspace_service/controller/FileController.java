package com.Distributed.workspace_service.controller;

import com.Distributed.common_lib.dto.FileTreeDto;
import com.Distributed.workspace_service.dto.project.FileContentResponse;
import com.Distributed.workspace_service.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/files")
public class FileController {

    private final ProjectFileService fileService;

    @GetMapping
    public ResponseEntity<FileTreeDto> getFileTree(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFileTree(projectId));
    }

    @GetMapping("/content") // /src/hooks/get-user-hook.jsx
    public ResponseEntity<String> getFile(
            @PathVariable Long projectId,
            @RequestParam String path
    ) {
//        Long userId = 1L;
        return ResponseEntity.ok(fileService.getFileContent(projectId, path));
    }

}
