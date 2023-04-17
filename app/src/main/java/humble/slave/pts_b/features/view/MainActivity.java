package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import humble.slave.pts_b.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent home_intent = new Intent(this, HomeScreen.class);
        startActivity(home_intent);
        finish();
    }
}