package nadia.com.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

import nadia.com.promome.R;

/**
 * Created by ngarcia on 5/5/15.
 */
public class CharacteristicAdapter extends BaseAdapter {
    private ArrayList<Caracteristicas> caracteristicas = new ArrayList<>();
    private Context context;

    public CharacteristicAdapter (String[] generales, Context context, String unspc){
        this.context = context;

        for(int i = 0; i < generales.length; i++){
            Caracteristicas c = new Caracteristicas();
            c.setCaracteristica(generales[i]);
            this.caracteristicas.add(c);
            new GetCharacteristicUnit(unspc,generales[i],i).execute();
        }
    }

    @Override
    public int getCount() {
        return caracteristicas.size();
    }

    @Override
    public Object getItem(int position) {
        return caracteristicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Caracteristicas caracteristica = caracteristicas.get(position);

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.add_caracteristic_rows, parent, false);
        }

        TextView tv_key = (TextView)rowView.findViewById(R.id.tv_key);
        EditText et_value = (EditText) rowView.findViewById(R.id.et_value);
        TextView tv_units = (TextView) rowView.findViewById(R.id.tv_units);

        tv_key.setText(caracteristica.getCaracteristica());
        if(caracteristica.getValor() == null)
            et_value.setHint(R.string.llena_valor);
        else
            et_value.setText(caracteristica.getValor());
        tv_units.setText(caracteristica.getUnidad());


        return rowView;

    }

    class GetCharacteristicUnit extends AsyncTask<Void, Integer, Void> {
        String url;
        int position;
        String unidad;

        public GetCharacteristicUnit(String unspc, String characteristicName, int position) {
            url = "http://54.165.43.110:8080/ProyectoFinalRest/products/products/getCharacteristicUnit?unspc=" +
                    unspc + "&char=" + characteristicName;
            this.position = position;
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
                unidad = gson.fromJson(inputStreamReader, String.class);

            } catch (IOException e) {
                getRequest.abort();
                Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Caracteristicas c = caracteristicas.get(position);
            c.setUnidad(unidad);
            caracteristicas.set(position,c);
            notifyDataSetChanged();
        }
    }

    public void addItem(Caracteristicas c){
        caracteristicas.add(c);
        notifyDataSetChanged();
    }

    public ArrayList<Caracteristicas>getList(){
        return caracteristicas;
    }

}
