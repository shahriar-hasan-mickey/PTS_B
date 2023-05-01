package humble.slave.pts_b.features.view.commonActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import humble.slave.pts_b.R;
import humble.slave.pts_b.databinding.ActivityPreviousItemsBinding;
import humble.slave.pts_b.features.model.LocalDB.DataBase;

public class PreviousItems extends AppCompatActivity {

    ActivityPreviousItemsBinding binding;
    RecyclerView outputRecyclerView;
    ArrayList<String> message;
    TempAdapter adapter;
    DataBase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviousItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        back(binding.goBack);

        DB = new DataBase(this);
        outputRecyclerView = binding.recyclerView;
        message = new ArrayList<>();
        adapter = new TempAdapter(this, message);
        outputRecyclerView.setAdapter(adapter);
        outputRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayLayout();
//        binding.btn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder;
//                builder = new AlertDialog.Builder(PreviousItems.this);
//                builder.setTitle("Please Enter Your Password : ");
//
//
//
//                final EditText password = new EditText(PreviousItems.this);
//                final EditText re_password = new EditText(PreviousItems.this);
//                password.setInputType(InputType.TYPE_CLASS_TEXT);
//                re_password.setInputType(InputType.TYPE_CLASS_TEXT);
//                builder.setView(password);
//                builder.setView(re_password);
//
//                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(PreviousItems.this, "PASSWORD SAVED", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(PreviousItems.this, "NON PRESSED", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(PreviousItems.this, "CANCEL PRESSED", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();
//            }
//        });
    }

    private void displayLayout() {
        Cursor response = DB.getMessage();
        if(response.getCount()==0){
            Toast.makeText(this, "NO ENTRY EXISTS", Toast.LENGTH_SHORT).show();
        }else {
            while (response.moveToNext()){
                message.add(response.getString(1));
            }
        }
    }

    public void back(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder>{

        private Context context;
        private ArrayList outputViewArray;

        public TempAdapter(Context context, ArrayList outputViewArray) {
            this.context = context;
            this.outputViewArray = outputViewArray;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.message_entry, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.outputView.setText(
                    String.valueOf(
                            outputViewArray.get(position)
                    )
            );
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence[] delete = {"Delete"};
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setItems(delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0){
                                outputViewArray.get(position);
                                notifyItemRemoved(position);
                            }
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return outputViewArray.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView outputView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                outputView = itemView.findViewById(R.id.outputView);
            }
        }
    }
}