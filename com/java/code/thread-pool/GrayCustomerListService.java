package com.pingan.finance.loan.entry.jf.GrayCoustomerList.service;

import com.google.common.collect.Lists;
import com.pingan.finance.loan.api.provider.protocol.BaseResp;
import com.pingan.finance.loan.common.util.PoolUtil;
import com.pingan.finance.loan.dao.readrcpmdao.GrayCustomerListDao;
import com.pingan.finance.loan.entry.fzd.query.contract.service.ContractUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by EX_WLJR_ZHANGXUEQ on 2018/4/23.
 */
@Service
public class GrayCustomerListService {
    private static Logger logger = LoggerFactory.getLogger(ContractUploadService.class);

    @Autowired
    private GrayCustomerListDao dao;
    private String reasonCode = "1103";
    private String code = "1003";

    public BaseResp<Object> query() {

        List<String> reasonCodeList = dao.getReasonCode(code);
        reasonCodeList.add(reasonCode);
        logger.info("reasonCodeList:" + reasonCodeList);
        String flag = dao.getFlag();
        logger.info("不可进件灰名单flag: " + flag);
        if ("0".equals(flag)) {
            logger.info("不可进件灰名单flag更新为1。");
            dao.updateFlag();//更新标志
        }

        List<Callable<Void>> list = Lists.newArrayList();

        for (int i = 0; i < reasonCodeList.size(); i++) {
            final String reasonCode = reasonCodeList.get(i);
            Callable<Void> t = null;
            if ("0".equals(flag)) {
                t = new GrayCustomerList60Thread(reasonCode, dao);//定时任务第一次执行查询60日内的数据
            } else {
                t = new GrayCustomerListThread(reasonCode, dao);
            }
            list.add(t);
        }

        try {
            PoolUtil.excute(list, 1);
        } catch (Exception e) {
            logger.error("GrayCustomerListService线程调用: ", e);
        }
        return BaseResp.successInstance();
    }
}
