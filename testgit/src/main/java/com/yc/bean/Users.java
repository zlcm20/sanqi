package com.yc.bean;

import java.io.Serializable;

public class Users implements Serializable {
	
	
	private static final long serialVersionUID = 5848632170888435504L;
	private Integer uid;
	private String uname;
	private String password;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Users [uid=" + uid + ", uname=" + uname + ", password="
				+ password + "]\n";
	}
	
	
}
