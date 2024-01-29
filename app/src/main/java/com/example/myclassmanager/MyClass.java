package com.example.myclassmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public void test() {
        String json = "{\"class3\":{\"class_name\":\"22高二（3）\",\"class_time\":\"星期三第3节\",\"class_location\":\"501\",\"students\":[{\"notes\":\"\",\"id\":\"1\",\"name\":\"陈炎霖\"},{\"notes\":\"\",\"id\":\"2\",\"name\":\"侯奕楠\"}]}}";
        String classNum = "22高二（3）";
        List<Student> students = getStudentsFromClass(json, classNum);
        for (Student student : students) {
            System.out.println(student.getId() + " " + student.getName());
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
