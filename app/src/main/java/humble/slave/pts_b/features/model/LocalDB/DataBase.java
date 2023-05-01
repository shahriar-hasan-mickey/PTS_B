package humble.slave.pts_b.features.model.LocalDB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context) {
        super(context, "PTSPreference.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SettingsState(id TEXT PRIMARY KEY, state TEXT)");
        db.execSQL("CREATE TABLE Messages(id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, data DATE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SettingsState");
        db.execSQL("DROP TABLE IF EXISTS Messages");
    }

    public Boolean insertSettingsData(String id, String state){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("state", state);
        long result = DB.insert("SettingsState", null, contentValues);
        return result != -1;
    }

    public Boolean insertMessage(String message){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("message", message);
        long result = DB.insert("Messages", null, contentValues);
        return result != -1;
    }


    public Boolean updateSettingsData(String id, String state){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", state);
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM SettingsState WHERE id = ?", new String[]{id});
        if(cursor.getCount() > 0) {
            long result = DB.update("SettingsState", contentValues,"id = ?", new String[]{id});
            return result != -1;
        }else{
            return false;
        }
    }


    public Boolean deleteSettingsData(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("SELECT * FROM SettingsState WHERE id = ?", new String[]{id});
        if(cursor.getCount() > 0) {
            long result = DB.delete("SettingsState", "id = ?", new String[]{id});
            return result != -1;
        }else{
            return false;
        }
    }

    public Boolean deleteMessage(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Messages WHERE id = ?", new String[]{id});
        if(cursor.getCount()>0){
            long result = DB.delete("Messages", "id = ?", new String[]{id});
            return result != -1;
        }else{
            return false;
        }
    }

    public Cursor getData(String id){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM SettingsState WHERE id = ?", new String[]{id});
    }

    public Cursor getMessage(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Messages", null);
    }
}
