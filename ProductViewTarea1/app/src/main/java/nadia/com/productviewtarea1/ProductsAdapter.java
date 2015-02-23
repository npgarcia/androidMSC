package nadia.com.productviewtarea1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by ngarcia on 2/23/15.
 */
public class ProductsAdapter extends ArrayAdapter {

    public ProductsAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        TextView text = (TextView) view.findViewById(android.R.id.text1);
        text.setTextColor(view.getResources().getColor(R.color.textoCajon));
        return view;
    }
}
