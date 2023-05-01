package humble.slave.pts_b.features.view.settingsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import humble.slave.pts_b.R;
import humble.slave.pts_b.databinding.ActivityAddTwilioAccountBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.addAccount.AccountPresenter;
import humble.slave.pts_b.features.presenter.addAccount.AccountPresenterImpl;
import humble.slave.pts_b.features.view.HomeScreen;

public class AddTwilioAccount extends AppCompatActivity {


    ActivityAddTwilioAccountBinding binding;
    DataBase DB;

    AccountPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTwilioAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addBtnVisibility(View.GONE);
        updateBtnVisibility(View.GONE);
        progressBarVisibility(View.VISIBLE);

        DB = new DataBase(this);
        presenter = new AccountPresenterImpl(DB, this);
        presenter.doesAccountExist(binding.sid, binding.token);

        back(binding.goBack);
        onAddAccount(binding.addAccount);
        onAddAccount(binding.updateAccount);
        removeAccount(binding.removeAccount);
    }


    public void removeAccount(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(AddTwilioAccount.this);
        builder.setTitle("Your Account information will be Removed");

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.removeAccount();
                Toast.makeText(AddTwilioAccount.this, "ACCOUNT REMOVED", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AddTwilioAccount.this, "CANCELLED PROCESS", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    public void progressBarVisibility(int visibility){
        binding.progressBar.setVisibility(visibility);
    }

    public void inputBoxVisibility(int visibility){
        binding.inputBox.setVisibility(visibility);
    }

    public void addBtnVisibility(int visibility){
        binding.button1Box.setVisibility(visibility);
        inputBoxVisibility(visibility);
    }

    public void updateBtnVisibility(int visibility){
        binding.button2Box.setVisibility(visibility);
        binding.button3Box.setVisibility(visibility);
        inputBoxVisibility(visibility);
    }
    private void onAddAccount(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if(binding.sid.getText().toString().equals("") || binding.token.getText().toString().equals("") || binding.number.getText().toString().equals("")){
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(AddTwilioAccount.this);
                    builder.setTitle("Input Cannot be empty!!");
                    builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(AddTwilioAccount.this);
                    builder.setTitle("Your Account information will be Saved");

                    builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.updateOnAddAccount(binding.sid.getText().toString(), binding.token.getText().toString(), binding.number.getText().toString());
                            Toast.makeText(AddTwilioAccount.this, "ACCOUNT ADDED", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(AddTwilioAccount.this, "CANCELLED PROCESS", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    public void closeMethod(){
        finish();
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