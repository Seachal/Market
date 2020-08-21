package com.dajukeji.hslz.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.area.CityInfo;
import com.dajukeji.hslz.fragment.AreaFragment;
import com.dajukeji.hslz.util.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 *   地区选择
 */
public class AreaSelectActivity extends AppCompatActivity implements AreaFragment.OnFragmentInteractionListener {

    private Fragment oneFragment;
    private Fragment twoFragment;


    private Map map = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_select);
        ButterKnife.bind(this);

        String areaStr = FileUtils.readAssets(getApplicationContext(), "city.json");

        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(areaStr);
            JSONArray jsonArray = jsonObject.getJSONArray("citylist");
            Type type = new TypeToken<List<CityInfo>>() {
            }.getType();
            List<CityInfo> cityInfos = gson.fromJson(jsonArray.toString(), type);
            oneFragment = AreaFragment.newInstance(cityInfos);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, oneFragment).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }


    /**
     * 处理交互，hide前一个fragment，并且调用addToBackStack让Fragment可以点击back的时候显示前一个fragment
     * 如果是第三级地区则直接返回地区选择数据给上个Activity
     *
     * @param areaInfo 被点击的地区信息
     */
    @Override
    public void onFragmentInteraction(CityInfo areaInfo) {
        if (areaInfo == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int level = areaInfo.getLevel();
        switch (level) {
            case 1:
                map.put("provId", areaInfo.getId());
                map.put("provName", areaInfo.getAreaName());
                if (areaInfo.isLeaf()) {
                    Intent intent = new Intent();
                    intent.putExtra("addressInfo", (Serializable) map);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    transaction.hide(oneFragment);
                    transaction.add(R.id.content, twoFragment = AreaFragment.newInstance(areaInfo.getCitys())).addToBackStack(null).commit();
                }
                break;
            case 2:
                map.put("cityId", areaInfo.getId());
                map.put("cityName", areaInfo.getAreaName());
                if (areaInfo.isLeaf()) {
                    Intent intent = new Intent();
                    intent.putExtra("addressInfo", (Serializable) map);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    transaction.hide(twoFragment);
                    transaction.add(R.id.content, AreaFragment.newInstance(areaInfo.getCitys())).addToBackStack(null).commit();
                }
                break;
            case 3:
                map.put("districtId", areaInfo.getId());
                map.put("districtName", areaInfo.getAreaName());
                map.put("area_Id",areaInfo.getAreaId()); // 放入地区ID
                Intent intent = new Intent();
                intent.putExtra("addressInfo", (Serializable) map);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
