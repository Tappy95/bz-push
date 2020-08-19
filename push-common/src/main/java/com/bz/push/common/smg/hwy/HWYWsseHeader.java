package com.bz.push.common.smg.hwy;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class HWYWsseHeader {
	
	private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
			
	 public static String buildWsseHeader(String appKey, String appSecret) {
	        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
	            System.out.println("buildWsseHeader(): appKey or appSecret is null.");
	            return null;
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	        String time = sdf.format(new Date()); //Created
	        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

	        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
	        String hexDigest = Hex.encodeHexString(passwordDigest);

	        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
	        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes()); //PasswordDigest
	        //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
	        //String passwordDigestBase64Str = Base64.encodeBase64String(hexDigest.getBytes(Charset.forName("utf-8"))); //PasswordDigest
	        //若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
	        //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");

	        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
	    }
}
