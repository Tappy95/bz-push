package com.bz.push.timing;

import com.bz.push.common.config.SmsParamConf;
import com.bz.push.common.utils.DateUtil;
import com.bz.push.common.utils.StringUtil;
import com.bz.push.dao.sms.SSmsBlackDao;
import com.bz.push.dao.sms.SSmsLogDao;
import com.bz.push.model.sms.SSmsBlack;
import com.bz.push.model.sms.SSmsParamConf;
import com.bz.push.service.redis.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * 验证码黑名单定时任务
 */
@Component
public class BlackTimingTask {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SSmsLogDao sSmsLogDao;
    @Autowired
    private SSmsBlackDao sSmsBlackDao;
    /**
     *5分钟执行一次，将发送验证码异常的账号，ip拉入黑名单
     */
    //@Scheduled(cron = "0 */5 * * * ?")
    public void addBlack(){
        SSmsParamConf confMax=null;//异常存在最大值
        SSmsParamConf confTime=null;//拉黑时间
        SSmsParamConf confNum=null;//次数
        //获取异常ip地址
        confMax=(SSmsParamConf)redisService.getObject(SmsParamConf.IP_PER_DAY);
        confTime=(SSmsParamConf)redisService.getObject(SmsParamConf.IP_BLACK_TIME);
        confNum=(SSmsParamConf)redisService.getObject(SmsParamConf.IP_BLACK_NUM);
        List<String> ipList=sSmsLogDao.getIpOverrun(confMax.getMaxValue());
        blackRule(confMax,confTime,confNum,ipList);

        /*confTime=(SSmsParamConf)redisService.getObject(SmsParamConf.PHONE_EMAIL_BLACK_TIME);
        //获取异常手机号
        confMax=(SSmsParamConf)redisService.getObject(SmsParamConf.PHONE_PER_DAY);
        confNum=(SSmsParamConf)redisService.getObject(SmsParamConf.PHONE_BLACK_NUM);
        List<String> phooneList=sSmsLogDao.getAccountOverrun(1,confMax.getMaxValue());
        blackRule(confMax,confTime,confNum,phooneList);

        //获取异常邮箱
        confMax=(SSmsParamConf)redisService.getObject(SmsParamConf.EMAIL_PER_DAY);
        confNum=(SSmsParamConf)redisService.getObject(SmsParamConf.EMAIL_BLACK_NUM);
        List<String> emailList=sSmsLogDao.getAccountOverrun(2,confMax.getMaxValue());
        blackRule(confMax,confTime,confNum,emailList);
*/
    }
    /**
     *1分钟执行一次，将到达限制时间的账号解除拉黑状态
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    public void deleteBlack(){
        List<SSmsBlack>  blacks=sSmsBlackDao.getBlackTimeList();
        for(SSmsBlack s:blacks){
            if(DateUtil.getMinute(s.getCreateTime(),new Date())>=s.getLimitTime()*60){
                sSmsBlackDao.updateType("1",s.getAccountNum());
                redisService.delete(s.getAccountNum());
            }
        }
    }
    public void blackRule(SSmsParamConf confMax,SSmsParamConf confTime,SSmsParamConf confNum,List<String> list){
        if(list.size()!=0){
            SSmsBlack sSmsBlack=null;
            for(int i=0;i<list.size();i++){
                sSmsBlack=sSmsBlackDao.getBlackAccount(list.get(i));
                if(StringUtil.isNullOrEmpty(sSmsBlack)){
                    SSmsBlack black=new SSmsBlack();
                    black.setAccountNum(list.get(i));
                    black.setCreateTime(new Date());
                    black.setLimitTime(confTime.getMaxValue());
                    sSmsBlackDao.saveBlack(black);
                    redisService.put(list.get(i), black);
                }else{
                    sSmsBlack.setBlackCount(sSmsBlack.getBlackCount()+1);
                    sSmsBlack.setCreateTime(new Date());
                    if(sSmsBlack.getBlackCount()>=confNum.getMaxValue()){
                        sSmsBlack.setLimitTime(0);
                    }else{
                        sSmsBlack.setLimitTime(confTime.getMaxValue());
                    }
                    sSmsBlack.setType("0");
                    sSmsBlackDao.updateBlackCount(sSmsBlack);
                    redisService.put(sSmsBlack.getAccountNum(), sSmsBlack);
                }
            }
        }
    }
}
