package nadia.com.doodle;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class ActivityMain extends ActionBarActivity {
    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.start_action:
                alarm.setAlarm(this); break;
            case R.id.cancel_action:
                alarm.cancelAlarm(this); break;
        }
    }

}
