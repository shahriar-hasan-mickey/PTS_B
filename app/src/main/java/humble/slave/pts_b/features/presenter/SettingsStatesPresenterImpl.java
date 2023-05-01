package humble.slave.pts_b.features.presenter;

import android.database.Cursor;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.Settings_Activity;

public class SettingsStatesPresenterImpl implements SettingsStatespresenter {

    DataBase dataBase;
    private Settings_Activity temporaryContext;

    public SettingsStatesPresenterImpl(DataBase dataBase, Settings_Activity temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }

    @Override
    public void findSwitchState(String id, Switch toggleSwitch) {
        Cursor response = dataBase.getData(id);
        if(response.getCount()==0){
            Toast.makeText(temporaryContext, "NOT SET", Toast.LENGTH_SHORT).show();
        }else{
            if(id.equals("07")){
                temporaryContext.setVisibilityOfSMSmode(View.VISIBLE);
                id = "10";
                response = dataBase.getData(id);
                if(response.getCount()==0){
                    updateOnChangeState(id, false);
                }
                response = dataBase.getData(id);
                response.moveToNext();
                temporaryContext.togglerSMSmode(response.getString(1).equals("ON") ? true : false);
            }else {
                response.moveToNext();
                if (response.getString(1).equals("ON")) {
                    temporaryContext.toggler(true, toggleSwitch);
                } else if (response.getString(1).equals("OFF")) {
                    temporaryContext.toggler(false, toggleSwitch);
                }
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


}
