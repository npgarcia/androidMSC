package nadia.com.promome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.ArrayList;

import nadia.com.adapters.UNSPCAdapters;

public class ActivityUNSPC extends ActionBarActivity implements AdapterView.OnItemSelectedListener  {

    String action;
    TextView tv_instrucciones;
    Spinner spinner_segment, spinner_family, spinner_class, spinner_product;
    Button button_next;

    ArrayList unspcSegments = new ArrayList();
    ArrayList unspcFamilies = new ArrayList();
    ArrayList unspcClasses = new ArrayList();
    ArrayList unspcProducts = new ArrayList();

    String userName;

    static final String SELECTED_SEGMENT = "SEGMENT";
    static final String SELECTED_FAMILY = "FAMILY";
    static final String SELECTED_CLASSES = "CLASES";
    static final String SELECTED_PRODUCTS = "PRODUCTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unspc);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        userName =intent.getStringExtra("userName");

        tv_instrucciones = (TextView) findViewById(R.id.tv_instrucciones);
        button_next = (Button) findViewById(R.id.button_next);
        spinner_segment = (Spinner) findViewById(R.id.spinner_segment);
        spinner_family = (Spinner) findViewById(R.id.spinner_family);
        spinner_class = (Spinner) findViewById(R.id.spinner_class);
        spinner_product = (Spinner) findViewById(R.id.spinner_producto);

        spinner_segment.setEnabled(false);
        spinner_family.setEnabled(false);
        spinner_class.setEnabled(false);
        spinner_product.setEnabled(false);

        new GetUnspcData("segment", null).execute();

        if (action.equals("see")) {
            tv_instrucciones.setText(getString(R.string.see_product));
            button_next.setText(getString(R.string.button_get_product));
        } else if (action.equals("add")) {
            tv_instrucciones.setText(getString(R.string.add_product));
            button_next.setText(getString(R.string.button_set_product));
        }
    }

    public void onClick(View view){
        Intent intent;

        if (action.equals("see")) {
            intent = new Intent(this,ActivityGetProducts.class);
            intent.putExtra("productoUnspc",UNSPCAdapters.getSelectedProductId(unspcProducts,spinner_product.getSelectedItemPosition()));
            intent.putExtra("productoNombre",spinner_product.getSelectedItem().toString());
            intent.putExtra("userName",userName);
            startActivity(intent);
        } else if (action.equals("add")) {
            intent = new Intent(this,ActivityAddProduct.class);
            intent.putExtra("productoUnspc",UNSPCAdapters.getSelectedProductId(unspcProducts, spinner_product.getSelectedItemPosition()));
            intent.putExtra("productoNombre",spinner_product.getSelectedItem().toString());
            intent.putExtra("userName",userName);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_segment:
                if (position > 0){
                    new GetUnspcData("family",UNSPCAdapters.getSelectedSegmentId(unspcSegments,position)).execute();
                } else if (position == 0) {
                    spinner_family.setAdapter(null);
                    spinner_family.setEnabled(false);
                    spinner_class.setAdapter(null);
                    spinner_class.setEnabled(false);
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                break;
            case R.id.spinner_family:
                if (position > 0){
                    new GetUnspcData("class",UNSPCAdapters.getSelectedFamilyId(unspcFamilies, position)).execute();
                } else if (position == 0) {
                    spinner_class.setAdapter(null);
                    spinner_class.setEnabled(false);
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                break;
            case R.id.spinner_class:
                if (position > 0){
                    new GetUnspcData("product",UNSPCAdapters.getSelectedClassId(unspcClasses, position)).execute();
                } else if (position == 0) {
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                break;
            case R.id.spinner_producto:
                if (position > 0){
                    button_next.setEnabled(true);
                } else if (position == 0) {
                    button_next.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //outState.putStringArrayList();
        super.onSaveInstanceState(outState);
    }

    class GetUnspcData extends AsyncTask<Void, Integer, Void> {
        String url;
        String data;

        public GetUnspcData(String data, String id) {
            this.data = data;
            switch (data) {
                case "segment":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getSegments";
                    break;
                case "family":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getFamiliesBySegment?id="+id;
                    break;
                case "class":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getClassesByFamily?id="+id;
                    break;
                case "product":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getProductsByClass?id="+id;
                    break;
            }
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
                switch (data) {
                    case "segment":
                        unspcSegments = gson.fromJson(inputStreamReader, unspcSegments.getClass());
                        break;
                    case "family":
                        unspcFamilies = gson.fromJson(inputStreamReader, unspcFamilies.getClass());
                        break;
                    case "class":
                        unspcClasses = gson.fromJson(inputStreamReader, unspcClasses.getClass());
                        break;
                    case "product":
                        unspcProducts = gson.fromJson(inputStreamReader, unspcProducts.getClass());
                        break;
                }
            } catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (data) {
                case "segment":
                    spinner_segment.setEnabled(true);
                    spinner_segment.setAdapter(UNSPCAdapters.getSegmentDescriptions(unspcSegments, ActivityUNSPC.this));
                    spinner_segment.setOnItemSelectedListener(ActivityUNSPC.this);
                    break;
                case "family":
                    spinner_family.setEnabled(true);
                    spinner_family.setAdapter(UNSPCAdapters.getFamiliesDescriptions(unspcFamilies, ActivityUNSPC.this));
                    spinner_family.setOnItemSelectedListener(ActivityUNSPC.this);
                    break;
                case "class":
                    spinner_class.setEnabled(true);
                    spinner_class.setAdapter(UNSPCAdapters.getClassesDescriptions(unspcClasses, ActivityUNSPC.this));
                    spinner_class.setOnItemSelectedListener(ActivityUNSPC.this);
                    break;
                case "product":
                    spinner_product.setEnabled(true);
                    spinner_product.setAdapter(UNSPCAdapters.getProductsDescriptions(unspcProducts, ActivityUNSPC.this));
                    spinner_product.setOnItemSelectedListener(ActivityUNSPC.this);
                    break;
            }
        }
    }

}
