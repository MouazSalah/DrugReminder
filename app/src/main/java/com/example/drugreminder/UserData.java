package com.example.drugreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserData extends AppCompatActivity {


    EditText username, age, dr, dio, proc;
    Button save;
    DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);


        db = new DataBase(this);
        username = (EditText) findViewById(R.id.username);
        age = (EditText) findViewById(R.id.age);
        dr = (EditText) findViewById(R.id.dr);
        dio = (EditText) findViewById(R.id.dio);
        proc = (EditText) findViewById(R.id.doanum);
        save = (Button) findViewById(R.id.save);

        addUser();
    }

    private void addUser() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("")|| age.getText().toString().equals("") ||
                        dr.getText().toString().equals("") || dio.getText().toString().equals("") || proc.getText().toString().equals("") ){

                    Toast.makeText(UserData.this, "أكمل البيانات من فضلك", Toast.LENGTH_SHORT).show();
                }

                else {

                    boolean isInserted = db.insertUser(username.getText().toString(), age.getText().toString(),
                            dr.getText().toString(), dio.getText().toString(), proc.getText().toString());

                    if (isInserted == true) {
                        Toast.makeText(UserData.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserData.this, Users.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(UserData.this, "فشل التسجيل", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
