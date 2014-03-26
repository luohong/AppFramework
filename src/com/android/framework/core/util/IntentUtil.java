package com.android.framework.core.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

public class IntentUtil {
    
    /**
     * 
     * 从外部存储�?SD卡�?选择图片
     * 
     */
    public static final int PICK_PHOTO_FROM_EXT_STORAGE = 0;
    
    /**
     * 
     * 相机拍照
     * 
     */
    public static final int TAKE_PHOTO_FROM_CAMERA = 1;
    
    /**
     * 
     * 图片裁剪
     * 
     */
    public static final int CROP_PHOTO = 1002;
    
    public static final int CROP_USER_PHOTO = 1003;
    
    public static final String IMAGE_TYPE = "image/*";
    
    
    public static final String ACTION_CROP = "com.android.camera.action.CROP";
    
    
    public static final String IMAGE_MEDIA = "content://media/external/images/media/";
    
	
	private IntentUtil() {
		
	}
	
	public static void redirect(Context context, Class<?> cls, boolean finishSelf, Bundle bundle){
		Intent it = new Intent();
		it.setClass(context, cls);
		if ( bundle != null ){
			it.putExtras(bundle);
		}
		
		context.startActivity(it);
		
		if ( finishSelf ) {
			Activity activity = (Activity) context;
			activity.finish();
		}
	}

	public static void openUrl(Context context, String url){
		
		if (!TextUtils.isEmpty(url)) {
			if ((!url.startsWith("http://") && !url
					.startsWith("https://"))) {
				url = "http://" + url;
			}
		} else {
			return ;
		}

		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url.replace(" ","")));
		context.startActivity(it);
	}
	
	   /**
     * 
     * 从存储设备上获取图片
     * 
     * @param context
     */
    public static void pickPhotoFromStorage(Context context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_TYPE);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        ((Activity) context).startActivityForResult(intent, PICK_PHOTO_FROM_EXT_STORAGE);
    }
    
    /**
     * 
     * 启动照相机拍�?     * 
     * @param context
     * @param uri
     */
    public static void takePhotoByCamera(Context context, Uri uri) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity) context).startActivityForResult(intent, TAKE_PHOTO_FROM_CAMERA);
    }
    
    /**
     * 启动图片裁剪
     * @param context
     * @param requestCode
     * @param data
     * @param srcFile
     * @param dstFile
     */
    public static void cropPhoto(Context context, int requestCode, Intent data, String srcFile, String dstFile, int resultCode, int width, int height) {
        Intent intent = new Intent(ACTION_CROP);
        Uri imageUri = null;
        
        if (data != null) {
            imageUri = data.getData();
        }
        
        if (requestCode == 1) {
            if (imageUri == null) {
                imageUri = Uri.fromFile(new File(srcFile));
            }
        }
        
        if (imageUri != null) {
            if (imageUri.toString().startsWith("file")) {
                String fileName = imageUri.getPath();
                long id = SDcardUtil.getImageIdFromFilePath(context, fileName);
                if (id <= 0) {
                    context.sendBroadcast(
                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
                    
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    
                    id = SDcardUtil.getImageIdFromFilePath(context, fileName);
                }
                
                if (id > 0) {
                    imageUri = Uri.parse(IMAGE_MEDIA + id);
                }
            }
        }
        

        FileUtil.parentFolder(dstFile);
        intent.setDataAndType(imageUri, IMAGE_TYPE);
        intent.putExtra("crop", true); // crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("scale", true);
        intent.putExtra("output", Uri.fromFile(new File(dstFile)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //返回格式
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", false);
        ((Activity) context).startActivityForResult(intent, resultCode);
    }
}
