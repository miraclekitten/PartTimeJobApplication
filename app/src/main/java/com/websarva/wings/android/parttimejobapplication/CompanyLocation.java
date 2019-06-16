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
        addLanLon("京都中央信用金庫草津駅前店", 35.022288, 135.963905);
        addLanLon("栗東鉄鋼", 35.049118, 135.943866);















        //最後にMapへ保存, キャスト確認すべき
        for(int i = 0; i < locationStr.size(); i++){
            companyMap.put((String)locationStr.get(i), (Double[])locationLonLat.get(i));
        }

    }
    public static void addLanLon(String companyName, Double lat, Double lon){
        Double[] latlon = {lat, lon};
        locationStr.add(companyName);
        locationLonLat.add(latlon);
    }
}
