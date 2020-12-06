package com.project.fileservice.util;

import org.springframework.beans.factory.annotation.Value;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;

//获取盘符大小
public class FormetSize {


    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 获取硬盘的剩余大小
     */
    public static long driver(String strFileTheRootDisk){
        File file = new File(strFileTheRootDisk);
        long totalSpace = file.getTotalSpace(); //total disk space in bytes.
        long freeSpace = file.getFreeSpace(); //unallocated / free disk space in bytes.


        //System.out.println("磁盘大小 : " + totalSpace + " bytes");
        //System.out.println("已用空间 : " + (totalSpace-freeSpace) + " bytes");
        //System.out.println("剩余空间 : " + freeSpace + " bytes");
        return freeSpace;



/**
        // 当前文件系统类
        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fsv.getSystemDisplayName(fs[i]));
            System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
            System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()));
        }
 **/
    }




}
