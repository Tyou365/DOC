package com.pingan.finance.xyd.entry.blacklist.dto;

import org.springframework.stereotype.Component;

import com.pingan.finance.xyd.entry.common.AbstractTransfer;

@Component
public class BlacklistTransfer extends
		AbstractTransfer<BlacklistVerifyForm, EsbBlacklistVerifyForm> {

	public void convertExt(BlacklistVerifyForm source,
			EsbBlacklistVerifyForm target) {
		target.setGlobalType(source.getCertType());
		target.setGlobalId(source.getCertID());
		target.setClientName(source.getCustomerName());
		target.setMobile(source.getMobileTel());
		target.setCompanyName(source.getWorkCorp());
		target.setCompanyAddress(source.getWorkAdd());
		target.setCompanyPhoneNo(source.getWorkTel());
		target.setHomeAddress(source.getFamilyAdd());
		target.setSalesPersonNo(source.getSaleManager());
		target.setSaleChannelCode(source.getSaleChannel());
		target.setChannelType(source.getInputChannel());

	}

}
