package com.websarva.wings.android.parttimejobapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
                resultCompany = key;
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
                resultCompany = key;
                //前と今が位置情報同じとき
            } else {
                return "計算ミス";
            }
        }

        return resultCompany;

    }
}
