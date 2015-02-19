package nadia.iteso.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ngarcia on 2/18/15.
 */
public class ListFragment extends android.app.ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String values[] = new String[]{"Nadia", "Juan", "Pedro", "Chana", "Pepe", "Julio", "Veronica"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String selected = (String) getListAdapter().getItem(position);
        DetailFragment detailFragments = (DetailFragment) getFragmentManager().findFragmentById(R.id.fragment_detail);
        if(detailFragments != null && detailFragments.isInLayout()){
            detailFragments.setText(selected);
        }else{
            Intent intent = new Intent(getActivity(), ActivityDetail.class);
            intent.putExtra("nombres",selected);
            startActivity(intent);
        }
    }
}
