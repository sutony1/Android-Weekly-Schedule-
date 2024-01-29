package com.example.myclassmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//一个班级的所有学生姓名
public class MainActivity2 extends AppCompatActivity {
    LinearLayout studentList;
    String json = "";
    String classNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        studentList = findViewById(R.id.student_list2);
        Intent intent = getIntent();
        classNum = intent.getStringExtra("extra_data");
        try {
            // Get a reference to the file
            InputStream inputStream = getResources().openRawResource(R.raw.class_3_8);//R.raw.gaoerjson3_8_full
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(json);
        List<Student> students = getStudentsFromClass(json, classNum);
        for (Student student : students) {
            System.out.println(student.getId() + " " + student.getName());
        }
        int NumStudent;
        NumStudent = students.size();
        String studentIdName;
        for (int i = 0; i < NumStudent; i++) {
            RelativeLayout studentLayout = new RelativeLayout(this);
            studentLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView studentName = new TextView(this);
            studentName.setId(View.generateViewId());
            studentIdName = students.get(i).getId()+" "+students.get(i).getName();
            studentName.setText(studentIdName);
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

    private static List<Student> getStudentsFromClass(String json, String classNum) {
        List<Student> students = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject classObject = jsonObject.getJSONObject(classNum);
            JSONArray studentsArray = classObject.getJSONArray("students");
            for (int i = 0; i < studentsArray.length(); i++) {
                JSONObject studentObject = studentsArray.getJSONObject(i);
                String id = studentObject.getString("id");
                String name = studentObject.getString("name");
                students.add(new Student(id, name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return students;
    }

    private static class Student {
        private String id;
        private String name;

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}