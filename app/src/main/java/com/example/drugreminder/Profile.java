package com.example.drugreminder;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    DataBase db;
    String userid, pname, page, pdr, pdio;
    TextView tname, tage, tdr, tdio;
    FloatingActionButton fab;

    ListView listView;
    ArrayList<String> username, Doaid;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = new ArrayList<>();
        Doaid = new ArrayList<>();
        db= new DataBase(this);

        Bundle data = getIntent().getExtras();
        userid = data.getString("id");
        UserData();

        DoaData();

        adapter = new Adapter(this, username, Doaid);

        listView = (ListView) findViewById(R.id.doalist);

        fab = (FloatingActionButton) findViewById(R.id.fabdoa);
        tname = (TextView) findViewById(R.id.patientname);
        tage = (TextView) findViewById(R.id.patientage);
        tdr = (TextView) findViewById(R.id.drname);
        tdio = (TextView) findViewById(R.id.patientdio);



        tname.setText(pname);
        tage.setText(page);
        tdr.setText(pdr);
        tdio.setText(pdio);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Profile.this, DoaDetails.class);
                intent.putExtra("id", Doaid.get(i));

                startActivity(intent);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, DoaData.class);
                intent.putExtra("id", userid);
                startActivity(intent);
            }
        });

    }

    public void UserData() {
        Cursor res = db.getUsers();
        if (res.getCount() == 0) {
            Toast.makeText(this, "NOTHING FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {

            if(res.getString(0).equals(userid)) {
                pname = res.getString(1);
                page = res.getString(2);
                pdr = res.getString(3);
                pdio = res.getString(4);
                break;
            }
        }
    }


    public void DoaData() {
        Cursor res = db.getDoa();
        if (res.getCount() == 0) {
            Toast.makeText(this, "NOTHING FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            // Log.v("profile1", res.getString(1));
            Log.v("profile2", userid);
            if(res.getString(1).equals(userid)) {

                Doaid.add(res.getString(0));
                username.add(res.getString(2));
            }
        }
    }





}
