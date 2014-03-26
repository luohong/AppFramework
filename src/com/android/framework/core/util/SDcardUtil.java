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
* $Id: SDcardUtil.java 599 2014-02-07 06:15:24Z zhiyong $
* 
*/ 
package com.android.framework.core.util;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
/**
 * SD Card相关工具�?
 * 
 * @author pengjianbo <pengjianbo@iboxpay.com>
 *
 */
public class SDcardUtil {

    /**
     * 判断SD卡是否存�?
     * @return
     */
    public static boolean checkSdCardEnable() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); 
    }
    
    /**
     * 获取SD卡根目录
     * @return
     */
    public static File getRoot() {
        File file = Environment.getExternalStorageDirectory();
        return file;
    }
    
    /**
     * 获取SD卡剩余的容量大小
     * 
     * @return
     */
    public static long getFreeSpace() {
        File root = getRoot();
        StatFs stat = new StatFs(root.getPath());
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks;
    }

    /**
     * 从相册获取图片是获取图片的真实路�?不同手机有两种方�?
     * 
     * @param cxt
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    public static String getAbsoluteFilePath(Context cxt, Uri uri) {
        String path = "unknown";
        String schema = uri.getScheme().toString();
        if (schema.startsWith("content")) {
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(DocumentsContract.isDocumentUri(cxt, uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];  //wholeID = "image:xxxx"
                    String[] column = { MediaStore.Images.Media.DATA };
                    cursor = cxt.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                            MediaStore.Images.Media._ID + "=?", new String[] { id }, null);
                } else {
                    CursorLoader loader = new CursorLoader(cxt, uri, proj, null, null, null);
                    cursor = loader.loadInBackground();
                }
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                CursorLoader loader = new CursorLoader(cxt, uri, proj, null, null, null);
                cursor = loader.loadInBackground();
            } else {
                cursor = ((Activity) cxt).managedQuery(uri, proj, null, null, null);
            }
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(columnIndex);
        } else if (schema.startsWith("file")) {
            path = uri.getLastPathSegment().toString();
        } else {
            path = path + "_" + uri.getLastPathSegment();
        }

        return path;
    }

    
    /**
     * 
     * 根据文件路径获取图片在数据库中的ID
     * 
     * @param cxt
     * @param filePath
     * @return
     */
    public static long getImageIdFromFilePath(Context cxt, String filePath) {

        long id = 0;

        // This returns us content://media/external/videos/media (or something like that)
        // I pass in "external" because that's the MediaStore's name for the external
        // storage on my device (the other possibility is "internal")
        Uri imageUri = MediaStore.Images.Media.getContentUri("external");
        Cursor cursor = null;
        
        
        ContentResolver contentResolver = cxt.getContentResolver();
        String[] projection = { MediaStore.Images.Media._ID };
        
        // TODO This will break if we have no matching item in the MediaStore.
       
        cursor = contentResolver.query(imageUri, projection, MediaStore.Images.Media.DATA + " LIKE ?",
            new String[] { filePath }, null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                id = cursor.getLong(columnIndex); 
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    
        return id;
    }
    
}
