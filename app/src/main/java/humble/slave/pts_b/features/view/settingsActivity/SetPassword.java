package humble.slave.pts_b.features.view.settingsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivitySetPasswordBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.passwordPresenter.SetPasswordPresenter;
import humble.slave.pts_b.features.presenter.passwordPresenter.SetPasswordPresenterImpl;

public class SetPassword extends AppCompatActivity {

    ActivitySetPasswordBinding binding;
    DataBase DB;
    SetPasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);

//        binding.outputGroup.setVisibility(View.GONE);
        contentVisibility(View.GONE);
        progressBarVisibility(View.VISIBLE);
        removePassword(binding.removePassword);

        DB = new DataBase(this);

        presenter = new SetPasswordPresenterImpl(DB, this);

        presenter.isPasswordSet();
        onSetPassword(binding.setPassword);
    }


    public void removePassword(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(SetPassword.this);
        builder.setTitle("Your Password will be Removed");

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.removePassword();
                Toast.makeText(SetPassword.this, "PASSWORD REMOVED", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SetPassword.this, "CANCELLED PROCESS", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    public void closeMethod(){
        finish();
    }

    private void onSetPassword(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                
                if(binding.password.getText().toString().equals(binding.rePassword.getText().toString())) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(SetPassword.this);
                    builder.setTitle("Your Password is going to be Saved");

                    builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.updateOnSetPassword(binding.password.getText().toString());

                            Toast.makeText(SetPassword.this, "PASSWORD SAVED", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(SetPassword.this, "CANCEL PRESSED", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }else{
                    Toast.makeText(SetPassword.this, "THE PASSWORD DOES NOT MATCH", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void progressBarVisibility(int visibility){
        binding.progressBar.setVisibility(visibility);
    }
    public void inputBoxVisibility(int visibility){
        binding.inputBox.setVisibility(visibility);
    }

    public void setBtnVisibility(int visibility){
        binding.button1Box.setVisibility(visibility);
        inputBoxVisibility(visibility);
    }

    public void removeBtnVisibility(int visibility){
        binding.button2Box.setVisibility(visibility);
        setBtnVisibility(visibility);
    }
    public void contentVisibility(int visibility){
        binding.inputBox.setVisibility(visibility);
        binding.button1Box.setVisibility(visibility);
        binding.button2Box.setVisibility(visibility);
//        binding.outputGroup.setVisibility(visibility);
    }
    public void passwordPrompt(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(SetPassword.this);
        builder.setTitle("Enter your Password");

        final EditText password = new EditText(SetPassword.this);
//        TODO : https://stackoverflow.com/questions/9892617/programmatically-change-input-type-of-the-edittext-from-password-to-normal-vic
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(password);

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressBarVisibility(View.VISIBLE);
                presenter.checkPassword(password.getText().toString());

//                presenter.updateOnSetPassword(binding.password.getText().toString());
//
//                Toast.makeText(SetPassword.this, "PASSWORD SAVED", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SetPassword.this, "CANCEL PRESSED", Toast.LENGTH_SHORT).show();
                closeMethod();
            }
        });
        builder.show();
    }
    public void back(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}