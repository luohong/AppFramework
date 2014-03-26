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
* $Id: StorageUtils.java 56 2013-09-11 16:37:02Z zhiyong $
* 
*/ 
package com.android.framework.core.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
/**
 * 存储获取工具
 * 
 * @author pengjianbo <pengjianbo@iboxpay.com>
 *
 */
public class StorageUtils {

    private static final String INDIVIDUAL_DIR_NAME = "uil-images";

    private StorageUtils() {
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted. Else - Android defines cache directory on
     * device's file system.
     * 
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * Returns individual application cache directory (for only image caching from ImageLoader). Cache directory will be
     * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is mounted. Else -
     * Android defines cache directory on device's file system.
     * 
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getIndividualCacheDirectory(Context context) {
        File cacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(cacheDir, INDIVIDUAL_DIR_NAME);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = cacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * Returns specified application cache directory. Cache directory will be created on SD card by defined path if card
     * is mounted. Else - Android defines cache directory on device's file system.
     * 
     * @param context Application context
     * @param cacheDir Cache directory path (e.g.: "AppCacheDir", "AppDir/cache/images")
     * @return Cache {@link File directory}
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }
}
