package com.websarva.wings.android.parttimejobapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class PartTimeJobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_time_job);
        setInputTypes();

        Button calcBtn = findViewById(R.id.btCalc);
        Button clearBtn = findViewById(R.id.btClear);
        calcBtn.setOnClickListener(new BtnListener());
        clearBtn.setOnClickListener(new BtnListener());
        /*
        無名クラスでの実装
        calcBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
            }
        });
        */

    }


    private class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View view){
            int id = view.getId();
            EditText dailyEdit = findViewById(R.id.daily_edittext);
            EditText daily2Edit = findViewById(R.id.daily2_edittext);
            EditText dailyPlusEdit = findViewById(R.id.dailyPlus_edittext);
            EditText dailyPlus2Edit = findViewById(R.id.dailyPlus2_edittext);
            EditText sdEdit = findViewById(R.id.sdSet_edittext);
            EditText sd2Edit = findViewById(R.id.sd2_edittext);
            EditText karuEdit = findViewById(R.id.karubi_edittext);
            EditText karu2Edit = findViewById(R.id.karubi2_edittext);
            TextView menu1 = findViewById(R.id.menu1);
            TextView menu2 = findViewById(R.id.menu2);

            switch (id) {

                case R.id.btCalc:

                    if(dailyEdit.getText().toString().length() != 0 && daily2Edit.getText().toString().length() != 0) {
                        int dailyNum = Integer.parseInt(dailyEdit.getText().toString()) + Integer.parseInt(daily2Edit.getText().toString());
                        int dailyBox = dailyNum / 8;
                        int dailyRem = dailyNum % 8;
                        String dailyStr = "デイリーの合計数は " + dailyNum + " です。(箱" + dailyBox + "個と" + dailyRem + "個です)";
                        dailyStr = dailyStr.replace(String.valueOf(dailyNum), "<strong><font color=\"blue\">" + String.valueOf(dailyNum) + "</font></strong>");
                        dailyStr = dailyStr.replace(String.valueOf(dailyBox), "<strong><font color=\"red\">" + String.valueOf(dailyBox) + "</font></strong>");
                        dailyStr = dailyStr.replace(String.valueOf(dailyRem), "<strong><font color=\"red\">" + String.valueOf(dailyRem) + "</font></strong>");
                        menu1.setText(Html.fromHtml(dailyStr));
                    }
                    else{
                        clearMenu(menu1, menu2);
                        menu1.setText(Html.fromHtml("<strong><font color=\"red\">デイリーの値を入力して下さい</font></strong>"));
                        clearEditText(dailyEdit, daily2Edit);
                        break;
                    }


                    if(dailyPlusEdit.getText().toString().length() != 0 && dailyPlus2Edit.getText().toString().length() != 0) {
                        int dailyPlusNum = Integer.parseInt(dailyPlusEdit.getText().toString()) + Integer.parseInt(dailyPlus2Edit.getText().toString());
                        int dailyPlusBox = dailyPlusNum / 6;
                        int dailyPlusRem = dailyPlusNum % 6;
                        String dailyPlusStr = "デイリープラスの合計数は " + dailyPlusNum + " です。(箱" + dailyPlusBox + "個と" + dailyPlusRem + "個です)";
                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusNum), "<strong><font color=\"blue\">" + String.valueOf(dailyPlusNum) + "</font></strong>");
                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusBox), "<strong><font color=\"red\">" + String.valueOf(dailyPlusBox) + "</font></strong>");
                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusRem), "<strong><font color=\"red\">" + String.valueOf(dailyPlusRem) + "</font></strong>");
                        menu2.setText(Html.fromHtml(dailyPlusStr));

                    }
                    else{
                        clearMenu(menu1, menu2);
                        menu1.setText(Html.fromHtml("<strong><font color=\"red\">デイリープラスの値を入力して下さい</font></strong>"));
                        clearEditText(dailyPlusEdit, dailyPlus2Edit);
                        break;
                    }

                    break;

                case R.id.btClear:
                    clearEditText(dailyEdit, daily2Edit, dailyPlusEdit, dailyPlus2Edit, sdEdit, sd2Edit, karuEdit, karu2Edit);
                    clearMenu(menu1, menu2);
                    break;

            }
        }
    }

    private void clearMenu(TextView... menus){
        for(int i = 0; i < menus.length; i++){
            menus[i].setText("");
        }
    }

    private void clearEditText(EditText... edits){
        for(int i = 0; i < edits.length; i++){
            edits[i].setText("");
        }
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