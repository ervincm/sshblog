package com.ervincm.entity;

import java.util.Date;

public class RegisterUsersInfo {

	private String username ;
	private String userMac ;
	private String ip;
	private String vipType;
	private Date registerTime;
	private Date deadlineTime;
	private String city;
	
	public  RegisterUsersInfo(){
		
	}



	public RegisterUsersInfo(String username, String userMac, String ip,
			String vipType, Date registerTime, Date deadlineTime,
			String city) {
		//super();
		this.username = username;
		this.userMac = userMac;
		this.ip = ip;
		this.vipType = vipType;
		this.registerTime = registerTime;
		this.deadlineTime = deadlineTime;
		this.city = city;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserMac() {
		return userMac;
	}

	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}
	
	
}
