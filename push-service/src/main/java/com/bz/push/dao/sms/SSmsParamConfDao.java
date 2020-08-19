package com.bz.push.dao.sms;


import com.bz.push.model.sms.SSmsParamConf;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SSmsParamConfDao {
    /**
     * 根据配置名称获取配置
     * @param name
     * @return
     */
    SSmsParamConf getName(@Param("name")String name);

    /**
     * 获取所有配置
     * @return
     */
    List<SSmsParamConf> getConfList();
    
    int update(SSmsParamConf sSmsParamConf);
    
    SSmsParamConf selectOne(int id);
    
    List<SSmsParamConf> selectList(SSmsParamConf sSmsParamConf);
    
    int insert(SSmsParamConf sSmsParamConf);
}
