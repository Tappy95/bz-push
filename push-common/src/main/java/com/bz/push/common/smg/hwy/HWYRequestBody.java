package com.bz.push.common.smg.hwy;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class HWYRequestBody {

	 public static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
		             String statusCallbackUrl, String signature) {
			if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
			|| templateId.isEmpty()) {
			System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
				return null;
			}
			List<NameValuePair> keyValues = new ArrayList<NameValuePair>();
			
			keyValues.add(new BasicNameValuePair("from", sender));
			keyValues.add(new BasicNameValuePair("to", receiver));
			keyValues.add(new BasicNameValuePair("templateId", templateId));
			if (null != templateParas && !templateParas.isEmpty()) {
				keyValues.add(new BasicNameValuePair("templateParas", templateParas));
			}
			if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
				keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
			}
			if (null != signature && !signature.isEmpty()) {
				keyValues.add(new BasicNameValuePair("signature", signature));
			}
			
			return URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
		}

}
