package com.project.fileservice.controller;

import com.project.fileservice.pojo.Project;
import com.project.fileservice.service.ProjectService;
import com.project.fileservice.util.DataMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.lang.reflect.Method;

@RestController
@RequestMapping(value = "/project", produces = "application/json;charset=UTF-8")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    /**
     * 添加项目空间
     * @author sht
     * @param
     * @return String
     */
    @RequestMapping(value = "/addProject",produces = "application/json;charset=UTF-8")
    public String addProject(@RequestBody Project oProject) {
        try {
            return projectService.addProject(oProject);
        } catch (Exception e) {
            e.printStackTrace();
            return DataMessage.ReturnData(0,"添加项目空间失败",null);
        }
    }

    /**
     * 查询项目数树空间的组合
     * @return
     */
    @RequestMapping(value = "/getProjectTree",produces = "application/json;charset=UTF-8")
    public String getProjectTree() {
        try {
            return projectService.getProjectTree();
        } catch (Exception e) {
            e.printStackTrace();
            return DataMessage.ReturnData(0,"查询项目树数据失败",null);
        }
    }

    /**
     *查询剩余空间
     * @return
     */
    @RequestMapping(value = "/getFreeSpace",produces = "application/json;charset=UTF-8")
    public String getFreeSpace(@RequestBody Project oProject){
        try {
            return projectService.getFreeSpace(oProject);
        } catch (Exception e) {
            e.printStackTrace();
            return DataMessage.ReturnData(0,"查询剩余空间失败",null);
        }
    }








}
