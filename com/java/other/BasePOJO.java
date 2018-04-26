package com.pingan.finance.xyd.entry.common;

import com.alibaba.fastjson.JSON;

public class BasePOJO {
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
