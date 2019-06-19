package com.websarva.wings.android.parttimejobapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Date;

public class StackAccountActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_account);

        // LocationClientクラスのインスタンスを生成
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button saveBtn = findViewById(R.id.saveAccount_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 位置情報取得開始
                startUpdateLocation();

                // 位置情報取得権限の確認
                if(ActivityCompat.checkSelfPermission(StackAccountActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // 権限がない場合、許可ダイアログ表示
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                    ActivityCompat.requestPermissions(StackAccountActivity.this, permissions, 2000);
                    return;
                }

                FusedLocationProviderClient client = new FusedLocationProviderClient(StackAccountActivity.this);

                client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    DatabaseHelper helper = new DatabaseHelper(StackAccountActivity.this);
                    //データベース接続オブジェクトの取得
                    SQLiteDatabase db = helper.getWritableDatabase();
                    EditText edittext = findViewById(R.id.todoMemo_edittext);
                    try{
                        ContentValues values = new ContentValues();
                        values.put("location", "LOCATION");
                        values.put("latitude", (double)location.getLatitude());
                        values.put("longitude", (double)location.getLongitude());
                        values.put("memo", edittext.getText().toString());

                        db.insert("account", null, values);
                    }
                    finally {
                        db.close();
                    }
                }
            });



            }
        });



        Button comfirmBtn = findViewById(R.id.confirmBtn);
        comfirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                confirmDatas();
                //db削除
                //deleteDatabase("account.db");
            }
        });

        Button finishBtn = findViewById(R.id.finishAccount_btn);
        finishBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(StackAccountActivity.this, AggregateActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * 位置情報取得開始メソッド
     */
    private void startUpdateLocation() {
        // 位置情報取得権限の確認
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 権限がない場合、許可ダイアログ表示
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 2000);
            return;
        }

        // 位置情報の取得方法を設定
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);       // 位置情報更新間隔の希望
        locationRequest.setFastestInterval(5000); // 位置情報更新間隔の最速値
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // この位置情報要求の優先度

        fusedLocationClient.requestLocationUpdates(locationRequest,  new MyLocationCallback(), null);
    }

    /**
     * 位置情報受取コールバッククラス
     */
    private class MyLocationCallback extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            // 現在値を取得
            Location location = locationResult.getLastLocation();
            TextView view = findViewById(R.id.testText);
          //  view.setText("緯度:" + location.getLatitude() + " 経度:" + location.getLongitude());


        }
    }

    /**
     * 許可ダイアログの結果受取
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 位置情報取得開始
            startUpdateLocation();
        }
    }

    private void confirmDatas(){


        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Log.d("debug","**********Cursor");

        Cursor cursor = db.query(
                "account",
                new String[] { "location", "latitude", "longitude" , "memo"},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < cursor.getCount(); i++) {
            sbuilder.append(cursor.getString(0));
            sbuilder.append(": ");
            sbuilder.append(cursor.getDouble(1));
            sbuilder.append(": ");
            sbuilder.append(cursor.getDouble(2));
            sbuilder.append(": ");
            sbuilder.append(cursor.getString(3));
            sbuilder.append("\n");
            cursor.moveToNext();
        }

        // 忘れずに！
        cursor.close();

        TextView textView = findViewById(R.id.testText);
        Log.d("debug","**********"+sbuilder.toString());
        textView.setText(sbuilder.toString());
    }


}
