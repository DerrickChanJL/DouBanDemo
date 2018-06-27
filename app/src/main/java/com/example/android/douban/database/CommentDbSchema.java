package com.example.android.douban.database;

/**
 * Created by Derrick on 2018/6/22.
 */

public class CommentDbSchema {

    public static final class CommentTable{

        public static final String NAME = "comments";

        public static final class Cols{
            public static final String ID = "id";
            public static final String USER_ID = "userId";
            public static final String MOVIE_ID = "movieId";
            public static final String USERNAME = "username";
            public static final String CONTENT = "content";

        }

    }
}
