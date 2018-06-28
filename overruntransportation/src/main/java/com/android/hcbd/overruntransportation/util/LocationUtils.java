package com.android.hcbd.overruntransportation.util;

import com.android.hcbd.overruntransportation.MyApplication;
import com.baidu.location.LocationClient;

/**
 * Created by guocheng on 2017/3/27.
 */

public class LocationUtils {
    private LocationClient client = MyApplication.mLocationClient;

    public void start(){
        if(client != null && !client.isStarted()){
            client.start();
        }
    }

    public void stop(){
        if(client != null && client.isStarted()){
            client.stop();
        }
    }

}
