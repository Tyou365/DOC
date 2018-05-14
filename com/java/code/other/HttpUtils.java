package com.pingan.finance.risk.info.getCreditReport.client;

import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Collections;
import java.util.Map;

/**
 * Created by EX_WLJR_ZHANGXUEQ on 2018/5/14.
 */
public class HttpUtils {

    private String getURLStr(Object obj) {
        StringBuffer sb = new StringBuffer("?");
        if(obj !=null){
            BeanMap beanMap = BeanMap.create(obj);
            for(Object key : beanMap.keySet()){
                sb.append(key).append("=").append(beanMap.get(key)).append("&");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }
    private String getURLStr2(Object obj) {
        StringBuffer sb = new StringBuffer("?");
        if(obj !=null){
            Map map = toMap(obj);
            for(Object key : map.keySet()){
                sb.append(key).append("=").append(map.get(key)).append("&");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String getURLStr(Map<String, String> params) {
        StringBuffer sb = new StringBuffer("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private Map toMap(Object object) {
        Map map = null;
        try {
            map = BeanUtils.describe(object);
            map.remove("class");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (map == null) {
            map = Collections.emptyMap();
        }
        return map;
    }

}
