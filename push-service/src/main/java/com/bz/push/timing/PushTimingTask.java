package com.bz.push.timing;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bz.push.common.aurora.PushUtil;
import com.bz.push.dao.app.AppNewsInformDao;
import com.bz.push.dao.app.LNoticeReadyDao;
import com.bz.push.dao.sms.SSmsLogDao;
import com.bz.push.dao.user.MUserInfoDao;
import com.bz.push.model.app.AppNewsInform;
import com.bz.push.model.app.LNoticeReady;
import com.bz.push.model.user.MUserInfo;

@Component
public class PushTimingTask {
    @Autowired
	private AppNewsInformDao appNewsInformDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
    @Autowired
    private LNoticeReadyDao lNoticeReadyDao;
    @Autowired
	private SSmsLogDao sSmsLogDao;
    
    private static String sign_title="每日红包领取提醒";
    
    private static String sign_content="别忘记领每日红包哦，另外您还有%s金币待提现，赶紧去宝猪乐园领取每日红包吧！更多红包任务等你来拿！";
    
	//@Scheduled(cron = "0 */1 * * * ?")
	public void pushTiming(){
		Long beginDate=new Date().getTime()-2*60*1000;
		Long endDate=new Date().getTime();
		List<AppNewsInform> apps=appNewsInformDao.selectList(2, beginDate, endDate);
		int result=0;
		for(AppNewsInform a:apps) {
			if(a.getPushObject()==2) {
				if(a.getPushMode()==2) {
					if(a.getInformType().intValue()==1) {
						result=PushUtil.sendAllNotice(a.getInformContent());
					}else {
						result=PushUtil.sendAll(a.getInformTitle(),a.getInformContent(),a.getInformUrl(),a.getInformType().toString());	
					}
				}
			}else {
				if("1".equals(a.getUserId())) {
					continue;
				}
				if(a.getPushMode()==2) {
					MUserInfo user=mUserInfoDao.selectOne(a.getUserId());
					if(a.getInformType().intValue()==1) {
						result=PushUtil.sendSingleNotice(user.getRegistrationId(),a.getInformContent());
					}else {
						result=PushUtil.sendSingle(user.getRegistrationId(),a.getInformTitle(),a.getInformContent(),a.getInformUrl(),a.getInformType().toString());	
					}
				}
			}
			if(result==1) {
				appNewsInformDao.update(a.getId());	
			}
		}
	}
	
	//@Scheduled(cron = "0 0 10 * * ?")
	public void pushSignTiming(){
		List<LNoticeReady> list=lNoticeReadyDao.selectList(1);
		for(LNoticeReady a:list) {
			try {
				MUserInfo user=mUserInfoDao.selectOne(a.getUserId());
				PushUtil.sendSingleNotice(user.getRegistrationId(),String.format(sign_content, user.getCoin()));
			}catch (Exception e) {
				e.getMessage();
			}
		}
	}
	
	//@Scheduled(cron = "0 0 1 * * ?")
	public void deleteSmsLog(){
		sSmsLogDao.delete();
	}
}
