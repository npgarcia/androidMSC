package nadia.com.asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button start;
    TextView progress_text;
    ProgressBar progress_bar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        intent = new Intent(this, GeoActivity.class);
        progress_text = (TextView) findViewById(R.id.progress_text);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        start = (Button) findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateProgress().execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class UpdateProgress extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            start.setClickable(true);
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            start.setClickable(false);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress_bar.setProgress(values[0]);
            progress_text.setText(values[0] + "%");
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 1; i <= 100; i++) {
                publishProgress(i);
                SystemClock.sleep(100);
            }
            return null;
        }
    }


}
