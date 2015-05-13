package nadia.com.promome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ActivityUNSPC extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

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
    static final String SELECTED_SEGMENT_ITEM_KEY = "SEGMENT_ITEM";
    static final String SELECTED_FAMILY_ITEM_KEY = "FAMILY_ITEM";
    static final String SELECTED_CLASSES_ITEM_KEY = "CLASES_ITEM";
    static final String SELECTED_PRODUCTS_ITEM_KEY = "PRODUCTS_ITEM";

    int SELECTED_SEGMENT_ITEM = -1;
    int SELECTED_FAMILY_ITEM = -1;
    int SELECTED_CLASSES_ITEM = -1;
    int SELECTED_PRODUCTS_ITEM = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unspc);

        tv_instrucciones = (TextView) findViewById(R.id.tv_instrucciones);
        button_next = (Button) findViewById(R.id.button_next);
        spinner_segment = (Spinner) findViewById(R.id.spinner_segment);
        spinner_family = (Spinner) findViewById(R.id.spinner_family);
        spinner_class = (Spinner) findViewById(R.id.spinner_class);
        spinner_product = (Spinner) findViewById(R.id.spinner_producto);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        userName = intent.getStringExtra("userName");

        if (action.equals("see")) {
            tv_instrucciones.setText(getString(R.string.see_product));
            button_next.setText(getString(R.string.button_get_product));
        } else if (action.equals("add")) {
            tv_instrucciones.setText(getString(R.string.add_product));
            button_next.setText(getString(R.string.button_set_product));
        }

        if(savedInstanceState == null) {

            spinner_segment.setEnabled(false);
            spinner_family.setEnabled(false);
            spinner_class.setEnabled(false);
            spinner_product.setEnabled(false);

            new GetUnspcData("segment", null).execute();
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        //SEGMENTS
        unspcSegments = savedInstanceState.getStringArrayList(SELECTED_SEGMENT);
        if (unspcSegments.size() > 0) {
            spinner_segment.setAdapter(UNSPCAdapters.getSegmentDescriptions(unspcSegments, ActivityUNSPC.this));
            SELECTED_SEGMENT_ITEM = savedInstanceState.getInt(SELECTED_SEGMENT_ITEM_KEY);
            spinner_segment.setSelection(SELECTED_SEGMENT_ITEM);
            spinner_segment.setOnItemSelectedListener(ActivityUNSPC.this);
        } else {
            spinner_segment.setAdapter(null);
            spinner_segment.setEnabled(false);
        }

        //FAMILIES
        unspcFamilies = savedInstanceState.getStringArrayList(SELECTED_FAMILY);
        if (unspcFamilies.size() > 0) {
            spinner_family.setAdapter(UNSPCAdapters.getSegmentDescriptions(unspcFamilies, ActivityUNSPC.this));
            SELECTED_FAMILY_ITEM = savedInstanceState.getInt(SELECTED_FAMILY_ITEM_KEY);
            spinner_family.setSelection(SELECTED_FAMILY_ITEM);
            spinner_family.setOnItemSelectedListener(ActivityUNSPC.this);
        } else {
            spinner_family.setAdapter(null);
            spinner_family.setEnabled(false);
        }

        //CLASSES
        unspcClasses = savedInstanceState.getStringArrayList(SELECTED_CLASSES);
        if (unspcClasses.size() > 0) {
            spinner_class.setAdapter(UNSPCAdapters.getSegmentDescriptions(unspcClasses, ActivityUNSPC.this));
            SELECTED_CLASSES_ITEM = savedInstanceState.getInt(SELECTED_CLASSES_ITEM_KEY);
            spinner_class.setSelection(SELECTED_CLASSES_ITEM);
            spinner_class.setOnItemSelectedListener(ActivityUNSPC.this);
        } else {
            spinner_class.setAdapter(null);
            spinner_class.setEnabled(false);
        }

        //PRODUCTS
        unspcProducts = savedInstanceState.getStringArrayList(SELECTED_PRODUCTS);
        if (unspcProducts.size() > 0) {
            spinner_product.setAdapter(UNSPCAdapters.getSegmentDescriptions(unspcProducts, ActivityUNSPC.this));
            SELECTED_PRODUCTS_ITEM = savedInstanceState.getInt(SELECTED_PRODUCTS_ITEM_KEY);
            spinner_product.setSelection(SELECTED_PRODUCTS_ITEM);
            spinner_product.setOnItemSelectedListener(ActivityUNSPC.this);
        } else {
            spinner_product.setAdapter(null);
            spinner_product.setEnabled(false);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onClick(View view) {
        Intent intent;

        if (action.equals("see")) {
            intent = new Intent(this, ActivityGetProducts.class);
            intent.putExtra("productoUnspc", UNSPCAdapters.getSelectedProductId(unspcProducts, spinner_product.getSelectedItemPosition()));
            intent.putExtra("productoNombre", spinner_product.getSelectedItem().toString());
            intent.putExtra("userName", userName);
            startActivity(intent);
        } else if (action.equals("add")) {
            intent = new Intent(this, ActivityAddProduct.class);
            intent.putExtra("productoUnspc", UNSPCAdapters.getSelectedProductId(unspcProducts, spinner_product.getSelectedItemPosition()));
            intent.putExtra("productoNombre", spinner_product.getSelectedItem().toString());
            intent.putExtra("userName", userName);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_segment:
                if (position > 0 && position != SELECTED_SEGMENT_ITEM) {
                    new GetUnspcData("family", UNSPCAdapters.getSelectedSegmentId(unspcSegments, position)).execute();
                } else if (position == 0) {
                    spinner_family.setAdapter(null);
                    spinner_family.setEnabled(false);
                    spinner_class.setAdapter(null);
                    spinner_class.setEnabled(false);
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                SELECTED_SEGMENT_ITEM = position;
                break;
            case R.id.spinner_family:
                if (position > 0 && position != SELECTED_FAMILY_ITEM) {
                    new GetUnspcData("class", UNSPCAdapters.getSelectedFamilyId(unspcFamilies, position)).execute();
                } else if (position == 0) {
                    spinner_class.setAdapter(null);
                    spinner_class.setEnabled(false);
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                SELECTED_FAMILY_ITEM = position;
                break;
            case R.id.spinner_class:
                if (position > 0 && position != SELECTED_CLASSES_ITEM) {
                    new GetUnspcData("product", UNSPCAdapters.getSelectedClassId(unspcClasses, position)).execute();
                } else if (position == 0) {
                    spinner_product.setAdapter(null);
                    spinner_product.setEnabled(false);
                }
                SELECTED_CLASSES_ITEM = position;
                break;
            case R.id.spinner_producto:
                if (position > 0 && position != SELECTED_PRODUCTS_ITEM) {
                    button_next.setEnabled(true);
                }else if(position == 0) {
                    button_next.setEnabled(false);
                }
                SELECTED_PRODUCTS_ITEM = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //SEGMENTOS
        outState.putStringArrayList(SELECTED_SEGMENT, unspcSegments);
        outState.putInt(SELECTED_SEGMENT_ITEM_KEY, SELECTED_SEGMENT_ITEM);

        //FAMILIA
        outState.putStringArrayList(SELECTED_FAMILY, unspcFamilies);
        outState.putInt(SELECTED_FAMILY_ITEM_KEY, SELECTED_FAMILY_ITEM);

        //CLASE
        outState.putStringArrayList(SELECTED_CLASSES, unspcClasses);
        outState.putInt(SELECTED_CLASSES_ITEM_KEY, SELECTED_CLASSES_ITEM);

        //CLASE
        outState.putStringArrayList(SELECTED_PRODUCTS, unspcProducts);
        outState.putInt(SELECTED_PRODUCTS_ITEM_KEY, SELECTED_PRODUCTS_ITEM);


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
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getFamiliesBySegment?id=" + id;
                    break;
                case "class":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getClassesByFamily?id=" + id;
                    break;
                case "product":
                    url = "http://54.165.43.110:8080/ProyectoFinalRest/rest/unspc/getProductsByClass?id=" + id;
                    break;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String waitStringArray[] = {getString(R.string.unspc_espera)};
            ArrayAdapter<String> wait = new ArrayAdapter<>(ActivityUNSPC.this, android.R.layout.simple_list_item_1, waitStringArray);

            switch (data) {
                case "segment":
                    spinner_segment.setAdapter(wait);
                    break;
                case "family":
                    spinner_family.setAdapter(wait);
                    break;
                case "class":
                    spinner_class.setAdapter(wait);
                    break;
                case "product":
                    spinner_product.setAdapter(wait);
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
                    spinner_family.setEnabled(false);
                    spinner_class.setEnabled(false);
                    spinner_product.setEnabled(false);
                    break;
                case "family":
                    spinner_family.setEnabled(true);
                    spinner_family.setAdapter(UNSPCAdapters.getFamiliesDescriptions(unspcFamilies, ActivityUNSPC.this));
                    spinner_family.setOnItemSelectedListener(ActivityUNSPC.this);
                    spinner_class.setEnabled(false);
                    spinner_product.setEnabled(false);
                    break;
                case "class":
                    spinner_class.setEnabled(true);
                    spinner_class.setAdapter(UNSPCAdapters.getClassesDescriptions(unspcClasses, ActivityUNSPC.this));
                    spinner_class.setOnItemSelectedListener(ActivityUNSPC.this);
                    spinner_product.setEnabled(false);
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
