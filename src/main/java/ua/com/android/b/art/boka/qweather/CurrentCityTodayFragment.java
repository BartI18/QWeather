package ua.com.android.b.art.boka.qweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by B'Art on 20.09.2017.
 */

public class CurrentCityTodayFragment extends Fragment {

    private View mainView;
    private TextView currentCityTV;
    private ListView mainInformationLV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.current_city_today_fragment,container,false);

        currentCityTV = mainView.findViewById(R.id.currentCityTV);
        mainInformationLV = mainView.findViewById(R.id.mainInformationLV);

        currentCityTV.setText(getActivity().getIntent().getStringExtra("nameCity"));

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,
                getActivity().getIntent().getStringArrayExtra("mainInfo"));
        mainInformationLV.setAdapter(arrayAdapter);

        return mainView;
    }
}
