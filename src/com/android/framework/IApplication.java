package com.android.framework;

import android.app.Application;

import com.android.framework.core.cache.ACache;
import com.android.framework.core.config.Config;
import com.android.framework.core.util.CrashHandler;
/**
 * application
 * 
 * @author pengjianbo
 *
 */
public class IApplication extends Application {
	
	private ACache mCache;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mCache = ACache.get(this, Config.PFILE_NAME);

        // 异常捕获处理
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
	}
	
	/**
	 * 获取缓存实例
	 * 
	 * @return
	 */
	public ACache getACache() {
		return mCache;
	}
	
}
