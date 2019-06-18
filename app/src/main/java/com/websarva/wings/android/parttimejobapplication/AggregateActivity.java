package com.websarva.wings.android.parttimejobapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AggregateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate);
        readSavedDatas();

        final Button finishBtn = findViewById(R.id.aggregateFin_btn);
        Button confirmBtn = findViewById(R.id.aggregateConfirFin_btn);

        //誤ってアプリを終了させないようにボタンを二重化
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishBtn.setEnabled(true);
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDatabase("account.db");
                /*
                FLAG_ACTIVITY_CLEAR_TOPは、遷移先のアクティビティが既に動いていればそのアクティビティより上にあるアクティビティを消す、という挙動を設定する。
                これによって、A→B→C→D→Aと遷移した後にbackボタンを押してもDに戻ることはなくなる。
                FLAG_ACTIVITY_SINGLE_TOPは、既に動いているアクティビティに遷移するとそのアクティビティを閉じてもう一度作りなおすデフォルトの挙動（multiple mode）から、
                作りなおさずに再利用する挙動に変更する。これによって、D→Aへの遷移のときのアニメーションが戻る動きになる。
                 */
                Intent intent = new Intent(AggregateActivity.this, PartTimeJobActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private void readSavedDatas(){

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                "account",
                new String[]{"location", "latitude", "longitude", "memo"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            String ans = "";
            ans = calcLocation(cursor.getDouble(1), cursor.getDouble(2)) + ": " + cursor.getString(3);
            sbuilder.append(ans);
            sbuilder.append("\n");
            cursor.moveToNext();
        }
        String memos = sbuilder.toString();
        TextView result = findViewById(R.id.result);
        result.setText(memos);


    }


    //latitude=緯度　longitude=経度
    private String calcLocation(double latitude, double longitude) {

        //一番近い場所の情報を保持する
        double nearestCompanyLatitude = 0.0;
        double nearestCompanyLogitude = 0.0;

        // 結果を格納するための配列を生成
        float[] results1 = new float[3];
        // 結果を格納するための配列を生成
        float[] results2 = new float[3];

        CompanyLocation compLoc = new CompanyLocation();

        Map<String, Double[]> companyMap = compLoc.getCompanyMap();
        Double[] latLon = {0.0, 0.0};
        String resultCompany = "";
        for (String key : companyMap.keySet()) {
            //一番初めのみ
            if(latLon[0] == 0.0 && latLon[1] == 0.0) {
                latLon = companyMap.get(key);
                nearestCompanyLatitude = latLon[0];
                nearestCompanyLogitude = latLon[1];
                //ID + key
                resultCompany = "" + latLon[2] + ":" +  key;
                continue;
            }

            Location.distanceBetween(latitude, longitude, nearestCompanyLatitude, nearestCompanyLogitude, results1);
            Location.distanceBetween(latitude, longitude, companyMap.get(key)[0], companyMap.get(key)[1], results2);

            //nearestの方が近い時
            if(results1[0] < results2[0]){

                //新しい方が近い時
            } else if (results1[0] > results2[0]){
                nearestCompanyLatitude = companyMap.get(key)[0];
                nearestCompanyLogitude = companyMap.get(key)[1];
                //ID + key
                resultCompany = "" + latLon[2] + ":" +  key;
                //前と今が位置情報同じとき
            } else {
                return "計算ミス";
            }
        }

        return resultCompany;

    }
}
