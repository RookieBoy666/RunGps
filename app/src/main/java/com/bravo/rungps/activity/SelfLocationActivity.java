package com.bravo.rungps.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
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
import com.bravo.rungps.R;

import java.util.ArrayList;
import java.util.List;


public class SelfLocationActivity extends AppCompatActivity {
    public LocationClient locationClient;
    private TextView textView;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());//要在setContentView调用之前初始化
        setContentView(R.layout.activity_selflocation);
        mapView = (MapView) findViewById(R.id.baidu_map);
        textView = (TextView) findViewById(R.id.abc);
        baiduMap = mapView.getMap();//地图总控制台
        baiduMap.setMyLocationEnabled(true);
        List<String> list = new ArrayList<>();
        //运行时权限，没有注册就重新注册
        if (ContextCompat.checkSelfPermission(SelfLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(SelfLocationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(SelfLocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!list.isEmpty()) {//没有权限就添加
            String[] permissions = list.toArray(new String[list.size()]);//如果list不为空，就调用ActivityCompat.requestPermissions添加权限
            ActivityCompat.requestPermissions(SelfLocationActivity.this, permissions, 1);
        } else {//有相关权限则执行程序
            requestLocation();
        }
    }

    private void requestLocation() {
        init();
        locationClient.start();
    }

    private void init() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setScanSpan(5000);
        //设置发起定位请求的间隔时间为5000ms
        // 处理百度地图开源项目的定位偏差
        option.setCoorType("bd09ll");
        locationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            Toast.makeText(this, "维度：" + location.getLatitude() + "  经线：" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());//获取当前经纬度并且传递给LatLng
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(update);//将地图定位更新到当前位置
            update = MapStatusUpdateFactory.zoomTo(16.0f);//地图缩放倍数
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();   //将当前位置在地图上标记出来
        builder.latitude(location.getLatitude());//传入经纬度
        builder.longitude(location.getLongitude());//传入经纬度
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "用户取消权限，程序运行失败", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();//调用定位
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            {//定位信息获取
                java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.000");
                StringBuilder currentPosition = new StringBuilder();
                currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
                currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
                currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
                currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
                currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
                currentPosition.append("纬度：").append(myformat.format(bdLocation.getLatitude())).append("\n");
                currentPosition.append("经度：").append(myformat.format(bdLocation.getLongitude())).append("\n");
                currentPosition.append("定位方式：");
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    currentPosition.append("GPS");
                }
                else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                    currentPosition.append("网络");
                }
                else {
                    currentPosition.append("Error:" + bdLocation.getLocType());
                }
                textView.setText(currentPosition);
            }
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
        }
    }
}
