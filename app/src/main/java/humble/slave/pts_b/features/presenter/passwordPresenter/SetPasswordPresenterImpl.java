package humble.slave.pts_b.features.presenter.passwordPresenter;

import android.database.Cursor;
import android.view.View;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.settingsActivity.SetPassword;

public class SetPasswordPresenterImpl implements SetPasswordPresenter {
    DataBase dataBase;
    private SetPassword temporaryContext;

    private String passwordID = "05";
    public SetPasswordPresenterImpl(DataBase dataBase, SetPassword temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }



    @Override
    public void updateOnSetPassword(String password) {
        Cursor response = dataBase.getData(passwordID);
        Boolean checkStateUpdateData;
        if(response.getCount()==1) {
            checkStateUpdateData = dataBase.updateSettingsData(passwordID, md5(password));
        }else{
           checkStateUpdateData = dataBase.insertSettingsData(passwordID, md5(password));
        }
        if(checkStateUpdateData){
            Toast.makeText(temporaryContext, "SUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(Settings_Activity.this, "", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(temporaryContext, "UNSUCCESSFUL INSERT/UPDATE", Toast.LENGTH_SHORT).show();
        }
        temporaryContext.closeMethod();
    }

    public void isPasswordSet(){
        Cursor response = dataBase.getData(passwordID);
        if(response.getCount()==0){
            temporaryContext.progressBarVisibility(View.GONE);
//            temporaryContext.contentVisibility(View.VISIBLE);
            temporaryContext.setBtnVisibility(View.VISIBLE);
        }else{
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.passwordPrompt();
        }
    }

    public void checkPassword(String pwd){
        Cursor response = dataBase.getData(passwordID);
        response.moveToNext();
        if(response.getString(1).equals(md5(pwd))){
            temporaryContext.progressBarVisibility(View.GONE);
            temporaryContext.removeBtnVisibility(View.VISIBLE);
        }else{
            Toast.makeText(temporaryContext, "WRONG PASSWORD", Toast.LENGTH_SHORT).show();
            temporaryContext.closeMethod();
        }
    }

    public void removePassword(){
        dataBase.deleteSettingsData(passwordID);
        temporaryContext.closeMethod();
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
