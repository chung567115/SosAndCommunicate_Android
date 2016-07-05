package com.chung.server;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

	// ��λ���
	LocationClient mLocClient;
	public MyLocationListener myListener = new MyLocationListener();

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	private final String TAG = "LocationService";

	// ����Ҫʵ�ֵķ���
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind����������!");
		return null;
	}

	// Service������ʱ����
	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate����������!");
		super.onCreate();

		sharedPreferences = getSharedPreferences("LOCATIONINFO",Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		
		// ��λ��ʼ��
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);

		initLocation();
	}
	
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		option.setScanSpan(10000);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
//		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
//		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
//		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
//		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	/**
	 * ��λSDK��������
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null) {
				return;
			}else {
				List<Poi> list = location.getPoiList();// POI����
				if (list != null) {
					int index = 0;
					editor.putString("addr", location.getAddrStr());
					for (Poi p : list) {
						editor.putString("place" + String.valueOf(index), p.getName());
						editor.putString("describe" + String.valueOf(index), 
								location.getProvince()+" �� "
								+location.getCity()+" �� "
								+location.getDistrict()+" �� "
								+location.getStreet()
								+location.getStreetNumber());
						index++;
					}
					editor.commit();
				}
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {}

	}

	// Service������ʱ����
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand����������!");
		return super.onStartCommand(intent, flags, startId);
	}

	// Service���ر�֮ǰ�ص�
	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestory����������!");
		super.onDestroy();
	}
}