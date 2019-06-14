package com.websarva.wings.android.parttimejobapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static java.lang.Math.abs;

public class Prepare2Activity extends AppCompatActivity {

    private  final List<String> diffMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare2);


        setInputTypes();

        Button calcBtn = findViewById(R.id.btCalc);
        Button clearBtn = findViewById(R.id.btClear);
        Button todoBtn = findViewById(R.id.btTodo);
        calcBtn.setOnClickListener(new Prepare2Activity.BtnListener());
        clearBtn.setOnClickListener(new Prepare2Activity.BtnListener());
        todoBtn.setOnClickListener(new Prepare2Activity.BtnListener());

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

        private Intent intent = getIntent();
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


        @Override
        public void onClick(View view){
            int id = view.getId();


            switch (id) {

                case R.id.btCalc:
                    //初期化
                    gohanNum = 0;

                    //未入力を検出
                    if(TextUtils.isEmpty(dailyEdit.getText()) || TextUtils.isEmpty(daily2Edit.getText()) || TextUtils.isEmpty(dailyPlusEdit.getText()) || TextUtils.isEmpty(dailyPlus2Edit.getText()) ||
                            TextUtils.isEmpty(sdEdit.getText()) || TextUtils.isEmpty(sd2Edit.getText()) || TextUtils.isEmpty(karuEdit.getText()) || TextUtils.isEmpty(karu2Edit.getText())
                            || TextUtils.isEmpty(gohanBigEdit.getText()) || TextUtils.isEmpty(gohanSmallEdit.getText())){
                        clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                        menu1.setText(Html.fromHtml("<strong><font color=\"red\">未入力箇所があります。</font></strong>"));
                        break;
                    }



                    /*
                    デイリーを計算する処理
                     */
                    String diffMessage = "";
                    if(Integer.parseInt(dailyEdit.getText().toString()) != 0 && Integer.parseInt(daily2Edit.getText().toString()) != 0) {
                        dailyNum = Integer.parseInt(dailyEdit.getText().toString()) + Integer.parseInt(daily2Edit.getText().toString());
                        dailyBox = dailyNum / 8;
                        dailyRem = dailyNum % 8;
                        //データがない場合、第二引数の0が返される。
                        int preDailyNum = intent.getIntExtra("dailyNum", 0);
                        if(dailyNum == 0){
                            clearMenu(menu1, menu2, menu3, menu4);
                            menu1.setText("予定数に間違いがある可能性があります。(＊デイリーが0個)");
                            break;
                        }

                        int dailyDiff = dailyNum - preDailyNum;
                        if(dailyDiff == 0){
                            diffMessage = "デイリーの積み下ろしはありません";
                        } else if(dailyDiff > 0){
                            diffMessage = "個積んでください";
                        } else {
                            diffMessage = "個降ろしてください";
                        }


                        String dailyStr = "";
                        if(dailyDiff == 0){
                            dailyStr = "デイリーの合計数は " + dailyNum + " です。(" + diffMessage + ")";
                            diffMessages.add(diffMessage);
                            dailyStr = dailyStr.replace(diffMessage + ")", "<strong><font color=\"red\">" + diffMessage +  "</font></strong>)");
                        } else {
                            dailyStr = "デイリーの合計数は " + dailyNum + " です。(" + abs(dailyDiff) + diffMessage + ")";
                            diffMessages.add("デイリーを" + abs(dailyDiff) + diffMessage);
                            dailyStr = dailyStr.replace(abs(dailyDiff) + diffMessage + ")", "<strong><font color=\"red\">" + abs(dailyDiff) + diffMessage +  "</font></strong>)");
                        }

                        dailyStr = dailyStr.replace(String.valueOf(dailyNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(dailyNum) + "</font></strong> です");
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


                        int preDailyPlusNum = intent.getIntExtra("dailyPlusNum", 0);
                        if(dailyPlusNum == 0){
                            clearMenu(menu1, menu2, menu3, menu4);
                            menu1.setText("予定数に間違いがある可能性があります。(＊デイリープラスが0個)");
                            break;
                        }

                        int dailyPlusDiff = dailyPlusNum - preDailyPlusNum;
                        diffMessage = "";
                        if(dailyPlusDiff == 0){
                            diffMessage = "デイリープラスの積み下ろしはありません";
                        } else if(dailyPlusDiff > 0){
                            diffMessage = "個積んでください";
                        } else {
                            diffMessage = "個降ろしてください";
                        }


                        String dailyPlusStr = "";
                        if(dailyPlusDiff == 0){
                            dailyPlusStr = "デイリープラスの合計数は " + dailyPlusNum + " です。(" + diffMessage + ")";
                            diffMessages.add(diffMessage);
                            dailyPlusStr = dailyPlusStr.replace(diffMessage + ")", "<strong><font color=\"red\">" + diffMessage +  "</font></strong>)");
                        } else {
                            dailyPlusStr = "デイリープラスの合計数は " + dailyPlusNum + " です。(" + abs(dailyPlusDiff) + diffMessage + ")";
                            diffMessages.add("デイリープラスを" + abs(dailyPlusDiff) + diffMessage);
                            dailyPlusStr = dailyPlusStr.replace(abs(dailyPlusDiff) + diffMessage + ")", "<strong><font color=\"red\">" + abs(dailyPlusDiff) + diffMessage +  "</font></strong>)");
                        }

                        dailyPlusStr = dailyPlusStr.replace(String.valueOf(dailyPlusNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(dailyPlusNum) + "</font></strong> です");
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
                    int presdNum = intent.getIntExtra("sdNum", 0);
                        /*
                        if(presdNum == 0){
                            clearMenu(menu1, menu2, menu3, menu4);
                            menu1.setText("予定数に間違いがある可能性があります。(＊SDが0個)");
                            break;
                        }
                        */

                    int sdDiff = sdNum - presdNum;
                    diffMessage = "";
                    if(sdDiff == 0){
                        diffMessage = "SDの積み下ろしはありません";
                        diffMessages.add(diffMessage);
                    } else if(sdDiff > 0){
                        diffMessage = "個積んでください";
                    } else {
                        diffMessage = "個降ろしてください";
                    }
                    if(Integer.parseInt(sdEdit.getText().toString()) != 0 || Integer.parseInt(sd2Edit.getText().toString()) != 0) {
                        sdNum = Integer.parseInt(sdEdit.getText().toString()) + Integer.parseInt(sd2Edit.getText().toString());
                        sdBox = sdNum / 6;
                        sdRem = sdNum % 6;




                        String sdStr = "";
                        if(sdDiff == 0){
                            sdStr = "SDの合計数は " + sdNum + " です。(" + diffMessage + ")";
                            diffMessages.add(diffMessage);
                            sdStr = sdStr.replace(diffMessage + ")", "<strong><font color=\"red\">" + diffMessage +  "</font></strong>)");
                        } else {
                            sdStr = "SDの合計数は " + sdNum + " です。(" + abs(sdDiff) + diffMessage + ")";
                            diffMessages.add("SDを" + abs(sdDiff) + diffMessage);
                            sdStr = sdStr.replace(abs(sdDiff) + diffMessage + ")", "<strong><font color=\"red\">" + abs(sdDiff) + diffMessage +  "</font></strong>)");
                        }
                        sdStr = sdStr.replace(String.valueOf(sdNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(sdNum) + "</font></strong> です");
                        sdStr = sdStr.replace(abs(sdDiff) + diffMessage, "<strong><font color=\"red\">" + abs(sdDiff) + diffMessage + "</font></strong>");
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
                    int prekaruNum = intent.getIntExtra("karuNum", 0);

                    int karuDiff = karuNum - prekaruNum;
                    diffMessage = "";
                    if(karuDiff == 0){
                        diffMessage = "カルビの積み下ろしはありません";
                        diffMessages.add(diffMessage);
                    } else if(karuDiff > 0){
                        diffMessage = "個積んでください";
                    } else {
                        diffMessage = "個降ろしてください";
                    }

                    if(Integer.parseInt(karuEdit.getText().toString()) != 0 || Integer.parseInt(karu2Edit.getText().toString()) != 0) {
                        karuNum = Integer.parseInt(karuEdit.getText().toString()) + Integer.parseInt(karu2Edit.getText().toString());
                        karuBox = karuNum / 6;
                        karuRem = karuNum % 6;





                        String karuStr = "";
                        if(karuDiff == 0){
                            karuStr = "カルビの合計数は " + dailyNum + " です。(" + diffMessage + ")";
                            diffMessages.add(diffMessage);
                            karuStr = karuStr.replace(diffMessage + ")", "<strong><font color=\"red\">" + diffMessage +  "</font></strong>)");
                        } else {
                            karuStr = "カルビの合計数は " + dailyNum + " です。(" + abs(karuDiff) + diffMessage + ")";
                            diffMessages.add("カルビを" + abs(karuDiff) + diffMessage);
                            karuStr = karuStr.replace(abs(karuDiff) + diffMessage + ")", "<strong><font color=\"red\">" + abs(karuDiff) + diffMessage +  "</font></strong>)");
                        }
                        karuStr = karuStr.replace(String.valueOf(karuNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(karuNum) + "</font></strong> です");
                        karuStr = karuStr.replace(abs(karuDiff) + diffMessage, "<strong><font color=\"red\">" + abs(karuDiff) + diffMessage + "</font></strong>");
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

                        int preGohanNum = intent.getIntExtra("gohanNum", 0);
                        int preGohanBigNum = intent.getIntExtra("gohanBigNum", 0);
                        int preGohanSmallNum = intent.getIntExtra("gohanSmallNum", 0);
                        if(preGohanNum == 0){
                            clearMenu(menu1, menu2, menu3, menu4);
                            menu1.setText("予定数に間違いがある可能性があります。(＊ごはんが0個)");
                            break;
                        }

                        int gohanDiff = gohanNum - preGohanNum;
                        int gohanBigDiff = gohanBigNum - preGohanBigNum;
                        int gohanSmallDiff = gohanSmallNum - preGohanSmallNum;

                        diffMessage = "";
                        String todoDiffMessage = "";
                        if(gohanDiff == 0 && gohanBigDiff == 0 && gohanSmallDiff == 0){
                            diffMessage = "ご飯の積み下ろしはありません。";
                            diffMessages.add(diffMessage);
                        }
                        else {
                            //全体数に変更があるとき
                            if (gohanDiff != 0) {
                                //全体数に変化はあるが、普通盛によるものでない場合
                                if (gohanDiff == (gohanBigDiff + gohanSmallDiff)) {

                                }
                                //普通盛が関係しているとき
                                else {
                                    if (gohanDiff - (gohanBigDiff + gohanSmallDiff) > 0) {
                                        diffMessage += "普通盛が" + (gohanDiff - (gohanBigDiff + gohanSmallDiff)) + "個増えました。";
                                        todoDiffMessage += "普通盛を" + (gohanDiff - (gohanBigDiff + gohanSmallDiff)) + "個増やし";
                                    } else {
                                        diffMessage += "普通盛が" + abs(gohanDiff - (gohanBigDiff + gohanSmallDiff)) + "個減りました。。";
                                        todoDiffMessage += "普通盛を" + abs((gohanDiff - (gohanBigDiff + gohanSmallDiff))) + "個減らし";
                                    }
                                }
                            }

                            //大盛に変更があるとき
                            if (gohanBigDiff != 0) {
                                if (gohanBigDiff > 0) {
                                    diffMessage += "大盛が" + gohanBigDiff + "個増えました。";
                                    todoDiffMessage += "、大盛を" + gohanBigDiff + "個増やし";
                                } else if (gohanBigDiff < 0) {
                                    diffMessage += "大盛が" + abs(gohanBigDiff) + "個減りました。";
                                    todoDiffMessage += "、大盛を" + abs(gohanBigDiff) + "個減らし";
                                }
                            }

                            //小盛に変更があるとき
                            if (gohanSmallDiff != 0) {
                                if (gohanSmallDiff > 0) {
                                    diffMessage += "小盛が" + gohanSmallDiff + "個増えました。";
                                    todoDiffMessage += "、小盛を" + gohanSmallDiff + "個増やし";
                                } else if (gohanSmallDiff < 0) {
                                    diffMessage += "小盛が" + abs(gohanSmallDiff) + "個減りました。";
                                    todoDiffMessage += "、小盛を" + abs(gohanSmallDiff) + "個減らし";
                                }
                            }
                            diffMessages.add(todoDiffMessage + "てください");
                        }


                        String gohanStr ="ご飯の合計数は "  + gohanNum + " です。";
                        String gohanSubStr = diffMessage;


                        gohanStr = gohanStr.replace(String.valueOf(gohanNum) + " です", "<strong><font color=\"blue\">" + String.valueOf(gohanNum) + "</font></strong> です");
                        gohanStr = gohanStr.replace("箱" + String.valueOf(gohanBox) + "個", "箱<strong><font color=\"red\">" + String.valueOf(gohanBox) + "</font></strong>個");
                        gohanStr = gohanStr.replace(String.valueOf(gohanRem) + "個", "<strong><font color=\"red\">" + String.valueOf(gohanRem) + "</font></strong>個");

                        gohanSubStr = gohanSubStr.replace(diffMessage, "<strong><font color=\"red\">" + diffMessage + "</font></strong>");
                        gohan.setText(Html.fromHtml(gohanStr));
                        gohanSub.setText(Html.fromHtml(gohanSubStr));
                    }

                    //TODO画面へのボタンを有効化
                    findViewById(R.id.btTodo).setEnabled(true);
                    break;

                case R.id.btClear:
                    clearEditText(dailyEdit, daily2Edit, dailyPlusEdit, dailyPlus2Edit, sdEdit, sd2Edit, karuEdit, karu2Edit, gohanBigEdit, gohanSmallEdit);
                    clearMenu(menu1, menu2, menu3, menu4, gohan, gohanSub);
                    break;

                case R.id.btTodo:
                    // finish();
                    Intent intent = new Intent(Prepare2Activity.this, ToDoActivity.class);

                    for(int i = 0; i < diffMessages.size(); i++){
                        intent.putExtra("TODO" + i, diffMessages.get(i));
                    }

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
