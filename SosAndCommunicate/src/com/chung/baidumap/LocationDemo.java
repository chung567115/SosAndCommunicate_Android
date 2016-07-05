package com.chung.baidumap;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.chung.baidumap.BaiduMapUtilByRacer.GeoCodePoiListener;
import com.chung.baidumap.BaiduMapUtilByRacer.LocateListener;
import com.chung.baidumap.BaiduMapUtilByRacer.PoiSearchListener;
import com.chung.sosandcommunicate.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * ��demo����չʾ��ν�϶�λSDKʵ�ֶ�λ����ʹ��MyLocationOverlay���ƶ�λλ�� ͬʱչʾ���ʹ���Զ���ͼ����Ʋ����ʱ��������
 * 
 */
public class LocationDemo extends Activity {
	private static Context mContext;
	private LocationBean mLocationBean;
	// ��λpoi������Ϣ����Դ
	private List<PoiInfo> aroundPoiList;
	private AroundPoiAdapter mAroundPoiAdapter;
	// ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	private Marker mMarker = null;
	// ������ǰ����poi����Դ
	private static List<LocationBean> searchPoiList;
	private SearchPoiAdapter mSearchPoiAdapter;
	private BaiduMap mBaiduMap;
	// �ؼ�
	private MapView mMapView;
	private EditText etMLCityPoi;
	private TextView tvShowLocation;
	private LinearLayout llMLMain;
	private ListView lvAroundPoi, lvSearchPoi;
	private ImageView ivMLPLoading;
//	private Button btMapZoomIn, btMapZoomOut;
	private ImageButton ibMLLocate;
	// ���R
	public static final int SHOW_MAP = 0;
	private static final int SHOW_SEARCH_RESULT = 1;
	// ��ʱ������diss��dialog
	private static final int DELAY_DISMISS = 1000 * 30;

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview_location_poi);
		this.mContext = this;
		ibMLLocate = (ImageButton) findViewById(R.id.ibMLLocate);
		etMLCityPoi = (EditText) findViewById(R.id.etMLCityPoi);
		tvShowLocation = (TextView) findViewById(R.id.tvShowLocation);
		lvAroundPoi = (ListView) findViewById(R.id.lvPoiList);
		lvSearchPoi = (ListView) findViewById(R.id.lvMLCityPoi);
		ivMLPLoading = (ImageView) findViewById(R.id.ivMLPLoading);
//		btMapZoomIn = (Button) findViewById(R.id.btMapZoomIn);
//		btMapZoomOut = (Button) findViewById(R.id.btMapZoomOut);
		llMLMain = (LinearLayout) findViewById(R.id.llMLMain);
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.mMapView);
		BaiduMapUtilByRacer.goneMapViewChild(mMapView, true, true);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
		mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
		mBaiduMap.setOnMapClickListener(mapOnClickListener);
		mBaiduMap.getUiSettings().setZoomGesturesEnabled(false);// ��������
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		locate();
		iniEvent();
	}

	// ��ʾ��ͼ������������������
	private void showMapOrSearch(int index) {
		if (index == SHOW_SEARCH_RESULT) {
			llMLMain.setVisibility(View.GONE);
			lvSearchPoi.setVisibility(View.VISIBLE);
		} else {
			lvSearchPoi.setVisibility(View.GONE);
			llMLMain.setVisibility(View.VISIBLE);
			if (searchPoiList != null) {
				searchPoiList.clear();
			}
		}
	}

	public void locate() {
		BaiduMapUtilByRacer.locateByBaiduMap(mContext, 2000,
				new LocateListener() {

					@Override
					public void onLocateSucceed(LocationBean locationBean) {
						mLocationBean = locationBean;
						if (mMarker != null) {
							mMarker.remove();
						} else {
							mBaiduMap.clear();
						}
						mMarker = BaiduMapUtilByRacer.showMarkerByResource(
								locationBean.getLatitude(),
								locationBean.getLongitude(), R.drawable.point,
								mBaiduMap, 0, true);
					}

					@Override
					public void onLocateFiled() {

					}

					@Override
					public void onLocating() {

					}
				});
	}

	public void getPoiByPoiSearch() {
		BaiduMapUtilByRacer.getPoiByPoiSearch(mLocationBean.getCity(),
				etMLCityPoi.getText().toString().trim(), 0,
				new PoiSearchListener() {

					@Override
					public void onGetSucceed(List<LocationBean> locationList,
							PoiResult res) {
						if (etMLCityPoi.getText().toString().trim().length() > 0) {
							if (searchPoiList == null) {
								searchPoiList = new ArrayList<LocationBean>();
							}
							searchPoiList.clear();
							searchPoiList.addAll(locationList);
							updateCityPoiListAdapter();
						}
					}

					@Override
					public void onGetFailed() {
						Toast.makeText(mContext, "��Ǹ��δ���ҵ����",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	public void reverseGeoCode(LatLng ll, final boolean isShowTextView) {
		BaiduMapUtilByRacer.getPoisByGeoCode(ll.latitude, ll.longitude,
				new GeoCodePoiListener() {

					@Override
					public void onGetSucceed(LocationBean locationBean,
							List<PoiInfo> poiList) {
						mLocationBean = (LocationBean) locationBean.clone();
//						Toast.makeText(
//								mContext,
//								mLocationBean.getProvince() + "-"
//										+ mLocationBean.getCity() + "-"
//										+ mLocationBean.getDistrict() + "-"
//										+ mLocationBean.getStreet() + "-"
//										+ mLocationBean.getStreetNum(),
//								Toast.LENGTH_SHORT).show();
						// mBaiduMap.setMapStatus(MapStatusUpdateFactory
						// .newLatLng(new LatLng(locationBean
						// .getLatitude(), locationBean
						// .getLongitude())));
						if (isShowTextView) {
							tvShowLocation.setText(locationBean.getLocName());
						}
						if (aroundPoiList == null) {
							aroundPoiList = new ArrayList<PoiInfo>();
						}
						aroundPoiList.clear();
						if (poiList != null) {
							aroundPoiList.addAll(poiList);
						} else {
							Toast.makeText(mContext, "ԓ��߅�]�П��c",
									Toast.LENGTH_SHORT).show();
						}
						updatePoiListAdapter(aroundPoiList, -1);
					}

					@Override
					public void onGetFailed() {
						Toast.makeText(mContext, "��Ǹ��δ���ҵ����",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	private void iniEvent() {
		etMLCityPoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etMLCityPoi.getText().toString().trim().length() > 0) {
					getPoiByPoiSearch();
				}
			}
		});
		etMLCityPoi.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int start, int before,
					int count) {
				if (cs.toString().trim().length() > 0) {
					getPoiByPoiSearch();
				} else {
					if (searchPoiList != null) {
						searchPoiList.clear();
					}
					showMapOrSearch(SHOW_MAP);
					hideSoftinput(mContext);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		ibMLLocate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				locate();
			}
		});
//		btMapZoomIn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				isCanUpdateMap = false;
//				BaiduMapUtilByRacer.zoomInMapView(mMapView);
//			}
//		});
//		btMapZoomOut.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				isCanUpdateMap = false;
//				BaiduMapUtilByRacer.zoomOutMapView(mMapView);
//			}
//		});
		lvAroundPoi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				isCanUpdateMap = false;
				BaiduMapUtilByRacer.moveToTarget(
						aroundPoiList.get(arg2).location.latitude,
						aroundPoiList.get(arg2).location.longitude, mBaiduMap);
				tvShowLocation.setText(aroundPoiList.get(arg2).name);
				String Latitude = String.valueOf(aroundPoiList.get(arg2).location.latitude);
				String Longitude = String.valueOf(aroundPoiList.get(arg2).location.longitude);
				Log.i("chat->Latitude", Latitude);
				Log.i("chat->Longitude", Longitude);
				Log.i("chat", aroundPoiList.get(arg2).name);
				Intent intent=new Intent();
			    intent.putExtra("center", Longitude+","+Latitude);
			    intent.putExtra("name", aroundPoiList.get(arg2).name);
			    setResult(RESULT_OK, intent);
				finish();
			}
		});
		lvSearchPoi.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// Geo����
				// mGeoCoder.geocode(new GeoCodeOption().city(
				// searchPoiList.get(arg2).getCity()).address(
				// searchPoiList.get(arg2).getLocName()));
				hideSoftinput(mContext);
				isCanUpdateMap = false;
				BaiduMapUtilByRacer.moveToTarget(searchPoiList.get(arg2)
						.getLatitude(), searchPoiList.get(arg2).getLongitude(),
						mBaiduMap);
				tvShowLocation.setText(searchPoiList.get(arg2).getLocName());
				// ��Geo����
				reverseGeoCode(new LatLng(searchPoiList.get(arg2).getLatitude(), searchPoiList.get(arg2).getLongitude()), false);
//				Log.i("chat->Latitude", searchPoiList.get(arg2).getLatitude().toString());
//				Log.i("chat->Longitude", searchPoiList.get(arg2).getLongitude().toString());
//				Log.i("chat", "????????????");
				if (ivMLPLoading != null
						&& ivMLPLoading.getVisibility() == View.GONE) {
					loadingHandler.sendEmptyMessageDelayed(1, 0);
				}
				showMapOrSearch(SHOW_MAP);
			}
		});
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		if (llMLMain.getVisibility() == View.GONE) {
			showMapOrSearch(SHOW_MAP);
		} else {
			this.finish();
		}
	};

	OnMapClickListener mapOnClickListener = new OnMapClickListener() {
		/**
		 * ��ͼ�����¼��ص�����
		 * 
		 * @param point
		 *            ����ĵ�������
		 */
		public void onMapClick(LatLng point) {
			hideSoftinput(mContext);
		}

		/**
		 * ��ͼ�� Poi �����¼��ص�����
		 * 
		 * @param poi
		 *            ����� poi ��Ϣ
		 */
		public boolean onMapPoiClick(MapPoi poi) {
			return false;
		}
	};
	private boolean isCanUpdateMap = true;
	OnMapStatusChangeListener mapStatusChangeListener = new OnMapStatusChangeListener() {
		/**
		 * ���Ʋ�����ͼ�����õ�ͼ״̬�Ȳ������µ�ͼ״̬��ʼ�ı䡣
		 * 
		 * @param status
		 *            ��ͼ״̬�ı俪ʼʱ�ĵ�ͼ״̬
		 */
		public void onMapStatusChangeStart(MapStatus status) {
		}

		/**
		 * ��ͼ״̬�仯��
		 * 
		 * @param status
		 *            ��ǰ��ͼ״̬
		 */
		public void onMapStatusChange(MapStatus status) {
		}

		/**
		 * ��ͼ״̬�ı����
		 * 
		 * @param status
		 *            ��ͼ״̬�ı������ĵ�ͼ״̬
		 */
		public void onMapStatusChangeFinish(MapStatus status) {
			if (isCanUpdateMap) {
				LatLng ptCenter = new LatLng(status.target.latitude,
						status.target.longitude);
				// ��Geo����
				reverseGeoCode(ptCenter, true);
				if (ivMLPLoading != null
						&& ivMLPLoading.getVisibility() == View.GONE) {
					loadingHandler.sendEmptyMessageDelayed(1, 0);
				}
			} else {
				isCanUpdateMap = true;
			}
		}
	};
	private static Animation hyperspaceJumpAnimation = null;
	@SuppressLint("HandlerLeak")
	Handler loadingHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0: 
				// if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				// mLoadingDialog.dismiss();
				// // showToast(mActivity.getString(R.string.map_locate_fault),
				// // DialogType.LOAD_FAILURE);
				// }
				if (ivMLPLoading != null) {
					ivMLPLoading.clearAnimation();
					ivMLPLoading.setVisibility(View.GONE);
				}
				break;
			
			case 1: 
				// ���ض���
				hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
						mContext, R.anim.dialog_loading_animation);
				lvAroundPoi.setVisibility(View.GONE);
				ivMLPLoading.setVisibility(View.VISIBLE);
				// ʹ��ImageView��ʾ����
				ivMLPLoading.startAnimation(hyperspaceJumpAnimation);
				if (ivMLPLoading != null
						&& ivMLPLoading.getVisibility() == View.VISIBLE) {
					loadingHandler.sendEmptyMessageDelayed(0, DELAY_DISMISS);
				}
				break;
			
			default:
				break;
			}
		}
	};

	/**
	 * ���������
	 * 
	 * @param view
	 */
	private void hideSoftinput(Context mContext) {
		InputMethodManager manager = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (manager.isActive()) {
			manager.hideSoftInputFromWindow(etMLCityPoi.getWindowToken(), 0);
		}
	}

	// ˢ�����ŵ����б�����adapter
	private void updatePoiListAdapter(List<PoiInfo> list, int index) {
		ivMLPLoading.clearAnimation();
		ivMLPLoading.setVisibility(View.GONE);
		lvAroundPoi.setVisibility(View.VISIBLE);
		if (mAroundPoiAdapter == null) {
			mAroundPoiAdapter = new AroundPoiAdapter(mContext, list);
			lvAroundPoi.setAdapter(mAroundPoiAdapter);
		} else {
			mAroundPoiAdapter.setNewList(list, index);
		}
	}

	// ˢ�µ�ǰ������Ȥ�ص��б�����adapter
	private void updateCityPoiListAdapter() {
		if (mSearchPoiAdapter == null) {
			mSearchPoiAdapter = new SearchPoiAdapter(mContext, searchPoiList);
			lvSearchPoi.setAdapter(mSearchPoiAdapter);
		} else {
			mSearchPoiAdapter.notifyDataSetChanged();
		}
		showMapOrSearch(SHOW_SEARCH_RESULT);
	}

	@Override
	protected void onDestroy() {
		mLocationBean = null;
		lvAroundPoi = null;
		lvSearchPoi = null;
//		btMapZoomIn.setBackgroundResource(0);
//		btMapZoomIn = null;
//		btMapZoomOut.setBackgroundResource(0);
//		btMapZoomOut = null;
		ibMLLocate.setImageBitmap(null);
		ibMLLocate.setImageResource(0);
		ibMLLocate = null;
		if (aroundPoiList != null) {
			aroundPoiList.clear();
			aroundPoiList = null;
		}
		mAroundPoiAdapter = null;
		if (searchPoiList != null) {
			searchPoiList.clear();
			searchPoiList = null;
		}
		mSearchPoiAdapter = null;
		if (mBaiduMap != null) {
			mBaiduMap.setMyLocationEnabled(false);// �رն�λͼ��
			mBaiduMap = null;
		}
		if (mMapView != null) {
			mMapView.destroyDrawingCache();
			mMapView.onDestroy();
			mMapView = null;
		}
		if (etMLCityPoi != null) {
			etMLCityPoi.setBackgroundResource(0);
			etMLCityPoi = null;
		}
		mMarker = null;
		super.onDestroy();
		System.gc();
	}
}
