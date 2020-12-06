package com.project.fileservice.serviceImpl;

import com.project.fileservice.dao.ProjectMapper;
import com.project.fileservice.pojo.Project;
import com.project.fileservice.service.ProjectService;
import com.project.fileservice.util.CodeUtil;
import com.project.fileservice.util.DataMessage;
import com.project.fileservice.util.FormetSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {
    //目标磁盘
    @Value("${fileTheRootDisk}")
    private String strFileTheRootDisk;
    //目标磁盘
    @Value("${fileMessage}")
    private String strFileMessage;
    //项目空间
    @Autowired
    private ProjectMapper projectMapper;
    /**
     * 添加项目空间
     * @author sht
     * @param
     * @return String
     */
    @Transactional
    @Override
    public String addProject(Project oProject)throws Exception {
        if(FormetSize.driver(strFileTheRootDisk) <= 0){
            return DataMessage.ReturnData(1,"目标磁盘已满，无法添加空间",null);
        }
        //获取存储的路径
        String strPath = strFileMessage;
        File topFile = new File(strPath);
        if(!topFile.exists()){
            topFile.mkdirs(); //创建目录
        }
        //如果该项目目录有父级目录
        if(oProject.getnParentSpace() != null){
            Project parentProject = projectMapper.seletProjectById(oProject.getnParentSpace());
            strPath = parentProject.getStrProjectSpace();
        }
        //获取组合成数据
        strPath = strPath +  File.separator + CodeUtil.getUUID();
        oProject.setStrProjectSpace(strPath);
        int nSign = projectMapper.saveProjectSpace(oProject);
        if(nSign == 1){
            //目录
            File file = new File(strPath);
            //如果目录为空，创建目录
            if(!file.exists()){
                file.mkdirs(); //创建目录
            }
            return DataMessage.ReturnData(1,"添加项目空间成功",null);
        }
        return DataMessage.ReturnData(0,"添加项目空间失败",null);
    }

    /**
     * 查询项目数树空间的组合
     * @return
     */
    @Override
    public String getProjectTree()throws Exception{
        //查询所有的顶层数据
        List<Project> projectTopList = projectMapper.seletProjectTop();
        //遍历组合数据
        List<Map<String,Object>> mapAllList = new ArrayList<>();
        for(Project oProject:projectTopList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",oProject.getnId());
            if(oProject.getStrProjectName()!=null){
                map.put("label",oProject.getStrProjectName());
            }else{
                map.put("label","");
            }
            List<Map<String,Object>> mapList = getChildrenList(oProject.getnId());
            if(mapList.size()!=0){
                map.put("children",mapList);
            }
            mapAllList.add(map);
        }
        //遍历组合数据
        return DataMessage.ReturnData(1,"查询项目树数据成功",mapAllList);
    }

    //查询子节点
    private List<Map<String,Object>> getChildrenList(Integer strParentId){
        List<Map<String,Object>> listMap = new ArrayList<>();
        //查询该父节点下的子节点
        List<Project> projectTopList = projectMapper.seletProjectChildren(strParentId);
        for(Project oProject:projectTopList){
            Map<String,Object> map = new HashMap<>();
            map.put("id",oProject.getnId());
            if(oProject.getStrProjectName()!=null){
                map.put("label",oProject.getStrProjectName());
            }else{
                map.put("label","");
            }
            List<Map<String,Object>> mapList = getChildrenList(oProject.getnId());
            if(mapList.size()!= 0){
                    map.put("children",mapList);
             }
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 查询剩余空间
     * @param oProject
     * @return
     * @throws Exception
     */
    @Override
    public String getFreeSpace(Project oProject) throws Exception {
        Long size = FormetSize.driver(strFileTheRootDisk);
        //获取当前磁盘剩余空间的大小
        String strSize = FormetSize.FormetFileSize(size);
        Project project = projectMapper.seletProjectById(oProject.getnId());
        //获取当前目录下的文件数
        File file = new File(project.getStrProjectSpace());
        //如果找不到目录
        if(!file.exists()){
            return DataMessage.ReturnData(0,"查询剩余空间失败，目录不存在",null);
        }
        //文件个数
        Long nSize = 0L;
        for(File oFile:file.listFiles()){
            if(oFile.isFile()){
                nSize++;
            }
        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("fileSpace",strSize);
        map.put("fileNumber",nSize);
        return DataMessage.ReturnData(1,"查询剩余空间成功",map);
    }

}
