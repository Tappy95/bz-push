package com.bz.push.common.weixin;

import com.alibaba.fastjson.JSONObject;
import com.bz.push.common.utils.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WxSupport {
    private static String app_appId = "";
    private static String app_appsecret = "";

    /**
     * 获取AccessToken
     * @return
     */
    public static Map<String,Object> getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+app_appId+"&secret="+app_appsecret;
        //String urlNameString = url.replace("${APPID}",app_appId).replace("${SECRET}",app_appsecret);
        return JSONObject.parseObject(HttpClient.getUrl(url));
    }

    /**
     * 获取消息模板
     * @param accessToken //接口调用凭证
     * @param templateIdShort //模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return
     */
    public static Map<String,Object> getTemplate(String accessToken,String templateIdShort){
        String url = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="+accessToken;
        Map<String,Object>  data=new HashMap<>();
        data.put("template_id_short",templateIdShort);
        String postData = JSONObject.toJSONString(data);
        return JSONObject.parseObject(HttpClient.postUrl(url,postData));
    }
   /* https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277*/
    /**
     * 发送模板消息
     * @param openid 用户openid
     * @param templateData 模板内容
     * @param templateIdShort 模板编号
     */
    public static Map<String,Object> sendTemplateNews(String openid,Map<String,TemplateData> mapdata,String templateIdShort){

        //获取access_token
        Map<String,Object> accessData =getAccessToken();
        String access_token = String.valueOf(accessData.get("access_token"));
        //获取消息模板
        /*Map<String,Object> temData=getTemplate(access_token,templateIdShort);*/
       /* TemplateData first = new TemplateData();
        first.setValue("您好，您已注册成为XXX平台用户。");
        first.setColor("#173177");*/
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(templateIdShort);
        wechatTemplate.setTouser(openid);
        wechatTemplate.setUrl("");
        wechatTemplate.setData(mapdata);
        String postData= JSONObject.toJSONString(wechatTemplate);
        System.out.println(postData);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        return JSONObject.parseObject(HttpClient.postUrl(postData,url));
    }
    
    
    /**
     * 获取用户列表
     * @return
     */
    public static Map<String,Object> getUserList(String accessToken){
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
        return JSONObject.parseObject(HttpClient.getUrl(url));
    }
    
    public static Map<String,Object> getUserList(String accessToken,String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
        return JSONObject.parseObject(HttpClient.getUrl(url));
    }
    
}
