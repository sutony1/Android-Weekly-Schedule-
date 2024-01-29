package com.example.myclassmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    EditText notesEditText;
    boolean isFinished = false;
    String classNum;
    String stuName;
    String jsonString = "";
    JSONObject jsonObject;
    private List<Bean> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent=getIntent();//获取传递过来的intent,当心别误删
        String classNum_stuName = intent.getStringExtra("extra_data");
        classNum = classNum_stuName.split(",")[0];
        stuName = classNum_stuName.split(",")[1];
        TextView textView=findViewById(R.id.stuName);//findViewById()方法可能速度较慢，在MyAdapter中有更高级的用法。
        textView.setText(stuName);
        notesEditText = findViewById(R.id.notes);
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isFinished = true;
                Toast.makeText(AttendanceActivity.this, "完成本节课任务", Toast.LENGTH_SHORT).show();
//                notesEditText.setVisibility(View.VISIBLE);
            } else {
//                notesEditText.setVisibility(View.GONE);
            }
    });
    }
    public void saveAttendance(View view) throws JSONException, IOException {
        String notesText = notesEditText.getText().toString();
        Toast.makeText(this, classNum, Toast.LENGTH_SHORT).show();
        try {
            // Get a reference to the file
            InputStream inputStream = getResources().openRawResource(R.raw.class_3_8);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonString = stringBuilder.toString();
            jsonObject = new JSONObject(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        // Locate the class you want to modify
        JSONObject classData = jsonObject.getJSONObject(classNum);
        // Locate the student you want to modify
        JSONArray students = classData.getJSONArray("students");
        JSONObject student = null;
        for (int i = 0; i < students.length(); i++) {
            JSONObject s = students.getJSONObject(i);
            if (s.getString("name").equals(stuName)) {
                student = s;
                break;
            }
        }
        // Modify the student's notes
        student.put("notes", "Some new notes");
/*        // Write the modified JSONObject back to the file
        String modifiedJsonString = jsonObject.toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.write(Paths.get("d:\\addNotesfile.json"), modifiedJsonString.getBytes("UTF-8"));
        }*/
        try {
            OutputStream outputStream = new FileOutputStream("d:\\addNotesfile.json");
            outputStream.write(jsonObject.toString().getBytes("UTF-8"));
            outputStream.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
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