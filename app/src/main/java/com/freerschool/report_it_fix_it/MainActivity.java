package com.freerschool.report_it_fix_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fixIt(View view){
        Intent intent = new Intent(this, FixStuff.class);
        startActivity(intent);

    }

    public void viewFixIts(View view){
        Intent intent = new Intent(this, ViewFixIts.class);
        startActivity(intent);
    }
}
