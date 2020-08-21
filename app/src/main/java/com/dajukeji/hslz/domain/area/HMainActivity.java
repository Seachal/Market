package com.dajukeji.hslz.domain.area;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dajukeji.hslz.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Gordo on 2016/12/15.
 */

public class HMainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String areaStr = FileUtils.readAssets(getApplicationContext(), "city.json");
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(areaStr);
            JSONArray jsonArray = jsonObject.getJSONArray("citylist");
            Type type = new TypeToken<List<CityInfo>>() {
            }.getType();
            List<CityInfo> cityInfos = gson.fromJson(jsonArray.toString(), type);
            resetData(cityInfos);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resetData(List<CityInfo> cityInfos) {
        if (cityInfos != null && cityInfos.size() > 0) {
            for (int i = 0; i < cityInfos.size(); i++) {
                cityInfos.get(i).setLeaf(false);
                cityInfos.get(i).setLevel(1);
                cityInfos.get(i).setParentCode(String.valueOf(0));
                cityInfos.get(i).setId(i + 1);

                if (cityInfos.get(i).getCitys() != null && cityInfos.get(i).getCitys().size() > 0) {
                    for (int j = 0; j < cityInfos.get(i).getCitys().size(); j++) {
                        cityInfos.get(i).getCitys().get(j).setLevel(2);
                        cityInfos.get(i).getCitys().get(j).setParentCode(String.valueOf(cityInfos.get(i).getId()));
                        cityInfos.get(i).getCitys().get(j).setId(j);

                        if (cityInfos.get(i).getCitys().get(j).getCitys() != null && cityInfos.get(i).getCitys().get(j).getCitys().size() > 0) {
                            for (int x = 0; x < cityInfos.get(i).getCitys().get(j).getCitys().size(); x++) {
                                cityInfos.get(i).getCitys().get(j).getCitys().get(x).setLevel(3);
                                cityInfos.get(i).getCitys().get(j).getCitys().get(x).setParentCode(String.valueOf(cityInfos.get(i).getId()));
                                cityInfos.get(i).getCitys().get(j).getCitys().get(x).setId(j);
                            }
                        } else {
                            cityInfos.get(i).getCitys().get(j).setLeaf(true);
                        }
                    }
                }else {
                    cityInfos.get(i).setLeaf(true);
                }
            }
        }
        Gson gson = new Gson();

        String string = gson.toJson(cityInfos);


        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray(string);
            jsonObject.put("citylist", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.print(jsonObject.toString());
    }


}
