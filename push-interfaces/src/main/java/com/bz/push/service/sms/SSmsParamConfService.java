package com.bz.push.service.sms;

import com.bz.push.model.sms.SSmsParamConf;
import com.github.pagehelper.PageInfo;

public interface SSmsParamConfService {
	
    int update(SSmsParamConf sSmsParamConf);
    
    SSmsParamConf selectOne(int id);
    
    PageInfo<SSmsParamConf> selectList(SSmsParamConf sSmsParamConf);
    
    int insert(SSmsParamConf sSmsParamConf);

}
