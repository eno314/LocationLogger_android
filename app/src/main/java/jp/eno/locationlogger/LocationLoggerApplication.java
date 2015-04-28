package jp.eno.locationlogger;

import android.app.Application;
import android.content.Intent;

/**
 * Created by eno314 on 2015/04/28.
 */
public class LocationLoggerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Intent intent = new Intent(this, LocationLoggerService.class);
        startService(intent);
    }
}
