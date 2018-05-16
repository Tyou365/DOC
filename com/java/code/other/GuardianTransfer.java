package com.pingan.finance.xyd.entry.guardian.dto;

import org.springframework.stereotype.Component;

import com.pingan.finance.xyd.entry.common.AbstractTransfer;

@Component
public class GuardianTransfer extends
		AbstractTransfer<GuardianForm, EsbGuardianForm> {

	public void convertExt(GuardianForm source,
			EsbGuardianForm target) {
		target.setBarCodeNo(source.getBarcodeNO());
		target.setApplySerialNo(source.getApplySerialNo());
		target.setOperateUser(source.getOperateUser());
		target.setPingAnGuardTerm(source.getInsuranceTerm());
		target.setPingAnGuardType(source.getInsuranceType());
		target.setInsuranceLoanFlag(source.getInsuranceLoan());
		target.setInputChannel(source.getChannelType());
		

	}

}
