package com.example.drugreminder;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoaDetails extends AppCompatActivity {

    TextView patientName, drugName, drugDose, drugRoute, drugNotes, drugProc;
    DataBase db;
    Button deleteDoa, deleteUser, modify;
    String DoaID, UserId, proc, doaName, dose, route, notes, userName ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (null != mShareActionProvider)
        {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }

        return super.onCreateOptionsMenu(menu);
    }

    private Intent createShareForecastIntent()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "اسم المريض : " + userName + "\n اسم الدواء : " + doaName
                + "\n Dose : " + dose + "\n Route : " + route + "\n ملاحظات دوائية : " + notes + "\n اجراءات تمريضية : " + proc);
        return shareIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_details);

        patientName = (TextView) findViewById(R.id.patient_name);
        drugName = (TextView) findViewById(R.id.drug_name);
        drugDose = (TextView) findViewById(R.id.drug_dose);
        drugRoute = (TextView) findViewById(R.id.drug_route);
        drugNotes = (TextView) findViewById(R.id.drug_notes);
        drugProc = (TextView) findViewById(R.id.drug_peoc);
        modify = (Button) findViewById(R.id.modify);

        db = new DataBase(this);

        Bundle data = getIntent().getExtras();
        DoaID = data.getString("id");

        DoaData();
        UserData();
        setData();


        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoaDetails.this, Update.class);
                intent.putExtra("name", doaName);
                intent.putExtra("id", DoaID);
                intent.putExtra("userid", UserId);
                startActivity(intent);
            }
        });

        deleteDoa = (Button) findViewById(R.id.deleteDrug_btn);
        deleteDoa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DeleteDrug();
            }
        });
        deleteUser = (Button) findViewById(R.id.deleteUser_btn);
        deleteUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DeleteUser();
            }
        });

    }

    private void setData() {
        patientName.setText(userName);
        drugName.setText(doaName);
        drugDose.setText(dose);
        drugRoute.setText(route);
        drugNotes.setText(notes);
        drugProc.setText(proc);
    }

    public void DoaData() {
        Cursor res = db.getDoa();
        if (res.getCount() == 0) {
            Toast.makeText(this, "NOTHING FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            if(res.getString(0).equals(DoaID)) {
                UserId = res.getString(1);
                doaName = res.getString(2);
                dose = res.getString(3);
                route = res.getString(4);
                notes = res.getString(5);
                break;
            }
        }
    }

    public void UserData() {
        Cursor res = db.getUsers();
        if (res.getCount() == 0) {
            Toast.makeText(this, "NOTHING FOUND", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            if(res.getString(0).equals(UserId)) {
                userName = res.getString(1);
                proc = res.getString(5);
                break;
            }
        }
    }



    private void DeleteDrug()
    {
        Integer deletedRows=db.deleteDoa(DoaID);

        if(deletedRows>0)
            Toast.makeText(DoaDetails.this, "تم مسح الدواء", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DoaDetails.this, "لم يتم مسح الدواء", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DoaDetails.this, Profile.class);
        intent.putExtra("id", UserId);
        startActivity(intent);

    }



    private void DeleteUser()
    {
        Integer deletedRows=db.deleteuser(UserId);

        if(deletedRows>0)
            Toast.makeText(DoaDetails.this, "تم مسح المريض", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DoaDetails.this, "لم يتم مسح المريض", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DoaDetails.this, Users.class);
        startActivity(intent);

    }

}
