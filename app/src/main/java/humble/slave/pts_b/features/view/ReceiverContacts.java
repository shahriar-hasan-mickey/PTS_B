package humble.slave.pts_b.features.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import humble.slave.pts_b.databinding.ActivityReceiverContactsBinding;
import humble.slave.pts_b.databinding.ActivityWeatherBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;
import humble.slave.pts_b.features.presenter.addAccount.AccountPresenterImpl;
import humble.slave.pts_b.features.presenter.recipientInfoPresenter.RecipientPresenter;
import humble.slave.pts_b.features.presenter.recipientInfoPresenter.RecipientPresenterImpl;
import humble.slave.pts_b.features.view.settingsActivity.AddTwilioAccount;

public class ReceiverContacts extends AppCompatActivity {
    ActivityReceiverContactsBinding binding;

    DataBase DB;
    RecipientPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceiverContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);
        addBtnVisibility(View.GONE);
        updateBtnVisibility(View.GONE);
        progressBarVisibility(View.VISIBLE);

        DB = new DataBase(this);
        presenter = new RecipientPresenterImpl(DB, this);
        presenter.doesNumberExist(binding.to);

        back(binding.goBack);
        onAddNumber(binding.addNumber);
        onAddNumber(binding.updateNumber);
        removeNumber(binding.removeNumber);
    }


    public void removeNumber(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ReceiverContacts.this);
        builder.setTitle("Receiver number will be Removed");

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.removeNumber();
                Toast.makeText(ReceiverContacts.this, "NUMBER REMOVED", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ReceiverContacts.this, "CANCELLED PROCESS", Toast.LENGTH_SHORT).show();
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
    private void onAddNumber(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ReceiverContacts.this);
                builder.setTitle("Recipient contact number will be Saved");

                builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.updateOnAddNumber("+88"+binding.to.getText().toString());
                        Toast.makeText(ReceiverContacts.this, "ACCOUNT ADDED", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ReceiverContacts.this, "CANCELLED PROCESS", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
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