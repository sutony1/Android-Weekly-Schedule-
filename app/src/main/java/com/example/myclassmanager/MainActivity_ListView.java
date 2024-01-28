package com.example.myclassmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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

public class MainActivity_ListView extends AppCompatActivity {
    LinearLayout studentList;
    String json = "";
    String classNum = "";
    private List<Bean> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_listview);
        Intent intent=getIntent();//获取传递过来的intent,当心别误删
        classNum = intent.getStringExtra("extra_data");
        try {
            // Get a reference to the file
            InputStream inputStream = getResources().openRawResource(R.raw.class_3_8);//gaoerjson3_8_full
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        classNum="class5";
//        System.out.println(json);
        List<Student> students = getStudentsFromClass(json, classNum);
        for (Student student : students) {
            Bean bean=new Bean();
            bean.setName(student.getId() + "  " + student.getName());
            data.add(bean);
//            System.out.println(student.getId() + " " + student.getName());
        }
//        for (int i = 0; i < 100; i++) {
//            Bean bean=new Bean();
//            bean.setName(i+" 陈淑怡");
//            data.add(bean);
//        }
        ListView listView=findViewById(R.id.lv);
        listView.setAdapter(new MyAdapter(data,this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3=new Intent(MainActivity_ListView.this,AttendanceActivity.class);
                intent3.putExtra("extra_data", classNum+","+data.get(position).getName());
                startActivity(intent3);
                Toast.makeText(MainActivity_ListView.this, "Click" + position, Toast.LENGTH_SHORT).show();

            }
        });
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