package nadia.com.sensores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.button_sensores:
                i = new Intent(this, ActivitySensors.class);
                startActivity(i);
                break;
            case R.id.button_ubicacion:
                i = new Intent(this, ActivityLocation.class);
                startActivity(i);
                break;
            case R.id.button_acelerometro:
                i = new Intent(this, ActivityAccelerometer.class);
                startActivity(i);
                break;
            case R.id.button_compass:
                i = new Intent(this,ActivityCompass.class);
                startActivity(i);
                break;
        }
    }


}
