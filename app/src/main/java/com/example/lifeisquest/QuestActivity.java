package com.example.lifeisquest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QuestActivity extends AppCompatActivity {
    private Quest quest;
    private RatingBar ratingDiff;
    private TextView rewardText, informText, deadLineText;
    private ImageButton setDeadLineBtn, sendQuestBtn,  plusBtn;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String targetUid;
    private String TAG = "KKKKDd";
    private final int REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_quest);

        //view 지정코드
        ratingDiff = findViewById(R.id.ratingdifficulty);
        rewardText = findViewById(R.id.rewardQuest);
        informText = findViewById(R.id.informQuest);
        deadLineText = findViewById(R.id.deadLineText);
        setDeadLineBtn = findViewById(R.id.setDeadlineBtn);
        sendQuestBtn = findViewById(R.id.sendQuestBtn);
        plusBtn = findViewById(R.id.plusBtn);


        plusBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), SelectFriend.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = (data.getStringExtra("result"));
        targetUid = result;
        Toast.makeText(QuestActivity.this,targetUid, Toast.LENGTH_SHORT);
        Log.d(TAG,"act");
    }

    static String getDate(Calendar c) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd(HH:mm)");
        String strToday = sdf.format(c.getTime());
        return strToday;
    }
}