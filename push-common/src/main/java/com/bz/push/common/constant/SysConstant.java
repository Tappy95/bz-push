package com.bz.push.common.constant;

import java.util.Date;

public class SysConstant {

	/** 缓存中的角色信息开头+角色ID获取缓存信息 */
	public final static String MEMCACHE_MENU_INFO = "MEMCACHE_MENU_INFO";
	/** 缓存清分分细查询信息 */
	public final static String MEMCACHE_LIQUIDATION_LIST = "MEMCACHE_LIQUIDATION_LIST";
	/** 下载导出列表时的线程数量 */
	public final static int ThreadCount = 10;
	/** 角色信息有效时间 */
	public final static int MEMCACHE_MENU_INFO_AGE = 60 * 60 * 24; // 有效时间一天，除非修改该角色权限
	/** 字符集编码UTF-8 */
	public final static String CHARSET_UTF_8 = "UTF-8";
	/** 字符集编码GBK */
	public final static String CHARSET_GBK = "GBK";
	/** rsa私钥密码加密key */
	public final static String PRIVATE_KEY_PW_KEY = "1234567890qwertyuiopasdfghjklzxcvbnm";

	/** 管理后台 登录cookie名称 */
	public final static String ADMIN_COOKIE_NAME = "ADMIN_WEB_TOKEN_CONSTANT";
	/** 商户后台 登录cookie名称 */
	public final static String MERCHENT_COOKIE_NAME = "MERCHENT_WEB_TOKEN_CONSTANT";
	/** 登录cookie有效时间 */
	public final static int COOKIE_AGE = 1800;
	/** 登录memcache缓存有效时间 */
	public final static Date MEMCACHE_LOGIN_TIME = new Date(900 * 1000);

	/** AES 加密卡号key */
	public final static String AES_ENCRYPT_KEY_TO_CARD = "encrypt_card_encrypt_card_encrypt_card";
	/** csv分页导出条数 */
	public final static Integer CSV_DEFAULT_SIZE = 5000;
	/** 日志ID */
	public final static String LOGGER_ID = "logId";
	/** 通知线程池大小 */
	public final static Integer THREADS_NUM = 200;
	/** 默认通知次数 */
	public final static Integer NOTIFY_TOTAL_COUNT = 8;

	/** 短信请求间隔时间，单位：秒 */
	public final static Integer GETVERIFYCODE_TIMEOUT = 60;
	/** 二维码DES加密key */
	public final static String QRCODE_DES_KEY = "318da9ceff034cdf9bc854a4fcdb6cc7";
	/** 等号 */
	public final static String EQUAL = "=";
	/** and符号 */
	public final static String AMPERSAND = "&";
	/** 每页数据最大数 */
	public final static Integer ListQuerySize = 5000;

	/** 应用id */
	public final static String APPKEY = "app_key";
	public final static String APPUserToken = "user_token";
	/** 无需token列表 */
	public final static String NOTOKENLIST = "/sysAdmin/loginAdmin,/user/login,/user/reg,/user/recPwd,/sms/sendSms,/homePage,/moduleProduct,/moduleBanktype/all/listNoPage,/moduleProduct/**";
	/**APP 组装token所属参数 **/
	public final static String APPTokenKey="app_key,open_account_id,imei";
	/**Web 组装token所属参数 **/
	public final static String WebTokenKey="app_key,open_account_id,user_agent";


	public static final String MongoDB = "MongoDB";
	public static final String MySql = "MySql";
	public static final String Oracle = "Oracle";
	public static final String SqlServer = "SqlServer";
	public static final String MemCache = "MemCache";
	public static final String Redis = "Redis";
	public static final String OtherDB = "OtherDB";
	
	
	public static final String BASE64="";
}
