package com.example.myclassmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;
//一个班级的所有学生姓名
public class MainActivity2 extends AppCompatActivity {
    LinearLayout studentList;
    int totalStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        studentList = findViewById(R.id.student_list);
        LinkedList list = new LinkedList();
        Intent intent = getIntent();
        String classNum = intent.getStringExtra("extra_data");
        try {
            // Get a reference to the file
            InputStream inputStream = getResources().openRawResource(R.raw.gaoerjson3_8);

            // Read the contents of the file into a string
            String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();

            // Parse the JSON string into an array of objects
            JSONArray jsonArray = new JSONArray(jsonString);

            // Create an array of names with the same length as the JSON array
            String[] names = new String[jsonArray.length()];
            totalStudents = jsonArray.length();

            // Loop over the JSON array and add each name to the names array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("class").equals(classNum) && jsonObject.getString("name") != "null") {
                    String sName= jsonObject.getString("name");
                    String sNum=jsonObject.getString("id");
                    list.add(sNum+"  "+sName);//java双引号和单引号有区别
//                    list.add(jsonObject.getString("name"));
                }
            }
            // Use the names array as needed
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int NumStudent;
        NumStudent = list.size();
        for (int i = 0; i < NumStudent; i++) {
            RelativeLayout studentLayout = new RelativeLayout(this);
            studentLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView studentName = new TextView(this);
            studentName.setId(View.generateViewId());
            studentName.setText(list.get(i));
            studentName.setTextSize(20);
            RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            nameParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            studentName.setLayoutParams(nameParams);

            Button downArrow = new Button(this);
            downArrow.setId(View.generateViewId());
            downArrow.setText("▼");
            RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            downArrow.setLayoutParams(arrowParams);

            studentLayout.addView(studentName);
            studentLayout.addView(downArrow);
            studentList.addView(studentLayout);
        }

    }
}