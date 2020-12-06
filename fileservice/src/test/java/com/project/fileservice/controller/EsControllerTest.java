package com.project.fileservice.controller;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class EsControllerTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    private void createTest(){
        try{
            //创建索引的请求
            CreateIndexRequest test = new CreateIndexRequest("Test");
            //请求完之后的相应
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(test, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}