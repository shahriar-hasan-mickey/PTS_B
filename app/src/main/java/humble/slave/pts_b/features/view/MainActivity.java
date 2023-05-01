package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import humble.slave.pts_b.R;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.passwordChecker.PasswordChecker;
import humble.slave.pts_b.features.presenter.passwordChecker.PasswordCheckerImpl;

public class MainActivity extends AppCompatActivity {

    DataBase  DB;
    PasswordChecker presenter;
    Intent home_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_intent = new Intent(this, HomeScreen.class);


        DB = new DataBase(this);
        presenter = new PasswordCheckerImpl(DB, this);
        presenter.isPasswordSet();
    }

    public void showAlert(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Enter your Password");

        final EditText password = new EditText(MainActivity.this);
//        TODO : https://stackoverflow.com/questions/9892617/programmatically-change-input-type-of-the-edittext-from-password-to-normal-vic
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(password);

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.checkPassword(password.getText().toString());

            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "CANCEL PRESSED", Toast.LENGTH_SHORT).show();
                closeMethod();
            }
        });
        builder.show();
    }

    public void closeMethod(){
        finish();
    }

    public void nextIntent(){
        startActivity(home_intent);
        closeMethod();
    }


}