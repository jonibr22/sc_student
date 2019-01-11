package com.example.apps.studycommunity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apps.studycommunity.Adapter.DataAccess;
import com.example.apps.studycommunity.Model.ScheduleModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudentScheduleActivity extends AppCompatActivity {
    TextView icCalendar,tvDate,tvTime, tvClass, tvTeacher, tvMateri;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);
        final DataAccess dataAccess = DataAccess.getInstance(this);

        icCalendar = findViewById(R.id.ic_calendar);
        tvDate = findViewById(R.id.tv_showDate);
        tvTime = findViewById(R.id.tv_showTime);
        tvClass = findViewById(R.id.tv_showClass);
        tvTeacher = findViewById(R.id.tv_showTeacher);
        tvMateri = findViewById(R.id.tv_showCourse);
        btnBack = findViewById(R.id.btn_back);

        final String id = getIntent().getStringExtra("studentId");

        final Calendar myCalendar = Calendar.getInstance();
        String selectedDate = dateSetter(myCalendar);
        scheduleSetter(id,selectedDate, dataAccess);

        icCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String selectedDate = dateSetter(myCalendar);
                        scheduleSetter(id,selectedDate, dataAccess);
                    }

                };
                new DatePickerDialog(StudentScheduleActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(),StudentMenuActivity.class);
                backIntent.putExtra("studentId",id);
                startActivity(backIntent);
            }
        });

    }
    private String dateSetter(Calendar myCalendar){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        String dateText = sdf.format(myCalendar.getTime());
        tvDate.setText(dateText);
        return dateText;
    }

    private void scheduleSetter(String id, String tanggal, DataAccess dataAccess){
        ScheduleModel schedule = dataAccess.getSchedule(id, tanggal);
        if(schedule.getIsLoaded()){
            String jam = schedule.getJam();
            String kelas = schedule.getKelas();
            String guru = schedule.getGuru();
            String materi = schedule.getMateri();
            tvTime.setText(jam);
            tvClass.setText(kelas);
            tvTeacher.setText(guru);
            tvMateri.setText(materi);
        }
        else{
            tvTime.setText("-");
            tvClass.setText("-");
            tvTeacher.setText("-");
            tvMateri.setText("-");
            Toast.makeText(StudentScheduleActivity.this, "Failed to load data!",Toast.LENGTH_LONG).show();
        }
    }
}
