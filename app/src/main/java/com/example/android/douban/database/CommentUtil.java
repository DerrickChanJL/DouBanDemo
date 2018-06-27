package com.example.android.douban.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.douban.database.CommentDbSchema.CommentTable;
import com.example.android.douban.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2018/6/22.
 */

public class CommentUtil {

    private static CommentUtil commentUtil;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    /**
     * 单例模式
     * @param context
     * @return
     */
    public static CommentUtil getCommentUtil(Context context){
        if(commentUtil == null){
            commentUtil = new CommentUtil(context);
        }
        return commentUtil;
    }

    /**
     * 初始化数据库
     * @param context
     */
    private CommentUtil(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CommentBaseHelper(mContext).getWritableDatabase();
    }

    /**
     * 整理数据模型
     * @param comment
     * @return
     */
    private static ContentValues getContentValues(Comment comment){

        ContentValues values = new ContentValues();
        values.put(CommentTable.Cols.USER_ID,comment.getUserId());
        values.put(CommentTable.Cols.MOVIE_ID,comment.getMovieId());
        values.put(CommentTable.Cols.USERNAME,comment.getUsername());
        values.put(CommentTable.Cols.CONTENT,comment.getContent());

        return values;
    }

    /**
     * 通过电影id查询评论
     * @return
     */
    private CommentCursorWrapper queryNotesByMovieId(String whereClause,String[] whereArgs){

        Cursor cursor = mDatabase.query(
                CommentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null

        );
        //返回查询的wrapper
        return new CommentCursorWrapper(cursor);

    }

    /**
     * 添加一条评论（写评论）
     */
    public void addComment(Comment comment){

        ContentValues values = getContentValues(comment);
        //插入一条数据
        mDatabase.insert(CommentTable.NAME,null,values);
    }

    /**
     * 删除一条评论
     */
    public void deleteComment(int id){
        mDatabase.delete(CommentTable.NAME,CommentTable.Cols.ID + "=?",new String[]{Integer.toString(id)});
    }

    /**
     * 根据电影id返回对应的评论
     * @return
     */
    public List<Comment> getComments(String movieId){
        List<Comment> commentList = new ArrayList<>();
        CommentCursorWrapper cursorWrapper = queryNotesByMovieId(CommentTable.Cols.MOVIE_ID + "=?",new String[]{movieId});
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                commentList.add(cursorWrapper.getComment());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }

        return commentList;
    }



}
