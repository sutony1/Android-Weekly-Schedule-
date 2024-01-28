package com.example.myclassmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CLASS = "create table Class ("
            + "id integer primary, "
            + "class_name text, "
            + "class_time text"
            + "class_location text)";
    public static final String CREATE_STUDENT = "create table Student ("
            + "id integer primary key, "
            + "notes text, "
            + "gender text, "
            + "class_name text, "
            + "FOREIGN KEY (class_name) REFERENCES class(class_name))";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context; // 保存传入的Context
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS); // 创建Book表
        db.execSQL(CREATE_STUDENT); // 创建Category表
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Class");
        db.execSQL("drop table if exists Student");
        onCreate(db);

    }
}
