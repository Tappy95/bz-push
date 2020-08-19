package com.bz.push.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.bz.push.common.smg.hwy.HWYSmsResponse;

public class HttpClient {
	 //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
    
	public static String getUrl(String url){
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
	
	public static String postUrl(String postData, String postUrl) {
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
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }

	public static Boolean sendHWYSms( String wsseHeader,String url,String body) throws Exception {
		CloseableHttpClient client = HttpClients.custom()
	                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
	                        (x509CertChain, authType) -> true).build())
	                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
	                .build();
	        HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
	                .setUri(url)
	                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
	                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
	                .addHeader("X-WSSE", wsseHeader)
	                .setEntity(new StringEntity(body)).build());
	        String resp = EntityUtils.toString(response.getEntity());
	        HWYSmsResponse responseEntity = JSON.parseObject(resp,HWYSmsResponse.class);
	        if("000000".equals(responseEntity.getCode())){
	        	return true;
	        }else {
	        	return false;
	        }
	}

	public static String sendHWYSmsNew( String wsseHeader,String url,String body) throws Exception {
		CloseableHttpClient client = HttpClients.custom()
				.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
						(x509CertChain, authType) -> true).build())
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.build();
		HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
				.setUri(url)
				.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
				.addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
				.addHeader("X-WSSE", wsseHeader)
				.setEntity(new StringEntity(body)).build());
		String resp = EntityUtils.toString(response.getEntity());
		HWYSmsResponse responseEntity = JSON.parseObject(resp,HWYSmsResponse.class);
		if("000000".equals(responseEntity.getCode())){
			return responseEntity.getResult().get(0).getSmsMsgId();
		}else {
			return null;
		}
	}
}
