package com.example.amit.todoapps;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Amit on 7/18/2017.
 */

public class BackgroundTask extends AsyncTask<String,Product,String> {
    Context context;
    Activity activity;
    ProductAdapter productAdapter;
    ListView listView;

    public BackgroundTask(Context context) {
        this.context = context;
        activity=(Activity)context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String method=params[0];
        if(method.equals("save_info")){
            String title=params[1];
            String description=params[2];
            String date=params[3];
            DBOperation dbOperation=new DBOperation(context);
            SQLiteDatabase database=dbOperation.getWritableDatabase();
            dbOperation.AddInformation(database,title,description,date);
            return "data Successfully Saved";

        }else if(method.equals("get_info")){
            productAdapter=new ProductAdapter(context,R.layout.show_product);
            DBOperation dbOperation=new DBOperation(context);
            SQLiteDatabase database=dbOperation.getReadableDatabase();
            Cursor cursor=dbOperation.GetInformation(database);
            while (cursor.moveToNext()){
                listView=(ListView)activity.findViewById(R.id.listview);
                String date1,title,description,date2;
                date1=cursor.getString(cursor.getColumnIndex(DBInformation.DATE));
                title=cursor.getString(cursor.getColumnIndex(DBInformation.TITLE));
                description=cursor.getString(cursor.getColumnIndex(DBInformation.DESCRIPTION));
                date2=cursor.getString(cursor.getColumnIndex(DBInformation.DATE));
                Product product=new Product(title,description,date1,date2);
                publishProgress(product);
            }
            return "get_info";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Product... values) {
        productAdapter.add(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("get_info")) {
            listView.setAdapter(productAdapter);
        }
        else {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
