package nadia.com.sensores;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;


public class ActivitySensors extends ActionBarActivity {
    private SensorManager sMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        TextView sensorsData = (TextView)findViewById(R.id.textView1);

        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        List<Sensor> list = sMgr.getSensorList(Sensor.TYPE_ALL);

        StringBuilder data = new StringBuilder();
        for(Sensor sensor: list){
            data.append("Name:\t\t" + sensor.getName() + "\n");
            data.append("Vendor:\t\t" + sensor.getVendor() + "\n");
            data.append("Version:\t\t" + sensor.getVersion() + "\n");

        }
        sensorsData.setText(data);

    }

}
