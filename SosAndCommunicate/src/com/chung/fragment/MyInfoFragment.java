package com.chung.fragment;

import com.chung.sosandcommunicate.InfoFiveActivity;
import com.chung.sosandcommunicate.InfoFourActivity;
import com.chung.sosandcommunicate.InfoOneActivity;
import com.chung.sosandcommunicate.InfoSixActivity;
import com.chung.sosandcommunicate.InfoThreeActivity;
import com.chung.sosandcommunicate.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyInfoFragment extends BaseFragment {

	private RelativeLayout info1;
	private RelativeLayout info2;
	private RelativeLayout info3;
	private RelativeLayout info4;
	private RelativeLayout info5;
	private RelativeLayout info6;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myinfo, container, false);
		info1 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_introduce);
		info1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
				Intent intent1 = new Intent(getActivity(),InfoOneActivity.class);
				startActivity(intent1);
			}
		});
		
		info2 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_invite);
		info2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
//				Intent intent2 = new Intent(getActivity(),InfoTwoActivity.class);
//				startActivity(intent2);
				
				Intent intent = new Intent(Intent.ACTION_SEND);
			     intent.setType("text/plain"); // ���ı� 
			     intent.putExtra(Intent.EXTRA_SUBJECT, "�������");
			     intent.putExtra(Intent.EXTRA_TEXT, "# ������ʹ�ó��������������ϵͳ���ǳ����ã��������԰ɣ����ص�ַwww.qwer.cn #");  
			     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			     startActivity(Intent.createChooser(intent, "����"));
				
			}
		});
		
		info3 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_check_update);
		info3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
				Intent intent3 = new Intent(getActivity(),InfoThreeActivity.class);
				startActivity(intent3);
			}
		});
		
		info4 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_recommend);
		info4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
				Intent intent4 = new Intent(getActivity(),InfoFourActivity.class);
				startActivity(intent4);
			}
		});
		
		info5 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_comment);
		info5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
				Intent intent5= new Intent(getActivity(),InfoFiveActivity.class);
				startActivity(intent5);
			}
		});
		
		info6 = (RelativeLayout) view.findViewById(R.id.rl_myinfo_aboutAuthor);
		info6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(v);
				Intent intent6 = new Intent(getActivity(),InfoSixActivity.class);
				startActivity(intent6);
			}
		});
		
		return view;
	}

	public void showDialog(View v) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setIcon(R.drawable.wxts);// ���öԻ���ͼ��
		alertDialog.setTitle("��ܰ��ʾ");// ���ñ���
		alertDialog.setMessage("��ҳ��������ͣ�������ڰ�ҹ�����У����ʼ������֣�");// ��������

		/***** ���ð�ť *****/
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "���ͼ���",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast toast = Toast.makeText(getActivity(),"������лл�㣡", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getActivity().getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.smile);
						toastView.addView(imageCodeProject, 0);
						toast.show();
					}
				});
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "�������",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast toast = Toast.makeText(getActivity(),"�Ҷ��������", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = (LinearLayout) toast.getView();
						ImageView imageCodeProject = new ImageView(getActivity().getApplicationContext());
						imageCodeProject.setImageResource(R.drawable.cry);
						toastView.addView(imageCodeProject, 0);
						toast.show();
					}
				});
		alertDialog.show();// �����Ի�����ʾ
	}
	
}
