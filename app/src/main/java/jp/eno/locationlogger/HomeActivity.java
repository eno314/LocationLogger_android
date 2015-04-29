package jp.eno.locationlogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_start_location) {
            LocationLoggerService.start(getApplicationContext());
            return true;
        }

        if (id == R.id.action_stop_location) {
            LocationLoggerService.stop(getApplicationContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
