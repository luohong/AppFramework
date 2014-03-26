package com.android.framework.core.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.framework.R;

public class ImageFetcherFactory {
	
	public static ImageFetcher getImageFetcher(FragmentActivity activity) {
		// Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        final DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        final int width = displayMetrics.widthPixels;
        final int height = displayMetrics.heightPixels;

        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(activity, "image");
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        ImageFetcher mImageFetcher = new ImageFetcher(activity, width, height);
        mImageFetcher.addImageCache(activity.getSupportFragmentManager(), cacheParams);
        mImageFetcher.setImageFadeIn(false);
        mImageFetcher.setLoadingImage(R.drawable.ic_launcher);
		
		return mImageFetcher;
	}
	
	public static DisplayMetrics getDisplayMetrics(Context context) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		
		WindowManager wm = (WindowManager)context.getSystemService(Activity.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		return outMetrics;
	}
	
}
