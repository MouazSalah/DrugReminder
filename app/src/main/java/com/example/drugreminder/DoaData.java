package com.example.drugreminder;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class DoaData extends AppCompatActivity {

    EditText doaname, doadose, doaroute, doanotes, doanum, doatime;
    String  userid ;
    Button save;
    DataBase db;
    MyService service;

    //  ArrayList<String> id;

    int hour, mint;
    String [] time = {null, null, null, null, null, null, null, null, null, null, null, null};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doa_data);


        //  id = new ArrayList<>();

        db = new DataBase(this);

        doaname = (EditText) findViewById(R.id.doaname);
        doadose = (EditText) findViewById(R.id.doadose);
        doaroute = (EditText) findViewById(R.id.doaroute);
        doanotes = (EditText) findViewById(R.id.doanotes);
        doanum = (EditText) findViewById(R.id.doanum);
        doatime = (EditText) findViewById(R.id.doatime);
        save = (Button) findViewById(R.id.savedoa);

        Bundle data = getIntent().getExtras();
        userid = data.getString("id");
        addDoa();

        addTime();
    }

    private void addDoa() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( doaname.getText().toString().equals("") ||  doadose.getText().toString().equals("") ||
                        doaroute.getText().toString().equals("")||  doanotes.getText().toString().equals("")||
                        doatime.getText().toString().equals("") || doanum.getText().toString().equals("")){

                    Toast.makeText(DoaData.this, "أكمل البيانات من فضلك", Toast.LENGTH_SHORT).show();

                }
                else {

                    boolean isInserted = db.insertDOA(userid, doaname.getText().toString(), doadose.getText().toString()
                            , doaroute.getText().toString(), doanotes.getText().toString(), time);

                    //     Toast.makeText(DoaData.this, time[0] , Toast.LENGTH_SHORT).show();

                    if (isInserted == true) {
                        // getDoaData();
                        //new MyService(DoaData.this);

                        timer();


                        Toast.makeText(DoaData.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DoaData.this, Profile.class);
                        intent.putExtra("id", userid);
                        startActivity(intent);
                    } else
                        Toast.makeText(DoaData.this, "فشل التسجيل", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void timer()
    {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        Intent i = new Intent(getApplicationContext(),MyService.class);
                        startService(i);
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0 , 60000);

    }


    private void addTime() {
        doatime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doanum.getText().toString().equals("")) {
                    Toast.makeText(DoaData.this, "من فضلك ادخل عدد الجرعات اولا", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int h = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int m = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(DoaData.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            doatime.setText(selectedHour + ":" + selectedMinute);
                            hour = selectedHour;
                            mint = selectedMinute;
                            time[0] = selectedHour + ":" + selectedMinute;
                        }
                    }, h, m, false);//Yes 24 hour time
                    mTimePicker.setTitle("       Select Time");
                    mTimePicker.show();


                    int n = Integer.parseInt(doanum.getText().toString());
                    int nn = 24 / n;

                    for (int i = 1; i < n; i++) {
                        hour = (hour + nn)%24;
                        time[i] = hour + ":" + mint;
                    }
                    for (int i = n; i < 12; i++)
                        time[1] = null;

                }
            }
        });

    }

}
