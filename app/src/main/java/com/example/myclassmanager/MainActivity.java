package com.example.myclassmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    // 声明变量
    TableLayout tableLayout;
//    定义一个9*5的二维数组，用来存放课程表中的数据，初始化为0
    int[][] course = new int[][]{
            {0,0,0,4,0},
            {0,0,5,0,0},
            {0,0,3,0,0},
            {0,0,7,0,0},
            {0,0,8,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,6,0,0,0},
            {0,0,0,0,0},
    };
    TextView currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 绘制表格
        initTable();
        currentTime = findViewById(R.id.currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String currentDateTime = dateFormat.format(new Date());
        currentTime.setText(currentDateTime);
    }

    private void initTable() {
        tableLayout = findViewById(R.id.tableLayout);

        int padding = dip2px(getApplicationContext(), 5);

        for(int i = 0; i<course.length; i++){//10行

            // 新建一个TableRow并设置样式
            TableRow newRow = new TableRow(getApplicationContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
            newRow.setLayoutParams(layoutParams);

            //新建一个LinearLayout
            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            // 底部边框的宽度
            int bottomLine = dip2px(getApplicationContext(), 1);
//            if(i == 10 - 1) {
//                // 如果当前行是最后一行, 则底部边框加粗
//                bottomLine = dip2px(getApplicationContext(), 2);
//            }

            // 第一列
            TextView tvNo = new TextView(getApplicationContext());
            // 设置文字居中
            tvNo.setGravity(Gravity.CENTER);
            // 设置表格中的数据不自动换行
            tvNo.setSingleLine();
            // 设置边框和weight
            LinearLayout.LayoutParams lpNo = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            //dpValue:如设成2则左边框加粗
            lpNo.setMargins(dip2px(getApplicationContext(), 1), 0, dip2px(getApplicationContext(), 1), bottomLine);
            tvNo.setLayoutParams(lpNo);
            // 设置padding和背景颜色
            tvNo.setPadding(padding, padding, padding, padding);
            tvNo.setBackgroundColor(Color.parseColor("#FFFFFF"));
            // 填充文字数据
            tvNo.setText(i+1 + "");


            // 将所有新的组件加入到对应的视图中
            linearLayout.addView(tvNo);
            int num=0;
            String color = "white";
            for (int j = 0; j < course[0].length; j++) {
                if(course[i][j]>0) {
                    num = course[i][j];
                    color = "yellow";
                }
                linearLayout.addView(AddTableColumn(num,color));
                num = 0;
                color = "white";
            }
            newRow.addView(linearLayout);
            tableLayout.addView(newRow);
        }
    }
    private TextView AddTableColumn(int num,String color){
        TextView tvDay = new TextView(getApplicationContext());

        int padding = dip2px(getApplicationContext(), 5);
        int bottomLine = dip2px(getApplicationContext(), 1);
        // 设置文字居中
        tvDay.setGravity(Gravity.CENTER);
        // 设置表格中的数据不自动换行
        tvDay.setSingleLine();
        // 设置边框和weight
        LinearLayout.LayoutParams lpDay = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2f);
        lpDay.setMargins(0, 0, dip2px(getApplicationContext(), 1), bottomLine);
        tvDay.setLayoutParams(lpDay);
        // 设置padding和背景颜色
        tvDay.setPadding(padding, padding, padding, padding);
        tvDay.setBackgroundColor(Color.parseColor(color));
        String classnum = "class"+num;
        if(num>0){
            // 填充文字数据
            tvDay.setText(num+"");
        //添加点击事件
            tvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理点击事件
                    Toast.makeText(getApplicationContext(), "Click " + classnum, Toast.LENGTH_SHORT).show();
                    Intent intent2=new Intent(MainActivity.this,MainActivity_ListView.class);
                    intent2.putExtra("extra_data",classnum);
                    startActivity(intent2);
                }
            });
        }
        return tvDay;
    }
    private int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
