package com.chung.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chung.sosandcommunicate.ChatActivity;
import com.chung.sosandcommunicate.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MessageFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message, container, false);

		// ���Layout�����ListView
		ListView list = (ListView) view.findViewById(R.id.message_list);
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.message_list_item,
				new String[] { "img", "username", "theme", "info" },
				new int[] { R.id.message_img, R.id.message_username, R.id.message_theme, R.id.message_info });
		// ��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);
		// ��ӵ�������
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(),ChatActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}

	private List<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.outbox);
		map.put("username", "Ҷ����");
		map.put("theme", "");
		map.put("info", "������Ҷ���������Ͱ�������");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.inbox);
		map.put("username", "Ҷ����");
		map.put("theme", "");
		map.put("info", "��Ҷ�������ѽ������İ�������");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.outbox);
		map.put("username", "�̲�����");
		map.put("theme", "");
		map.put("info", "�Ա�У԰���ƽ�·����");
		listitem.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.inbox);
		map.put("username", "������");
		map.put("theme", "");
		map.put("info", "�õ�");
		listitem.add(map);

		return listitem;
	}

}
