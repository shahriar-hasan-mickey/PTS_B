package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityHomeScreenBinding;
import humble.slave.pts_b.services.ReceiveAndSendSMS;

public class HomeScreen extends AppCompatActivity {


    ActivityHomeScreenBinding binding;
    IntentFilter filter;
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

//        Intent serviceIntent = new Intent(this, ReceiveAndSendSMS.class);

        checkState(binding.startStop);

        filter = new IntentFilter("com.example.BroadCastInService");
        registerReceiver(receiver, filter);

        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver2, filter);
//
//        TODO : https://www.youtube.com/watch?v=u_EZRqOapf4


        checkUserPermission();

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle b = intent.getExtras();
                if(intent.getAction().equalsIgnoreCase("com.example.BroadCastInService")){
                    String msg = b.getString("msg");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }
        };


    BroadcastReceiver receiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
                if(b != null){
                    final Object[] pdusObj = (Object[]) b.get("pdus");
                    SmsMessage[] messages = new SmsMessage[pdusObj.length];
                    for(int i = 0; i < messages.length; i++){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            String format = b.getString("format");
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        } else {
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        }
                        String senderNum = messages[i].getOriginatingAddress();
                        String message = messages[i].getMessageBody();
                        Toast.makeText(context, senderNum + " : " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    void checkUserPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if((ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    Toast.makeText(this, "denailed", Toast.LENGTH_SHORT).show();
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
                    startService(startStop);
                } else {
                    stopService(startStop);
                }
            }
        });
    }

    public void settings(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(Home_Screen_i.this, "clicked", Toast.LENGTH_SHORT).show();
                if(view.getId() == binding.settings.getId()){
                    nextIntent = new Intent(getApplicationContext(), Settings_Activity.class);
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
//                    nextIntent = new Intent(getApplicationContext(), ReceiverContacts.class);
                    nextIntent = new Intent();
                    nextIntent.setAction("com.example.BroadCastInService");
                    nextIntent.putExtra("msg", "This is a test message being broadcast");
                    sendBroadcast(nextIntent);
//                    Toast.makeText(HomeScreen.this, "RECEIVER CONTACTS PRESSED", Toast.LENGTH_SHORT).show();
                }
//                startActivity(nextIntent);

            }
        });
    }


    private void startService(View v) {
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("TOGGLE", "TOGGLED");

//                String input = edtInput.getText().toString();
                Intent serviceIntent = new Intent(getApplicationContext(), ReceiveAndSendSMS.class);
                serviceIntent.putExtra("android.provider.Telephony.SMS_RECEIVED", "android.provider.Telephony.SMS_RECEIVED");
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