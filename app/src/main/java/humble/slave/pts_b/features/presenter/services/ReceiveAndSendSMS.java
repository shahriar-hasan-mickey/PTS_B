package humble.slave.pts_b.features.presenter.services;

import static humble.slave.pts_b.features.presenter.services.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

//import humble.slave.pts_b.Manifest;
//import com.twilio.Twilio;

import java.util.HashMap;
import java.util.Map;

import humble.slave.pts_b.R;
import humble.slave.pts_b.common.RequestCompleteListener;
import humble.slave.pts_b.features.model.CallServer.CallServer;
import humble.slave.pts_b.features.model.CallServer.CallServerImpl;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;
import humble.slave.pts_b.features.model.data.twilioAccountData.ResponseTwilio;
import humble.slave.pts_b.features.view.HomeScreen;
import humble.slave.pts_b.network.RetrofitClient;

public class ReceiveAndSendSMS extends Service {



    IntentFilter filter;
    IntentFilter filter2;
    DataBase DB;

    String to = "+8801975017102";

    SmsManager smsManagerSend = SmsManager.getDefault();
    CallServer callServer = new CallServerImpl();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("data", "Inside service");
        twilioMessage("AC755c99a7e41c4170fb9bf3e7276ead58",
                "b54a366b22dc5c324029b90ae2273324",
                "+13132173712",
                "+01975017102",
                "test");

        Toast.makeText(this, "SERVICE ACTIVATED", Toast.LENGTH_SHORT).show();

        filter = new IntentFilter("com.example.BroadCastInService");

        filter2 = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        registerReceiver(receiver, filter);
        registerReceiver(receiver2, filter2);
        registerReceiver(receiver4, filter2);


//        TODO : https://www.youtube.com/watch?v=FbpD5RZtbCc
//        TODO : https://www.youtube.com/watch?v=FbpD5RZtbCc&t=784s

        startService();


        return START_NOT_STICKY;
    }


    private void startService(){
        String input = "Service Running";
        Intent notificationIntent = new Intent(this, HomeScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            if(intent.getAction().equalsIgnoreCase("com.example.BroadCastInService")){
                String msg = b.getString("msg");
                Toast.makeText(context, msg+"\n[FROM THE SERVICE CLASS]", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private final BroadcastReceiver receiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
                //action for sms received
                Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
            }
            else if(action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
                //action for phone state changed
                Toast.makeText(context, "something", Toast.LENGTH_SHORT).show();
            }
        }
    };



    BroadcastReceiver receiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();

            DB = new DataBase(context);

//            String to = b.getString("to");

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



                        if(messages[i].getOriginatingAddress().equals(to)){
                            Toast.makeText(context, "MATCHED", Toast.LENGTH_SHORT).show();
                            callServer.getTrafficAndTemperature(getApplicationContext(), new RequestCompleteListener<HomeServerResponse>() {
                                @Override
                                public void onRequestSuccess(HomeServerResponse data) {

                                    Cursor sid = DB.getData("07");
                                    if(sid.getCount()!=0) {
                                        Cursor response = DB.getData("10");
                                        response.moveToNext();
                                        if(response.getString(1).equals("ON")) {
                                            Cursor token = DB.getData("08");
                                            Cursor from = DB.getData("09");

                                            sid.moveToNext();
                                            token.moveToNext();
                                            from.moveToNext();
//                                            twilioMessage(
//                                                    sid.getString(1),
//                                                    token.getString(1),
//                                                    from.getString(1),
//                                                    to,
//                                                    packMessage(data));
                                            defaultMessage(packMessage(data), to);
                                        }else {
                                            defaultMessage(packMessage(data), to);
                                        }
                                    }else{
                                        defaultMessage(packMessage(data), to);
                                    }

                                }

                                @Override
                                public void onRequestFailed(String errorMessage) {
                                    Toast.makeText(ReceiveAndSendSMS.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }
        }
    };



    public void twilioMessage(String ACCOUNT_SID, String AUTH_TOKEN, String FROM, String TO, String BODY){
        Toast.makeText(this, "Inside twilio message option", Toast.LENGTH_SHORT).show();

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);


        Map<String, String> data = new HashMap<>();
        data.put("From", FROM);
        data.put("To", TO);
        data.put("Body", BODY);
        Toast.makeText(this, "Trying to send", Toast.LENGTH_SHORT).show();
        callServer.sendSMS(ACCOUNT_SID, base64EncodedCredentials, data, new RequestCompleteListener<ResponseTwilio>() {
            @Override
            public void onRequestSuccess(ResponseTwilio data) {
                if(data.getBody()!=null){
//                    TODO : SAVE THE DATA
                    Toast.makeText(ReceiveAndSendSMS.this, "Success", Toast.LENGTH_SHORT).show();
                    storeData(BODY);
                }
            }

            @Override
            public void onRequestFailed(String errorMessage) {
                Toast.makeText(ReceiveAndSendSMS.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void defaultMessage(String message, String number){

        smsManagerSend.sendTextMessage(number, null, message, null ,null);
//        TODO : save the data
        storeData(message);
    }

    private String packMessage(HomeServerResponse data){
        final String[] message = new String[1];
        message[0] = ("Traffic Leve : "+data.getMessage().getSet1().getTrfficLevel() + "\n" +
                "Traffic Motion : "+data.getMessage().getSet1().getTrafficMotion() + "\n" +
                "Temperature : "+data.getMessage().getSet1().getTemperature() + " Degrees");
        return message[0];
    }


    private void storeData(String BODY){
        Boolean response = DB.insertMessage(BODY);
        if(response){
            Toast.makeText(ReceiveAndSendSMS.this, "Insert Success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ReceiveAndSendSMS.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
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
        unregisterReceiver(receiver4);
        Toast.makeText(this, "SERVICE DEACTIVATED", Toast.LENGTH_SHORT).show();
    }
}
