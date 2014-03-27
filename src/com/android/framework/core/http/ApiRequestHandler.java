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
* $Id: ApiRequestHandler.java 623 2014-03-09 13:59:50Z zhiyong $
* 
*/ 
package com.android.framework.core.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.framework.core.util.Debug;
/**
 * API 请求处理类
 * 
 * @author pengjianbo <pengjianbo@iboxpay.com>
 *
 */
public abstract class ApiRequestHandler extends AsyncHttpResponseHandler{
    
	public static final String TAG = ApiRequestHandler.class.getSimpleName();
	
	private Context context;
	
	public ApiRequestHandler() {
	}
	
	public ApiRequestHandler( Context context ) {
		this.context = context;
	}
	
    @Override
    public void onSuccess(String content) {
        super.onSuccess(content);
        
        try {
        	if ( content == null ) {
        		content = " null";
        	}
        	
        	Debug.Log("http result" + content);
        	
            JSONObject json = new JSONObject(content);
            int status = json.getInt("status");
            JSONObject result = json.getJSONObject("result");
            
            if ( status == 200 ) {
                handlerSuccess(result);
            } else {
                String msg = result.getString("errormsg");
                if (!TextUtils.isEmpty(msg)) {
                		handlerFailure(status, msg);
                }
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
            handlerFailure(-1, null);
        }
    }
    
    @Override
    public void onFailure(Throwable error, String content) {
        super.onFailure(error, content);
        handlerFailure(-1, null);
    }
    
    /**
     * 请求成果
     * 
     * @param result
     */
    public abstract void handlerSuccess( JSONObject result);
    
    /**
     * 请求失败
     * 
     * @param status 等于-1请求网络失败
     * 
     * @param msg 请求失败显示的内容，如果msg为空则显示网络异常,
     * 
     */
    public void handlerFailure(int status, String msg){
    	if ( context != null ) {
    		if (status == -1) {
    			//Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT).show();
    		} else {
    			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    		}
    	}
    }
}
