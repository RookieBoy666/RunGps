package com.bravo.rungps.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bravo.rungps.R;


public class SearchLocationActivity extends AppCompatActivity{
    private LocationClient mLocationClient = null;
    private BaiduMap mBaiduMap = null;
    private GeoCoder geocoder;
    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_searchlocation);

        mLocationClient = new LocationClient(this);
        // 获取百度地图的视图控件
        MapView baidumapview = (MapView) findViewById(R.id.baidumv);

        mBaiduMap = baidumapview.getMap();
        // 设置地图指针
        mBaiduMap.setMyLocationEnabled(true);

        // 处理百度地图开源项目的定位偏差
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");

        mLocationClient.setLocOption(option);

        MyLocationListenner myListenner = new MyLocationListenner();
        mLocationClient.registerLocationListener(myListenner);
        mLocationClient.start();

        geocoder = GeoCoder.newInstance();

        geocoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult location) {
            }

            @Override
            public void onGetGeoCodeResult(GeoCodeResult location) {
                // 根据输入地址来获得经纬度
                LatLng ll = location.getLocation();
                // 获取精度纬度完成定位
                MyLocationData mMyLocationData = new MyLocationData.Builder().latitude(ll.latitude)
                        .longitude(ll.longitude).build();

                // 设置定位球的位置
                mBaiduMap.setMyLocationData(mMyLocationData);
                // 移动定位球到中心位置

                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(msu);

            }
        });

        et1 = (EditText) findViewById(R.id.et1);

        et2 = (EditText) findViewById(R.id.et2);

        Button btn = (Button) findViewById(R.id.sousuo);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (et1.getText().toString().equals("") || et2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入地址", 0).show();
                }
                else
                    geocoder.geocode(
                            new GeoCodeOption().city(et1.getText().toString()).address(et2.getText().toString()));

            }
        });

    }

    private class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 获取精度纬度完成定位
            MyLocationData mMyLocationData = new MyLocationData.Builder().latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置定位球的位置
            mBaiduMap.setMyLocationData(mMyLocationData);
            // 移动定位球到中心位置
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
            mBaiduMap.animateMapStatus(msu);

        }

    }

    // 程序退出时关闭
    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.clear();
        geocoder.destroy();
        super.onDestroy();
    }

}