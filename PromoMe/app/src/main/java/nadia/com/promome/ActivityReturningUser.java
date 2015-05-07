package nadia.com.promome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityReturningUser extends ActionBarActivity {

    private TextView tvUserName;
    private Button btnAgregarProductos;
    private Button btnVerProductos;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returning_user);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        createPreferences();

        tvUserName = (TextView) findViewById(R.id.text_userName);
        btnAgregarProductos = (Button) findViewById(R.id.button_agregar_productos);
        btnVerProductos = (Button) findViewById(R.id.button_ver_productos);

        tvUserName.setText(userName);

    }

    private void createPreferences(){
        SharedPreferences preferences = getSharedPreferences("PROMO_ME_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("USUARIO",userName);
        editor.commit();
    }

    public void onClick(View view){
        Intent intent;

        switch (view.getId()){
            case R.id.button_agregar_productos:
                intent = new Intent(this, ActivityUNSPC.class);
                intent.putExtra("action","add");
                intent.putExtra("userName",userName);
                startActivity(intent);
                break;
            case R.id.button_ver_productos:
                intent = new Intent(this, ActivityUNSPC.class);
                intent.putExtra("action","see");
                intent.putExtra("userName",userName);
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_returning_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_profile) {
            return true;
        }else if(id == R.id.action_log_out){
            
            SharedPreferences preferences = getSharedPreferences("PROMO_ME_PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.clear();
            editor.commit();

            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
