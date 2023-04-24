package humble.slave.pts_b.network;

import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;
import humble.slave.pts_b.features.model.data.trafficData.RouteDuration;
import humble.slave.pts_b.features.model.data.weatherData.WeatherInfoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("v8/routes")
    Call<RouteDuration> apiRouteDurationResponse(@Query("transportMode") String mode, @Query("origin") String coord, @Query("destination") String coord2, @Query("return") String type, @Query("apikey") String key);

    @GET("weather")
    Call<WeatherInfoResponse> apiWeatherInfoResponse(@Query("q") String cityName, @Query("appid") String APP_ID);

    @GET("/")
    Call<HomeServerResponse> apiHomeServerResponse();
}
