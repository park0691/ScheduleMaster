package com.snailpong.schedulemaster;

import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.List;

public class CalendarInregularAddActivity extends AppCompatActivity {

    private int starthour, startmin, endhour, endmin;

    DBHelper helper;
    SQLiteDatabase db;

    Button add, cancel;
    EditText name;
    LinearLayout startlin, endlin, datelin;
    TextView starttxt, endtxt, datetxt;
    CheckBox chkVib, chkGPS;
    TimePickerDialog.OnTimeSetListener startTimeSetListener;
    TimePickerDialog.OnTimeSetListener endTimeSetListener;
    double y, x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_inregular_add);
        setTitle("일정 추가");

        name = (EditText) findViewById(R.id.calender_add_inreg_name);

        chkVib = (CheckBox)findViewById(R.id.checkBox_add_inreg_Vib);
        chkGPS = (CheckBox)findViewById(R.id.checkBox_add_inreg_GPS);
        chkGPS.setEnabled(false);

        add = (Button)findViewById(R.id.dia_add_inreg_add);
        cancel = (Button)findViewById(R.id.dia_add_inreg_cancel);
        startlin = (LinearLayout) findViewById(R.id.calender_add_inreg_starttime_lin);
        endlin = (LinearLayout) findViewById(R.id.calender_add_inreg_endtime_lin);
        datelin = (LinearLayout) findViewById(R.id.calender_add_inreg_date_lin);
        starttxt = (TextView)findViewById(R.id.calender_add_inreg_starttime_txt);
        endtxt = (TextView)findViewById(R.id.calender_add_inreg_endtime_txt);
        datetxt = (TextView) findViewById(R.id.calender_add_inreg_date_txt);

        helper = new DBHelper(CalendarInregularAddActivity.this, "db.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                starthour = hourOfDay;
                startmin = minute;
                starttxt.setText(String.format("%02d", starthour) + ":" + String.format("%02d", startmin));
                if (endhour == 0 && endmin == 0) {
                    endhour = starthour + 1;
                    endmin = startmin;
                    endtxt.setText(String.format("%02d", endhour) + ":" + String.format("%02d", endmin));
                }
            }
        };

        endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endhour = hourOfDay;
                endmin = minute;
                endtxt.setText(String.format("%02d", endhour) + ":" + String.format("%02d", endmin));
            }
        };

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(starttxt.getText().toString() == endtxt.getText().toString() || name.getText().toString().length() <= 1) {
                    /////////////////////////
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CalendarInregularAddActivity.this);
                    //builder.setTitle("오류");
                    builder.setMessage("모든 항목을 입력하세요.");
                    builder.setPositiveButton("닫기", null);
                    builder.show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CalendarInregularAddActivity.this, startTimeSetListener, starthour, startmin, false).show();
            }
        });

        endlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CalendarInregularAddActivity.this, endTimeSetListener, endhour, endmin, false).show();
            }
        });

        chkVib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    chkGPS.setEnabled(true);
                } else {
                    chkGPS.setChecked(false);
                    chkGPS.setEnabled(false);
                }
            }
        });
    }
}
