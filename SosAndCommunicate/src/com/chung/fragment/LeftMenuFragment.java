package com.chung.fragment;

import java.io.File;

import com.chung.sosandcommunicate.R;
import com.chung.server.SendAndStoreData;
import com.chung.sosandcommunicate.AccountActivity;
import com.chung.sosandcommunicate.ChangeAccountActivity;
import com.chung.sosandcommunicate.CommunicateActivity;
import com.chung.sosandcommunicate.CreditActivity;
import com.chung.sosandcommunicate.HistoryActivity;
import com.chung.sosandcommunicate.LoginActivity;
import com.chung.sosandcommunicate.MainActivity;
import com.chung.sosandcommunicate.SettingsActivity;
import com.chung.sosandcommunicate.SosActivity;
import com.chung.tools.HeadPicShowerActivity;
import com.chung.tools.HeadPicToCircle;
import com.chung.tools.SlidingMenuTool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LeftMenuFragment extends Fragment implements OnClickListener {
	public final static Intent sosIntent = new Intent();
	// public static final String settingsURL="http://192.168.23.1:80/0/index.php/Settings/settings/";//���URL
	// public static final String settingsURL="http://10.0.3.2:80/0/index.php/Settings/settings/";//GenymotionURL
	public static final String settingsURL = LoginActivity.SAE_URL + "index.php/Settings/settings/";// SAE URL

//	public static boolean LOGOUTFLAG = false;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	
	public static String arg0;
	
	// private static final String TAG = "LeftMenuFragment";
	private static boolean togglebtnFlag = false;
	public static ImageView left_menu_headPic;
	private TextView tv_name;
	private TextView tv_sign;
	private ToggleButton tbnight;
	private String[] args = new String[4];

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SendAndStoreData.SENDINTERNETERROR:
				Toast toast1 = Toast.makeText(getActivity(),"δ��ͬ������������������������", Toast.LENGTH_SHORT);
				toast1.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView1 = (LinearLayout) toast1.getView();
				ImageView imageCodeProject1 = new ImageView(getActivity().getApplicationContext());
				imageCodeProject1.setImageResource(R.drawable.cry);
				toastView1.addView(imageCodeProject1, 0);
				toast1.show();
				break;
			case SendAndStoreData.SENDSERVERERROR:
				Toast toast2 = Toast.makeText(getActivity(),"δ��ͬ������������������������", Toast.LENGTH_SHORT);
				toast2.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView2 = (LinearLayout) toast2.getView();
				ImageView imageCodeProject2 = new ImageView(getActivity().getApplicationContext());
				imageCodeProject2.setImageResource(R.drawable.cry);
				toastView2.addView(imageCodeProject2, 0);
				toast2.show();
				break;
			case SendAndStoreData.DATAUPLOADSUCCEED:

				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.slide_menu_fragment, container, false);
		sharedPreferences = getActivity().getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		
		File headPIC = new File(getActivity().getFilesDir(),sharedPreferences.getString("studentID", null) + ".png");
		left_menu_headPic = (ImageView) view.findViewById(R.id.left_menu_headPic);
		if(headPIC.canRead()){
			left_menu_headPic.setImageBitmap(HeadPicToCircle.toRoundBitmap(BitmapFactory.decodeFile(headPIC.getPath())));
		}else {
			left_menu_headPic.setImageResource(R.drawable.user);
		}
		
		tv_name = (TextView) view.findViewById(R.id.tv_left_menu_name);
		tv_sign = (TextView) view.findViewById(R.id.tv_left_menu_sign);
		tbnight = (ToggleButton) view.findViewById(R.id.tb_night);

		arg0 = sharedPreferences.getString("studentID", null);// ����studentID
		args[0] = arg0;
		
		tv_name.setText(sharedPreferences.getString("name", null) + sharedPreferences.getString("goalsRank", null));
		tv_sign.setText(sharedPreferences.getString("sign", null));

		if (sharedPreferences.getInt("tbnight", 0) == 1) {
			togglebtnFlag = true;
			tbnight.setChecked(togglebtnFlag);
			sosIntent.setAction("com.chung.sosandcommunicate.ONEKEYSOS_SERVICE");
			getActivity().startService(sosIntent);
			Log.i("onekeysos-service", "started");
		} else {
			togglebtnFlag = false;
			tbnight.setChecked(togglebtnFlag);
		}
		Log.i("leftMenuData", "setSucceed");

		args[1] = "0";
		args[2] = "tbnight";
		getAndSetViews(view);

		return view;
	}

	private void getAndSetViews(View view) {

		view.findViewById(R.id.left_menu_headPic).setOnClickListener(this);
		view.findViewById(R.id.tv_left_menu_name).setOnClickListener(this);
		view.findViewById(R.id.tv_left_menu_sign).setOnClickListener(this);
		view.findViewById(R.id.left_menu1).setOnClickListener(this);
		view.findViewById(R.id.left_menu2).setOnClickListener(this);
		view.findViewById(R.id.left_menu3).setOnClickListener(this);
		view.findViewById(R.id.left_menu4).setOnClickListener(this);
		view.findViewById(R.id.left_menu5).setOnClickListener(this);
		view.findViewById(R.id.left_menu6).setOnClickListener(this);
		view.findViewById(R.id.tb_night).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.left_menu_headPic:
			intent = new Intent(getActivity(),HeadPicShowerActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
			break;
		case R.id.tv_left_menu_name:
			intent = new Intent(getActivity(), AccountActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.tv_left_menu_sign:
			intent = new Intent();
			intent.setClass(getActivity(), ChangeAccountActivity.class);
			intent.putExtra("TVName", "�޸�ǩ��");
			startActivityForResult(intent, AccountActivity.CHANGE_REQUEST_CODE);// ��ʾ���Է��ؽ��
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu1:
			intent = new Intent();
			intent.putExtra("studentID", arg0);
			intent.setClass(getActivity(), SosActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu2:
			intent = new Intent();
			intent.putExtra("studentID", arg0);
			intent.setClass(getActivity(), CommunicateActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu3:
			intent = new Intent(getActivity(), HistoryActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu4:
			intent = new Intent(getActivity(), CreditActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu5:
			intent = new Intent(getActivity(), SettingsActivity.class);
			startActivity(intent);
			SlidingMenuTool.toggleSlidingMenu();
			getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.left_menu6:
			SendAndStoreData sendAndStoreData1 = new SendAndStoreData(handler, LoginActivity.SAE_URL+"index.php/Login/logout/", args, 1);
			sendAndStoreData1.saveDataToInternet();//�޸ķ������˵�¼״̬
			LoginActivity.LOGINFLAG1 = false;//���û����½״̬
			LoginActivity.LOGINFLAG2 = false;//���û����½״̬
//			LOGOUTFLAG = true;//����ע��״̬
			editor.putString("password", "");
			editor.commit();
			intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().stopService(MainActivity.serviceIntent);
			getActivity().finish();//ע��
			break;
		case R.id.tb_night:
			if (!togglebtnFlag) {
				togglebtnFlag = true;
				args[3] = "1";
				/*����ҡ������*/
				sosIntent.setAction("com.chung.sosandcommunicate.ONEKEYSOS_SERVICE");
				getActivity().startService(sosIntent);
				Log.i("onekeysos-service", "started");
				/**********/
				Toast.makeText(getActivity(), "ҡ�����������ȣ����������ڴ��ֶ��ر�", Toast.LENGTH_LONG).show();
				SendAndStoreData sendAndStoreData2 = new SendAndStoreData(handler, settingsURL, args, 4);
				sendAndStoreData2.saveDataToInternet();
			} else {
				togglebtnFlag = false;
				args[3] = "0";
				/*�رշ���*/
				getActivity().stopService(sosIntent);
				/**/
				Toast.makeText(getActivity(), "����ģʽ�ѹر�", Toast.LENGTH_SHORT).show();
				SendAndStoreData sendAndStoreData = new SendAndStoreData(handler, settingsURL, args, 4);
				sendAndStoreData.saveDataToInternet();
			}
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { // Intent����ֵ�Ļص�����
		if (resultCode != AccountActivity.RESULT_CANCELED) {// ����벻����ȡ��ʱ
			if (requestCode == 1) {
				tv_sign.setText(data.getExtras().getString("changedSign"));// �����޸ĺ��sign
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
