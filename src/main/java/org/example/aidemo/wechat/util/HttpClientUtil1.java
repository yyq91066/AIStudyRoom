package org.example.aidemo.wechat.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientUtil1 {
    public static HttpEntity doGet(String url) throws Exception{
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse execute = aDefault.execute(httpGet);

        int statusCode = execute.getStatusLine().getStatusCode();
        HttpEntity entity = execute.getEntity();

        execute.close();
        aDefault.close();
        return entity;

    }
}
