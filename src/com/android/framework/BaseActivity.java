package com.android.framework;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.android.framework.core.cache.ACache;
import com.android.framework.core.image.ImageFetcher;
import com.android.framework.core.image.ImageFetcherFactory;

public abstract class BaseActivity extends FragmentActivity {

    public ACache mACache;
    
    public ImageFetcher mImageFetcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        mImageFetcher = ImageFetcherFactory.getImageFetcher(this);

        IApplication app = (IApplication) this.getApplication();
        mACache = app.getACache();
	}
	
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        
        //调用顺序
        onInit();
        onFindViews();
        onInitViewData();
        onBindListener();
    }
    
    public OnClickListener mBackClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	}; 

	/**
     * 初始化
     * 优先顺序：<br/>
     * <font color=red>onInit();</font><br/>
     * onFindViews();<br/>
     * onInitViewData();<br/>
     * onBindListener();<br/>
     */
    public void onInit(){}
    
    /**
     * 查找控件 <br/>
     * 优先顺序：<br/>
     * onInit();<br/>
     * <font color=red>onFindViews();</font><br/>
     * onInitViewData();<br/>
     * onBindListener();<br/>
     */
    public void onFindViews() {}
    
    /**
     * 初始化控件内容
     * 优先顺序：<br/>
     * onInit();<br/>
     * onFindViews();<br/>
     * <font color=red>onInitViewData();</font><br/>
     * onBindListener();<br/>
     */
    public void onInitViewData(){}
    
    /**
     * 注册控件事件
     * 优先顺序：<br/>
     * onInit();<br/>
     * onFindViews();<br/>
     * onInitViewData();<br/>
     * <font color=red>onBindListener();</font><br/>
     */
    public void onBindListener(){}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if (mImageFetcher != null) {
    		mImageFetcher.setExitTasksEarly(false);
    	}
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	if (mImageFetcher != null) {
    		mImageFetcher.setExitTasksEarly(true);
    		mImageFetcher.flushCache();
    	}
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if (mImageFetcher != null) {
    		mImageFetcher.closeCache();
    	}
    }
    
}
