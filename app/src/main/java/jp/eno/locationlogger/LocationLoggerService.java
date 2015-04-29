package jp.eno.locationlogger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by eno314 on 2015/04/28.
 */
public class LocationLoggerService extends Service {

    private static final long LOCATION_REQUEST_INTERVAL = /*1000 * 60 * 10*/ 1000 * 60 * 2;
    private static final long LOCATION_REQUEST_FASTEST_INTERVAL = /*1000 * 60 * 5*/ 1000 * 60 * 1;

    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;

    private LocationListener mLocationListener;

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

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(getConnectionCallbacks())
                .addOnConnectionFailedListener(getOnConnectionFailedListener())
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(LOCATION_REQUEST_INTERVAL)
                .setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showLocationInfo(location);
            }
        };

        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        Log.d("BBBBB", "service on destroy");
        LocationServices.FusedLocationApi
                .removeLocationUpdates(mGoogleApiClient, mLocationListener);
        super.onDestroy();
    }

    private GoogleApiClient.ConnectionCallbacks getConnectionCallbacks() {
        return new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                showToast("位置情報の取得処理を開始しました。");

                // 接続が成功したら、位置情報取得開始
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, mLocationListener);
            }

            @Override
            public void onConnectionSuspended(int i) {
                showToast("GoogleApiClientの接続が停止");
                retryConnectGoogleApiClient();
            }
        };
    }

    private GoogleApiClient.OnConnectionFailedListener getOnConnectionFailedListener() {
        return new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                showToast("GoogleApiClientに接続失敗");
                retryConnectGoogleApiClient();
            }
        };
    }

    private void retryConnectGoogleApiClient() {
        // TODO 一定時間後にGoogleApiClientの再接続処理を行う
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showLocationInfo(Location location) {
        if (location != null) {
            Log.d("BBBBB", "time : " + location.getTime());
            Log.d("BBBBB", "lat : " + location.getLatitude());
            Log.d("BBBBB", "lon : " + location.getLongitude());
            Log.d("BBBBB", "speed : " + location.getSpeed());
            Log.d("BBBBB", "accuracy : " + location.getAccuracy());
        }
    }

    public static void start(Context context) {
        final Intent intent = new Intent(context, LocationLoggerService.class);
        context.startService(intent);
    }

    public static void stop(Context context) {
        final Intent intent = new Intent(context, LocationLoggerService.class);
        context.stopService(intent);
    }
}
