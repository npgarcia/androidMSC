package nadia.iteso.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ngarcia on 2/18/15.
 */
public class DetailFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("sesion 5", "DetailFragments.onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setText(String item) {
        TextView tv = (TextView) getView().findViewById(R.id.fragment_detail_textview);
        tv.setText(item);
    }
}
