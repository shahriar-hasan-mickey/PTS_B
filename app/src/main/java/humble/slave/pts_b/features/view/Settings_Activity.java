package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivitySettingsBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.SettingsStatesPresenterImpl;
import humble.slave.pts_b.features.presenter.SettingsStatespresenter;
import humble.slave.pts_b.features.view.commonActivity.PreviousItems;
import humble.slave.pts_b.features.view.settingsActivity.AddTwilioAccount;
import humble.slave.pts_b.features.view.settingsActivity.SetPassword;

public class Settings_Activity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    DataBase DB;
    SettingsStatespresenter presenter;

    Intent nextIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);

        setVisibilityOfSMSmode(View.GONE);
        DB = new DataBase(this);
        presenter = new SettingsStatesPresenterImpl(DB, this);

        presenter.findSwitchState("01", binding.toggleTrafficLight);
        presenter.findSwitchState("02", binding.toggleWeather);
        presenter.findSwitchState("03", binding.toggleTodoRemainder);




        onChangeState(binding.toggleTrafficLight);
        onChangeState(binding.toggleWeather);
        onChangeState(binding.toggleTodoRemainder);

        onSMSmodeChangeState(binding.accountSelector);
        presenter.findSwitchState("07", null);

        options(binding.setPassword);
        options(binding.addTwilioAccount);
        options(binding.addReceiverContact);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityOfSMSmode(View.GONE);
        presenter.findSwitchState("07", null);
    }

    public void setVisibilityOfSMSmode(int visibility){
        binding.accountSelector.setVisibility(visibility);
    }

    private void back(ImageView goBack) {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void onChangeState(Switch switchOption) {
        switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String id = "";
                if(switchOption.getId() == binding.toggleTrafficLight.getId()){
                    id = "01";
                } else if (switchOption.getId() == binding.toggleWeather.getId()) {
                    id = "02";
                } else if (switchOption.getId() == binding.toggleTodoRemainder.getId()) {
                    id = "03";
                }
                if(id.equals("")){
                    Toast.makeText(Settings_Activity.this, "SOME ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                }else {
                    presenter.updateOnChangeState(id, switchOption.isChecked());
                }
            }
        });
    }

    private void onSMSmodeChangeState(SwitchCompat switchOption){
        switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String id = "10";
                presenter.updateOnChangeState(id, switchOption.isChecked());
            }
        });
    }

    public void togglerSMSmode(Boolean state){
        binding.accountSelector.setChecked(state);
    }

    public void toggler(Boolean state, Switch toggle){
        toggle.setChecked(state);
    }


    public void options(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.addReceiverContact.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), ReceiverContacts.class);
                    Toast.makeText(Settings_Activity.this, "ADD RECEIVER INFO", Toast.LENGTH_SHORT).show();
                } else if (binding.setPassword.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), SetPassword.class);
                    Toast.makeText(Settings_Activity.this, "SET PASSWORD", Toast.LENGTH_SHORT).show();
                } else if (binding.previousItems.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), PreviousItems.class);
                    Toast.makeText(Settings_Activity.this, "PREVIOUS ITEMS", Toast.LENGTH_SHORT).show();
                } else if (binding.addTwilioAccount.getId() == view.getId()) {
                    nextIntent = new Intent(getApplicationContext(), AddTwilioAccount.class);
                    Toast.makeText(Settings_Activity.this, "ADD ACCOUNT", Toast.LENGTH_SHORT).show();
                }
                startActivity(nextIntent);


            }
        });
    }




}