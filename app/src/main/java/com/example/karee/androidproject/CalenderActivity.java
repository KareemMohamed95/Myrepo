package com.example.karee.androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalenderActivity extends AppCompatActivity {

    private CalendarView Calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        Calender = (CalendarView)findViewById(R.id.calendarView);
        Calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                Intent intent = new Intent(CalenderActivity.this,RegisterActivity.class);
                intent.putExtra("date",date);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
