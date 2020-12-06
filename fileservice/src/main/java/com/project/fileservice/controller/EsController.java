package com.project.fileservice.controller;

import com.alibaba.fastjson.JSON;
import com.project.fileservice.pojo.DocMessage;
import com.project.fileservice.util.FormetSize;
import com.project.fileservice.util.WordByPDF;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class EsController {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引
    @GetMapping("wordByPDF")
    public String wordByPDF(){
        try{
            WordByPDF.wordByPDF("D:\\test\\new.doc","D:\\test\\new.pdf");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //创建索引
    @GetMapping("createIndex")
    public String createIndex(){
        try{
            //创建索引的请求
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("test");
            //请求完之后的相应
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //获取索引
    @GetMapping("getIndex")
    public String getIndex(){
        try{
            //获取索引的请求
            GetIndexRequest getIndexRequest =  new GetIndexRequest("test");
            //请求及请求相应的值(判断该索引存不存在)
            boolean nSign = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
            System.out.println(nSign);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //删除索引
    @GetMapping("deleteIndex")
    public String deleteIndex(){
        try{
            //删除索引请求
            DeleteIndexRequest deleteIndexRequest  = new DeleteIndexRequest("test");
            //请求及请求相应的值(判断该索引存不存在)
            AcknowledgedResponse  acknowledgedResponse= restHighLevelClient.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
            System.out.println(acknowledgedResponse.isAcknowledged());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //添加文档
    @GetMapping("addDocument")
    public String addDocument(){
        try{
            String strDocMessage = "周三(12月2日)欧市盘中，全球市场的大行情：金价持续上攻，最高升至1831.69美元/盎司，创下11月24日以来高位；美元指数跌势缓和，早前最低下挫至91.11。投资者风险情绪降温，欧美股市多回落，欧洲股市主要股指走低，美国股指期货也是震荡下跌。分析认为，在经历了11月的持续拉升之后，欧美股市回落，投资者谨慎情绪升温。尽管新冠疫苗再度爆出重磅消息，经济复苏预期回暖，以及美国政治局势明朗，再度引发新一轮财政刺激出炉预期，但是市场试图更加谨慎地押注。这背后可能在量大经济体国家之间的贸易关系有关";
            DocMessage oDocMessage = new DocMessage("测试文档","中美信息",strDocMessage,"D:\\titileTest");
            //声明连接索引
            IndexRequest indexRequest = new IndexRequest("test");
            //规则
            indexRequest.id("1");
            //超时设置(1s)
            indexRequest.timeout(TimeValue.timeValueSeconds(1));
            //将我们数据放入其中 json数据
            indexRequest.source(JSON.toJSONString(oDocMessage), XContentType.JSON);
            //客户端发送请求,获取响应的结果
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
            System.out.println(indexResponse.toString());
            //对应命令返回的状态
            System.out.println(indexResponse.status());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //获取文档是否存在 /get命令 get /index/doc/1
    @GetMapping("isExisit")
    public String testFileDocumentIsExisit(){
        try{
            GetRequest getRequest =  new GetRequest("test","1");
            //不获取返回的上下文，效率更高
            getRequest.fetchSourceContext(new FetchSourceContext(false));
            boolean  nSign = restHighLevelClient.exists(getRequest,RequestOptions.DEFAULT);
            System.out.println(nSign);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //获取文档内容
    @GetMapping("getDocument")
    public String getDocument(){
        try{
            GetRequest getRequest =  new GetRequest("test","1");
            GetResponse getResponse = restHighLevelClient.get(getRequest,RequestOptions.DEFAULT);
            System.out.println(getResponse.getSourceAsString());//打印文档的内容
            System.out.println(getResponse);//返回全部内容与命令相同
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //更新文档内容
    @GetMapping("updateDocument")
    public String updateDocument(){
        try{
            UpdateRequest updateRequest = new UpdateRequest("test","1");
            //配置超时时间
            updateRequest.timeout("1s");
            String strDocMessage = "营造富有科幻感的节日氛围，在这个冬季引领公众一道探索人与宇宙之间的关系……11月29日晚的成都远洋太古里热闹非凡，“留步·放想”冬季主题活动于漫广场正式拉开帷幕，五件充满科幻感的艺术作品正式向公众开放。近年来，成都的科幻氛围更加浓郁，产业基础更加扎实。成都市明确提出规划建设“中国科幻城”，积极推动科幻创作以及包括游戏、影视、动漫等在内的相关行业走向全国前列。“此次成都远洋太古里特别邀请青年新媒体艺术家钱泓霖，以艺术化的创新手法展现宇宙的星河灿烂、光影流转。”成都远洋太古里方面表示，“成都远洋太古里与艺术家合作，旨在通过艺术作品耀动的光芒以变幻的节奏传递来自宇宙的‘问候’，也点亮人们对美好未来的希望。";
            DocMessage oDocMessage = new DocMessage("测试文档01","科幻感的节日",strDocMessage,"D:\\titileTests");
            updateRequest.doc(JSON.toJSONString(oDocMessage),XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
            System.out.println(updateResponse.status());//更新成功
            System.out.println(updateResponse);//更新返回值
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }


    //删除文档内容
    @GetMapping("deleteDocument")
    public String deleteDocument(){
        try{
            DeleteRequest deleteRequest = new DeleteRequest("test","1");
            deleteRequest.timeout("1s");
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
            System.out.println(deleteResponse.status());//删除成功
            System.out.println(deleteResponse);//删除返回值
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //大批量请求同时放入(删除和更新本质不变，只是将请求变更)
    @GetMapping("addBlueDocument")
    public String addBlueDocument(){
        try{
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("10s");
            List<DocMessage> docMessageList = new ArrayList<>();
            String strDocMessage1 = "营造富有科幻感的节日氛围，在这个冬季引领公众一道探索人与宇宙之间的关系……11月29日晚的成都远洋太古里热闹非凡，“留步·放想”冬季主题活动于漫广场正式拉开帷幕，五件充满科幻感的艺术作品正式向公众开放。近年来，成都的科幻氛围更加浓郁，产业基础更加扎实。成都市明确提出规划建设“中国科幻城”，积极推动科幻创作以及包括游戏、影视、动漫等在内的相关行业走向全国前列。“此次成都远洋太古里特别邀请青年新媒体艺术家钱泓霖，以艺术化的创新手法展现宇宙的星河灿烂、光影流转。”成都远洋太古里方面表示，“成都远洋太古里与艺术家合作，旨在通过艺术作品耀动的光芒以变幻的节奏传递来自宇宙的‘问候’，也点亮人们对美好未来的希望。";
            DocMessage oDocMessage1 = new DocMessage("测试文档01","科幻感的节日",strDocMessage1,"D:\\titileTests");
            DocMessage oDocMessage2 = new DocMessage("测试文档02","科幻感的节日",strDocMessage1,"D:\\titileTests");
            DocMessage oDocMessage3 = new DocMessage("测试文档03","科幻感的节日",strDocMessage1,"D:\\titileTests");
            DocMessage oDocMessage4 = new DocMessage("测试文档04","科幻感的节日",strDocMessage1,"D:\\titileTests");
            docMessageList.add(oDocMessage1);
            docMessageList.add(oDocMessage2);
            docMessageList.add(oDocMessage3);
            docMessageList.add(oDocMessage4);
            for(int i = 0;i<docMessageList.size();i++){
                //批量更新和批量删除只有这个地方的区别
                bulkRequest.add(new IndexRequest("test")
                        .id("" + (i+1))
                        .source(JSON.toJSONString(docMessageList.get(i)),XContentType.JSON
                ));
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
            System.out.println(bulkResponse.hasFailures());//是否失败 false 代表成功，true代表失败

        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //查询文档内容
    //搜索请求
    //条件构造
    //SearchSourceBuilder 高亮
    @GetMapping("selectDocument")
    public String selectDocument(){
        try{
            SearchRequest searchRequest = new SearchRequest("test");
            //构建搜索的条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //精确匹配，可以使用QueryBuilders工具类快速匹配
            //QueryBuilders.matchPhraseQueryBuilder("strPath","D:\titileTests"); 精确匹配
            //QueryBuilders.matchAllQuery 匹配全部的查询
            //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();//匹配的查询条件，查询strTitle为测试文档01的
            //searchSourceBuilder.query(matchAllQueryBuilder);
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("strTitle","测试文档01");
            searchSourceBuilder.query(matchPhraseQueryBuilder);

            //分页
            //从多少页开始
            //searchSourceBuilder.from();
            //查多少条
            //searchSourceBuilder.size();
            //查询不超过60s
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse.getHits()));
            System.out.println("------------------------------------------");
            for(SearchHit searchHit:searchResponse.getHits().getHits()){
                System.out.println(searchHit.getSourceAsMap());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

    //查询文档内容,搜索高亮功能
    //搜索请求
    //条件构造
    //SearchSourceBuilder 高亮
    @GetMapping("selectDocumentHigh")
    public String selectDocumentHigh(){
        try{
            SearchRequest searchRequest = new SearchRequest("test");
            //构建搜索的条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //精确匹配，可以使用QueryBuilders工具类快速匹配
            //QueryBuilders.matchPhraseQueryBuilder("strPath","D:\titileTests"); 精确匹配
            //QueryBuilders.matchAllQuery 匹配全部的查询
            //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();//匹配全部的查询
            //searchSourceBuilder.query(matchAllQueryBuilder);
            MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("strTitle","01");
            searchSourceBuilder.query(matchPhraseQueryBuilder);

            //配置高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            //设置高亮的字段
            highlightBuilder.field("strTitle");
            //前缀标签
            highlightBuilder.preTags("<span style='color:red'>");
            //后缀标签
            highlightBuilder.postTags("</span>");
            //是否需要多个字段高亮
            highlightBuilder.requireFieldMatch(false);
            searchSourceBuilder.highlighter(highlightBuilder);


            //分页
            //从多少页开始
            //searchSourceBuilder.from();
            //查多少条
            //searchSourceBuilder.size();
            //查询不超过60s
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse.getHits()));
            System.out.println("------------------------------------------");
            List<Map<String,Object>> list = new ArrayList<>();
            for(SearchHit searchHit:searchResponse.getHits().getHits()){
                //解析高亮的字段
                Map<String, HighlightField> highlightField = searchHit.getHighlightFields();
                HighlightField title = highlightField.get("strTitle");

                Map<String,Object> stringObjectMap = searchHit.getSourceAsMap();//原来的结果
                //解析高亮的字段，将原来的字段换为高亮的字段即可
                if(title != null){
                    Text[] fragments  = title.fragments();
                    String strTitle = "";
                    for(Text fragment:fragments){
                        strTitle += fragment;
                    }
                    stringObjectMap.put("strTitle",strTitle);//高亮的替换原来的内容即可
                }
                list.add(stringObjectMap);
                System.out.println(searchHit.getSourceAsMap());
            }
            for(Map<String,Object> map : list){
                System.out.println(map.get("strTitle"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
