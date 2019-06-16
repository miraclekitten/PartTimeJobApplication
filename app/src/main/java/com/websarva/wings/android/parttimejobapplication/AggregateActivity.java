package com.websarva.wings.android.parttimejobapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        //京都中央信用金庫草津駅前店
        double companyLatitude = 35.022288;
        double companyLogitude = 135.963905;
        //栗東鉄鋼
        double companyLatitude2 = 35.049118;
        double companyLogitude2 = 135.943866;
        //一番近い場所の情報を保持する
        double nearestCompanyLatitude = 0.0;
        double nearestCompanyLogitude = 0.0;
        //赤道半径
        double r = 6378137.0;

        double averageLatitude = 0.0;
        double averageLongitude = 0.0;

        // 結果を格納するための配列を生成
        float[] results1 = new float[3];
        // 結果を格納するための配列を生成
        float[] results2 = new float[3];
        Location.distanceBetween(latitude, longitude, companyLatitude, companyLogitude, results1);
        Location.distanceBetween(latitude, longitude, companyLatitude2, companyLogitude2, results2);

        if(results1[0] < results2[0]){
            return "中央信用";
        } else if (results1[0] > results2[0]){
            return "栗東鉄鋼";
        } else {
            return "計算ミス";
        }
    }
}
