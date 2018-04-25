package com.pingan.finance.loan.entry.jf.GrayCoustomerList.service;

import com.pingan.finance.loan.dao.readrcpmdao.GrayCustomerListDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by EX_WLJR_ZHANGXUEQ on 2018/4/24.
 */
public class GrayCustomerListThread implements Callable<Void> {

    private static Logger logger = LoggerFactory.getLogger(GrayCustomerListThread.class);
    private String reasonCode;
    private GrayCustomerListDao dao;

    public GrayCustomerListThread(String reasonCode, GrayCustomerListDao dao) {
        this.reasonCode = reasonCode;
        this.dao = dao;
    }

    @Override
    public Void call() throws Exception {
        long start = System.currentTimeMillis();
        dao.saveGrayCustomer(reasonCode);
        long end = System.currentTimeMillis();
        logger.info(reasonCode + "执行SQL耗时:" + (end - start));
        return null;
    }
}
