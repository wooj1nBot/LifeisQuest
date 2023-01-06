package com.example.lifeisquest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class QuestActivity extends AppCompatActivity {
    private Quest quest;
    private RatingBar ratingDiff;
    private TextView rewardText, informText, deadLineText;
    private Button setDeadLineBtn, sendQuestBtn;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        //view 지정코드
        ratingDiff = findViewById(R.id.ratingdifficulty);
        rewardText = findViewById(R.id.rewardQuest);
        informText = findViewById(R.id.informQuest);
        deadLineText = findViewById(R.id.deadLineText);
        setDeadLineBtn = findViewById(R.id.setDeadLineBtn);
        sendQuestBtn = findViewById(R.id.sendQuestBtn);

        //핸들러로 deadline Text 조정
        new Thread(new Runnable() {
            Handler handler = new Handler();

            @Override
            public void run() {
                handler.post(new Runnable() {
                    Calendar c = Calendar.getInstance();

                    @Override
                    public void run() {
                        deadLineText.setText(getDate(c));
                    }
                });
            }
        });

        //deadLine 설정코드
        setDeadLineBtn.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int pYear = calendar.get(Calendar.YEAR);
            int pMonth = calendar.get(Calendar.MONTH);
            int pDay = calendar.get(Calendar.DAY_OF_MONTH);
            StringBuilder sb = new StringBuilder();

            Calendar objCalendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(QuestActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;
                            String date = year + "/" + month + "/" + day;
                            objCalendar.set(year, day, month);
                            sb.append(date);
                        }
                    }, pYear, pMonth, pDay);
            datePickerDialog.show();

            timePickerDialog = new TimePickerDialog(QuestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = "(" + hour + ":" + minute + ")";
                    objCalendar.set(Calendar.HOUR, hour);
                    objCalendar.set(Calendar.MINUTE, minute);
                    sb.append(time);
                }
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
            deadLineText.setText(getDate(objCalendar));
        });


    }



    static String getDate(Calendar c) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd(HH:mm)");
        String strToday = sdf.format(c.getTime());
        return strToday;
    }
}