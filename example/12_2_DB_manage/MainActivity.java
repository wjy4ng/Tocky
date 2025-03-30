package com.cookandroid.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    myDBHelper myHelper;
    EditText edttxt_name, edttxt_number, edttxt_result_name, edttxt_result_number;
    Button btn_init, btn_insert, btn_select;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("가수 그룹 관리 DB");

        edttxt_name = findViewById(R.id.edttxt_name);
        edttxt_number = findViewById(R.id.edttxt_number);
        edttxt_result_name = findViewById(R.id.edttxt_result_name);
        edttxt_result_number = findViewById(R.id.edttxt_result_number);
        btn_init = findViewById(R.id.btn_init);
        btn_insert = findViewById(R.id.btn_insert);
        btn_select = findViewById(R.id.btn_select);

        myHelper = new myDBHelper(this);
        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                // insert into groupTBL values ('----', ----);
                sqlDB.execSQL("insert into groupTBL values ('"
                        + edttxt_name.getText().toString()
                        + "',"
                        + edttxt_number.getText().toString()
                        + ");"
                );
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor = sqlDB.rawQuery("select * from groupTBL;", null);

                String strNames = "그룹 이름" + "\r\n" + "-------" + "\r\n";
                String strNumbers = "인원" + "\r\n" + "-------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNumbers += cursor.getString(1) + "\r\n";
                }

                edttxt_result_name.setText(strNames);
                edttxt_result_number.setText(strNumbers);

                cursor.close();
                sqlDB.close();
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table groupTBL (gName char(20) primary key, gNumber integer);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists groupTBL");
            onCreate(db);
        }
    }


}
