package ua.com.android.b.art.boka.qweather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.android.b.art.boka.qweather.consts.Const;

/**
 * Created by B'Art on 20.09.2017.
 */

public class ListCityFragment extends Fragment {

    private View mainView;
    private ListView citiesListLV;
    private ArrayList<String> citiesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.list_city_fragment,container,false);
        citiesListLV = mainView.findViewById(R.id.citiesListLV);

        updateList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, citiesList);
        citiesListLV.setAdapter(arrayAdapter);

        actionFromList();

        return mainView;
    }

    /**
     * A special method for updating our list.
     */
    public void updateList(){
        citiesList.removeAll(citiesList);
        SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
        Map<String,String> listMap = new HashMap<>();
        listMap.putAll((Map<? extends String, ? extends String>) sh.getAll());
        for (Map.Entry<String, String> entry : listMap.entrySet()) {
            citiesList.add(entry.getValue());
        }
    }

    private void actionFromList(){
        citiesListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                App.loadAPI().getTodayResult(citiesList.get(i), Const.appID).enqueue(new Callback<Json_Weather>() {
                    @Override
                    public void onResponse(Call<Json_Weather> call, Response<Json_Weather> response) {

                        Json_Weather json_weather;

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

        citiesListLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int current, long l) {
                new AlertDialog.Builder(getActivity()).setTitle("Remove?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sh  = getActivity().getPreferences(Context.MODE_PRIVATE);
                        sh.edit().remove("key_"+citiesList.get(current)).apply();
                        System.out.println(current);
                        updateList();
                        citiesListLV.deferNotifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                return false;
            }
        });
    }

}
