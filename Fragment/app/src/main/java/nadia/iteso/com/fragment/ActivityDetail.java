package nadia.iteso.com.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by ngarcia on 2/18/15.
 */
public class ActivityDetail extends ActionBarActivity {
    DetailFragment df;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        df = (DetailFragment) getFragmentManager().findFragmentById(R.id.fragment_detail);

        if(getIntent().getExtras()!=null){
            String item = getIntent().getExtras().getString("nombres");
            df.setText(item);
        }

    }
}
