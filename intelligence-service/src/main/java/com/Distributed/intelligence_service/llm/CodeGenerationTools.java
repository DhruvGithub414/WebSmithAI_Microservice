package com.Distributed.intelligence_service.llm;

import com.Distributed.intelligence_service.client.WorkspaceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class CodeGenerationTools {

    private final Long projectId;
    private final WorkspaceClient workspaceClient;

    @Tool(
            name = "read_files",
            description = "Read the content of files. Only input file names present inside the FILE_TREE. DO NOT input any path which is not inside the FILE_TREE."
    )
    public List<String> readfiles(
            @ToolParam(description = "List of relative paths (e.g., ['src/App.tsx'])")
            List<String> paths

    ){
        List<String> result = new ArrayList<>();

        for(String path : paths){
            String cleanPath = path.startsWith("/")?path.substring(1):path;
            log.info("Requested file: {}", cleanPath);
            String content = workspaceClient.getFileContent(projectId, cleanPath);

            result.add(String.format(
               "--- START OF FILE: %s ---\n%s\n--- END OF FILE ---"  ,
               cleanPath, content
            ));
        }
        return result;
    }

}
