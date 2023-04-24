package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityHomeScreenBinding;
import humble.slave.pts_b.features.presenter.services.ReceiveAndSendSMS;

public class HomeScreen extends AppCompatActivity {


    ActivityHomeScreenBinding binding;
    Intent nextIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
//        getActionBar().show();
        setContentView(binding.getRoot());
        settings(binding.settings);
        settings(binding.previousItems);
        settings(binding.traffic);
        settings(binding.weather);
        settings(binding.todoRemainder);
        settings(binding.receiverContacts);

        checkState(binding.startStop);


//        TODO : https://www.youtube.com/watch?v=u_EZRqOapf4


        checkUserPermission();

    }



    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    void checkUserPermission(){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void checkState(SwitchCompat startStop) {
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    Toast.makeText(HomeScreen.this, "toggled", Toast.LENGTH_SHORT).show();
                    startService(startStop);
                } else {
                    Toast.makeText(HomeScreen.this, "toggled", Toast.LENGTH_SHORT).show();
                    stopService(startStop);
                }
            }
        });
    }

    public void settings(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.getId() == binding.settings.getId()){
                    nextIntent = new Intent(getApplicationContext(), Settings_Activity.class);
                    startActivity(nextIntent);
                } else if (binding.previousItems.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), PreviousItems.class);
                    Toast.makeText(HomeScreen.this, "PREVIOUS PRESSED", Toast.LENGTH_SHORT).show();
                } else if (binding.traffic.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), Traffic.class);
                    Toast.makeText(HomeScreen.this, "TRAFFIC PRESSED", Toast.LENGTH_SHORT).show();
                } else if (binding.weather.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), Weather.class);
                    Toast.makeText(HomeScreen.this, "WEATHER PRESSED", Toast.LENGTH_SHORT).show();
                } else if (binding.todoRemainder.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), TodoReminder.class);
                    Toast.makeText(HomeScreen.this, "TODO/REMAINDER PRESSED", Toast.LENGTH_SHORT).show();
                } else if (binding.receiverContacts.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), ReceiverContacts.class);
                    Toast.makeText(HomeScreen.this, "RECEIVER CONTACTS PRESSED", Toast.LENGTH_SHORT).show();
                    startActivity(nextIntent);
                }
//

            }
        });
    }



    private void startService(View v) {
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("TOGGLE", "TOGGLED");

                Intent serviceIntent = new Intent(getApplicationContext(), ReceiveAndSendSMS.class);
                serviceIntent.putExtra("android.provider.Telephony.SMS_RECEIVED", "android.provider.Telephony.SMS_RECEIVED");
                ActivityCompat.requestPermissions(HomeScreen.this,new String[]{Manifest.permission.SEND_SMS},1);
                startService(serviceIntent);

            }
        });

    }

    public void stopService(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TOGGLE", "TOGGLED");
                Intent serviceIntent = new Intent(getApplicationContext(), ReceiveAndSendSMS.class);
                stopService(serviceIntent);
            }
        });

    }
}