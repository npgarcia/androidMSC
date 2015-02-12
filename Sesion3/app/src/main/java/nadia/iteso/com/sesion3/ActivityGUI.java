package nadia.iteso.com.sesion3;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by ngarcia on 2/11/15.
 */
public class ActivityGUI extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui);

        AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_countries);
        String countries[] = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, countries);

        autoComplete.setAdapter(adapter);

        Spinner theSpinner = (Spinner) findViewById(R.id.spinner_countries);
        theSpinner.setOnItemSelectedListener(this);

    }

    public void onClick(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radio1:

                break;
            case R.id.radio2:

                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
