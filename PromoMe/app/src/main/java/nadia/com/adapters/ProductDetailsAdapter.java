package nadia.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nadia.com.promome.R;

/**
 * Created by ngarcia on 5/5/15.
 */
public class ProductDetailsAdapter extends BaseAdapter {
    private final Context context;
    private final Caracteristicas[] caracteristicas;

    public ProductDetailsAdapter(Caracteristicas[] products, Context context) {
        this.context = context;
        this.caracteristicas = products;
    }

    @Override
    public int getCount() {
        return caracteristicas.length;
    }

    @Override
    public Object getItem(int position) {
        return caracteristicas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Caracteristicas caract = caracteristicas[position];

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.see_characteristics_row, parent, false);
        }

        TextView tv_key = (TextView)rowView.findViewById(R.id.tv_key);
        TextView tv_value = (TextView) rowView.findViewById(R.id.tv_value);

        tv_key.setText(caract.getCaracteristica());
        tv_value.setText(caract.getValor());


        return rowView;
    }
}
