package humble.slave.pts_b.features.presenter.recipientInfoPresenter;

import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.ReceiverContacts;

public class RecipientPresenterImpl implements RecipientPresenter {

    DataBase dataBase;
    ReceiverContacts temporaryContext;

    private final String RECEIVER_ID = "04";
    public RecipientPresenterImpl(DataBase dataBase, ReceiverContacts temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }
    @Override
    public void updateOnAddNumber(String number) {

        Cursor response = dataBase.getData(number);
        Boolean checkStateUpdateData;
        if(response.getCount()==1) {
            checkStateUpdateData = dataBase.updateSettingsData(RECEIVER_ID, number);
        }else{
            checkStateUpdateData = dataBase.insertSettingsData(RECEIVER_ID, number);
        }
        if(checkStateUpdateData){
            Toast.makeText(temporaryContext, "SUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(temporaryContext, "UNSUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }
        temporaryContext.closeMethod();

    }

    @Override
    public void doesNumberExist(EditText number) {
        Cursor response = dataBase.getData(RECEIVER_ID);
        if(response.getCount()==0){
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.addBtnVisibility(View.VISIBLE);
        }else{
            response.moveToNext();
            number.setText(response.getString(1));
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.updateBtnVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeNumber() {
        dataBase.deleteSettingsData(RECEIVER_ID);
        temporaryContext.closeMethod();
    }
}
