package com.bz.push.common.utils;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XMLUtils {

	private static Logger logger = LoggerFactory.getLogger(XMLUtils.class);
	
	public static int getElementCounts(String elementName,Document document){
		Element rootElm = document.getRootElement();   
		List messageList=rootElm.elements("message");
		if(null ==messageList){
			return 0;
		}
		return messageList.size();
	}
	
	/*
	 * 专属用来获取短信接口返回的xml文档的rcode节点值
	 */
	public static String getElementTextValue(Document document){
		Element rootElm = document.getRootElement();   
		List messageList=rootElm.elements("rcode");
		if(null != messageList && messageList.size()>0){
			Element nodeElement=(Element)messageList.get(0);
			return nodeElement.getText();
		}
		return null;
	}
	
	public static String getElementTextValue(Document document,String nodeName){
		Element rootElm = document.getRootElement();   
		List messageList=rootElm.elements(nodeName);
		if(null != messageList && messageList.size()>0){
			Element nodeElement=(Element)messageList.get(0);
			return nodeElement.getText();
		}
		return null;
	}
}
