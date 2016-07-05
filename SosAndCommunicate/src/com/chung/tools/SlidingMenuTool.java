package com.chung.tools;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;

import com.chung.fragment.LeftMenuFragment;
import com.chung.slidingmenu.lib.SlidingMenu;
import com.chung.slidingmenu.lib.anim.CustomAnimation;
import com.chung.slidingmenu.lib.app.SlidingFragmentActivity;
import com.chung.sosandcommunicate.R;

public class SlidingMenuTool {
	
	private static SlidingMenu sm;
	/**
	 * @param fm
	 * @param sl �����activityӦ�ü̳д���
	 * @param mainactivity ,Ҫ��Ӳ໬menu��activity
	 */
	public static void slidingMenuView(FragmentManager fm,SlidingFragmentActivity sl,Activity mainactivity){
		
		sm = sl.getSlidingMenu();
		// ����
		sm.setBackgroundColor(Color.rgb(37, 37, 37));
		// ��Ӱ
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.slide_menu_shadow);
		// ƫ��
		DisplayMetrics metrics = new DisplayMetrics();
		mainactivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		if(metrics.widthPixels > 0) {
			// ��Դ���ã��ڲ�ͬ�ֱ��ʣ��ܻ��г��ֱ�Ť�Ļ��ͣ�
			// ����ͨ����Ļʵ�ʿ�ȣ�����������ƫ�ƣ����磺�ƽ����
			sm.setBehindOffset((int) (metrics.widthPixels * 0.382));
		} else {
			// ͨ����Դ����ƫ��
			sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		}
		// ���ò໬������
		sm.setBehindCanvasTransformer((new CustomAnimation()).getCustomZoomAnimation());
		
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//.TOUCHMODE_FULLSCREEN);
		sm.setMode(SlidingMenu.LEFT);
		
		// ������໬������
		LeftMenuFragment lmf = new LeftMenuFragment();
		sl.setBehindContentView(R.layout.slide_menu_frame);
		fm.beginTransaction().replace(R.id.menu_frame, lmf).commit();
	
	}
	
	// �л�SlidingMenu��״̬
	public static void toggleSlidingMenu() {
	        sm.toggle();// �л�״̬����ʾʱ���أ�����ʱ��ʾ
	}
	
}
