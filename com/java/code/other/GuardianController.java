package com.pingan.finance.xyd.entry.guardian.controller;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paic.pafa.validator.annotation.Valid;
import com.paic.pafa.web.BaseController;
import com.pingan.finance.channel.fx.metrics.ApiMetrics;
import com.pingan.finance.xyd.common.ErrorCodePre;
import com.pingan.finance.xyd.common.SqlCommon;
import com.pingan.finance.xyd.dao.readrcpmdao.CheckPingAnGuardDaoR;
import com.pingan.finance.xyd.dao.writercpmdao.PingAnGuardDaoW;
import com.pingan.finance.xyd.entry.common.BeanToMapUtil;
import com.pingan.finance.xyd.entry.common.GatewayClient;
import com.pingan.finance.xyd.entry.guardian.dto.EsbGuardianForm;
import com.pingan.finance.xyd.entry.guardian.dto.GuardMsgToCode;
import com.pingan.finance.xyd.entry.guardian.dto.GuardianForm;
import com.pingan.finance.xyd.entry.guardian.dto.GuardianResp;
import com.pingan.finance.xyd.entry.guardian.dto.GuardianTransfer;
import com.pingan.finance.xyd.entry.guardian.service.GuardianService;
import com.pingan.pafa.papp.ESA;

/**
 * 平安守护提交接口
 * @author ZHANGDANFENG974
 *
 */
@Controller
@RequestMapping("/guardian")
public class GuardianController extends BaseController{
	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	@Qualifier("guardianClient")
	private GatewayClient<GuardianResp> client;
	
	@Autowired
    @Qualifier("guardianTransfer")
	GuardianTransfer transfer;
	
	@Autowired
	SqlCommon<GuardianForm> sqlCommon;
	
	@Autowired
	private GuardianService guardianService;	
	
	@ResponseBody
	@ESA(name="xyd_entry.guardian.guard",timeout=30000)
	@RequestMapping(value = "/guard.do",method = RequestMethod.POST)
	@ApiMetrics(apiName="xyd_entry.guardian.guard")
	public GuardianResp guard(@Valid GuardianForm form) {
		
		if (!sqlCommon.clearSql(form)) {
			GuardianResp resp = new GuardianResp();
			resp.setRetMsg("包含特殊字符校验失败");
			resp.setReturnMsg("包含特殊字符校验失败");
			resp.setReturnCode(ErrorCodePre.GUARD+"999999");
			return  resp;
		}

		String myManagerNO = guardianService.getManagerNoByBarCode(form.getBarcodeNO());
		String managerNO = form.getOperateUser();
		if(!managerNO.equals(myManagerNO)){
			if(myManagerNO != null && !"".equals(myManagerNO)){
				GuardianResp resp = new GuardianResp();
				resp.setRetMsg("客户经理不一致，订单校验失败！");
				resp.setReturnMsg("客户经理不一致，订单校验失败！");
				resp.setReturnCode(ErrorCodePre.GUARD+"999978");
				return  resp;
			}
		}
		//提交
		if("1".equals(form.getOrderStatus())){
			logger.info("提交平安守护form:"+form);
			EsbGuardianForm esbForm = transfer.convert(form);
	        
			Map<String, Object> map = BeanToMapUtil.toMap(esbForm);
			
			GuardianResp resp = client.execute(map);				
			
			String retMsg = resp != null ? resp.getRetMsg() : null;
			
			String guardCode = GuardMsgToCode.decodeMsg(retMsg);
			
			resp.setReturnMsg(retMsg);
			if(guardCode == null || "".equals(guardCode)){
				guardCode = resp.getRetCode();
			}
			resp.setReturnCode(guardCode);
			if("000000".equals(resp.getRetCode())){
				resp.setApplySerialNo(form.getApplySerialNo());
			}
			
			return resp;
			
		}else if("0".equals(form.getOrderStatus())){//取消
			GuardianResp resp = new GuardianResp();
			logger.info("取消平安守护form:"+form);
			try{
				guardianService.updatePingAnGuard(form.getBarcodeNO());
			}catch(Exception e){
				logger.info("取消平安守护失败");
				resp.setRetCode("999977");
				resp.setRetMsg("取消平安守护失败");
				resp.setReturnMsg("取消平安守护失败");
				resp.setReturnCode(ErrorCodePre.GUARD+"999977");
				e.printStackTrace();
				return resp;
								
			}
			resp.setApplySerialNo(form.getApplySerialNo());
			resp.setRetCode("000000");
			resp.setRetMsg("返回成功");
			return resp;
		
		}else{
			GuardianResp resp = new GuardianResp();
			resp.setRetCode("000099");
			resp.setRetMsg("订单状态超出范围");
			resp.setReturnMsg("订单状态超出范围");
			resp.setReturnCode(ErrorCodePre.GUARD+"999976");
			return  resp;
		}		
		
	}


}
