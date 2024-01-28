package com.example.myclassmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class MainActivity3 extends AppCompatActivity {
    private GridLayout gridLayout;
    private TextView currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        gridLayout = findViewById(R.id.gridLayout);
        currentTime = findViewById(R.id.currentTime);
        LocalDate startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = LocalDate.of(2023, 2, 13);
        }

        // Add buttons to the grid
        for (int i = 0; i < 24; i++) {
            LocalDate weekStartDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                weekStartDate = startDate.plusDays((i - 1) * 7);
            }
            LocalDate classDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                classDate = weekStartDate.with(DayOfWeek.WEDNESDAY);
            }
            String classDateString = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                classDateString = classDate.format(DateTimeFormatter.ofPattern("M月d日"));
            }
            System.out.println("第" + i + "周上课时间：" + classDateString);

            Button button = new Button(this);
            button.setId(i);
            button.setText(classDateString);
//          计算classDateString的日期和当前时间的差值，如果差值小于等于7天，则显示红色，否则显示绿色
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate currentDate = LocalDate.now();
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(currentDate, classDate);
                if (daysBetween <= 7) {
                    button.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    button.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click event
                    Intent intent = new Intent(MainActivity3.this, AttendanceActivity.class);
                    startActivity(intent);
                }
            });
            gridLayout.addView(button);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        currentTime.setText(currentDateTime);

    }
}