package nadia.com.sensores;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class ActivityLocation extends ActionBarActivity implements LocationListener {

    LocationManager locationManager;
    TextView latitudeValue;
    TextView longitudeValue;
    TextView providerValue;
    TextView accuracyValue;
    TextView timeToFixValue;
    TextView enabledProvidersValue;
    List<String> enabledProviders;
    long uptimeAtResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        latitudeValue = (TextView) findViewById(R.id.latitudeValue);
        longitudeValue = (TextView) findViewById(R.id.longitudeValue);
        providerValue = (TextView) findViewById(R.id.providerValue);
        accuracyValue = (TextView) findViewById(R.id.accuracyValue);
        timeToFixValue = (TextView) findViewById(R.id.timeToFixValue);
        enabledProvidersValue = (TextView) findViewById(R.id.enabledProvidersValue);

    }

    @Override
    public void onLocationChanged(Location location) {

        long timeToFix = SystemClock.uptimeMillis() - uptimeAtResume;

        latitudeValue.setText(String.valueOf(location.getLatitude()));
        longitudeValue.setText(String.valueOf(location.getLongitude()));
        providerValue.setText(String.valueOf(location.getProvider()));
        accuracyValue.setText(String.valueOf(location.getAccuracy()));
        timeToFixValue.setText(String.valueOf(timeToFix / 1000));

        findViewById(R.id.timeToFixUnits).setVisibility(View.VISIBLE);
        findViewById(R.id.accuracyUnits).setVisibility(View.VISIBLE);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    protected void onResume() {
        super.onResume();

        StringBuffer stringBuffer = new StringBuffer();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        enabledProviders = locationManager.getProviders(criteria, true);

        if (enabledProviders.isEmpty()) {
            enabledProvidersValue.setText("");
        } else {
            for (String enabledProvider : enabledProviders) {
                stringBuffer.append(enabledProvider).append(" ");
                locationManager.requestSingleUpdate(enabledProvider, this,
                        null);
            }
            enabledProvidersValue.setText(stringBuffer);
        }

        uptimeAtResume = SystemClock.uptimeMillis();
        latitudeValue.setText("");
        longitudeValue.setText("");
        providerValue.setText("");
        accuracyValue.setText("");
        timeToFixValue.setText("");

        findViewById(R.id.timeToFixUnits).setVisibility(View.GONE);
        findViewById(R.id.accuracyUnits).setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

}
