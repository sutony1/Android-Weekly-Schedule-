package com.example.myclassmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MainActivity2.class);
    }
    public void gaoer3Click(View view) {
        intent.putExtra("extra_data", "class3");
        startActivity(intent);
    }

    public void gaoer4Click(View view) {
        intent.putExtra("extra_data", "class4");
        startActivity(intent);
    }

    public void gaoer5Click(View view) {
        intent.putExtra("extra_data", "class5");
        startActivity(intent);
    }

    public void gaoer6Click(View view) {
        intent.putExtra("extra_data", "class6");
        startActivity(intent);
    }

    public void gaoer7Click(View view) {
        intent.putExtra("extra_data", "class7");
        startActivity(intent);
    }

    public void gaoer8Click(View view) {
        intent.putExtra("extra_data", "class8");
        startActivity(intent);
    }

    public void testClick(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
}