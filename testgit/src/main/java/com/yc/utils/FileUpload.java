package com.yc.utils;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class FileUpload {
	private String allowedFilesList = "gif,jpg,png,bmp";
	private String deniedFilesList = "jsp,asp,php,aspx,html,htm,exe,bat,js,py,sh";
	private long singleFileSize = 20 * 1024 * 1024;
	private long totalMaxFileSize = 5 * singleFileSize;
	// 将来在页面上<immg src ="" /> 访问路径 ，是一个相对路径
//	private String dirName = "../upload/";
//	private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	public Map<String,String> uploadFiles(PageContext pageContext,HttpServletRequest request) throws SmartUploadException, IOException, ServletException, SQLException {
		Map<String, String> map = new HashMap<String,String>();
		SmartUpload su = new SmartUpload();
		su.setCharset("UTF-8");
		su.initialize(pageContext);

		//定义允许上传的文件类型
		su.setAllowedFilesList(allowedFilesList);
		//不允许上传的类型
		su.setDeniedFilesList(deniedFilesList);
		//单个文件最大限制
		su.setMaxFileSize(singleFileSize);
		//所有上传文件的总容量限制
		su.setTotalMaxFileSize(totalMaxFileSize);
		su.upload();

		//取出jspsmartupload中的request对象，以取出uname，message
		com.jspsmart.upload.Request req = su.getRequest();
		Enumeration<String> enu = req.getParameterNames();
		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			map.put(key,req.getParameter(key).trim());
		}

		Files files = su.getFiles();
		int count = files.getCount();
		if (files!=null&&count>0) {
			
			for (int i = 0; i < count; i++) {
				//获取单个上传文件
				Calendar c=Calendar.getInstance();
				String tomcatdir=request.getRealPath("/");
				File tomcatFile=new File(tomcatdir);
				File webappath=tomcatFile.getParentFile();
				
				File picpath=new File(webappath,"pic"+File.separator+c.get(Calendar.YEAR)+File.separator+(c.get(Calendar.MONTH)+1)+File.separator);
				String weburl="../pic/"+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/";
				if(picpath.exists() == false){
					picpath.mkdirs();
				}
				com.jspsmart.upload.File file = files.getFile(i);
				String oldname = file.getFileName();
				String filePrefixName = getNewFilePrefixName();
				
				String extName=file.getFileExt();
				String fileName=filePrefixName+"."+extName;
				
				weburl+=fileName;
				String destFilePathName=picpath+"/"+fileName;
				
				file.saveAs(destFilePathName, SmartUpload.SAVE_PHYSICAL);
				
				String filedName=file.getFieldName();
				String oldFileName=file.getFileName();
				//将这个文件的信息存到result中 键的规则：域名_relativepath
				//										域名_absolutepath
				map.put(file.getFieldName() + "_weburl", weburl);
				map.put(file.getFieldName() + "_destFilePathName", destFilePathName);
				map.put(file.getFieldName() + "_fileName", fileName);
			}
		}
		return map;
	}

	private String getNewFilePrefixName() {
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("SSyyyymmddHHmmss");
		String filePrefixName=sdf.format(d);
		return filePrefixName;
	}

	public String getAllowedFilesList() {
		return allowedFilesList;
	}

	public void setAllowedFilesList(String allowedFilesList) {
		this.allowedFilesList = allowedFilesList;
	}

	public String getDeniedFilesList() {
		return deniedFilesList;
	}

	public void setDeniedFilesList(String deniedFilesList) {
		this.deniedFilesList = deniedFilesList;
	}

	public long getSingleFileSize() {
		return singleFileSize;
	}

	public void setSingleFileSize(long singleFileSize) {
		this.singleFileSize = singleFileSize;
	}

	public long getTotalMaxFileSize() {
		return totalMaxFileSize;
	}

	public void setTotalMaxFileSize(long totalMaxFileSize) {
		this.totalMaxFileSize = totalMaxFileSize;
	}

	
}