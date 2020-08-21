package com.dajukeji.hslz.util;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**搜索记录
 * Created by Administrator on 2017/9/21.
 */

public class SearchRecordUtil {
    private static Gson gson=new Gson();
    private static int max_record_count=4;//最大值为4

    /**
     * 插入一条记录
     * @param phoneNumber
     * @param content
     * @param pref
     */
    public static void put(String phoneNumber, String content, SharedPreferences pref) {
        SharedPreferences.Editor editor=pref.edit();
        String search=pref.getString("search_"+phoneNumber,"");
        List<String> list;
        if("".equals(search)){//如果为空
            list=new ArrayList<String>();
        }else{
            list=gson.fromJson(search,List.class);
            if(list.contains(content)){
                list.remove(content);
            }
        }
        list.add(content);
        if(list.size()>4){
            list.remove(0);
        }
        editor.putString("search_"+phoneNumber,gson.toJson(list));
        editor.commit();
    }

    /**
     * 取出一条记录
     * @param phoneNumber
     * @param pref
     * @return
     */
    public static List<String> get(String phoneNumber, SharedPreferences pref){
        String search=pref.getString("search_"+phoneNumber,"");
        if(!"".equals(search)){
            List<String> list=gson.fromJson(search,List.class);
            Collections.reverse(list);
            return list;
        }else{
            return null;
        }
    }

    /**
     * 获取记录大小
     * @param phoneNumber
     * @param pref
     * @return
     */
    public static int getSize(String phoneNumber, SharedPreferences pref){
        List<String> list=get(phoneNumber,pref);
        if(list!=null){
            return list.size();
        }else{
            return 0;
        }
    }

    /**
     * 清理所有的记录
     * @param phoneNumber
     * @param pref
     */
    public static void clearAll(String phoneNumber, SharedPreferences pref){
        SharedPreferences.Editor editor=pref.edit();
        editor.remove("search_"+phoneNumber);
        editor.commit();
    }
}
