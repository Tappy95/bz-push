package com.bz.push.dao.sms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bz.push.model.sms.SSmsBlack;

import java.util.List;

@Mapper
public interface SSmsBlackDao {
	/**
	 * 添加黑名单
	 * @param smsBlack
	 * @return
	 */
	Integer saveBlack(SSmsBlack smsBlack);
	/**
	 * 查看账号、ip是否被拉黑
	 * @param type
	 * @param accountNum
	 * @return
	 */
	SSmsBlack getBlack(@Param("type")String type,@Param("accountNum")String accountNum,@Param("ip")String ip);
	/**
	 * 修改账号被拉黑的次数
	 * @param accountNum
	 * @return
	 */
	Integer updateBlackCount(SSmsBlack smsBlack);
	/**
	 * 修改账号拉黑状态
	 * @param type
	 * @param accountNum
	 * @return
	 */
	Integer updateType(@Param("type")String type,@Param("accountNum")String accountNum);

	/**
	 * 获取去所有处于拉黑状态的黑名单
	 * @return
	 */
	List<SSmsBlack> getBlackList();

	/**
	 * 根据账号或者ip查看黑名单信息
	 * @param accountNum
	 * @return
	 */
	SSmsBlack getBlackAccount(@Param("accountNum")String accountNum);

	/**
	 * 获取所有被拉黑的账号，ip，永久拉黑除外
	 * @return
	 */
	List<SSmsBlack> getBlackTimeList();

}
