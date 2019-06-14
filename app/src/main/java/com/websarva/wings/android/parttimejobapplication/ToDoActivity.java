package com.websarva.wings.android.parttimejobapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ToDoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);


        CheckBox box1 = findViewById(R.id.checkBox1);
        CheckBox box2 = findViewById(R.id.checkBox2);
        CheckBox box3 = findViewById(R.id.checkBox3);

        box1.setOnClickListener(new CheckClickListener());
        box2.setOnClickListener(new CheckClickListener());
        box3.setOnClickListener(new CheckClickListener());

        Button backFirstBtn = findViewById(R.id.backFirst_btn);
        backFirstBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 最初に戻るボタンが押された時の処理
                finish();
            }
        });;
    }

    private class CheckClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Button departureBtn = findViewById(R.id.departureBtn);
            if (checkStates()){
                departureBtn.setEnabled(true);
            } else {
                departureBtn.setEnabled(false);
            }
        }
    }


    //チェックボックスのすべてがONの時Trueを返す
    private boolean checkStates(){
        CheckBox box1 = findViewById(R.id.checkBox1);
        CheckBox box2 = findViewById(R.id.checkBox2);
        CheckBox box3 = findViewById(R.id.checkBox3);
        if (box1.isChecked() && box2.isChecked() && box3.isChecked()){
            return true;
        }
        return false;
    }



}
