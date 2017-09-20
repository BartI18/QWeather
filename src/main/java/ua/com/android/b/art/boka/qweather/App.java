package ua.com.android.b.art.boka.qweather;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by B'Art on 20.09.2017.
 */

public class App extends Application {

    private Retrofit retrofit;
    private static QueryWeatherI queryWeather;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        queryWeather = retrofit.create(QueryWeatherI.class);
    }

    protected static QueryWeatherI loadAPI(){
        return queryWeather;
    }
}
