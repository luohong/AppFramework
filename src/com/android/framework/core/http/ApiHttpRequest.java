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
* $Id: ApiHttpRequest.java 616 2014-02-14 13:09:56Z zhiyong $
* 
*/ 
package com.android.framework.core.http;

import com.android.framework.core.config.Config;
import com.android.framework.core.util.Debug;


public class ApiHttpRequest {
	
	public static final String TAG = ApiHttpRequest.class.getSimpleName();
    
    /**
     * 带参数POST请求
     * @param action
     * @param params 回调参数   
     * @param handler 请求参数  参考{@link ApiRequestHandler}
     */
    public static void requestApiByPost( String action, RequestParams params, ApiRequestHandler handler) {
        String url = Config.getUrl(action);
        Debug.Log("http params  = " +params.toString());
        HttpRequestManager.getInstance().requestApi(url, HttpMethod.POST, params, handler);
    }
    
    /**
     * 不带参数POST请求
     * @param action 请求方法       参考{@link Config}
     * @param handler 请求参数  参考{@link ApiRequestHandler}
     */
    public static void requestApiByPost( String action, ApiRequestHandler handler) {
        String url = Config.getUrl(action);
        RequestParams params = new RequestParams();
        HttpRequestManager.getInstance().requestApi(url, HttpMethod.POST, params, handler);
    }
    
    /**
     * 带参数GET请求
     * @param action 请求方法       参考{@link Config}
     * @param params 回调参数   
     * @param handler 请求参数  参考{@link ApiRequestHandler}
     */
    public static void requestApiByGet( String action, RequestParams params, ApiRequestHandler handler) {
        String url = Config.getUrl(action);
        Debug.Log("http params = " +params.toString());
        HttpRequestManager.getInstance().requestApi(url, HttpMethod.POST, params, handler);
    }
    
    /**
     * 不带参数GET请求
     * @param action 请求方法       参考{@link Config}
     * @param handler 请求参数  参考{@link ApiRequestHandler}
     */
    public static void requestApiByGet( String action, ApiRequestHandler handler) {
        String url = Config.getUrl(action);
        RequestParams params = new RequestParams();
        HttpRequestManager.getInstance().requestApi(url, HttpMethod.POST, params, handler);
    }
    
    public static void requestApiByGet1(String url, ApiRequestHandler handler) {
        RequestParams params = new RequestParams();
        HttpRequestManager.getInstance().requestApi(url, HttpMethod.POST, params, handler);
    }

}
