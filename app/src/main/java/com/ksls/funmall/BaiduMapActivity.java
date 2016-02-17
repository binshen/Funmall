package com.ksls.funmall;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.ksls.funmall.base.BaseActivity;

//==>经纬度查询：http://map.yanue.net/
public class BaiduMapActivity extends BaseActivity implements OnGetGeoCoderResultListener {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private BitmapDescriptor mIconMaker;
	private GeoCoder mSearch;
	
	private String name = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidu_map);

		initHeader();
		setUpViews();
	}
	
	private void setUpViews() {
		header.getTitle().setText("百度地图");
				
		mMapView = (MapView) findViewById(R.id.id_bmapView);

		mBaiduMap = mMapView.getMap();
		mIconMaker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		//MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
		MapStatus status = new MapStatus.Builder().target(new LatLng(31.387544,120.952436)).zoom(16.0f).build();
		MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(status);
		mBaiduMap.setMapStatus(msu);
		
		name = getIntent().getExtras().getString("name");
		double latitude = getIntent().getExtras().getDouble("latitude");
		double longitude = getIntent().getExtras().getDouble("longitude");
		if(latitude > 0 && longitude > 0) {
			loadBaiduMap(latitude, longitude);
		} else {
			mSearch = GeoCoder.newInstance();
			mSearch.setOnGetGeoCodeResultListener(this);
			mSearch.geocode(new GeoCodeOption().city("昆山").address(name));
		}
	}
	
	private void initMarkerClickEvent() {

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(final Marker marker) {
				TextView location = new TextView(getApplicationContext());
				location.setBackgroundResource(R.drawable.location_tips);
				location.setPadding(30, 20, 30, 50);
				location.setText(name);
				Point p = mBaiduMap.getProjection().toScreenLocation(marker.getPosition());
				p.y -= 47;
				LatLng latitude = mBaiduMap.getProjection().fromScreenLocation(p);
//				InfoWindow mInfoWindow = new InfoWindow(location, latitude, new OnInfoWindowClickListener() {
//					@Override
//					public void onInfoWindowClick() {
//						mBaiduMap.hideInfoWindow();
//					}
//				});
//				mBaiduMap.showInfoWindow(mInfoWindow);
				return true;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		if(mSearch != null) {
			mSearch.destroy();
		}
		mIconMaker.recycle();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(BaiduMapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
			return;
		}
		double latitude = result.getLocation().latitude;
		double longitude = result.getLocation().longitude;
		loadBaiduMap(latitude, longitude);
	}

	private void loadBaiduMap(double latitude, double longitude) {
		mBaiduMap.clear();
		LatLng latLng = new LatLng(latitude, longitude);
		OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(mIconMaker).zIndex(5);
		mBaiduMap.addOverlay(overlayOptions);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(u);
		
		initMarkerClickEvent();
	}
	
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		
	}
}