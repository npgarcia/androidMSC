package nadia.com.preferences;

import android.app.Activity;
import android.os.Bundle;


public class ActivityPreferences extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentPreference())
                .commit();
    }
}
