package com.httpclient.tool;

import com.httpclient.tool.test.TiKuInterfaceTest;

/**
 * 程序启动入口
 * 
 */
public class App {
	private static TiKuInterfaceTest tiku = null;

	public static void main(String[] args) {
		tiku = new TiKuInterfaceTest();
		
		// 调用表单文件上传接口
		//tiku.formFileUpload();
		
		// 调用获取用户文档列表信息接口
		//tiku.getDocList();
		
		// 调用获取文档内容信息接口
		//tiku.getTopicList();
		
		//调用删除文档信息接口
		tiku.delDocInfo();
	}
}
