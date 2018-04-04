package com.ximi.wendashequ.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 单广美 on 2017/10/11.
 *
 * @Description:
 */

public class ViewObject {
    private Map<String,Object> hashMap = new HashMap<>();
    public void set(String key,Object value){
        hashMap.put(key,value);
    }
    public Object get(String key){
        return hashMap.get(key);
    }
}
