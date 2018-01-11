package com.ervincm.entity;

import java.util.Date;

import net.sf.json.JSONObject;

public class VipUsersInfo {

	private Integer id;
	private String userName;
	private String userMac;
	private String city;
	private Date useChangeDay;
	private String useChangeTimes;
	
	private String tencentVipName;
	private String tencentVipPwd;
	private String tencentUseState;
	private Date tencentregisterTime;
	private Date tencentdeadlineTime;
	private String istencentVip;
	private Date tencentLoginTime;
	
	private String iqiyiVipName;
	private String iqiyiVipPwd;
	private String iqiyiUseState;
	private Date iqiyiregisterTime;
	private Date iqiyideadlineTime;
	private String isiqiyiVip;
	private Date iqiyiLoginTime;
	
	private String youkuVipName;
	private String youkuVipPwd;
	private String youkuUseState;
	private Date youkuregisterTime;
	private Date youkudeadlineTime;
	private String isyoukuVip;
	private Date youkuLoginTime;

	public VipUsersInfo() {

	}


	







	public VipUsersInfo(String userName, String userMac, String city, VipCount tencent,
			VipCount iqiyi, VipCount youku) {
		this.userName = userName;
		this.userMac = userMac;
		this.city = city;
		this.tencentVipName = tencent.getName();
		this.tencentVipPwd = tencent.getPassword();
		this.tencentUseState = tencent.getUseState();
		this.tencentregisterTime=tencent.getRegisterTime();
		this.tencentdeadlineTime=tencent.getDeadlineTime();
		this.istencentVip=tencent.getIsvip();
		
		this.iqiyiVipName = iqiyi.getName();
		this.iqiyiVipPwd = iqiyi.getPassword();
		this.iqiyiUseState = iqiyi.getUseState();
		this.iqiyiregisterTime=iqiyi.getRegisterTime();
		this.iqiyideadlineTime=iqiyi.getDeadlineTime();
		this.isiqiyiVip=iqiyi.getIsvip();
		
		this.youkuVipName = youku.getName();
		this.youkuVipPwd = youku.getPassword();
		this.youkuUseState = youku.getUseState();
		this.youkuregisterTime=youku.getRegisterTime();
		this.youkudeadlineTime=youku.getDeadlineTime();
		this.isyoukuVip=youku.getIsvip();
	}

	public VipCount getVipCountByType(String type){
		VipCount vipCount=null;
		if(type.equalsIgnoreCase("tencent")){
			vipCount=new VipCount(this.tencentVipName, this.tencentVipPwd, this.tencentUseState,this.getTencentregisterTime(),this.getTencentdeadlineTime(),this.getIstencentVip());
		}
		else if (type.equalsIgnoreCase("iqiyi")) {
			vipCount=new VipCount(this.iqiyiVipName, this.iqiyiVipPwd, this.iqiyiUseState,this.getIqiyiregisterTime(),this.getIqiyideadlineTime(),this.getIsiqiyiVip());
		}
		else if (type.equalsIgnoreCase("youku")) {
			vipCount=new VipCount(this.youkuVipName, this.youkuVipPwd, this.youkuUseState,this.getYoukuregisterTime(),this.getYoukudeadlineTime(),this.getIsyoukuVip());
		}
		return vipCount;
	}
	public VipCount getTencentVipInfo() {
		VipCount tencent = new VipCount(this.tencentVipName, this.tencentVipPwd, this.tencentUseState,this.getTencentregisterTime(),this.getTencentdeadlineTime(),this.getIstencentVip());
		return tencent;
	}

	public void setTencentVipInfo(VipCount tencent) {
		this.tencentVipName = tencent.getName();
		this.tencentVipPwd = tencent.getPassword();
		this.tencentUseState = tencent.getUseState();
		this.tencentregisterTime=tencent.getRegisterTime();
		this.tencentdeadlineTime=tencent.getDeadlineTime();
		this.istencentVip=tencent.getIsvip();
	}

	public VipCount getIqiyiVipInfo() {
		VipCount iqiyi = new VipCount(this.iqiyiVipName, this.iqiyiVipPwd, this.iqiyiUseState,this.getIqiyiregisterTime(),this.getIqiyideadlineTime(),this.getIsiqiyiVip());
		return iqiyi;
	}

	public void setIqiyiVipInfo(VipCount iqiyi) {
		this.iqiyiVipName = iqiyi.getName();
		this.iqiyiVipPwd = iqiyi.getPassword();
		this.iqiyiUseState = iqiyi.getUseState();
		this.iqiyiregisterTime=iqiyi.getRegisterTime();
		this.iqiyideadlineTime=iqiyi.getDeadlineTime();
		this.isiqiyiVip=iqiyi.getIsvip();
	}

	public VipCount getYoukuVipInfo() {
		VipCount youku = new VipCount(this.youkuVipName, this.youkuVipPwd, this.youkuUseState,this.getYoukuregisterTime(),this.getYoukudeadlineTime(),this.getIsyoukuVip());
		return youku;
	}

	public void setYoukuVipInfo(VipCount youku) {
		this.youkuVipName = youku.getName();
		this.youkuVipPwd = youku.getPassword();
		this.youkuUseState = youku.getUseState();
		this.youkuregisterTime=youku.getRegisterTime();
		this.youkudeadlineTime=youku.getDeadlineTime();
		this.isyoukuVip=youku.getIsvip();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMac() {
		return userMac;
	}

	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTencentVipName() {
		return tencentVipName;
	}

	public void setTencentVipName(String tencentVipName) {
		this.tencentVipName = tencentVipName;
	}

	public String getTencentVipPwd() {
		return tencentVipPwd;
	}

	public void setTencentVipPwd(String tencentVipPwd) {
		this.tencentVipPwd = tencentVipPwd;
	}

	public String getTencentUseState() {
		return tencentUseState;
	}

	public void setTencentUseState(String tencentUseState) {
		this.tencentUseState = tencentUseState;
	}

	public String getIqiyiVipName() {
		return iqiyiVipName;
	}

	public void setIqiyiVipName(String iqiyiVipName) {
		this.iqiyiVipName = iqiyiVipName;
	}

	public String getIqiyiVipPwd() {
		return iqiyiVipPwd;
	}

	public void setIqiyiVipPwd(String iqiyiVipPwd) {
		this.iqiyiVipPwd = iqiyiVipPwd;
	}

	public String getIqiyiUseState() {
		return iqiyiUseState;
	}

	public void setIqiyiUseState(String iqiyiUseState) {
		this.iqiyiUseState = iqiyiUseState;
	}

	public String getYoukuVipName() {
		return youkuVipName;
	}

	public void setYoukuVipName(String youkuVipName) {
		this.youkuVipName = youkuVipName;
	}

	public String getYoukuVipPwd() {
		return youkuVipPwd;
	}

	public void setYoukuVipPwd(String youkuVipPwd) {
		this.youkuVipPwd = youkuVipPwd;
	}

	public String getYoukuUseState() {
		return youkuUseState;
	}

	public void setYoukuUseState(String youkuUseState) {
		this.youkuUseState = youkuUseState;
	}

	
	public Date getTencentregisterTime() {
		return tencentregisterTime;
	}



	public void setTencentregisterTime(Date tencentregisterTime) {
		this.tencentregisterTime = tencentregisterTime;
	}



	public Date getTencentdeadlineTime() {
		return tencentdeadlineTime;
	}



	public void setTencentdeadlineTime(Date tencentdeadlineTime) {
		this.tencentdeadlineTime = tencentdeadlineTime;
	}



	public Date getIqiyiregisterTime() {
		return iqiyiregisterTime;
	}



	public void setIqiyiregisterTime(Date iqiyiregisterTime) {
		this.iqiyiregisterTime = iqiyiregisterTime;
	}



	public Date getIqiyideadlineTime() {
		return iqiyideadlineTime;
	}



	public void setIqiyideadlineTime(Date iqiyideadlineTime) {
		this.iqiyideadlineTime = iqiyideadlineTime;
	}



	public Date getYoukuregisterTime() {
		return youkuregisterTime;
	}



	public void setYoukuregisterTime(Date youkuregisterTime) {
		this.youkuregisterTime = youkuregisterTime;
	}



	public Date getYoukudeadlineTime() {
		return youkudeadlineTime;
	}



	public void setYoukudeadlineTime(Date youkudeadlineTime) {
		this.youkudeadlineTime = youkudeadlineTime;
	}



	public Date getUseChangeDay() {
		return useChangeDay;
	}



	public void setUseChangeDay(Date useChangeDay) {
		this.useChangeDay = useChangeDay;
	}



	public String getUseChangeTimes() {
		return useChangeTimes;
	}



	public void setUseChangeTimes(String useChangeTimes) {
		this.useChangeTimes = useChangeTimes;
	}



	public String getIstencentVip() {
		return istencentVip;
	}


	public void setIstencentVip(String istencentVip) {
		this.istencentVip = istencentVip;
	}


	public String getIsiqiyiVip() {
		return isiqiyiVip;
	}


	public void setIsiqiyiVip(String isiqiyiVip) {
		this.isiqiyiVip = isiqiyiVip;
	}


	public String getIsyoukuVip() {
		return isyoukuVip;
	}


	public void setIsyoukuVip(String isyoukuVip) {
		this.isyoukuVip = isyoukuVip;
	}


	public Date getTencentLoginTime() {
		return tencentLoginTime;
	}


	public void setTencentLoginTime(Date tencentLoginTime) {
		this.tencentLoginTime = tencentLoginTime;
	}


	public Date getIqiyiLoginTime() {
		return iqiyiLoginTime;
	}


	public void setIqiyiLoginTime(Date iqiyiLoginTime) {
		this.iqiyiLoginTime = iqiyiLoginTime;
	}


	public Date getYoukuLoginTime() {
		return youkuLoginTime;
	}


	public void setYoukuLoginTime(Date youkuLoginTime) {
		this.youkuLoginTime = youkuLoginTime;
	}


	@Override
	public String toString() {

		return JSONObject.fromObject(this).toString();
		
//		return "VipUsersInfo [id=" + id + ", userName=" + userName
//				+ ", userMac=" + userMac + ", city=" + city + ", useChangeDay="
//				+ useChangeDay + ", useChangeTimes=" + useChangeTimes
//				+ ", tencentVipName=" + tencentVipName + ", tencentVipPwd="
//				+ tencentVipPwd + ", tencentUseState=" + tencentUseState
//				+ ", tencentregisterTime=" + tencentregisterTime
//				+ ", tencentdeadlineTime=" + tencentdeadlineTime
//				+ ", istencentVip=" + istencentVip + ", iqiyiVipName="
//				+ iqiyiVipName + ", iqiyiVipPwd=" + iqiyiVipPwd
//				+ ", iqiyiUseState=" + iqiyiUseState + ", iqiyiregisterTime="
//				+ iqiyiregisterTime + ", iqiyideadlineTime="
//				+ iqiyideadlineTime + ", isiqiyiVip=" + isiqiyiVip
//				+ ", youkuVipName=" + youkuVipName + ", youkuVipPwd="
//				+ youkuVipPwd + ", youkuUseState=" + youkuUseState
//				+ ", youkuregisterTime=" + youkuregisterTime
//				+ ", youkudeadlineTime=" + youkudeadlineTime + ", isyoukuVip="
//				+ isyoukuVip + "]";
	}


	









}
