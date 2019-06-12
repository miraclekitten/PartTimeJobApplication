package com.websarva.wings.android.parttimejobapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class PartTimeJobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_time_job);

        setInputTypes();
    }

    private void setInputTypes() {
        EditText daily = (EditText) findViewById(R.id.daily_edittext);
        daily.setInputType(TYPE_CLASS_NUMBER);

        EditText daily2 = (EditText) findViewById(R.id.daily2_edittext);
        daily2.setInputType(TYPE_CLASS_NUMBER);

        EditText dailyPlus = (EditText) findViewById(R.id.dailyPlus_edittext);
        dailyPlus.setInputType(TYPE_CLASS_NUMBER);

        EditText dailyPlus2 = (EditText) findViewById(R.id.dailyPlus2_edittext);
        dailyPlus2.setInputType(TYPE_CLASS_NUMBER);

        EditText sd = (EditText) findViewById(R.id.sdSet_edittext);
        sd.setInputType(TYPE_CLASS_NUMBER);

        EditText sd2 = (EditText) findViewById(R.id.sd2_edittext);
        sd2.setInputType(TYPE_CLASS_NUMBER);

        EditText karu = (EditText) findViewById(R.id.karubi_edittext);
        karu.setInputType(TYPE_CLASS_NUMBER);

        EditText karu2 = (EditText) findViewById(R.id.karubi2_edittext);
        karu2.setInputType(TYPE_CLASS_NUMBER);

    }
}