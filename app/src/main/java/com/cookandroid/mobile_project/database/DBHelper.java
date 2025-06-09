package com.cookandroid.mobile_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_DB.db"; // 데이터베이스
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email"; // 사용자 이메일
    public static final String COLUMN_PASSWORD = "password"; // 비밀번호

    // 테이블 생성 SQL
    private static final String TABLE_CREATE =
            "create table " + TABLE_USERS + " (" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_EMAIL + " text not null unique, " + // 이메일은 고유값
                    COLUMN_PASSWORD + " text not null);";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USERS); // 테이블 있으면 삭제
        onCreate(db); // 다시 테이블 생성
    }
}
