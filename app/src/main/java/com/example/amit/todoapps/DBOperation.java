package com.example.amit.todoapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amit on 7/18/2017.
 */

public class DBOperation extends SQLiteOpenHelper {
    public static final int VERSION=1;
    public static final String QUERY="Create Table "+DBInformation.TABLE_NAME+"("+DBInformation.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +DBInformation.TITLE+" TEXT,"+DBInformation.DESCRIPTION+" TEXT,"+DBInformation.DATE+" TEXT,"+DBInformation.STATUS+" TEXT"+");";
    public DBOperation(Context context) {
        super(context, DBInformation.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void AddInformation(SQLiteDatabase database,String title,String description,String date){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBInformation.TITLE,title);
        contentValues.put(DBInformation.DESCRIPTION,description);
        contentValues.put(DBInformation.DATE,date);
        database.insert(DBInformation.TABLE_NAME,null,contentValues);
    }
    public Cursor GetInformation(SQLiteDatabase database){
        String[]projections={DBInformation.TITLE,DBInformation.DESCRIPTION,DBInformation.DATE};
        Cursor cursor=database.query(DBInformation.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }
    public int UpdateInformation(SQLiteDatabase database,String date,String title,String description){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBInformation.DATE,date);
        contentValues.put(DBInformation.DESCRIPTION,description);
        String selection=DBInformation.TITLE+" LIKE ?";
        String[] selection_args={title};
        int count=database.update(DBInformation.TABLE_NAME,contentValues,selection,selection_args);
        return count;
    }
    public void DeleteInformation(SQLiteDatabase database,String title){
        String selection=DBInformation.TITLE+" LIKE ?";
        String[] selection_args={title};
        database.delete(DBInformation.TABLE_NAME,selection,selection_args);
    }
}
