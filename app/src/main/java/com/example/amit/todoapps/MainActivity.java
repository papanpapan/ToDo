package com.example.amit.todoapps;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    EditText title,description;
    DatePicker datePicker;
    Button save,cancel;
    ListView listView;
    ProductAdapter productAdapter;
    View view1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        String method = "get_info";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method);
        productAdapter = new ProductAdapter(this, R.layout.dialog_save_info);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Toast.makeText(getApplicationContext(),"Click On Position : "+productAdapter.getItemId(i),Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                view1=getLayoutInflater().inflate(R.layout.show_clicking_dialog,null);
                title=(EditText)view1.findViewById(R.id.title11);
                description=(EditText)view1.findViewById(R.id.description11);
                datePicker=(DatePicker)view1.findViewById(R.id.datePicker1);
                save=(Button)view1.findViewById(R.id.update1);
                cancel=(Button)view1.findViewById(R.id.cancel1);
                builder.setView(view1);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String TITLE,DESCRIPTION,DATE;
                        TITLE=title.getText().toString();
                        DESCRIPTION=description.getText().toString();
                        int day=datePicker.getDayOfMonth();
                        int month=datePicker.getMonth();
                        int year=datePicker.getYear();
                        DATE=day+" / "+month+" / "+year;
                        if(!TITLE.isEmpty()&&!DESCRIPTION.isEmpty()&&!DATE.isEmpty()) {
                            DBOperation dboperation=new DBOperation(getApplicationContext());
                            SQLiteDatabase database=dboperation.getWritableDatabase();
                            dboperation.UpdateInformation(database,DATE,TITLE,DESCRIPTION);

                                Toast.makeText(getApplicationContext(), "Update Success...!!!"+"\n"+TITLE + "\n" + DESCRIPTION + "\n" + DATE, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"Enter The All Details....!!!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Dialog is Dissmiss....!!!!",Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.Add:
                View view;
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                view=getLayoutInflater().inflate(R.layout.dialog_save_info,null);
                title=(EditText)view.findViewById(R.id.title1);
                description=(EditText)view.findViewById(R.id.description1);
                datePicker=(DatePicker)view.findViewById(R.id.datePicker);
                save=(Button)view.findViewById(R.id.save);
                cancel=(Button)view.findViewById(R.id.cancel);
               builder.setView(view);
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String TITLE,DESCRIPTION,DATE;
                        TITLE=title.getText().toString();
                        DESCRIPTION=description.getText().toString();
                        int day=datePicker.getDayOfMonth();
                        int month=datePicker.getMonth();
                        int year=datePicker.getYear();
                        DATE=day+" / "+month+" / "+year;
                        if(!TITLE.isEmpty()&&!DESCRIPTION.isEmpty()&&!DATE.isEmpty()) {
                            Toast.makeText(getApplicationContext(), TITLE + "\n" + DESCRIPTION + "\n" + DATE, Toast.LENGTH_SHORT).show();
                            BackgroundTask backgroundTask=new BackgroundTask(MainActivity.this);
                            String method="save_info";
                            backgroundTask.execute(method,TITLE,DESCRIPTION,DATE);
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"Enter The All Details....!!!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"Dialog is Dissmiss....!!!!",Toast.LENGTH_SHORT).show();
                       alertDialog.dismiss();
                    }
                });

                break;
            case R.id.delete:
                final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                View view2;
                view2=getLayoutInflater().inflate(R.layout.delete_dialog,null,false);
                builder1.setView(view2);
                Button delbutton=(Button)view2.findViewById(R.id.delete1);
                Button cancelbutton=(Button)view2.findViewById(R.id.cancelbutton);
                final EditText delete=(EditText)view2.findViewById(R.id.title123);
                final AlertDialog alertDialog1=builder1.create();
                alertDialog1.show();
                delbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder2=new AlertDialog.Builder(MainActivity.this);
                        builder2.setTitle("Delete The Detials...!!!");
                        builder2.setMessage("Are You Sure to delete..?????");
                        builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String del=delete.getText().toString();
                                DBOperation dbOperation=new DBOperation(MainActivity.this);
                                SQLiteDatabase database=dbOperation.getWritableDatabase();
                                dbOperation.DeleteInformation(database,del);
                                AlertDialog dialog=builder2.create();
                                Toast.makeText(MainActivity.this,"Information Successfully Deleted",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog dialog1=builder2.create();
                                dialog1.dismiss();
                                Toast.makeText(MainActivity.this,"Dialog Dismiss",Toast.LENGTH_SHORT).show();

                            }
                        });
                        AlertDialog alertDialog2=builder2.create();
                        alertDialog2.show();
                    }

                });
                cancelbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });
            case R.id.thumbUp:
                imageView=(ImageView)findViewById(R.id.imageView2);
                imageView.setColorFilter(Color.parseColor("#03a9f4"));
        }
        return super.onOptionsItemSelected(item);
    }



}
