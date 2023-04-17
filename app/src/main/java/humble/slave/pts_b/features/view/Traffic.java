package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityTrafficBinding;
import humble.slave.pts_b.features.model.data.trafficData.RouteDuration;
import humble.slave.pts_b.network.API;
import humble.slave.pts_b.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Traffic extends AppCompatActivity {


    String URL = "https://router.hereapi.com/";
    ActivityTrafficBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrafficBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);



        API api = new RetrofitClient().getRetrofit(URL).create(API.class);

        String ID = "";


        Call<RouteDuration> call = api.apiRouteDurationResponse("car", "23.780974349417,90.39985977299754", "23.780843282749526,90.39884344357667", "summary", ID);
        call.enqueue(new Callback<RouteDuration>() {
            @Override
            public void onResponse(Call<RouteDuration> call, Response<RouteDuration> response) {
                if(response.body()!=null){
                    Toast.makeText(Traffic.this, response.body().getRoutes().get(0).getSections().get(0).getSummary().getDuration().toString(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Traffic.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RouteDuration> call, Throwable t) {
                Toast.makeText(Traffic.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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