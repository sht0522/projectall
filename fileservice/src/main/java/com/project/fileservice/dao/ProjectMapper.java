package com.project.fileservice.dao;

import com.project.fileservice.pojo.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    //添加导出日志
    int saveProjectSpace(Project oProject);
    //查询所有项目的顶部空间
    List<Project> seletProjectTop();
    //查询所有项目的顶部空间
    List<Project> seletProjectChildren(Integer nParentId);

    Project seletProjectById(Integer nId);
}
