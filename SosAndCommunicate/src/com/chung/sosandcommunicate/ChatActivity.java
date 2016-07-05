package com.chung.sosandcommunicate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chung.baidumap.LocationDemo;
import com.chung.tools.ChatMsgEntity;
import com.chung.tools.ChatMsgViewAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("SimpleDateFormat")
public class ChatActivity extends Activity implements OnClickListener {
	private static final String URL="http://api.map.baidu.com/staticimage/v2";
	private static final String ak="mKvKvkKj9nMHc5TKTptz9YSmh2sQkSIx";
	private static final String mcode="D8:2E:44:BD:DE:77:FD:4D:B5:AB:5C:FC:BF:28:40:3D:56:E3:6B:1A;com.chung.sosandcommunicate";
	private static final String markericon = "http://sosandcommunicate-public.stor.sinaapp.com/markeicon.png";
	private static final int CAMERA_REQUEST_CODE = 0;
	private static final int IMAGE_REQUEST_CODE = 1;
	private static final int LOCATION_REQUEST_CODE = 2;
	
	private ImageButton chat_vedio;
	private ImageButton chat_pic;
	private ImageButton chat_location;
	private LinearLayout ll_more;
	private Button mBtnMore;// ���btn
	private Button mBtnSend;// ����btn
//	private Button mBtnBack;// ����btn
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// ��Ϣ��ͼ��Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// ��Ϣ��������

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		initView();// ��ʼ��view

		initData();// ��ʼ������
		mListView.setSelection(mAdapter.getCount() - 1);
	}

	/**
	 * ��ʼ��view
	 */
	public void initView() {
		chat_vedio = (ImageButton) findViewById(R.id.chat_vedio);
		chat_pic = (ImageButton) findViewById(R.id.chat_pic);
		chat_location = (ImageButton) findViewById(R.id.chat_location);
		
		ll_more = (LinearLayout) findViewById(R.id.ll_more);
		
		mListView = (ListView) findViewById(R.id.listview);
		
		mBtnMore = (Button) findViewById(R.id.btn_more);
		mBtnMore.setOnClickListener(this);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
//		mBtnBack = (Button) findViewById(R.id.btn_back);
//		mBtnBack.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		
	}

	private String[] msgArray = new String[] {
			"��ô���㣿", 
			"Ҫ��������£�����Ҵ�����", 
			"�аɣ������ģ�������λ����", 
			"�õ�", };

	private String[] dataArray = new String[] { 
			"2016-05-17 18:52:57",
			"2016-05-17 18:55:11", 
			"2016-05-17 18:56:45",
			"2016-05-17 18:57:33", };
	private final static int COUNT = 4;// ��ʼ����������

	/**
	 * ģ�������Ϣ��ʷ��ʵ�ʿ������Դ����ݿ��ж���
	 */
	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("������");
				entity.setMsgType(true);// �յ�����Ϣ
			} else {
				entity.setName("��");
				entity.setMsgType(false);// �Լ����͵���Ϣ
			}
			entity.setMessage(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_more:// ��Ӱ�ť����¼�
			more();
			break;
		case R.id.btn_send:// ���Ͱ�ť����¼�
			send();
			break;
//		case R.id.btn_back:// ���ذ�ť����¼�
//			finish();// ����,����������
//			break;
		case R.id.chat_vedio:// ������ť����¼�
//			Toast.makeText(ChatActivity.this, "vedio", Toast.LENGTH_SHORT).show();
			Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
			break;
		case R.id.chat_pic:// ͼƬ��ť����¼�
//			Toast.makeText(ChatActivity.this, "pic", Toast.LENGTH_SHORT).show();
			Intent intentFromGallery = new Intent();
			intentFromGallery.setType("image/*"); // �����ļ�����
			intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
			break;
		case R.id.chat_location:// ��λ��ť����¼�
//			Toast.makeText(ChatActivity.this, "location", Toast.LENGTH_SHORT).show();
			ll_more.setVisibility(View.GONE);
			Intent intent=new Intent();
			intent.setClass(ChatActivity.this,LocationDemo.class);
			startActivityForResult(intent, LOCATION_REQUEST_CODE);
			break;
		}
	}

	
	private void more(){
		if(ll_more.getVisibility()==View.GONE){
			ll_more.setVisibility(View.VISIBLE);
			chat_vedio.setOnClickListener(this);
			chat_pic.setOnClickListener(this);
			chat_location.setOnClickListener(this);
		}else if(ll_more.getVisibility()==View.VISIBLE){
			ll_more.setVisibility(View.GONE);
		}
	}
	
	/**
	 * ������Ϣ
	 */
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("�ذ�");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setMsgType(false);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// ֪ͨListView�������ѷ����ı�

			mEditTextContent.setText("");// ��ձ༭������

//			mListView.setSelection(mListView.getCount() - 1);// ����һ����Ϣʱ��ListView��ʾѡ�����һ��
			mListView.setSelection(mListView.getBottom());
		}
	}

	/**
	 * ������Ϣʱ����ȡ��ǰ�¼�
	 * 
	 * @return ��ǰʱ��
	 */
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			
			if (requestCode == LOCATION_REQUEST_CODE) {
				String center = data.getStringExtra("center");
				String name = data.getStringExtra("name");
				Log.i("ChatActivity->center", center);
				Log.i("ChatActivity->name", name);
				String locationURL = URL + "?ak=" + ak + "&mcode=" + mcode + "&center=" + center + "&width=300&height=200&zoom=18&markers=" + center + "&markerStyles=-1,"+markericon+",-1";
				Log.i("ChatActivity->locationURL", locationURL);
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setName("��");
				entity.setDate(getDate());
				entity.setMessage(locationURL);
				entity.setMsgType(false);
				
				mDataArrays.add(entity);
				mAdapter.notifyDataSetChanged();// ֪ͨListView�������ѷ����ı�
//				mListView.setSelection(mListView.getCount() - 1);// ����һ����Ϣʱ��ListView��ʾѡ�����һ��
				mListView.setSelection(mListView.getBottom());
			}
		}
		
		
	}
	
}