package jp.eno.locationlogger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by eno314 on 2015/04/28.
 */
public class LocationLoggerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BBBBB", "service on create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BBBBB", "service on destroy");
    }
}
