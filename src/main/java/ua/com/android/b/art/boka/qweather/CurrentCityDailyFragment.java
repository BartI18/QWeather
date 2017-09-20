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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.android.b.art.boka.qweather.consts.Const;

/**
 * Created by B'Art on 20.09.2017.
 */

public class CurrentCityDailyFragment extends Fragment {

    private View mainView;
    private TextView currentCityDailyTV;
    private ListView mainInformationDailyLV;
    private ArrayList<String> forecastList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.current_city_daily_fragment,container,false);

        currentCityDailyTV = mainView.findViewById(R.id.currentCityDailyTV);
        mainInformationDailyLV = mainView.findViewById(R.id.mainInformationDailyLV);

        currentCityDailyTV.setText(getActivity().getIntent().getStringExtra("nameCity"));

        connection();

        return mainView;
    }

    private void connection(){
        App.loadAPI().getDailyResult(getActivity().getIntent().getStringExtra("nameCity"), Const.appID).enqueue(new Callback<JDaily>() {
            @Override
            public void onResponse(Call<JDaily> call, Response<JDaily> response) {
                for (int i = 0; i < response.body().list.length-1 ; i++) {
                    forecastList.add(response.body().list[i].dt_txt.substring(0,response.body().list[i].dt_txt.length()-3)
                            + " Temperature: " + Math.round(response.body().list[i].main.temp-273)+ "°C");
                }
                mainInformationDailyLV.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,forecastList));
            }

            @Override
            public void onFailure(Call<JDaily> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
