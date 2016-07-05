package com.chung.sosandcommunicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.sosandcommunicate.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		TextView tv_head = (TextView) findViewById(R.id.head_name);
		tv_head.setText("��ʷ��¼");

		// ���Layout�����ListView
		ListView list = (ListView) findViewById(R.id.history_list);
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, getData(),
				R.layout.history_list_item, new String[] { "history_img",
						"history_username", "history_theme", "history_info" },
				new int[] { R.id.history_img, R.id.history_username,
						R.id.history_theme, R.id.history_info });
		// ��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);

		// ��ӵ�������
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv_username = (TextView) view
						.findViewById(R.id.history_username);
				String history_username = tv_username.getText().toString();
				Log.i("HistoryActivity", history_username);
				TextView tv_theme = (TextView) view
						.findViewById(R.id.history_theme);
				String history_theme = tv_theme.getText().toString();
				Log.i("HistoryActivity", history_theme);
				Toast.makeText(HistoryActivity.this,
						history_username + "," + history_theme,
						Toast.LENGTH_SHORT).show();

			}

		});

	}

	// ���ɶ�ά��̬���飬����������
	private List<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("history_img", R.drawable.ylc);
		map.put("history_username", "Ҷ����");
		map.put("history_theme", "��賵");
		map.put("history_info",
				"��������λ������С��¿�ܽ�����һ��м���������λ������С��¿�ܽ�����һ��м���������λ������С��¿�ܽ�����һ��м�������");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.zrt);
		map.put("history_username", "������");
		map.put("history_theme", "���Ǯ");
		map.put("history_info",
				"���������ͷ̫��������Ǯ�ɴ�Ƿ�����æ�����ͷ̫��������Ǯ�ɴ�Ƿ�����æ�����ͷ̫��������Ǯ�ɴ�Ƿ�����æ��");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.ncmm);
		map.put("history_username", "�̲�����");
		map.put("history_theme", "����������");
		map.put("history_info",
				"������������������·����ô�ƽⰡ��������������������·����ô�ƽⰡ��������������������·����ô�ƽⰡ������");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("history_img", R.drawable.frjj);
		map.put("history_username", "ܽ�ؽ��");
		map.put("history_theme", "����G20");
		map.put("history_info",
				"����G20�Ǹ�ʲô��Ϊɶ��ţ������ô��G20�Ǹ�ʲô��Ϊɶ��ţ������ô��G20�Ǹ�ʲô��Ϊɶ��ţ������ô�ࡣ");
		listitem.add(map);

		return listitem;
	}

}
