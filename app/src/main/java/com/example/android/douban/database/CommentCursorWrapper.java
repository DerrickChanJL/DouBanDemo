package com.example.android.douban.database;

import android.database.Cursor;
import android.database.CursorWrapper;


import com.example.android.douban.model.Comment;

import static com.example.android.douban.database.CommentDbSchema.*;

/**
 * Created by Derrick on 2018/6/22.
 */

public class CommentCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CommentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * 获取数据模型
     * @return
     */
    public Comment getComment(){

        int id = getInt(getColumnIndex(CommentTable.Cols.ID));
        int userId = getInt(getColumnIndex(CommentTable.Cols.USER_ID));
        String movieId = getString(getColumnIndex(CommentTable.Cols.MOVIE_ID));
        String username = getString(getColumnIndex(CommentTable.Cols.USERNAME));
        String content = getString(getColumnIndex(CommentTable.Cols.CONTENT));

        Comment comment = new Comment();
        comment.setId(id);
        comment.setUserId(userId);
        comment.setMovieId(movieId);
        comment.setUsername(username);
        comment.setContent(content);

        return comment;
    }
}
