package org.example.aidemo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HttpClient {
    @Test
    public void getTest()throws Exception{
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/test/get/hello");
        //发送请求,接受相应结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("状态码："+statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity);
        System.out.println("响应内容："+string);
        //关闭资源
        response.close();
        httpClient.close();

    }
    @Test
    public void postTest()throws Exception{
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/test/post/hello");
//        JsonObject jsonObject = new JsonObject();
        StringEntity entitys=new StringEntity("{\n" +
                "  \"name\": \"string\",\n" +
                "  \"id\": \"string\",\n" +
                "  \"content\": \"string\",\n" +
                "  \"code\": \"string\"\n" +
                "}");
        entitys.setContentType("application/json");
        entitys.setContentEncoding("utf-8");
        httpPost.setEntity(entitys);
        CloseableHttpResponse execute = aDefault.execute(httpPost);
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println("状态码："+statusCode);
        HttpEntity entity = execute.getEntity();
        String string = EntityUtils.toString(entity);
        System.out.println("响应内容："+string);

    }
}
