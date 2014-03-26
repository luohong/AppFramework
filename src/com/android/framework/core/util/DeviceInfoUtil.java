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
 * $Id$
 * 
 */
package com.android.framework.core.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

/**
 * 设备信息相关工具�?
 * 
 * @author pengjianbo <pengjianbo@iboxpay.com>
 * 
 */
public class DeviceInfoUtil {

	/**
	 * 获取屏幕大小正确方法，注意不要�?过Application来获取屏幕大�?
	 * <p/>
	 * 
	 * <B>待测�?/B>
	 * 
	 * @param a
	 * @return
	 */
	public static int[] getScreenSize(Activity a) {
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenWidth = (int) (dm.widthPixels /** dm.density + 0.5f */
		);// 屏幕宽（px，如�?80px�?
		int screenHeight = (int) (dm.heightPixels/* * dm.density + 0.5f */);// 屏幕高（px，如�?00px�?

		return new int[] { screenWidth, screenHeight };
	}

	public static String uriConvert2path(Context c, Uri uri) {
		String imagePath = null;
		String uriStr = uri.toString();
		if (uriStr.startsWith("file://")) {
			imagePath = uriStr.replaceFirst("file://", "");
		} else {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = c.getContentResolver().query(uri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			imagePath = cursor.getString(column_index);
			cursor.close();
		}
		return imagePath;
	}

}
