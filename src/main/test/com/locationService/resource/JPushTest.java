package com.locationService.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceEnum;
import cn.jpush.api.push.CustomMessageParams;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.ReceiverTypeEnum;
import cn.jpush.api.report.ReceivedsResult;

public class JPushTest {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushTest.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="fc1b8887a13e0e1fab9f68be";
	private static final String masterSecret = "0302be2675950813e2818cd7";
	
	public static final String msgTitle = "Test from API example";
    public static final String msgContent = "Test Test";
    public static final String registrationID = "010d2762e16";
    public static final String tag = "tag_api";

	public static void main(String[] args) {
		testSend();
		testGetReport();
	}

	private static void testSend() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 0, DeviceEnum.Android, false);
		CustomMessageParams params = new CustomMessageParams();
		params.setReceiverType(ReceiverTypeEnum.REGISTRATION_ID);
		params.setReceiverValue(registrationID);
//		params.setReceiverType(ReceiverTypeEnum.TAG);
//		params.setReceiverValue(tag);
		
		MessageResult msgResult = jpushClient.sendCustomMessage(msgTitle, msgContent, params, null);
        LOG.debug("responseContent - " + msgResult.responseResult.responseContent);
		if (msgResult.isResultOK()) {
	        LOG.info("msgResult - " + msgResult);
	        LOG.info("messageId - " + msgResult.getMessageId());
		} else {
		    if (msgResult.getErrorCode() > 0) {
		        // 涓氬姟寮傚父
		        LOG.warn("Service error - ErrorCode: "
		                + msgResult.getErrorCode() + ", ErrorMessage: "
		                + msgResult.getErrorMessage());
		    } else {
		        // 鏈埌杈�JPush 
		        LOG.error("Other excepitons - "
		                + msgResult.responseResult.exceptionString);
		    }
		}
	}
	
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.responseResult.responseContent);
		if (receivedsResult.isResultOK()) {
		    LOG.info("Receiveds - " + receivedsResult);
		} else {
            if (receivedsResult.getErrorCode() > 0) {
                // 涓氬姟寮傚父
                LOG.warn("Service error - ErrorCode: "
                        + receivedsResult.getErrorCode() + ", ErrorMessage: "
                        + receivedsResult.getErrorMessage());
            } else {
                // 鏈埌杈�JPush
                LOG.error("Other excepitons - "
                        + receivedsResult.responseResult.exceptionString);
            }
		}
	}

}


