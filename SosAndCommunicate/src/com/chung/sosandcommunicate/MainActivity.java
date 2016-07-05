package com.chung.sosandcommunicate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.chung.fragment.BaseFragment;
import com.chung.fragment.FindFragment;
import com.chung.fragment.HomeFragment;
import com.chung.fragment.MessageFragment;
import com.chung.fragment.MyInfoFragment;
import com.chung.slidingmenu.lib.app.SlidingFragmentActivity;
import com.chung.sosandcommunicate.R;
import com.chung.tools.SlidingMenuTool;

public class MainActivity extends SlidingFragmentActivity implements OnClickListener {
	public final static Intent serviceIntent = new Intent();
	
	private static final String TAG = "MainActivity";
	private SparseArray<BaseFragment> navigateMap = new SparseArray<BaseFragment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SDKInitializer.initialize(getApplicationContext());
		
		setContentView(R.layout.activity_main);

		serviceIntent.setAction("com.chung.sosandcommunicate.LOCATION_SERVICE");
		startService(serviceIntent);
		Log.i("location-service", "started");
		
		FragmentManager fm = getSupportFragmentManager();
		
		// ��Ӳ໬�˵�
		SlidingMenuTool.slidingMenuView(fm, this, this);

		// ���ѡ�
		intiFragment(fm);
	}
	
	
	/**
	 * ��ʼ��ѡ�
	 */
	private void intiFragment(FragmentManager fm) {
		navigateMap.clear();
		mapNaviToFragment(R.id.home_tag_id, new HomeFragment()); // ��ҳ
		mapNaviToFragment(R.id.message_tag_id, new MessageFragment()); // ��Ϣ
		mapNaviToFragment(R.id.find_tag_id, new FindFragment()); // ��λ
		mapNaviToFragment(R.id.myinfo_tag_id, new MyInfoFragment()); // ����

		// ������ҳĬ����ʾ
		hideorshow(fm, R.id.home_tag_id);
	}

	/**
	 * ��ʼ�������˵�
	 * ����view ID
	 */
	private void mapNaviToFragment(int id, BaseFragment fragment) {
		View view = findViewById(id);
		view.setOnClickListener(this);
		view.setSelected(false);
		navigateMap.put(id, fragment);
	}


	/**
	 * ��ʾ������fragment
	 */
	private void hideorshow(FragmentManager fm, int id) {
		Log.i(TAG, "replaceFragment EntryCount: " + fm.getBackStackEntryCount()+ " size: ");
		String tag = String.valueOf(id);
		FragmentTransaction trans = fm.beginTransaction();
		if (null == fm.findFragmentByTag(tag)) {
			TextView tv_head = (TextView) findViewById(R.id.head_name);
			switch (id) {
			case R.id.home_tag_id:
				tv_head.setText("�ס�ҳ");
				break;
			case R.id.message_tag_id:
				tv_head.setText("����Ϣ");
				break;
			case R.id.find_tag_id:
				tv_head.setText("����λ");
				break;
			case R.id.myinfo_tag_id:
				tv_head.setText("�ء���");
				break;
			}
			trans.replace(R.id.contentframe, navigateMap.get(id), tag);
		} else {
			trans.show(navigateMap.get(id));
		}
		trans.commit();
		// ���õ���ѡ��״̬
		for (int i = 0, size = navigateMap.size(); i < size; i++) {
			int curId = navigateMap.keyAt(i);
			Log.i(TAG, "curId: " + curId);
			if (curId == id) {
				findViewById(id).setSelected(true);
			} else {
				findViewById(curId).setSelected(false);
			}
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		if (navigateMap.indexOfKey(id) >= 0) {
			if (!view.isSelected()) {
				// ��ǰ��ѡ��״̬�����л���������
				hideorshow(getSupportFragmentManager(), id);
			} else {
				Log.i(TAG, "ignore --- selected !!!");
			}
		}
	}

	private long mExitTime;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				LoginActivity.LOGINFLAG1 = false;//���û����½״̬
				LoginActivity.LOGINFLAG2 = false;//���û����½״̬
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//	}

}
