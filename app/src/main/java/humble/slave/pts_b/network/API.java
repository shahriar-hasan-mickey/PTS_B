package humble.slave.pts_b.network;

import java.util.Map;

import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;
import humble.slave.pts_b.features.model.data.trafficData.RouteDuration;
import humble.slave.pts_b.features.model.data.twilioAccountData.ResponseTwilio;
import humble.slave.pts_b.features.model.data.weatherData.WeatherInfoResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("v8/routes")
    Call<RouteDuration> apiRouteDurationResponse(@Query("transportMode") String mode, @Query("origin") String coord, @Query("destination") String coord2, @Query("return") String type, @Query("apikey") String key);

    @GET("weather")
    Call<WeatherInfoResponse> apiWeatherInfoResponse(@Query("q") String cityName, @Query("appid") String APP_ID);

    @GET("/")
    Call<HomeServerResponse> apiHomeServerResponse();

//    @POST("/")
//    Call<ResponseTwilio> sendMessage(String ACCOUNT_SID, String base64EncodedCredentials, String data);

    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/SMS/Messages")
    Call<ResponseTwilio> sendMessage(
            @Path("ACCOUNT_SID") String accountSId,
            @Header("Authorization") String signature,
            @FieldMap Map<String, String> metadata
    );
}
