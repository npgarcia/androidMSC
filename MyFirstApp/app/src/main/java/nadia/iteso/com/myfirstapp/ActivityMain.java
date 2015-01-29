package nadia.iteso.com.myfirstapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityMain extends ActionBarActivity {

    protected EditText nombre;
    protected EditText telefono;
    protected TextView nombreText;
    protected TextView telefonoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = (EditText)findViewById(R.id.textPersonName);
        telefono = (EditText)findViewById(R.id.phone);
        nombreText = (TextView)findViewById(R.id.textView);
        telefonoText = (TextView)findViewById(R.id.textView2);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardar(View boton){

        String sNombre = nombre.getText().toString();
        String sTelefono = telefono.getText().toString();

        if(sNombre.equals("") || sTelefono.equals("")){
            //enviar mensaje
            Toast.makeText(this,R.string.errorDatosVacios,Toast.LENGTH_LONG).show();

        } else if(sNombre.equals(getString(R.string.editNombre))){
            //enviar nombre
            Toast.makeText(this,R.string.errorNombreEditar,Toast.LENGTH_LONG).show();

        } else if(sTelefono.equals(getString(R.string.editTelefono))){
            //enviar nombre
            Toast.makeText(this,R.string.errorTelefonoEditar,Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this,R.string.exito,Toast.LENGTH_SHORT).show();
            nombre.setText(getString(R.string.editNombre));
            telefono.setText(getString(R.string.editTelefono));
            nombreText.setText(sNombre);
            telefonoText.setText(sTelefono);
        }

    }
}
