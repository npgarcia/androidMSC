package nadia.iteso.com.sesion3;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ngarcia on 2/4/15.
 */
public class ActivityLinear extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        Button send = (Button) findViewById(R.id.activity_send);
        final EditText message = (EditText) findViewById(R.id.linear_message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLinear.this,ActivityMain.class); //intent explicito
                intent.putExtra("MESSAGE",message.getText().toString());
                startActivity(intent);
            }
        });
    }


}
