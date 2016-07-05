package com.chung.server;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * �ύ���洢��������̨
 */
public class SendAndStoreData {
	public static final int SENDSERVERERROR = 20;
	public static final int SENDINTERNETERROR = 21;
	public static final int DATAUPLOADSUCCEED = 22;
	// public static final int PHONEUPLOADSUCCEED = 23;
	private Handler handler;
	private String url;
	private String[] args = new String[20];
	private int num = 0;

	/**
	 * ���캯��
	 */
	public SendAndStoreData(Handler handler, String url, String[] args, int num) {
		this.handler = handler;
		this.url = url;
		this.num = num;
		for (int i = 0; i < num; i++) {
			this.args[i] = args[i];
			Log.i("arg[" + i + "]", args[i]);
		}
	}

	/**
	 * �ύ���ݵ�������
	 */
	public void saveDataToInternet() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(convertURL()).openConnection();
					conn.setConnectTimeout(3000);
					conn.setRequestMethod("GET");
					if (conn.getResponseCode() == 200) {
						Message msg = new Message();
						msg.what = DATAUPLOADSUCCEED; // ֪ͨUI�߳������ϴ����
						handler.sendMessage(msg);
						args = null;
						Log.i("DataUpload", "succeed");
					}else {
						Message msg = new Message();
						msg.what = SENDINTERNETERROR;// ֪ͨUI�̱߳����������
						handler.sendMessage(msg);
						args = null;
						Log.i("SEND INTERNET", "ERROR");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Message msg = new Message();
					msg.what = SENDSERVERERROR;// ֪ͨUI�̷߳������������
					handler.sendMessage(msg);
					args = null;
					Log.i("DataUpload", "error");
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * �����ύ��ַ
	 */
	public String convertURL() {
		String newUrl = url;
		for (int i = 0; i < num; i++) {
			try {
				if (i != 0) {
					newUrl += "&" + String.valueOf(i) + "=" + URLEncoder.encode(args[i], "utf-8");
				} else {
					newUrl += "?" + String.valueOf(i) + "=" + URLEncoder.encode(args[i], "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Log.i("NEWURL", newUrl);
		return newUrl;
	}

}