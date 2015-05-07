package nadia.com.promome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import nadia.com.adapters.ListStringWrapper;


public class ActivityAddProduct extends ActionBarActivity {

    TextView tv_unspc;
    ListView lv_select_caracteristica;
    Button button_siguiente;
    ListStringWrapper caracteristicas = new ListStringWrapper();
    ArrayAdapter<String> adapter;
    String unspc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prodcut);

        Intent intent = getIntent();
        tv_unspc = (TextView) findViewById(R.id.tv_unspc);
        tv_unspc.setText(intent.getStringExtra("productoNombre"));
        lv_select_caracteristica = (ListView) findViewById(R.id.lv_select_caracteristica);
        lv_select_caracteristica.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        unspc = intent.getStringExtra("productoUnspc");
        new GetUnspcCharacteristics(unspc, intent.getStringExtra("userName")).execute();

        button_siguiente = (Button) findViewById(R.id.button_siguiente);

    }

    public void onClick(View view) {

        SparseBooleanArray checked = lv_select_caracteristica.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<>();

        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        Intent intent = new Intent(this,
                ActivityAddCharacteristic.class);

        // Create a bundle object
        Bundle b = new Bundle();
        b.putStringArray("selectedItems", outputStrArr);

        // Add the bundle to the intent.
        intent.putExtras(b);
        intent.putExtra("unspcNumber",unspc);

        // start the ResultActivity
        startActivity(intent);
    }


    class GetUnspcCharacteristics extends AsyncTask<Void, Integer, Void> {
        String url;

        public GetUnspcCharacteristics(String unspc, String userName) {
            url = "http://54.165.43.110:8080/ProyectoFinalRest/products/products/getUnspcCharacteristicsForUser?" +
                    "unspc=" + unspc + "&user=" + userName;
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
                caracteristicas = gson.fromJson(inputStreamReader, caracteristicas.getClass());

            } catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ArrayAdapter<>(ActivityAddProduct.this, android.R.layout.simple_list_item_multiple_choice, caracteristicas.getList());
            lv_select_caracteristica.setAdapter(adapter);
        }
    }

}
