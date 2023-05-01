package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityHomeScreenBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.homePresenter.HomeScreenPresenter;
import humble.slave.pts_b.features.presenter.homePresenter.HomeScreenPresenterImpl;
import humble.slave.pts_b.features.presenter.services.ReceiveAndSendSMS;
import humble.slave.pts_b.features.view.commonActivity.PreviousItems;
import humble.slave.pts_b.features.view.settingsActivity.SetPassword;

public class HomeScreen extends AppCompatActivity {


    ActivityHomeScreenBinding binding;
    Intent nextIntent;

    DataBase DB;
    HomeScreenPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
//        getActionBar().show();

        DB = new DataBase(this);

        presenter = new HomeScreenPresenterImpl(DB, this);

        presenter.findSwitchState("00", binding.startStop);
        onChangeState(binding.startStop);

        setContentView(binding.getRoot());
        settings(binding.settings);
        settings(binding.previousItems);
        settings(binding.traffic);
        settings(binding.weather);
        settings(binding.todoRemainder);
        settings(binding.receiverContacts);




//        TODO : https://www.youtube.com/watch?v=u_EZRqOapf4
        checkUserPermission();


    }

    public void toggler(Boolean state, SwitchCompat toggle){
        toggle.setChecked(state);
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


    private void onChangeState(SwitchCompat startStop) {
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String id = "";
                if(startStop.getId() == binding.startStop.getId()){
                    id = "00";
                }

                if(compoundButton.isChecked()){
                    if(presenter.isRecipientAdded("04")) {
                        Toast.makeText(HomeScreen.this, "toggled", Toast.LENGTH_SHORT).show();
                        presenter.updateOnChangeState(id, startStop.isChecked());
                        startService(startStop);
                    }else{
                        showAlertAndExit();
                    }
                } else {
                    Toast.makeText(HomeScreen.this, "toggled off", Toast.LENGTH_SHORT).show();
                    presenter.updateOnChangeState(id, startStop.isChecked());
                    stopService(startStop);
                }
            }
        });
    }

    private void showAlertAndExit() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(HomeScreen.this);
        builder.setTitle("Recipient number not added!!");
        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toggler(false, binding.startStop);
            }
        });
        builder.show();
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
                    startActivity(nextIntent);
                    Toast.makeText(HomeScreen.this, "PREVIOUS PRESSED", Toast.LENGTH_SHORT).show();
                } else if (binding.traffic.getId() == view.getId()) {
                    if(presenter.isSwitchedOn("01")) {
                        nextIntent = new Intent(getApplicationContext(), Traffic.class);
                        startActivity(nextIntent);
                        Toast.makeText(HomeScreen.this, "TRAFFIC PRESSED", Toast.LENGTH_SHORT).show();
                    }else{
                        alertMessage("GO TO SETTINGS AND SELECT TRAFFIC");
                    }
                } else if (binding.weather.getId() == view.getId()) {
                    if(presenter.isSwitchedOn("02")) {
                        nextIntent = new Intent(getApplicationContext(), Weather.class);
                        startActivity(nextIntent);
                        Toast.makeText(HomeScreen.this, "WEATHER PRESSED", Toast.LENGTH_SHORT).show();
                    }else{
                        alertMessage("GO TO SETTINGS AND SELECT WEATHER");
                    }
                } else if (binding.todoRemainder.getId() == view.getId()) {
                    if(presenter.isSwitchedOn("03")) {
                        nextIntent = new Intent(getApplicationContext(), TodoReminder.class);
                        startActivity(nextIntent);
                        Toast.makeText(HomeScreen.this, "TODO/REMAINDER PRESSED", Toast.LENGTH_SHORT).show();
                    }else{
                        alertMessage("GO TO SETTINGS AND SELECT REMAINDER");
                    }
                } else if (binding.receiverContacts.getId() == view.getId()) {
                    if(presenter.isSwitchedOn("04")) {
                        nextIntent = new Intent(getApplicationContext(), ReceiverContacts.class);
                        startActivity(nextIntent);
                        Toast.makeText(HomeScreen.this, "RECEIVER CONTACTS PRESSED", Toast.LENGTH_SHORT).show();
                    }else{
                        alertMessage("GO TO SETTINGS AND ADD RECEIVER ACCOUNT");
                    }
                }


//

            }
        });
    }

    private void alertMessage(String string){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(HomeScreen.this);
        builder.setTitle(string);
        builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        builder.show();
    }


    private void startService(View v) {
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("TOGGLE", "TOGGLED");
                Cursor to = DB.getData("04");
                to.moveToNext();

                Intent serviceIntent = new Intent(getApplicationContext(), ReceiveAndSendSMS.class);
                serviceIntent.putExtra("android.provider.Telephony.SMS_RECEIVED", "android.provider.Telephony.SMS_RECEIVED");
                serviceIntent.putExtra("to", to.getString(1));
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