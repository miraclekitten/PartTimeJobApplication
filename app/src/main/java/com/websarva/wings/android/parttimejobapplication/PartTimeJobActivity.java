package com.websarva.wings.android.parttimejobapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
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
        Button nextBtn = findViewById(R.id.btPrepareNext);
        calcBtn.setOnClickListener(new BtnListener());
        clearBtn.setOnClickListener(new BtnListener());
        nextBtn.setOnClickListener(new BtnListener());
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

        private EditText dailyEdit = findViewById(R.id.daily_edittext);
        private EditText daily2Edit = findViewById(R.id.daily2_edittext);
        private EditText dailyPlusEdit = findViewById(R.id.dailyPlus_edittext);
        private EditText dailyPlus2Edit = findViewById(R.id.dailyPlus2_edittext);
        private EditText sdEdit = findViewById(R.id.sdSet_edittext);
        private EditText sd2Edit = findViewById(R.id.sd2_edittext);
        private EditText karuEdit = findViewById(R.id.karubi_edittext);
        private EditText karu2Edit = findViewById(R.id.karubi2_edittext);
        private EditText gohanBigEdit = (EditText) findViewById(R.id.gohanBig_edittext);
        private EditText gohanSmallEdit = (EditText) findViewById(R.id.gohanSmall_edittext);
        private TextView menu1 = findViewById(R.id.menu1);
        private TextView menu2 = findViewById(R.id.menu2);
        private TextView menu3 = findViewById(R.id.menu3);
        private TextView menu4 = findViewById(R.id.menu4);
        private TextView gohan = findViewById(R.id.gohan);
        private TextView gohanSub = findViewById(R.id.gohanSub);

        private int dailyNum = 0;
        private int dailyBox = 0;
        private int dailyRem = 0;
        private int dailyPlusNum = 0;
        private int dailyPlusBox = 0;
        private int dailyPlusRem = 0;
        private int sdNum = 0;
        private int sdBox = 0;
        private int sdRem = 0;
        private int karuNum = 0;
        private int karuBox = 0;
        private int karuRem = 0;
        private int gohanNum = 0;
        private int gohanBox = 0;
        private int gohanRem = 0;
        private int gohanBigNum = 0;
        private int gohanSmallNum =  0;


        private boolean checkEntered(){
            //未入力を検出
            if(TextUtils.isEmpty(dailyEdit.getText()) || TextUtils.isEmpty(daily2Edit.getText()) || TextUtils.isEmpty(dailyPlusEdit.getText()) || TextUtils.isEmpty(dailyPlus2Edit.getText()) ||
                    TextUtils.isEmpty(sdEdit.getText()) || TextUtils.isEmpty(sd2Edit.getText()) || TextUtils.isEmpty(karuEdit.getText()) || TextUtils.isEmpty(karu2Edit.getText())
                    || TextUtils.isEmpty(gohanBigEdit.getText()) || TextUtils.isEmpty(gohanSmallEdit.getText())){
                clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                menu1.setText(Html.fromHtml("<strong><font color=\"red\">未入力箇所があります。</font></strong>"));
                return false;
            }
            return true;
        }


        @Override
        public void onClick(View view){
            int id = view.getId();

            switch (id) {

                case R.id.btCalc:
                    //初期化
                    gohanNum = 0;

                    //未入力を検出
                    if (!checkEntered()){
                        break;
                    }



                    /*
                    デイリーを計算する処理
                     */
                    if(Integer.parseInt(dailyEdit.getText().toString()) != 0 && Integer.parseInt(daily2Edit.getText().toString()) != 0) {
                        dailyNum = Integer.parseInt(dailyEdit.getText().toString()) + Integer.parseInt(daily2Edit.getText().toString());
                        dailyBox = dailyNum / 8;
                        dailyRem = dailyNum % 8;
                        String dailyStr = "デイリーの合計数は " + dailyNum + " です。(箱" + dailyBox + "個と" + dailyRem + "個です)";
                        dailyStr = dailyStr.replace(String.valueOf(dailyNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(dailyNum) + "</font></strong> です");
                        dailyStr = dailyStr.replace("箱" + String.valueOf(dailyBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(dailyBox) + "</font></strong>個");
                        dailyStr = dailyStr.replace(String.valueOf(dailyRem) + "個", "<strong><font color=\"red\">" + String.valueOf(dailyRem) + "</font></strong>個");
                        menu1.setText(Html.fromHtml(dailyStr));
                        gohanNum += Integer.parseInt(dailyEdit.getText().toString());
                    }
                    else{
                        clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                        menu1.setText(Html.fromHtml("<strong><font color=\"red\">デイリーの値を入力して下さい</font></strong>"));
                        clearEditText(dailyEdit, daily2Edit);
                        break;
                    }


                    /*
                    デイリープラスを計算する処理
                     */
                    if(Integer.parseInt(dailyPlusEdit.getText().toString()) != 0 && Integer.parseInt(dailyPlus2Edit.getText().toString()) != 0) {
                        dailyPlusNum = Integer.parseInt(dailyPlusEdit.getText().toString()) + Integer.parseInt(dailyPlus2Edit.getText().toString());
                        dailyPlusBox = dailyPlusNum / 6;
                        dailyPlusRem = dailyPlusNum % 6;
                        String dailyPlusStr = "デイリープラスの合計数は " + dailyPlusNum + " です。(箱" + dailyPlusBox + "個と" + dailyPlusRem + "個です)";
                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(dailyPlusNum) + "</font></strong> です");
                        dailyPlusStr = dailyPlusStr.replace("箱" + String.valueOf(dailyPlusBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(dailyPlusBox) + "</font></strong>個");
                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusRem) + "個", "<strong><font color=\"red\">" + String.valueOf(dailyPlusRem) + "</font></strong>個");
                        menu2.setText(Html.fromHtml(dailyPlusStr));
                        gohanNum += Integer.parseInt(dailyPlusEdit.getText().toString());
                    }
                    else{
                        clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                        menu1.setText(Html.fromHtml("<strong><font color=\"red\">デイリープラスの値を入力して下さい</font></strong>"));
                        clearEditText(dailyPlusEdit, dailyPlus2Edit);
                        break;
                    }

                    /*
                    SDの入力があった時に計算を行う
                    無ければ何もしない
                     */
                    if(Integer.parseInt(sdEdit.getText().toString()) != 0 || Integer.parseInt(sd2Edit.getText().toString()) != 0) {
                        sdNum = Integer.parseInt(sdEdit.getText().toString()) + Integer.parseInt(sd2Edit.getText().toString());
                        sdBox = sdNum / 6;
                        sdRem = sdNum % 6;
                        String sdStr = "SDの合計数は " + sdNum + " です。(箱" + sdBox + "個と" + sdRem + "個です)";
                        sdStr = sdStr.replace(String.valueOf(sdNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(sdNum) + "</font></strong> です");
                        sdStr = sdStr.replace("箱" + String.valueOf(sdBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(sdBox) + "</font></strong>個");
                        sdStr = sdStr.replace(String.valueOf(sdRem) + "個", "<strong><font color=\"red\">" + String.valueOf(sdRem) + "</font></strong>個");
                        menu3.setText(Html.fromHtml(sdStr));
                        gohanNum += Integer.parseInt(sdEdit.getText().toString());
                    }
                    else{
                        menu3.setText("SDはありません。");
                    }

                    /*
                    カルビの入力があった時に計算を行う
                    無ければ何もしない
                     */
                    if(Integer.parseInt(karuEdit.getText().toString()) != 0 || Integer.parseInt(karu2Edit.getText().toString()) != 0) {
                        karuNum = Integer.parseInt(karuEdit.getText().toString()) + Integer.parseInt(karu2Edit.getText().toString());
                        karuBox = karuNum / 6;
                        karuRem = karuNum % 6;
                        String karuStr = "カルビの合計数は " + karuNum + " です。(箱" + karuBox + "個と" + karuRem + "個です)";
                        karuStr = karuStr.replace(String.valueOf(karuNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(karuNum) + "</font></strong> です");
                        karuStr = karuStr.replace("箱" + String.valueOf(karuBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(karuBox) + "</font></strong>個");
                        karuStr = karuStr.replace(String.valueOf(karuRem) + "個", "<strong><font color=\"red\">" + String.valueOf(karuRem) + "</font></strong>個");
                        menu4.setText(Html.fromHtml(karuStr));
                        gohanNum += Integer.parseInt(karuEdit.getText().toString());
                    }
                    else{
                       menu4.setText("カルビはありません。");
                    }

                    /**
                     * ご飯の処理
                     *　各場所で足し合わせているご飯の数から計算する
                     */
                    if(gohanNum != 0){
                        gohanBox = gohanNum / 20;
                        gohanRem = gohanNum % 20;
                        gohanBigNum = Integer.parseInt(gohanBigEdit.getText().toString());
                        gohanSmallNum =  Integer.parseInt(gohanSmallEdit.getText().toString());
                        String gohanStr = "ごはんの合計数は " + gohanNum + " です。(箱" + gohanBox + "個と" + gohanRem + "個です)";
                        String gohanSubStr = "普通盛は" + (gohanNum - (gohanBigNum + gohanSmallNum)) + "個で、大盛は" + gohanBigNum + "個で、小盛は" + gohanSmallNum + "個です。";

                        gohanStr = gohanStr.replace(String.valueOf(gohanNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(gohanNum) + "</font></strong> です");
                        gohanStr = gohanStr.replace("箱" + String.valueOf(gohanBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(gohanBox) + "</font></strong>個");
                        gohanStr = gohanStr.replace(String.valueOf(gohanRem) + "個", "<strong><font color=\"red\">" + String.valueOf(gohanRem) + "</font></strong>個");

                        gohanSubStr = gohanSubStr.replace("普通盛は" + String.valueOf(gohanNum - (gohanBigNum + gohanSmallNum)), "普通盛は<strong><font color=\"magenta\">" + String.valueOf(gohanNum - (gohanBigNum + gohanSmallNum)) + "</font></strong>");
                        gohanSubStr = gohanSubStr.replace("大盛は" + String.valueOf(gohanBigNum), "大盛は<strong><font color=\"magenta\">" + String.valueOf(gohanBigNum) + "</font></strong>");
                        gohanSubStr = gohanSubStr.replace("小盛は" + String.valueOf(gohanSmallNum), "小盛は<strong><font color=\"magenta\">" + String.valueOf(gohanSmallNum) + "</font></strong>");
                        gohan.setText(Html.fromHtml(gohanStr));
                        gohanSub.setText(Html.fromHtml(gohanSubStr));
                    }
                    findViewById(R.id.btPrepareNext).setEnabled(true);

                    break;

                case R.id.btClear:
                    clearEditText(dailyEdit, daily2Edit, dailyPlusEdit, dailyPlus2Edit, sdEdit, sd2Edit, karuEdit, karu2Edit, gohanBigEdit, gohanSmallEdit);
                    clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                    findViewById(R.id.btPrepareNext).setEnabled(false);

                    break;

                case R.id.btPrepareNext:
                    //未入力のみを検出している。間違いを防ぐため、デイリーなどの数を考慮すべき？
                    if(!checkEntered()){
                        break;
                    }

                    Intent intent = new Intent(PartTimeJobActivity.this, Prepare2Activity.class);
                    intent.putExtra("dailyNum", Integer.parseInt(dailyEdit.getText().toString()) + Integer.parseInt(daily2Edit.getText().toString()));
                    intent.putExtra("dailyPlusNum", Integer.parseInt(dailyPlusEdit.getText().toString()) + Integer.parseInt(dailyPlus2Edit.getText().toString()));
                    intent.putExtra("sdNum", Integer.parseInt(sdEdit.getText().toString()) + Integer.parseInt(sd2Edit.getText().toString()));
                    intent.putExtra("karuNum", Integer.parseInt(karuEdit.getText().toString()) + Integer.parseInt(karu2Edit.getText().toString()));
                    intent.putExtra("gohanNum", (Integer.parseInt(dailyEdit.getText().toString())) + Integer.parseInt(dailyPlusEdit.getText().toString()) +
                    Integer.parseInt(sdEdit.getText().toString()) + Integer.parseInt(karuEdit.getText().toString()));
                    intent.putExtra("gohanBigNum", Integer.parseInt(gohanBigEdit.getText().toString()));
                    intent.putExtra("gohanSmallNum", Integer.parseInt(gohanSmallEdit.getText().toString()));

                    startActivity(intent);
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
            edits[i].setText("0");
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

        EditText gohanBig = (EditText) findViewById(R.id.gohanBig_edittext);
        gohanBig.setInputType(TYPE_CLASS_NUMBER);

        EditText gohanSmall = (EditText) findViewById(R.id.gohanSmall_edittext);
        gohanSmall.setInputType(TYPE_CLASS_NUMBER);

    }
}