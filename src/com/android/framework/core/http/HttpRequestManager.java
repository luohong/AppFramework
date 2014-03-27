/* 
* Copyright (C) 2011 pengjianbo iBoxPay Information Technology Co.,Ltd. 
* 
* All right reserved. 
* 
* This software is the confidential and proprietary 
* information of iBoxPay Company of China. 
* ("Confidential Information"). You shall not disclose 
* such Confidential Information and shall use it only 
* in accordance with the terms of the contract agreement 
* you entered into with iBoxpay inc. 
* 
* $Id: HttpRequestManager.java 616 2014-02-14 13:09:56Z zhiyong $
* 
*/ 
package com.android.framework.core.http;

import org.apache.http.HttpEntity;

import android.text.TextUtils;

import com.android.framework.core.config.Config;
import com.android.framework.core.util.Debug;

/**
 * 接口请求管理类 <p/>
 * 
 * 采用登记单例模式
 * 
 * @author pengjianbo <pengjianbo@iboxpay.com>
 *
 */
public class HttpRequestManager {
    /**
     * 内部有线程池，采用的是 Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）
     */
    private static AsyncHttpClient apiHttpClient;
    
    private static AsyncHttpClient fileHttpClient;
    
    private static class HttpApiManagerHolder {
        private static final HttpRequestManager instance = new HttpRequestManager();
    }
    
    private HttpRequestManager() {
        apiHttpClient = new AsyncHttpClient();
        apiHttpClient.setTimeout(Config.HTTP_TIMEOUT);
        
        fileHttpClient = new AsyncHttpClient();
        fileHttpClient.setTimeout(Config.HTTP_TIMEOUT);
        
    }
    
    public static HttpRequestManager getInstance() {
        return HttpApiManagerHolder.instance;
    }
    
    /**
     * 添加网络请求
     * @param url 请求地址
     * @param method 请求类型   参考{@link HttpMethod}
     * @param params 回调参数   参考 {@link RequestParams}
     * @param handler 请求参数  参考{@link JsonHttpResponseHandler}
     */
    public void requestApi(String url, HttpMethod method, RequestParams params, AsyncHttpResponseHandler handler) {
        
    		Debug.Log("HttpRequestManager-----url = " + url);
        
        if ( TextUtils.isEmpty(url) ) {
            throw new NullPointerException("URL不能为空");
        }
        
         if ( method == HttpMethod.GET  ) { //GET请求
             apiHttpClient.get(url, params, handler);
         } else {
             apiHttpClient.post(url, params, handler);
         }
    }
    
    /**
     * /**
     * 添加网络请求
     * @param url 请求地址
     * @param method 请求类型   参考{@link HttpMethod}
     * @param params 回调参数   参考 {@link RequestParams}
     * @param entiry 
     * @param handler 请求参数  参考{@link JsonHttpResponseHandler}
     */
    public void requestApi(String url, HttpMethod method, RequestParams params, HttpEntity entiry, AsyncHttpResponseHandler handler) {
        
    		Debug.Log("HttpRequestManager-----url=" + url);
        
        if ( TextUtils.isEmpty(url) ) {
            throw new NullPointerException("URL不能为空");
        }
        
         if ( method == HttpMethod.GET  ) { //GET请求
             apiHttpClient.get(url, params, handler);
         } else {
             apiHttpClient.post(url, params, handler);
         }
    }
    
    /**
     * 下载图片
     * @param url 图片地址
     * @param handler 回调
     */
    public void downloadImage(String url, BinaryHttpResponseHandler handler) {
        fileHttpClient.get(url, handler);
    }
    
    public void downloadFile(String url, AsyncHttpResponseHandler handler) {
        fileHttpClient.get(url, handler);
    }
    
    /**
     * 终止接口请求，从请求集合对象中移除并且推出HTTP连接
     * @param url 接口地址
     */
    public void quitApiRequest(String url) {
        apiHttpClient.delete(url, new AsyncHttpResponseHandler());
    }
    
    /**
     * 终止文件请求，从请求集合对象中移除并且推出HTTP连接
     * @param url 文件地址
     */
    public void quitFileRequest(String url) {
        fileHttpClient.delete(url, new AsyncHttpResponseHandler());
    }
}
