package nadia.com.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ngarcia on 3/11/15.
 */
public class ActivityLogin extends Activity {

    private EditText usuario;
    private EditText password;
    private Button entrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User myUser = loadPreferences();

        if(myUser.isLogged()){
            goToMain();
        }else{
            setContentView(R.layout.activity_login);

            usuario = (EditText) findViewById(R.id.usuarioET);
            password = (EditText) findViewById(R.id.passwordET);
            entrar = (Button) findViewById(R.id.entrarB);
            entrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User usuario1 = new User(usuario.getText().toString(),
                            password.getText().toString(),true);
                    createPreferences(usuario1);
                    goToMain();
                }
            });

        }

    }

    private void goToMain(){
        Intent intent = new Intent(this,ActivityMain.class);
        startActivity(intent);
        finish();
    }

    private User loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("MY_PREFERENCES",Context.MODE_PRIVATE);

        User usuario = new User(preferences.getString("USUARIO",null),
                preferences.getString("PASSWORD",null),
                preferences.getBoolean("ISLOGGED",false));

        return usuario;
    }

    private void createPreferences(User usuario1){
        SharedPreferences preferences = getSharedPreferences("MY_PREFERENCES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("USUARIO",usuario1.getUsuario());
        editor.putString("PASSWORD",usuario1.getPassword());
        editor.putBoolean("ISLOGGED", usuario1.isLogged());
        editor.commit();
    }
}
