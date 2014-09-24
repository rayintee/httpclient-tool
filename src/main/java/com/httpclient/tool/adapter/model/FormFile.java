package com.httpclient.tool.adapter.model;

import java.io.File;
import java.util.Map;

public class FormFile {
	private String url;// 请求地址
	private File file;// 表单文件
	private String fileName;// 表单对像属性名称
	private Map<String, Object> params;// 表单参数

	public FormFile(String url, Map<String, Object> params) {
		this.url = url;
		this.params = params;
	}

	public FormFile(String url, File file, String fileName,
			Map<String, Object> params) {
		this.url = url;
		this.file = file;
		this.fileName = fileName;
		this.params = params;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
