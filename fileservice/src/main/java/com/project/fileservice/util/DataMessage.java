package com.project.fileservice.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

public class DataMessage {
    /**
     * 处理数据公共方法
     *
     * @param code 1 成功  0 失败
     *            存储状态
     * @param msg
     *            存储提示
     * @param data
     *            存储数据
     * @return
     */
    public static String ReturnData(Integer code, String msg, Object data) {
        // 返回数据
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 存储状态
        map.put("code", code);
        // 存储提示
        map.put("msg", msg);
        // 存储数据
        map.put("data", data);
        // 转换JSON
        return JSON.toJSONString(map);
    }

    /**
     * 返回layui表格数据模式处理公共方法
     *
     * @param code
     *            存储状态
     * @param msg
     *            存储提示
     * @param data
     *            存储数据
     * @param count
     *            存储总条数
     * @return
     */
    public static String ReturnDataPaging(String code, String msg, Object data, Object count) {
        // 返回数据
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 存储状态
        map.put("code", code);
        // 存储提示
        map.put("msg", msg);
        // 存储数据
        map.put("data", data);
        // 存储总条数
        map.put("count", count);
        // 转换JSON
        return JSON.toJSONString(map);
    }
}
