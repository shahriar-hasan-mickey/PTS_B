package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityWeatherBinding;
import humble.slave.pts_b.features.model.data.weatherData.WeatherInfoResponse;
import humble.slave.pts_b.network.API;
import humble.slave.pts_b.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends AppCompatActivity {

    ActivityWeatherBinding binding;
    String URL = "https://api.openweathermap.org/data/2.5/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);


        
        API api = new RetrofitClient().getRetrofit(URL).create(API.class);

        String ID = "";

        Call<WeatherInfoResponse> call = api.apiWeatherInfoResponse("dhaka", ID);

        call.enqueue(new Callback<WeatherInfoResponse>() {
            @Override
            public void onResponse(Call<WeatherInfoResponse> call, Response<WeatherInfoResponse> response) {
                if(response.body()!=null){
                    Toast.makeText(Weather.this, String.valueOf(response.body().getMain().getTemp() -273.15), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Weather.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherInfoResponse> call, Throwable t) {
                Toast.makeText(Weather.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void back(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}