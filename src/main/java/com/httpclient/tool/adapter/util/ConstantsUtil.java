package com.httpclient.tool.adapter.util;

public class ConstantsUtil {
	public final static int httpConnectTimeOut = 100 * 1000;// 连接超时时间,单位为毫秒

	public final static int httpReadtimeOut = 100 * 1000;// 读数据超时时间,单位为毫秒

	public final static String Default_Result_String = "{\"code\": 1, \"msg\": \"调用接口失败\"}";// 默认的接口返回的参数
	
	//动态获取信息
	public static String getResult(int code, String msg){
		return "{\"code\": " + code + ", \"msg\": \"" + msg + "\"}";
	}
}
