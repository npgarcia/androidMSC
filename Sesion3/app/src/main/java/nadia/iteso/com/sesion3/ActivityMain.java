package nadia.iteso.com.sesion3;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);

        Log.e("ITESO","onCreate()");

        String message = getIntent().getExtras().getString("MESSAGE");
        EditText reminder_name = (EditText) findViewById(R.id.reminder_name);
        reminder_name.setText(message);

        if(savedInstanceState!=null) {
            Log.v("ITESO", savedInstanceState.getString("TEST"));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null) {
            Log.v("ITESO", savedInstanceState.getString("TEST"));
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        Log.e("ITESO","onStart()");
    }

    @Override
    protected void onResume(){
        super.onStart();
        Log.e("ITESO","onResume()");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("TEST","mi mensaje");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause(){
        super.onStart();

        Log.e("ITESO","onPause()");
    }


    @Override
    protected void onStop(){
        super.onStart();

        Log.e("ITESO","onStop()");
    }

    @Override
    protected void onRestart(){
        super.onStart();

        Log.e("ITESO","onRestart()");
    }

    @Override
    protected void onDestroy(){
        super.onStart();

        Log.e("ITESO","onDestroy()");
    }
}
