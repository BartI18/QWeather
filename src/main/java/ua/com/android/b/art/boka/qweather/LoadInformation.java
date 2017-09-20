package ua.com.android.b.art.boka.qweather;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.com.android.b.art.boka.qweather.consts.Const;

/**
 * Created by B'Art on 20.09.2017.
 */

public class LoadInformation {

    private String q = "";
    private Json_Weather json_Weather;


    public void setCity(String nameCity){
        this.q = nameCity;
    }

    protected Json_Weather loadTodayWeather(){

        App.loadAPI().getTodayResult(q, Const.appID).enqueue(new Callback<Json_Weather>() {

            @Override
            public void onResponse(Call<Json_Weather> call, Response<Json_Weather> response) {
                json_Weather = response.body();
                System.out.println(response.body().main.temp);
                System.out.println(q);
                //System.out.println(json_Weather.cod);
            }

            @Override
            public void onFailure(Call<Json_Weather> call, Throwable t) {

            }

        });
        return json_Weather;
    }
}
