package humble.slave.pts_b.services;

import static humble.slave.pts_b.services.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Locale;

//import humble.slave.pts_b.Manifest;
import humble.slave.pts_b.R;
import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;
import humble.slave.pts_b.features.view.HomeScreen;
import humble.slave.pts_b.network.API;
import humble.slave.pts_b.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiveAndSendSMS extends Service {

    private static final String TAG = ReceiveAndSendSMS.class.getSimpleName();
    public static final String pdu_type = "pdus";

    TextToSpeech textToSpeech;
    IntentFilter filter;
    IntentFilter filter2;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            if(intent.getAction().equalsIgnoreCase("com.example.BroadCastInService")){
                String msg = b.getString("msg");
                Toast.makeText(context, msg+"\n[FROM THE SERVICE CLASS]", Toast.LENGTH_SHORT).show();
            }
        }
    };;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("data", "Inside service");

        filter = new IntentFilter("com.example.BroadCastInService");

        filter2 = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, filter);
        registerReceiver(receiver2, filter2);

        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver4, filter);

//        registerReceiver(receiver4, filter2);




//        checkUserPermission(intent.getExtras().getString("android.provider.Telephony.SMS_RECEIVED"));

//
//        return START_NOT_STICKY;
//        TODO : https://www.youtube.com/watch?v=FbpD5RZtbCc

        String input = "Service Running";
        Intent notificationIntent = new Intent(this, HomeScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }



    private final BroadcastReceiver receiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
                Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
                //action for sms received
            }
            else if(action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
                //action for phone state changed
                Toast.makeText(context, "something", Toast.LENGTH_SHORT).show();
            }
        }
    };



    BroadcastReceiver receiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("data", "Received");
                // Get the SMS message.
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs;
                String strMessage = "";
                String format = bundle.getString("format");
                // Retrieve the SMS message received.
                Object[] pdus = (Object[]) bundle.get(pdu_type);
                if (pdus != null) {
                    // Check the Android version.
                    boolean isVersionM =
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
                    // Fill the msgs array.
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        // Check Android version and use appropriate createFromPdu.
                        if (isVersionM) {
                            // If Android version M or newer:
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                        } else {
                            // If Android version L or older:
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        }
                        // Build the message to show.
                        strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                        strMessage += " :" + msgs[i].getMessageBody() + "\n";
                        // Log and display the SMS message.
                        Log.d("TAG", "onReceive: " + strMessage);
                        Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                        if(msgs[i].getOriginatingAddress().equals("+8801975017192")){
                            callServer();
                        }
                    }
                }
            }
        };

    private void callServer() {
        API api = new RetrofitClient().getRetrofit("http://192.168.1.10:8080/").create(API.class);

        Call<HomeServerResponse> call = api.apiHomeServerResponse();
        call.enqueue(new Callback<HomeServerResponse>() {
            @Override
            public void onResponse(Call<HomeServerResponse> call, Response<HomeServerResponse> response) {
                if(response.body()!=null){

                }
            }

            @Override
            public void onFailure(Call<HomeServerResponse> call, Throwable t) {

            }
        });
    }


    BroadcastReceiver receiver4 = new BroadcastReceiver() {
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
                        Toast.makeText(context, senderNum + " : " + message + "\n[FROM SERVICE]", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };





//    void checkUserPermission(String permission){
////        since min sdk is 24 no need to set constrain for checking if sdk is >= 23
//        if(checkSelfPermission(this, Manifest.permission.))
//    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        Log.i("INSIDE SERVICE", "Listening");
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//        filter.addAction("your_action_strings"); //further more
//        filter.addAction("your_action_strings"); //further more
//
//        registerReceiver(receiver, filter);
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
//        filter = new IntentFilter();
//        filter.addAction("android.provider.Telephony.SMS_RECEIVED");

//        registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(receiver2);
    }
}
