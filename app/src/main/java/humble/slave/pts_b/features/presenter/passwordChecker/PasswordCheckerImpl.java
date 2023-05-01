package humble.slave.pts_b.features.presenter.passwordChecker;

import android.database.Cursor;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.view.MainActivity;

public class PasswordCheckerImpl implements PasswordChecker{

    DataBase dataBase;
    MainActivity temporaryContext;
    String passwordID = "05";
    public PasswordCheckerImpl(DataBase dataBase, MainActivity temporaryContext){
        this.dataBase = dataBase;
        this.temporaryContext = temporaryContext;
    }

    public void isPasswordSet(){
        Cursor response = dataBase.getData(passwordID);
        if(response.getCount()==0){
            temporaryContext.nextIntent();
        }else{
            temporaryContext.showAlert();
        }
    }

    public void checkPassword(String pwd){
        Cursor response = dataBase.getData(passwordID);
        response.moveToNext();
        if(response.getString(1).equals(md5(pwd))){
            temporaryContext.nextIntent();
        }else{
            Toast.makeText(temporaryContext, "WRONG PASSWORD", Toast.LENGTH_SHORT).show();
            temporaryContext.closeMethod();
        }
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
