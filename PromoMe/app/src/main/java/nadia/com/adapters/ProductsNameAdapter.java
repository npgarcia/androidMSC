package nadia.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import nadia.com.promome.R;

/**
 * Created by ngarcia on 5/3/15.
 */
public class ProductsNameAdapter extends BaseAdapter {
    private final Context context;
    private final List products;

    public ProductsNameAdapter(List products, Context context) {
        this.context = context;
        this.products = products;

    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object objectProduct = products.get(position);
        LinkedTreeMap<String,String> product = (LinkedTreeMap<String,String>) objectProduct;

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.see_products_rows, parent, false);
        }

        TextView tv_brand = (TextView)rowView.findViewById(R.id.tv_brand);
        TextView tv_product_name = (TextView) rowView.findViewById(R.id.tv_product_name);

        tv_product_name.setText(product.get("productName"));
        tv_brand.setText(product.get("brand"));


        return rowView;
    }

}
