package ua.com.android.b.art.boka.qweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.android.b.art.boka.qweather.consts.Const;

/**
 * Created by B'Art on 20.09.2017.
 */

public class SearchCityFragment extends Fragment {

    private View mainView;
    private EditText ourCityET;
    private Button searchCityBtn;

    private LoadInformation loadInformation;
    private Json_Weather json_weather;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_city_fragment,container,false);
        loadInformation = new LoadInformation();
        init();

        searchCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.hasConnection(getContext())==false)
                    Toast.makeText(getActivity(), "Error ethernet connection", Toast.LENGTH_SHORT).show();
                else if(ourCityET.getText().length()<2)
                    Toast.makeText(getActivity(), "Little name city", Toast.LENGTH_SHORT).show();
                else
                App.loadAPI().getTodayResult(String.valueOf(ourCityET.getText()), Const.appID).enqueue(new Callback<Json_Weather>() {
                    @Override
                    public void onResponse(Call<Json_Weather> call, Response<Json_Weather> response) {

                        Intent currentCity = new Intent(getContext(),CurrentCityActivity.class);
                        ArrayList<String> mainInfo = new ArrayList<>(5);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));

                        if(response.body()!=null){
                            json_weather = response.body();
                            mainInfo.add("Temperature: "+Math.round(json_weather.main.temp-273)+"°C");
                            mainInfo.add("Humidity: "+json_weather.main.humidity+"%");
                            Date date = new Date(json_weather.sys.sunrise*1000L);
                            mainInfo.add("Sunrise: "+sdf.format(date));
                            date = new Date(json_weather.sys.sunset*1000L);
                            mainInfo.add("Sunset: "+sdf.format(date));
                            mainInfo.add("Country: "+json_weather.sys.country);

                            AddNewCityI addNewCityI = (AddNewCityI) getActivity();
                            addNewCityI.addNewCity(json_weather.name);

                            currentCity.putExtra("nameCity",json_weather.name);
                            currentCity.putExtra("mainInfo",mainInfo.toArray(new String[5]));
                            startActivity(currentCity);
                        }
                    }

                    @Override
                    public void onFailure(Call<Json_Weather> call, Throwable t) {

                    }
                });
            }
        });


        return mainView;
    }

    private void init(){
        ourCityET = mainView.findViewById(R.id.ourCityET);
        searchCityBtn = mainView.findViewById(R.id.searchCityBtn);
    }

}
