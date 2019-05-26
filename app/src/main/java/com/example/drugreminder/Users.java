package com.example.drugreminder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Users extends AppCompatActivity
{

    FloatingActionButton fab;
    ListView listView;
    DataBase db;
    ArrayList<String> username, userid;
    Adapter adapter;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = new DataBase(this);
        username = new ArrayList<>();
        userid = new ArrayList<>();

        UserData();

        listView = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        id = (TextView) findViewById(R.id.idtext);

        adapter = new Adapter(this, username, userid);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(Users.this, Profile.class);
                intent.putExtra("id", userid.get(i));

                Log.v("user", userid.get(i));

                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Users.this, UserData.class);
                startActivity(intent);
            }
        });
    }

    public void UserData()
    {
        Cursor res = db.getUsers();
        if (res.getCount() == 0)
        {
            Toast.makeText(this, "NOTHING FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext())
        {
            userid.add(res.getString(0));
            username.add(res.getString(1));
        }
    }
}


