package humble.slave.pts_b.features.presenter.homePresenter;

import android.database.Cursor;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.HomeScreen;

public class HomeScreenPresenterImpl implements HomeScreenPresenter {
    DataBase dataBase;
    private HomeScreen temporaryContext;

    public HomeScreenPresenterImpl(DataBase dataBase, HomeScreen temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }

    @Override
    public void findSwitchState(String id, SwitchCompat toggleSwitch) {
        Cursor response = dataBase.getData(id);
        if(response.getCount()==0){
            Toast.makeText(temporaryContext, "NOT SET", Toast.LENGTH_SHORT).show();
        }else{
            response.moveToNext();
            if(response.getString(1).equals("ON")){
                temporaryContext.toggler(true, toggleSwitch);
            } else if (response.getString(1).equals("OFF")){
                temporaryContext.toggler(false, toggleSwitch);

            }
        }
    }

    @Override
    public void updateOnChangeState(String id, Boolean switchOptionState) {
        Cursor response = dataBase.getData(id);
        Boolean checkStateUpdateData;
        if(response.getCount()==1) {
            if(switchOptionState) {
                checkStateUpdateData = dataBase.updateSettingsData(id, "ON");
            }else{
                checkStateUpdateData = dataBase.updateSettingsData(id, "OFF");
            }
        }else{
            if(switchOptionState) {
                checkStateUpdateData = dataBase.insertSettingsData(id, "ON");
            }else{
                checkStateUpdateData = dataBase.insertSettingsData(id, "OFF");
            }
        }
        if(checkStateUpdateData){
            Toast.makeText(temporaryContext, "SUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Settings_Activity.this, "", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(temporaryContext, "UNSUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Boolean isRecipientAdded(String id){
        Cursor response = dataBase.getData(id);
        if(response.getCount()==0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Boolean isSwitchedOn(String id) {
        Cursor response = dataBase.getData(id);
        if(response.getCount()==0){
            return false;
        }else{
            response.moveToNext();
            return !response.getString(1).equals("OFF");
        }
    }
}
