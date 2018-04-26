package com.pingan.finance.risk.info.common.http;

/**
 * Created by EX_WLJR_ZHANGXUEQ on 2018/2/1.
 */
public interface IHttpClient {

    <T> T doPost(String jsonParam, String url, Class<T> clazz) throws RuntimeException;

    <T> T doGet(String jsonParam, String url, Class<T> clazz) throws RuntimeException;
}
