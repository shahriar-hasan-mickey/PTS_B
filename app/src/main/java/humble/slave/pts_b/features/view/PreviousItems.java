package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import humble.slave.pts_b.databinding.ActivityPreviousItemsBinding;
import humble.slave.pts_b.databinding.ActivityWeatherBinding;

public class PreviousItems extends AppCompatActivity {

    ActivityPreviousItemsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviousItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);
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