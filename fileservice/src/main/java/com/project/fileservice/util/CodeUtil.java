package com.project.fileservice.util;

import java.util.UUID;

public class CodeUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
