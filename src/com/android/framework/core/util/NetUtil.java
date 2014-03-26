package com.android.framework.core.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	
	private NetUtil(){
		
	}
	
	public static boolean enable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = cm.getActiveNetworkInfo();

		if (network != null) {
			return network.isAvailable();
		}

		return false;
	}
}
