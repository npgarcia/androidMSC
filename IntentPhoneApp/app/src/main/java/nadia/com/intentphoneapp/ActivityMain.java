package nadia.com.intentphoneapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityMain extends ActionBarActivity {

    protected EditText editTextPhone;
    protected EditText editTextPage;
    protected Button buttonPhone;
    protected Button buttonPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //edit text phone
        editTextPhone = (EditText) findViewById(R.id.edit_text_phone);

        //edit text page
        editTextPage = (EditText) findViewById(R.id.edit_text_page);

        //boton telefono
        buttonPhone = (Button) findViewById(R.id.button_phone);
        buttonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent (Intent.ACTION_CALL, Uri.parse("tel:"+editTextPhone.getText().toString()));
                startActivity(intentCall);
            }
        });

        //boton pagina
        buttonPage = (Button) findViewById(R.id.button_page);
        buttonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+editTextPage.getText().toString()));
                startActivity(intentPage);
            }
        });

    }

}
