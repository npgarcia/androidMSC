package nadia.com.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import nadia.com.beans.DatoResponse;

/**
 * Created by ngarcia on 4/8/15.
 */
public class GeoActivity extends ActionBarActivity {

    EditText latitud;
    EditText longitud;
    EditText cidudad;
    EditText region;
    EditText regionAbreviada;
    EditText pais;
    Button dondeEstoy;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_activity);

        latitud = (EditText) findViewById(R.id.edit_text_latitud);
        longitud = (EditText) findViewById(R.id.edit_text_longitud);
        cidudad = (EditText) findViewById(R.id.edit_text_ciudad);
        region = (EditText) findViewById(R.id.edit_text_region);
        regionAbreviada = (EditText) findViewById(R.id.edit_text_region_abv);
        pais = (EditText) findViewById(R.id.edit_text_pais);
        dondeEstoy = (Button) findViewById(R.id.button_dondeEstoy);

        dondeEstoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                getData();
            }
        });
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void getData() {
        progressDialog = ProgressDialog.show(GeoActivity.this, "Please wait", "Retrieving data", true, true);
        GetData task = new GetData(latitud.getText().toString(), longitud.getText().toString());
        task.execute();
        progressDialog.setOnCancelListener(new CancelListener(task));
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

    class GetData extends AsyncTask<Void, Integer, Void> {
        String url;
        DatoResponse response;

        public GetData(String latitud, String longitud) {
            url = "http://www.geoplugin.net/extras/location.gp?lat=" + latitud + "&long=" + longitud + "&format=json";
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dondeEstoy.setClickable(true);

            cidudad.setText(response.getGeoplugin_place());
            region.setText(response.getGeoplugin_region());
            regionAbreviada.setText(response.getGeoplugin_regionAbbreviated());
            pais.setText(response.getGeoplugin_countryCode());

            progressDialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            dondeEstoy.setClickable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpGet getRequest = new HttpGet(url);
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse getResponse = httpClient.execute(getRequest);
                final int statusCode = getResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(), "Error " + statusCode
                            + " for URL " + url);
                    return null;
                }
                HttpEntity getResponseEntity = getResponse.getEntity();
                InputStream httpResponseStream = getResponseEntity.getContent();
                Reader inputStreamReader = new InputStreamReader(
                        httpResponseStream);
                Gson gson = new Gson();
                this.response = gson.fromJson(inputStreamReader, DatoResponse.class);
            } catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }
            return null;
        }
    }

    private class CancelListener implements
            DialogInterface.OnCancelListener {
        AsyncTask<?, ?, ?> cancellableTask;

        public CancelListener(AsyncTask<?, ?, ?> task) {
            cancellableTask = task;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            cancellableTask.cancel(true);
        }
    }


}
