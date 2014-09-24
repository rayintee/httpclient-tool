package com.httpclient.tool.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.httpclient.tool.adapter.model.FormFile;
import com.httpclient.tool.adapter.module.HttpGetClient;
import com.httpclient.tool.adapter.module.HttpPostClient;

public class TiKuInterfaceTest {

	/**
	 * 表单文件上传测试
	 */
	public void formFileUpload() {
		// 接口地址
		String url = "http://localhost:8015/tiku/service/document/upload";
		System.out.println("开始调用文档上传接口--->\n" + url);

		// 表单参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", "test01");// 上传用户
		params.put("school", "合肥市江淮小学");// 学校
		params.put("className", 2);// 年级
		params.put("subject", 1);// 科目
		params.put("hours", 1);// 课时
		Map<String, Object> m = new HashMap<String, Object>();
		String text = JSON.toJSONString(params);
		System.out.println("将参数转换成json字符串-->\n" + text);
		m.put("fileProperty", text);

		// 文件参数
		File file = new File("F:/temp/白板语文题库.doc");
		String fileName = "office";

		FormFile ff = new FormFile(url, file, fileName, m);

		//调用表单上传接口
		String ret = HttpPostClient.post(ff);
		System.out.println("接收到的返回报文-->\n" + ret);
		System.out.println("调用文档上传接口结束--->\n");
	}
	
	/**
	 * 获取当前用户所有的文档列表信息
	 */
	public void getDocList(){
		// 接口地址
		String url = "http://localhost:8015/tiku/service/topic/getDocList";
		System.out.println("开始调用获取文档信息列表接口-->\n" + url);
		
		// 表单参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", "test01");
		
		//调用获取文档列表接口
		String ret = HttpGetClient.get(url, params);
		System.out.println("接收到的返回报文-->\n" + ret);
		System.out.println("调用获取文档列表接口结束--->\n");
	}
	
	/**
	 *  获取当前文档内容信息
	 */
	public void getTopicList(){
		// 接口地址
		String url = "http://localhost:8015/tiku/service/topic/getTopicList";
		System.out.println("开始调用获取文档内容列表接口-->\n" + url);
		
		// 表单参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("docId", 27);
		
		//调用获取文档列表接口
		String ret = HttpGetClient.get(url, params);
		System.out.println("接收到的返回报文-->\n" + ret);
		System.out.println("调用获取文档内容接口结束--->\n");
	}
}