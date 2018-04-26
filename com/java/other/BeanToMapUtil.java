package com.pingan.finance.risk.info.common;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanToMapUtil {
	public static Map toMap(Object object) {
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
