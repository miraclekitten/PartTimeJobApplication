package com.websarva.wings.android.parttimejobapplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompanyLocation {

    private static Map<String, Double[]> companyMap = new LinkedHashMap();
    private static List locationStr = new ArrayList<String>();
    private static List locationLonLat = new ArrayList<Double[]>();

    /**
     * LinkedHashMap型
     * @return
     */
    public Map<String, Double[]> getCompanyMap(){
        return companyMap;
    }

    //初期化していく
    public CompanyLocation(){
        addLanLon("アイ薬局", 35.0208762,135.9647999, 3677.0);
        addLanLon("中信_草津駅前店", 35.022288, 135.963905, 4748.0);
        addLanLon("滋賀銀草津支店", 35.0208185, 135.9639722, 790.0);
        addLanLon("滋賀銀ハローサポート",35.0209794, 135.9640265, 787.0);
        addLanLon("ささきクリニック", 35.0245946, 135.9583107,248.0);
        addLanLon("中信_草津支店",35.0240996, 135.9584006,1954.0);
        addLanLon("クサツエストピアホテル", 35.0234758, 135.9585192,4776.0);
        addLanLon("(株)テクナート", 35.0218547, 135.9595197,2224.0);
        addLanLon("ボストンプラザ草津", 35.0225859, 135.9607931,4776.0);
        addLanLon("立岡写真館", 35.0248057, 135.9546388,198.0);
        addLanLon("堀文工業",35.0308675,135.9351931,3415.0);
        addLanLon("小寺製作所",35.0335813, 135.9381177,208.0);
        addLanLon("シブヤオートモービル",35.0450676, 135.9417377,3863.0);
        addLanLon("モトワークスオガワ",35.0475665, 135.9428394,212.0);
        addLanLon("栗東鉄工",35.0490830, 135.9438343,5152.0);
        addLanLon("国際湖沼委員会",35.0490830, 135.9438343,4591.0);
        addLanLon("琵琶湖博物館警備",35.0738862, 135.9349899,5202.0);
        addLanLon("琵琶湖博物館事務室",35.0741005, 135.9347502,4608.0);
        addLanLon("植物園",35.0738066, 135.9401877,597.0);
        addLanLon("水資源機構",35.0716502, 135.9373848,4098.0);
        addLanLon("(有)竹内精機",35.0592748, 135.9563812,4468.0);
        addLanLon("池田荷役産業(有)",35.0530502, 135.9695277,236.0);
        addLanLon("レンタルのニッケン",35.0486502, 135.9676123,4857.0);
        //少しズレ
        addLanLon("(有) 山本鉄工",35.0487001, 135.9653649,4988.0);
        addLanLon("エイテック",35.0402724, 135.9666701,234.0);
        addLanLon("(株)吉仲自動車販売",35.0409789, 135.9657921,4417.0);
        addLanLon("西田雪子様",35.0369302, 135.9726220,4198.0);
//        addLanLon("西田美代子様",null,null,4367.0);
        addLanLon("ハーモパレス草津管理室",35.0293270, 135.9633204,3756.0);
        addLanLon("石井表記",35.0253323, 135.9642572,4501.0);

        //最後にMapへ保存, キャスト確認すべき
        for(int i = 0; i < locationStr.size(); i++){
            companyMap.put((String)locationStr.get(i), (Double[])locationLonLat.get(i));
        }

    }
    public static void addLanLon(String companyName, Double lat, Double lon, Double id){
        Double[] latlon = {lat, lon, id};
        locationStr.add(companyName);
        locationLonLat.add(latlon);
    }
}
