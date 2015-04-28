package jp.eno.locationlogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class HomeActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(getConnectionCallbacks())
                .addOnConnectionFailedListener(getOnConnectionFailedListener())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_settings) {
            if (mGoogleApiClient.isConnected()) {
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続済み", Toast.LENGTH_SHORT).show();
            } else if (mGoogleApiClient.isConnecting()) {
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続中", Toast.LENGTH_SHORT).show();
            } else {
                mGoogleApiClient.connect();
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続開始", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private GoogleApiClient.ConnectionCallbacks getConnectionCallbacks() {
        return new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConnectionSuspended(int i) {
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続が停止", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private GoogleApiClient.OnConnectionFailedListener getOnConnectionFailedListener() {
        return new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Toast.makeText(getApplicationContext(), "GoogleApiClientに接続失敗", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
