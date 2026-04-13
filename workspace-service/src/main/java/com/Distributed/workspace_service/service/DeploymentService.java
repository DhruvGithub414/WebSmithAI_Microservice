package com.Distributed.workspace_service.service;

//import com.webagent.projects.websmith.dto.deploy.DeployResponse;

import com.Distributed.workspace_service.dto.project.DeployResponse;
import org.jspecify.annotations.Nullable;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);

}
