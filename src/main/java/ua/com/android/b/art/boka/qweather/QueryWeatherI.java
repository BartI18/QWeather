package ua.com.android.b.art.boka.qweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by B'Art on 20.09.2017.
 */

public interface QueryWeatherI {
    @GET("/data/2.5/forecast")
    Call<JDaily> getDailyResult(@Query("q") String q, @Query("appid") String appID);

    @GET("/data/2.5/weather")
    Call<Json_Weather> getTodayResult(@Query("q") String q, @Query("appid") String appID);
}
