/**
 * 
 */

package com.android.framework.core.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author Acher
 */
public class UiHelper {
    
    /**
     * 获取屏幕显示的宽度和高度
     * @param context 上下�?
     * @return 屏幕显示的宽度和高度
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
    
}
