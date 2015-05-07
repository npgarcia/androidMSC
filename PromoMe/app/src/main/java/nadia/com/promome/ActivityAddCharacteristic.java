package nadia.com.promome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nadia.com.adapters.Caracteristicas;
import nadia.com.adapters.CharacteristicAdapter;
import nadia.com.adapters.ProductDetailsAdapter;
import nadia.com.adapters.Producto;
import nadia.com.user.User;


public class ActivityAddCharacteristic extends ActionBarActivity implements DialogInterface.OnClickListener {
    ListView lv_fill_caracteristica;
    CharacteristicAdapter fill_caracteristica_adapter;
    Button button_otra_caracteristica;
    Button button_agregar_producto;
    String unspc;
    EditText et_nombre_caracteristica, et_valor_caracteristica, et_unidad_caracteristica, et_nombre_producto, et_marca_producto_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_characteristic);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] genericCharacteristics = bundle.getStringArray("selectedItems");
        unspc = intent.getStringExtra("unspcNumber");

        lv_fill_caracteristica = (ListView) findViewById(R.id.lv_fill_caracteristica);
        fill_caracteristica_adapter = new CharacteristicAdapter(genericCharacteristics, this, unspc);
        lv_fill_caracteristica.setAdapter(fill_caracteristica_adapter);
        button_otra_caracteristica = (Button) findViewById(R.id.button_otra_caracteristica);
        button_agregar_producto = (Button) findViewById(R.id.button_agregar_producto);
        et_marca_producto_producto = (EditText) findViewById(R.id.et_marca_producto_producto);
        et_nombre_producto = (EditText) findViewById(R.id.et_nombre_producto);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_otra_caracteristica:

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View alertView = inflater.inflate(R.layout.dialog_add_characteristic, null, false);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(alertView);
                // alertDialogBuilder.setTitle(getString(R.string.detalle_producto) + " " + detail.getNombre());
                alertDialogBuilder.setPositiveButton("Agregar", this);
                alertDialogBuilder.setNegativeButton("Cancelar", this);

                et_nombre_caracteristica = (EditText) alertView.findViewById(R.id.et_nombre_caracteristica);
                et_valor_caracteristica = (EditText) alertView.findViewById(R.id.et_valor_caracteristica);
                et_unidad_caracteristica = (EditText) alertView.findViewById(R.id.et_unidad_caracteristica);

                AlertDialog alertDialog = alertDialogBuilder.create();
                // show alert
                alertDialog.show();

                break;
            case R.id.button_agregar_producto:
                boolean cancel = false;

                String nombre = et_nombre_producto.getText().toString();
                String marca = et_marca_producto_producto.getText().toString();

                if (TextUtils.isEmpty(marca)) {
                    et_marca_producto_producto.setError(getString(R.string.error_field_required));
                    et_marca_producto_producto.requestFocus();
                    cancel = true;
                }

                if (TextUtils.isEmpty(nombre)) {
                    et_nombre_producto.setError(getString(R.string.error_field_required));
                    et_nombre_producto.requestFocus();
                    cancel = true;
                }

                Map<String,String> charMap = new HashMap<>();

                ArrayList<Caracteristicas> lasCaracteristicas = fill_caracteristica_adapter.getList();

                for(Caracteristicas c : lasCaracteristicas){
                    String key = c.getCaracteristica();
                    String value = c.getValor() ;

                    if(TextUtils.isEmpty(value)){
                        Toast.makeText(this,"Las caracteristicas agregadas no pueden estar vacias",Toast.LENGTH_LONG).show();
                        return;
                    }

                    value +=" " + c.getUnidad();
                    charMap.put(key,value);
                }


                if (!cancel) {
                    Producto producto = new Producto();

                    producto.setBrand(marca);
                    producto.setBusinessName("user");
                    producto.setProductName(nombre);
                    producto.setUnspc(unspc);

                    producto.setCharacteristics(charMap);

                    new AddProduct(producto).execute();
                }

                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {

            Caracteristicas c = new Caracteristicas();
            c.setCaracteristica(et_nombre_caracteristica.getText().toString());
            c.setValor(et_valor_caracteristica.getText().toString());
            c.setUnidad(et_unidad_caracteristica.getText().toString());

            fill_caracteristica_adapter.addItem(c);
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    class AddProduct extends AsyncTask<Void, Void, Boolean> {
        String url;
        Producto p;

        public AddProduct(Producto producto) {
            url = "http://54.165.43.110:8080/ProyectoFinalRest/users/users/newUser?pass=1234";
            p = producto;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success)
                finish();
            else
                Toast.makeText(ActivityAddCharacteristic.this,"Tu petición no pudo ser procesada",Toast.LENGTH_LONG);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpClient postClient = new DefaultHttpClient();
                HttpPost postNewUser = new HttpPost(url);

                String json = new GsonBuilder().create().toJson(p, Producto.class);
                postNewUser.setEntity(new StringEntity(json, HTTP.UTF_8));
                postNewUser.setHeader("Content-type", "application/json");

                HttpResponse response = postClient.execute(postNewUser);
                final int status = response.getStatusLine().getStatusCode();
                if (status != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(), "Error " + status
                            + " for URL " + url);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
    }
}
