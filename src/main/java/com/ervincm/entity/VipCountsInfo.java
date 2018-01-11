package com.ervincm.entity;

import java.util.Date;

public class VipCountsInfo {
	private Integer id;
	private String vipName;
	private String vipPwd;
	private String type; // tencent/iqiyi/youku
	private String phoneNumber;
	private String city;
	private String assignedNum;// 已分配人数
	private String usingNum; // 当前在使用人数
	private String changedTimes; // 使用频繁时，切换账号次数
	private String canChange;//是否可切换到该账号
	private Date vipRegisterTime;
	private Date vipDeadlineTime;
	
	private String assignedUsername1;
	private String assignedUsermac1;
	private String useVipState1;
	private Date registerTime1;
	private Date deadlineTime1;
	
	private String assignedUsername2;
	private String assignedUsermac2;
	private String useVipState2;
	private Date registerTime2;
	private Date deadlineTime2;
	
	private String assignedUsername3;
	private String assignedUsermac3;
	private String useVipState3;
	private Date registerTime3;
	private Date deadlineTime3;
	
	private String assignedUsername4;
	private String assignedUsermac4;
	private String useVipState4;
	private Date registerTime4;
	private Date deadlineTime4;
	
	private String assignedUsername5;
	private String assignedUsermac5;
	private String useVipState5;
	private Date registerTime5;
	private Date deadlineTime5;

	public  VipCountsInfo() {

	}

	
	
	public VipCountsInfo(String vipName, String vipPwd, String type,
			String city, String assignedNum, String usingNum,
			String changedTimes, String canChange, Date vipRegisterTime,
			Date vipDeadlineTime) {
		//super();
		this.vipName = vipName;
		this.vipPwd = vipPwd;
		this.type = type;
		this.city = city;
		this.assignedNum = assignedNum;
		this.usingNum = usingNum;
		this.changedTimes = changedTimes;
		this.canChange = canChange;
		this.vipRegisterTime = vipRegisterTime;
		this.vipDeadlineTime = vipDeadlineTime;
	}



	




	public VipCountsInfo(String vipName, String vipPwd, String type,
			String assignedNum, String usingNum, String changedTimes,
			VipUser userInfo1, VipUser userInfo2, VipUser userInfo3,
			VipUser userInfo4, VipUser userInfo5) {
		// super();
		this.vipName = vipName;
		this.vipPwd = vipPwd;
		this.type = type;
		this.assignedNum = assignedNum;
		this.usingNum = usingNum;
		this.changedTimes = changedTimes;
		this.assignedUsername1 = userInfo1.getUserName();
		this.assignedUsermac1 = userInfo1.getUserMac();
		this.useVipState1 = userInfo1.getUseVipState();
		this.registerTime1=userInfo1.getRegisterTime();
		this.deadlineTime1=userInfo1.getDeadlineTime();
		
		this.assignedUsername2 = userInfo2.getUserName();
		this.assignedUsermac2 = userInfo2.getUserMac();
		this.useVipState2 = userInfo2.getUseVipState();
		this.registerTime2=userInfo2.getRegisterTime();
		this.deadlineTime2=userInfo2.getDeadlineTime();

		this.assignedUsername3 = userInfo3.getUserName();
		this.assignedUsermac3 = userInfo3.getUserMac();
		this.useVipState3 = userInfo3.getUseVipState();
		this.registerTime3=userInfo3.getRegisterTime();
		this.deadlineTime3=userInfo3.getDeadlineTime();

		this.assignedUsername4 = userInfo4.getUserName();
		this.assignedUsermac4 = userInfo4.getUserMac();
		this.useVipState4 = userInfo4.getUseVipState();
		this.registerTime4=userInfo4.getRegisterTime();
		this.deadlineTime4=userInfo4.getDeadlineTime();

		this.assignedUsername5 = userInfo5.getUserName();
		this.assignedUsermac5 = userInfo5.getUserMac();
		this.useVipState5 = userInfo5.getUseVipState();
		this.registerTime5=userInfo5.getRegisterTime();
		this.deadlineTime5=userInfo5.getDeadlineTime();
	}

	public void setAssignedUserInfo1(VipUser user) {
		this.assignedUsername1 = user.getUserName();
		this.assignedUsermac1 = user.getUserMac();
		this.useVipState1 = user.getUseVipState();
		this.registerTime1=user.getRegisterTime();
		this.deadlineTime1=user.getDeadlineTime();
	}

	public void setAssignedUserInfo2(VipUser user) {
		this.assignedUsername2 = user.getUserName();
		this.assignedUsermac2 = user.getUserMac();
		this.useVipState2 = user.getUseVipState();
		this.registerTime2=user.getRegisterTime();
		this.deadlineTime2=user.getDeadlineTime();
	}

	public void setAssignedUserInfo3(VipUser user) {
		this.assignedUsername3 = user.getUserName();
		this.assignedUsermac3 = user.getUserMac();
		this.useVipState3 = user.getUseVipState();
		this.registerTime3=user.getRegisterTime();
		this.deadlineTime3=user.getDeadlineTime();
	}

	public void setAssignedUserInfo4(VipUser user) {
		this.assignedUsername4 = user.getUserName();
		this.assignedUsermac4 = user.getUserMac();
		this.useVipState4 = user.getUseVipState();
		this.registerTime4=user.getRegisterTime();
		this.deadlineTime4=user.getDeadlineTime();
	}

	public void setAssignedUserInfo5(VipUser user) {
		this.assignedUsername5 = user.getUserName();
		this.assignedUsermac5 = user.getUserMac();
		this.useVipState5 = user.getUseVipState();
		this.registerTime5=user.getRegisterTime();
		this.deadlineTime5=user.getDeadlineTime();
	}

	public VipUser getAssignedUserInfo1(){
		VipUser vipUser=new VipUser(this.assignedUsername1, this.assignedUsermac1, this.useVipState1, this.registerTime1, this.deadlineTime1);
		return vipUser;
	}
	
	public VipUser getAssignedUserInfo2(){
		VipUser vipUser=new VipUser(this.assignedUsername2, this.assignedUsermac2, this.useVipState2, this.registerTime2, this.deadlineTime2);
		return vipUser;
	}
	public VipUser getAssignedUserInfo3(){
		VipUser vipUser=new VipUser(this.assignedUsername3, this.assignedUsermac3, this.useVipState3, this.registerTime3, this.deadlineTime3);
		return vipUser;
	}
	public VipUser getAssignedUserInfo4(){
		VipUser vipUser=new VipUser(this.assignedUsername4, this.assignedUsermac4, this.useVipState4, this.registerTime4, this.deadlineTime4);
		return vipUser;
	}
	public VipUser getAssignedUserInfo5(){
		VipUser vipUser=new VipUser(this.assignedUsername5, this.assignedUsermac5, this.useVipState5, this.registerTime5, this.deadlineTime5);
		return vipUser;
	}
	public int getFirstIndexAssignedUserIsNull() {
		if (this.assignedUsermac1 == null) {
			return 1;
		} else if (this.assignedUsermac2 == null) {
			return 2;
		} else if (this.assignedUsermac3 == null) {
			return 3;
		} else if (this.assignedUsermac4 == null) {
			return 4;
		} else if (this.assignedUsermac5 == null) {
			return 5;
		}

		return -1;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getVipPwd() {
		return vipPwd;
	}

	public void setVipPwd(String vipPwd) {
		this.vipPwd = vipPwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssignedNum() {
		return assignedNum;
	}

	public void setAssignedNum(String assignedNum) {
		this.assignedNum = assignedNum;
	}

	public String getUsingNum() {
		return usingNum;
	}

	public void setUsingNum(String usingNum) {
		this.usingNum = usingNum;
	}

	public String getChangedTimes() {
		return changedTimes;
	}

	public void setChangedTimes(String changedTimes) {
		this.changedTimes = changedTimes;
	}

	public String getAssignedUsername1() {
		return assignedUsername1;
	}

	public void setAssignedUsername1(String assignedUsername1) {
		this.assignedUsername1 = assignedUsername1;
	}

	public String getAssignedUsermac1() {
		return assignedUsermac1;
	}

	public void setAssignedUsermac1(String assignedUsermac1) {
		this.assignedUsermac1 = assignedUsermac1;
	}

	public String getAssignedUsername2() {
		return assignedUsername2;
	}

	public void setAssignedUsername2(String assignedUsername2) {
		this.assignedUsername2 = assignedUsername2;
	}

	public String getAssignedUsermac2() {
		return assignedUsermac2;
	}

	public void setAssignedUsermac2(String assignedUsermac2) {
		this.assignedUsermac2 = assignedUsermac2;
	}

	public String getAssignedUsername3() {
		return assignedUsername3;
	}

	public void setAssignedUsername3(String assignedUsername3) {
		this.assignedUsername3 = assignedUsername3;
	}

	public String getAssignedUsermac3() {
		return assignedUsermac3;
	}

	public void setAssignedUsermac3(String assignedUsermac3) {
		this.assignedUsermac3 = assignedUsermac3;
	}

	public String getAssignedUsername4() {
		return assignedUsername4;
	}

	public void setAssignedUsername4(String assignedUsername4) {
		this.assignedUsername4 = assignedUsername4;
	}

	public String getAssignedUsermac4() {
		return assignedUsermac4;
	}

	public void setAssignedUsermac4(String assignedUsermac4) {
		this.assignedUsermac4 = assignedUsermac4;
	}

	public String getAssignedUsername5() {
		return assignedUsername5;
	}

	public void setAssignedUsername5(String assignedUsername5) {
		this.assignedUsername5 = assignedUsername5;
	}

	public String getAssignedUsermac5() {
		return assignedUsermac5;
	}

	public void setAssignedUsermac5(String assignedUsermac5) {
		this.assignedUsermac5 = assignedUsermac5;
	}

	public String getUseVipState1() {
		return useVipState1;
	}

	public void setUseVipState1(String useVipState1) {
		this.useVipState1 = useVipState1;
	}

	public String getUseVipState2() {
		return useVipState2;
	}

	public void setUseVipState2(String useVipState2) {
		this.useVipState2 = useVipState2;
	}

	public String getUseVipState3() {
		return useVipState3;
	}

	public void setUseVipState3(String useVipState3) {
		this.useVipState3 = useVipState3;
	}

	public String getUseVipState4() {
		return useVipState4;
	}

	public void setUseVipState4(String useVipState4) {
		this.useVipState4 = useVipState4;
	}

	public String getUseVipState5() {
		return useVipState5;
	}

	public void setUseVipState5(String useVipState5) {
		this.useVipState5 = useVipState5;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCanChange() {
		return canChange;
	}



	public void setCanChange(String canChange) {
		this.canChange = canChange;
	}



	public Date getRegisterTime1() {
		return registerTime1;
	}



	public void setRegisterTime1(Date registerTime1) {
		this.registerTime1 = registerTime1;
	}



	public Date getDeadlineTime1() {
		return deadlineTime1;
	}



	public void setDeadlineTime1(Date deadlineTime1) {
		this.deadlineTime1 = deadlineTime1;
	}



	public Date getRegisterTime2() {
		return registerTime2;
	}



	public void setRegisterTime2(Date registerTime2) {
		this.registerTime2 = registerTime2;
	}



	public Date getDeadlineTime2() {
		return deadlineTime2;
	}



	public void setDeadlineTime2(Date deadlineTime2) {
		this.deadlineTime2 = deadlineTime2;
	}



	public Date getRegisterTime3() {
		return registerTime3;
	}



	public void setRegisterTime3(Date registerTime3) {
		this.registerTime3 = registerTime3;
	}



	public Date getDeadlineTime3() {
		return deadlineTime3;
	}



	public void setDeadlineTime3(Date deadlineTime3) {
		this.deadlineTime3 = deadlineTime3;
	}



	public Date getRegisterTime4() {
		return registerTime4;
	}



	public void setRegisterTime4(Date registerTime4) {
		this.registerTime4 = registerTime4;
	}



	public Date getDeadlineTime4() {
		return deadlineTime4;
	}



	public void setDeadlineTime4(Date deadlineTime4) {
		this.deadlineTime4 = deadlineTime4;
	}



	public Date getRegisterTime5() {
		return registerTime5;
	}



	public void setRegisterTime5(Date registerTime5) {
		this.registerTime5 = registerTime5;
	}



	public Date getDeadlineTime5() {
		return deadlineTime5;
	}



	public void setDeadlineTime5(Date deadlineTime5) {
		this.deadlineTime5 = deadlineTime5;
	}



	public Date getVipRegisterTime() {
		return vipRegisterTime;
	}

	public void setVipRegisterTime(Date vipRegisterTime) {
		this.vipRegisterTime = vipRegisterTime;
	}

	public Date getVipDeadlineTime() {
		return vipDeadlineTime;
	}

	public void setVipDeadlineTime(Date vipDeadlineTime) {
		this.vipDeadlineTime = vipDeadlineTime;
	}

	
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "VipCountsInfo [id=" + id + ", vipName=" + vipName + ", vipPwd="
				+ vipPwd + ", type=" + type + ", city=" + city
				+ ", assignedNum=" + assignedNum + ", usingNum=" + usingNum
				+ ", changedTimes=" + changedTimes + ", canChange=" + canChange
				+ ", vipRegisterTime=" + vipRegisterTime + ", vipDeadlineTime="
				+ vipDeadlineTime + ", assignedUsername1=" + assignedUsername1
				+ ", assignedUsermac1=" + assignedUsermac1 + ", useVipState1="
				+ useVipState1 + ", registerTime1=" + registerTime1
				+ ", deadlineTime1=" + deadlineTime1 + ", assignedUsername2="
				+ assignedUsername2 + ", assignedUsermac2=" + assignedUsermac2
				+ ", useVipState2=" + useVipState2 + ", registerTime2="
				+ registerTime2 + ", deadlineTime2=" + deadlineTime2
				+ ", assignedUsername3=" + assignedUsername3
				+ ", assignedUsermac3=" + assignedUsermac3 + ", useVipState3="
				+ useVipState3 + ", registerTime3=" + registerTime3
				+ ", deadlineTime3=" + deadlineTime3 + ", assignedUsername4="
				+ assignedUsername4 + ", assignedUsermac4=" + assignedUsermac4
				+ ", useVipState4=" + useVipState4 + ", registerTime4="
				+ registerTime4 + ", deadlineTime4=" + deadlineTime4
				+ ", assignedUsername5=" + assignedUsername5
				+ ", assignedUsermac5=" + assignedUsermac5 + ", useVipState5="
				+ useVipState5 + ", registerTime5=" + registerTime5
				+ ", deadlineTime5=" + deadlineTime5 + "]";
	}






	
}
