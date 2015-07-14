package com.aimer.shd.webviewjs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
    /**
     * 检查网络状态
     *
     * @param conLastAppDownURL
     * @return
     */
    public static boolean isNetwork(Context conLastAppDownURL) {
        if (conLastAppDownURL != null) {
            ConnectivityManager manager = (ConnectivityManager)
                    conLastAppDownURL.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {

                return info.isAvailable();
            }
        }
        return false;

        // 上面仅仅是判断网络是否连接,而下面的方法是判断网络是否可用
//		ConnectivityManager cm = (ConnectivityManager) conLastAppDownURL
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (cm == null) {
//		} else {
//			// 如果仅仅是用来判断网络连接
//			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
//			NetworkInfo[] info = cm.getAllNetworkInfo();
//			if (info != null) {
//				for (int i = 0; i < info.length; i++) {
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;

    }


}
