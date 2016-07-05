package com.chung.sosandcommunicate;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
	public static String RegistrationID;
	private static MyApplication application = null;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(this);
		
        JPushInterface.setDebugMode(true);//���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);// ��ʼ�� JPush
        RegistrationID = JPushInterface.getRegistrationID(this);
        Log.i("RegistrationID", RegistrationID);
		
	}

	public static MyApplication getApplication() {
		return application;
	}

}