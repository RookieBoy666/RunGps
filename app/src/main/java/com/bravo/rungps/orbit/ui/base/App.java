  
package com.bravo.rungps.orbit.ui.base;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

/** 
 * ClassName:App <br/>

 * @version       
 */
public class App extends Application{
    private   static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 百度地图
        SDKInitializer.initialize(getApplicationContext());
    }

}
  