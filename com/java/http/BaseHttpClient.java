package com.pingan.finance.risk.info.common.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by EX_WLJR_ZHANGXUEQ on 2018/2/1.
 */
@Component
public class BaseHttpClient implements IHttpClient {

    protected Logger logger = LoggerFactory.getLogger(BaseHttpClient.class);

    @Override
    public <T> T doPost(String jsonParam, String url, Class<T> clazz) throws RuntimeException {
        logger.info("HttpClient URL: {}, Parameters: {}", url, jsonParam);
        logger.info("访问URL:" + url);

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        T bean = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            // 构造消息头
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            post.setHeader("Connection", "Close");

            // 构建消息实体
            StringEntity entity = new StringEntity(jsonParam, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);

            response = httpClient.execute(post);
            if (null != response && null != response.getStatusLine()) {
                // 检验返回码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("HttpClient status: " + statusCode);
                    throw new RuntimeException("HttpClient status is not OK!");
                }
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("HttpClient result: {}", result);
                bean = JSONObject.parseObject(result, clazz);
            }
        } catch (Exception e) {
            logger.error("HttpClient invoke error! URL: {}", url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("Response close error!", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("HttpClient close error!", e);
                }
            }
        }
        return bean;
    }

    @Override
    public <T> T doGet(String jsonParam, String url, Class<T> clazz) throws RuntimeException {

        logger.info("访问URL:" + url);

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        T bean = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet post = new HttpGet(url);
            // 构造消息头
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            post.setHeader("Connection", "Close");

            response = httpClient.execute(post);
            if (null != response && null != response.getStatusLine()) {
                // 检验返回码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("HttpClient status: " + statusCode);
                    throw new RuntimeException("HttpClient status is not OK!");
                }
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("HttpClient result: {}", result);
                bean = JSONObject.parseObject(result, clazz);
            }
        } catch (Exception e) {
            logger.error("HttpClient invoke error! URL: {}", url, e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("Response close error!", e);
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("HttpClient close error!", e);
                }
            }
        }
        return bean;

    }
}
