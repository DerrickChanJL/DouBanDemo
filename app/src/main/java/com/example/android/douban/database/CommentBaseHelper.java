package com.example.android.douban.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.douban.database.CommentDbSchema.CommentTable;

/**
 * Created by Derrick on 2018/6/22.
 * 评论数据库操作helper
 */

public class CommentBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "commentDB.db";
    private Context mContenxt;

    public CommentBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContenxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CommentTable.NAME + "("
                +"id integer primary key autoincrement,"
                + CommentTable.Cols.USER_ID + ","
                + CommentTable.Cols.MOVIE_ID + ","
                + CommentTable.Cols.USERNAME + ","
                + CommentTable.Cols.CONTENT + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
