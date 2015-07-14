package com.aimer.shd.webviewjs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
    /**
     * �������״̬
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

        // ����������ж������Ƿ�����,������ķ������ж������Ƿ����
//		ConnectivityManager cm = (ConnectivityManager) conLastAppDownURL
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (cm == null) {
//		} else {
//			// ��������������ж���������
//			// �����ʹ�� cm.getActiveNetworkInfo().isAvailable();
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
