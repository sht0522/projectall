package com.project.fileservice.service;

import com.project.fileservice.pojo.Project;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProjectService {
   String addProject(Project oProject) throws Exception;
   String getProjectTree()throws Exception;
   String getFreeSpace(Project oProject)throws Exception;
}
