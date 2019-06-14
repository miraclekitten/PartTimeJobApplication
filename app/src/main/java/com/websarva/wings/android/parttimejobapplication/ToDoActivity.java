package com.websarva.wings.android.parttimejobapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        setToDoText();

        CheckBox box1 = findViewById(R.id.checkBox1);
        CheckBox box2 = findViewById(R.id.checkBox2);
        CheckBox box3 = findViewById(R.id.checkBox3);
        CheckBox box4 = findViewById(R.id.checkBox4);
        CheckBox box5 = findViewById(R.id.checkBox5);

        box1.setOnClickListener(new CheckClickListener());
        box2.setOnClickListener(new CheckClickListener());
        box3.setOnClickListener(new CheckClickListener());
        box4.setOnClickListener(new CheckClickListener());
        box5.setOnClickListener(new CheckClickListener());

        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 最初に戻るボタンが押された時の処理
                finish();
            }
        });;
    }

    private class CheckClickListener implements View.OnClickListener {
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
        CheckBox box4 = findViewById(R.id.checkBox4);
        CheckBox box5 = findViewById(R.id.checkBox5);

        if (getIntent().getExtras() != null && box1.isChecked() && box2.isChecked() &&
                box3.isChecked() && box4.isChecked() && box5.isChecked()){
            return true;
        }
        return false;
    }

    private void setToDoText(){
        Intent intent = getIntent();
        List textViews = new ArrayList<TextView>();
        textViews.add(findViewById(R.id.todo_text1));
        textViews.add(findViewById(R.id.todo_text2));
        textViews.add(findViewById(R.id.todo_text3));
        textViews.add(findViewById(R.id.todo_text4));
        textViews.add(findViewById(R.id.todo_text5));

        if(intent.getExtras() == null){
            String str = "確定ボタンが押されていません。前の画面へ戻ってください。";
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < intent.getExtras().size(); i++) {
                TextView textView = (TextView) textViews.get(i);
                textView.setText(intent.getStringExtra("TODO" + i));
            }
        }
    }
}
