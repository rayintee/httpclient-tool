package com.httpclient.tool.adapter.module;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.httpclient.tool.adapter.util.ConstantsUtil;

public class HttpGetClient {
	private static CloseableHttpClient httpClient = null;// http client 对象
	private static HttpUriRequest httpGet = null;// http menthod 对象
	private static CloseableHttpResponse response = null;// http response对象
	private static HttpEntity entity = null;// http entity对象
	
	public static String get(String url, Map<String, Object> params){
		StringBuffer suf = new StringBuffer();
		String ret = ConstantsUtil.Default_Result_String;// 默认返回
		try {
			httpClient = HttpClients.createDefault();// 创建默认的http client
			RequestBuilder rb = RequestBuilder.get().setUri(new URI(url));
			//List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 传入各种参数
			for (Entry<String, Object> set : params.entrySet()) {
				String key = set.getKey();
				String value = set.getValue() == null ? "" : set.getValue().toString();
				rb.addParameter(new BasicNameValuePair(key, value));
				suf.append(" [\"" + key + "\":" + value + "] ");
			}
			System.out.println("params值-->\n" + suf.toString());
			//UrlEncodedFormEntity uefEntity= new UrlEncodedFormEntity(nvps, "UTF-8");
			httpGet = rb.build();
			response = httpClient.execute(httpGet);
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
					System.err.println("httpPost URL [" + url + "],httpEntity is null.");
					ret = ConstantsUtil.getResult(1, "调用接口失败-->\n httpPost URL [" + url + "],httpEntity is null.");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally{
			if(response != null){
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
				System.out.println("调用接口【" + url + "】结束，关闭http client连接!");
			}
		}
		return ret;
	}
}
