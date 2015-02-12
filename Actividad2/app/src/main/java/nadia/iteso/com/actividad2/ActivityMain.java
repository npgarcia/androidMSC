package nadia.iteso.com.actividad2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityMain extends ActionBarActivity {


    TextView name, phone;
    RadioButton radioMale;
    RadioButton radioFemale;
    AutoCompleteTextView books;
    CheckedTextView sports;
    Spinner scholar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoComplete =(AutoCompleteTextView) findViewById(R.id.autoCompleteBooks);
        String booksArray[] = getResources().getStringArray(R.array.auto_libro);

        ArrayAdapter<String> booksAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,booksArray);
        autoComplete.setAdapter(booksAdapter);

        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        radioMale = (RadioButton) findViewById(R.id.radioButtonMas);
        radioFemale = (RadioButton) findViewById(R.id.radioButtonFem);
        books = (AutoCompleteTextView) findViewById(R.id.autoCompleteBooks);
        sports = (CheckedTextView) findViewById(R.id.checkedTextSports);
        scholar = (Spinner) findViewById(R.id.spinnerScolar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Toast toast = Toast.makeText(getApplicationContext(), "You clicked on Save!", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCheck(View v){
        if(sports.isChecked())
            sports.setChecked(false);
        else
            sports.setChecked(true);
    }

    public void onClickClear(View v){

        cleanForm();

    }

    public void cleanForm(){
        name.setText("");
        phone.setText("");
        radioMale.setChecked(true);
        radioFemale.setChecked(false);
        books.setText("");
        sports.setChecked(false);

        scholar.setSelection(0);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea realmente limpiar?").setTitle("GUI TEST");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cleanForm();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
