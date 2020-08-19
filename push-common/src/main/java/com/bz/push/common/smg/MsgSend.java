package com.bz.push.common.smg;

import java.net.URLEncoder;

import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bz.push.common.utils.HttpClient;
import com.bz.push.common.utils.XMLUtils;

/**
 *
 * @author Administrator
 */
public class MsgSend {
    private static final Logger logger = LoggerFactory.getLogger(MsgSend.class);


    private static String account = "YZM4732516";
    private static String password = "NSw9x2aJvn45f9";

    private static String sendUrl="sname=dlyijian&spwd=yijian2018&scorpid=&sprdid=1012812&sdst=${mobile}&smsg=${msg}";
    /**
     * 验证码短信发送
     * @param code
     * @param mobile
     * @return
     */
    public static boolean sendVerificationCode(String code , String mobile) {
        try {
        	/*String msg = URLEncoder.encode(code,"UTF-8");
            String data = postData.replace("${mobile}",mobile).replace("${msg}",msg);
            String result = HttpClient.postUrl(data,postUrl);
	        String state=XMLUtils.getElementTextValue(DocumentHelper.parseText(result), "State");
	        if(null !=code && "0".equals(state)){
	            return true;
	        }*/
	        return false;
        } catch (Exception e) {
        	logger.error("短信发送异常："+e);
		}
        return false;
    }

    /*public static void sendAppointStartMsg(String mobile,String msg) throws UnsupportedEncodingException {
        String data = postData.replace("${mobile}",mobile).replace("${msg}",URLEncoder.encode(msg,"UTF-8"));
        String result = SMS(data,postUrl);
        System.out.println(result);
    }

    *//**
     * 营销短信发送
     * @param mobile
     * @param msg
     * @throws UnsupportedEncodingException
     *//*
    public static void sendAppointStartMarketingMsg(String mobile,String msg) throws UnsupportedEncodingException {
        String data = maPostData.replace("${mobile}",mobile).replace("${msg}",URLEncoder.encode(msg,"UTF-8"));
        String result = SMS(data,postUrl);
        System.out.println(result);
    }*/

   /* public static String SMS(String postData, String postUrl) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result.substring(result.indexOf("<State>")+7).substring(0,1);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }*/
}
