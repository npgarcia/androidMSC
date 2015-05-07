package nadia.com.promome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import nadia.com.adapters.ProductDetail;
import nadia.com.adapters.ProductDetailsAdapter;
import nadia.com.adapters.ProductsNameAdapter;


public class ActivityGetProducts extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView lista_productos;
    String unspc, descripcion, userName;
    TextView text_productName;
    TextView tv_no_products;
    View load_progress;
    ArrayList productos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_products);

        Intent intent = getIntent();
        unspc = intent.getStringExtra("productoUnspc");
        descripcion = intent.getStringExtra("productoNombre");
        userName = intent.getStringExtra("userName");

        load_progress = findViewById(R.id.load_progress);
        lista_productos = (ListView) findViewById(R.id.lista_productos);
        tv_no_products = (TextView) findViewById(R.id.tv_no_products);
        text_productName = (TextView) findViewById(R.id.text_productName);
        text_productName.setText(descripcion);

        getProducts();
    }

    private void getProducts() {
        showProgress(true);
        new GetProducts(unspc, userName).execute();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            lista_productos.setVisibility(show ? View.GONE : View.VISIBLE);
            lista_productos.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    lista_productos.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            load_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            load_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    load_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            load_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            lista_productos.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ProductDetail detail = new ProductDetail(productos.get(position));
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView = inflater.inflate(R.layout.dialog_product_detail, parent, false);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(alertView);
        alertDialogBuilder.setTitle(getString(R.string.detalle_producto) + " " + detail.getNombre());
        alertDialogBuilder.setNeutralButton("OK", null);

        TextView tv_proveedor = (TextView) alertView.findViewById(R.id.tv_proveedor);
        if (!detail.getProveedor().equals("")) {
            tv_proveedor.setText(detail.getProveedor());
            tv_proveedor.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        }

        TextView tv_nombre = (TextView) alertView.findViewById(R.id.tv_nombre);
        tv_nombre.setText(detail.getNombre());

        TextView tv_marca = (TextView) alertView.findViewById(R.id.tv_marca);
        tv_marca.setText(detail.getMarca());

        ListView lv_caracteristicas = (ListView) alertView.findViewById(R.id.lv_caracteristicas);
        lv_caracteristicas.setAdapter(new ProductDetailsAdapter(detail.getCaracteristicas(),this));

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();

    }

    public class GetProducts extends AsyncTask<Void, Void, Boolean> {
        String url;

        GetProducts(String unspcId, String user) {
            url = "http://54.165.43.110:8080/ProyectoFinalRest/products/products/getProductsByUNSPC?unspc=" + unspcId + "&user=" + user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpGet getRequest = new HttpGet(url);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            try {
                HttpResponse getResponse = httpClient.execute(getRequest);
                final int statusCode = getResponse.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w(getClass().getSimpleName(), "Error " + statusCode
                            + " for URL " + url);
                    return false;
                }

                HttpEntity getResponseEntity = getResponse.getEntity();
                InputStream httpResponseStream = getResponseEntity.getContent();
                Reader inputStreamReader = new InputStreamReader(
                        httpResponseStream);
                Gson gson = new Gson();
                productos = gson.fromJson(inputStreamReader, productos.getClass());

            } catch (IOException eio) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            showProgress(false);

            if (success && productos.size() > 0) {
                lista_productos.setAdapter(new ProductsNameAdapter(productos, ActivityGetProducts.this));
                lista_productos.setOnItemClickListener(ActivityGetProducts.this);
                return;
            }

            tv_no_products.setVisibility(View.VISIBLE);
            lista_productos.setVisibility(View.GONE);

        }
    }
}
