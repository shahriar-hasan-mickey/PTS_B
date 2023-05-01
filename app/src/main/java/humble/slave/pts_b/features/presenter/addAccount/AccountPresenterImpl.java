package humble.slave.pts_b.features.presenter.addAccount;

import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.settingsActivity.AddTwilioAccount;

public class AccountPresenterImpl implements AccountPresenter{

    DataBase dataBase;
    private final AddTwilioAccount temporaryContext;
    private final String ACCOUNT_SID_ID = "07";
    private final String AUTH_TOKEN_ID = "08";
    private final String ACCOUNT_NUMBER = "09";

    public AccountPresenterImpl(DataBase dataBase, AddTwilioAccount temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }


    @Override
    public void updateOnAddAccount(String SID, String TOKEN, String NUMBER) {
        Cursor response = dataBase.getData(ACCOUNT_SID_ID);
        Boolean checkStateUpdateData1, checkStateUpdateData2, checkStateUpdateData3;
        if(response.getCount()==1) {
            checkStateUpdateData1 = dataBase.updateSettingsData(ACCOUNT_SID_ID, SID);
            checkStateUpdateData2 = dataBase.updateSettingsData(AUTH_TOKEN_ID, TOKEN);
            checkStateUpdateData3 = dataBase.updateSettingsData(ACCOUNT_NUMBER, NUMBER);
        }else{
            checkStateUpdateData1 = dataBase.insertSettingsData(ACCOUNT_SID_ID, SID);
            checkStateUpdateData2 = dataBase.insertSettingsData(AUTH_TOKEN_ID, TOKEN);
            checkStateUpdateData3 = dataBase.insertSettingsData(ACCOUNT_NUMBER, NUMBER);
        }
        if(checkStateUpdateData1 && checkStateUpdateData2 && checkStateUpdateData3){
            Toast.makeText(temporaryContext, "SUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(temporaryContext, "UNSUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }
        temporaryContext.closeMethod();
    }

    public void doesAccountExist(EditText SID, EditText TOKEN){
        Cursor response1 = dataBase.getData(ACCOUNT_SID_ID);
        Cursor response2 = dataBase.getData(AUTH_TOKEN_ID);
        if(response1.getCount()==0 && response2.getCount()==0){
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.addBtnVisibility(View.VISIBLE);
        }else{
            response1.moveToNext();
            SID.setText(response1.getString(1));
//            response = dataBase.getData(AUTH_TOKEN_ID);
            response2.moveToNext();
            TOKEN.setText(response2.getString(1));
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.updateBtnVisibility(View.VISIBLE);
        }
    }
    public void removeAccount(){
        dataBase.deleteSettingsData(ACCOUNT_SID_ID);
        dataBase.deleteSettingsData(AUTH_TOKEN_ID);
        temporaryContext.closeMethod();
    }
}
