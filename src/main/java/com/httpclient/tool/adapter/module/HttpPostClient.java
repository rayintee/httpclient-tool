package com.httpclient.tool.adapter.module;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.httpclient.tool.adapter.model.FormFile;
import com.httpclient.tool.adapter.util.ConstantsUtil;

public class HttpPostClient {

	private static CloseableHttpClient httpClient = null;// http client 对象
	private static HttpPost httpPost = null;// http menthod 对象
	private static CloseableHttpResponse response = null;// http response对象
	private static HttpEntity entity = null;// http entity对象

	public static String post(FormFile ff) {
		StringBuffer suf = new StringBuffer();
		String ret = ConstantsUtil.Default_Result_String;// 默认返回
		try {
			httpClient = HttpClients.createDefault();// 创建默认的http client
			httpPost = new HttpPost(ff.getUrl());// 创建post方法对象
			//设置头部属性
			httpPost.addHeader("charset", "UTF-8");
			MultipartEntityBuilder meb = MultipartEntityBuilder.create();
			// 设置为浏览器兼容模式
			meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 设置请求的编码格式
			meb.setCharset(Charset.defaultCharset());
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 传入各种参数
			for (Entry<String, Object> set : ff.getParams().entrySet()) {
				String key = set.getKey();
				String value = set.getValue() == null ? "" : set.getValue().toString();
				nvps.add(new BasicNameValuePair(key, value));
				meb.addTextBody(key, value, ContentType.create("charset", "UTF-8"));
				suf.append(" [\"" + key + "\":" + value + "] ");
			}
			System.out.println(suf.toString());
			// 上传文件
			if (ff.getFile() != null) {
				FileBody fb = new FileBody(ff.getFile());
				meb.addPart(ff.getFileName(), fb);
			}
			httpPost.setEntity(meb.build());// 设置实体entity
			response = httpClient.execute(httpPost);// 发送消息
			response.addHeader("charset", "UTF-8");
			int statusCode = response.getStatusLine().getStatusCode();// 状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpStatus ERROR" + "Method failed: " + response.getStatusLine());
				ret = ConstantsUtil.getResult(1, "调用接口失败-->" + response.getStatusLine());
			} else {
				entity = response.getEntity();
				if (null != entity) {
					byte[] bytes = EntityUtils.toByteArray(entity);
					ret = new String(bytes, "UTF-8");
				} else {
					System.err.println("httpPost URL [" + ff.getUrl()
							+ "],httpEntity is null.");
					ret = ConstantsUtil.getResult(1, "调用接口失败-->\n httpPost URL [" + ff.getUrl() + "],httpEntity is null.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
					response = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();// 关闭httpClient
					httpClient = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("调用接口【" + ff.getUrl() + "】结束，关闭http client连接!");
			}
		}
		return ret;
	}
}
