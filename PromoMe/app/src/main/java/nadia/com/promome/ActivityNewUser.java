package nadia.com.promome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import nadia.com.user.Anhos;
import nadia.com.user.DatoResponse;
import nadia.com.user.User;


public class ActivityNewUser extends ActionBarActivity implements LocationListener {
    RadioButton sexoFemenino;
    RadioButton sexoMasculino;
    Spinner anhoNacimiento;
    TextView ubicacion;
    TextView bienvenido;
    String password;
    User newUser = new User();
    Button next;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Intent intent = getIntent();
        newUser.setUserName(intent.getStringExtra("userName"));
        password = intent.getStringExtra("password");

        bienvenido = (TextView) findViewById(R.id.tv_bienvenido);
        bienvenido.setText(bienvenido.getText().toString() + " " + newUser.getUserName() + "!");

        ubicacion = (TextView) findViewById(R.id.tv_ubicacion);
        sexoFemenino = (RadioButton) findViewById(R.id.femenino);
        sexoMasculino = (RadioButton) findViewById(R.id.masculino);

        anhoNacimiento = (Spinner) findViewById(R.id.text_anhoNacimiento);
        ArrayAdapter<String> anhosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Anhos.getAnhos());
        anhoNacimiento.setAdapter(anhosAdapter);
        anhoNacimiento.setSelection(80);

        next = (Button) findViewById(R.id.button_next);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, (long) 20000, 0f, this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.femenino:
                newUser.setSex(false);
                break;
            case R.id.masculino:
                newUser.setSex(true);
                break;
            case R.id.button_next:
                if (!sexoMasculino.isChecked() && !sexoFemenino.isChecked()) {
                    Toast.makeText(this, R.string.sexoRequerido, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    newUser.setYearOfBirth(Integer.parseInt((String) anhoNacimiento.getSelectedItem()));
                    newUser.setUserId(User.uuidForDate(new Date()));

                    CreateNewUser cnu = new CreateNewUser();
                    cnu.execute();
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();

        GetLocalizationData localizationData = new GetLocalizationData(latitud, longitud);
        localizationData.execute();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class GetLocalizationData extends AsyncTask<Void, Integer, Void> {
        String url;
        DatoResponse response;

        public GetLocalizationData(double latitud, double longitud) {
            url = "http://www.geoplugin.net/extras/location.gp?lat=" + latitud + "&long=" + longitud + "&format=json";
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String ciudad = response.getGeoplugin_place();
            String region = response.getGeoplugin_region();
            String pais = response.getGeoplugin_countryCode();

            ubicacion.setText(ciudad + ", " + region + ", " + pais);
            next.setText(R.string.registrame);
            next.setEnabled(true);

            newUser.setGeoLocation_country(pais);
            newUser.setGeoLocation_region(region);
            newUser.setGeoLocation_place(ciudad);
        }

        @Override
        protected void onPreExecute() {

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

    class CreateNewUser extends AsyncTask<Void, Integer, Void> {
        String url;

        public CreateNewUser() {
            url = "http://54.165.43.110:8080/ProyectoFinalRest/users/users/newUser?pass=" + password;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(ActivityNewUser.this, ActivityReturningUser.class);
            intent.putExtra("userName", newUser.getUserName());
            startActivity(intent);
            finish();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpClient postClient = new DefaultHttpClient();
                HttpPost postNewUser = new HttpPost(url);

                String json = new GsonBuilder().create().toJson(newUser, User.class);
                postNewUser.setEntity(new StringEntity(json, HTTP.UTF_8));
                postNewUser.setHeader("Content-type", "application/json");

                HttpResponse response = postClient.execute(postNewUser);
                final int status = response.getStatusLine().getStatusCode();
                if (status != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(), "Error " + status
                            + " for URL " + url);
                    return null;
                }
            } catch (UnsupportedEncodingException | ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
