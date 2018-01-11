package com.ervincm.service;

import com.ervincm.dao.VipCountsInfoDao;
import com.ervincm.dao.VipUsersInfoDao;
import com.ervincm.dao.WaitUsersInfoDao;
import com.ervincm.entity.*;
import com.ervincm.util.StringAndDate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Service {

	// state 0:

	public static int registerState = 0;
	public static final int ALREADY_REGISTER = 1;
	public static int getVipState = 0;
	public static final int IN_WAIT_TABLE = 1; // 进入等待队列
	public static final int WAIT_TABLE_FULL = 2;// 次月的等待人数满，不再排队，
	public static final int NO_REGISTER = 3;// 未注册为会员用户
	public static final int NO_REGISTER_TENCENT_VIP = 4;
	public static final int NO_REGISTER_IQIYI_VIP = 5;
	public static final int NO_REGISTER_YOUKU_VIP = 6;
	public static int changeVipState = 0;
	public static final int CHANGE_VIP_SUCCESS = 1; //
	public static final int CHANGE_VIP_FAIL = 2;//
	public static int switchTimes = 0;
	public static final int OUT_OF_SWITCH_TIMES = 2;//
	public static String outString;

	static {

	}

	public Boolean changeVipPwd(String vipName, String password, String vipType) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		VipCountsInfo vipCountsInfo = vipCountsInfoDao.loadVipCount(vipName,
				vipType);
		if (vipCountsInfo == null) {
			System.out.println("vipCountsInfo==null");
			return false;
		}

		// 得到账号下的user
		ArrayList<VipUser> list = new ArrayList<VipUser>();
		list.add(vipCountsInfo.getAssignedUserInfo1());
		list.add(vipCountsInfo.getAssignedUserInfo2());
		list.add(vipCountsInfo.getAssignedUserInfo3());
		list.add(vipCountsInfo.getAssignedUserInfo4());
		list.add(vipCountsInfo.getAssignedUserInfo5());
		// 提交更新vipCountsinfo的信息password
		vipCountsInfo.setVipPwd(password);
		vipCountsInfoDao.updateVipCountWithId(vipCountsInfo);

		for (int i = 0; i < list.size(); i++) {

			String dataString = StringAndDate.getStringDateShort();

			if (list.get(i).getDeadlineTime() == null) {
				continue;
			}
			long days = 0;
			try {
				days = StringAndDate.daysBetween(list.get(i).getDeadlineTime(),
						StringAndDate.getNowDateShort());// 后面-前面
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (days > 0) {// 用户会员时间到期
				// 删除vipCountsinfo的信息
				VipCountsInfo vipCountsInfo1 = vipCountsInfoDao.loadVipCount(
						vipName, vipType);
				updateDeleteVipCountsInfo(vipCountsInfo1, list.get(i)
						.getUserMac());// 可优化的地方，不用单次检测提交，数据循环完了再一次性提交到数据库
				// 删除用户表的信息
				VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(list.get(
						i).getUserMac());
				VipCount vipCountNew = new VipCount(null, null, null, null,
						null, null);
				updateChangeVipUserInfo(vipUsersInfo, vipCountsInfo.getType(),
						vipCountNew);
			} else {// 用户会员未到期
					// 更新用户表中的信息
				VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(list.get(
						i).getUserMac());
				if (vipUsersInfo == null) {
					System.out.println("vipUsersInfo==null");
					continue;
				}
				VipCount vipCountNew = vipUsersInfo
						.getVipCountByType(vipCountsInfo.getType());
				vipCountNew.setPassword(password);// 更改password
				updateChangeVipUserInfo(vipUsersInfo, vipCountsInfo.getType(),
						vipCountNew);
			}
		}

		return true;

	}

	public Boolean registerVip(RegisterUsersInfo registerInfo) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		WaitUsersInfoDao waitUsersInfoDao = new WaitUsersInfoDao();
		VipUsersInfo user = vipUsersInfoDao
				.loadUsers(registerInfo.getUserMac());

		if (user != null
				&& user.getVipCountByType(registerInfo.getVipType()).getName() != null) {
			registerState = ALREADY_REGISTER;
			return false;
		}
		// 优先分配同城市，且已分配人数少的账号
		VipCountsInfo vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
				registerInfo.getVipType(), registerInfo.getCity(), "0");
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "1");
		}
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "2");
		}
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "3");
		}

		// 次分配,从未分配的账号，并修改城市标签
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao
					.getVipCountWithoutCity(registerInfo.getVipType());
		}
		if (vipCountsInfo == null) {
			// 未添加，则添加到已注册用户表
			if (vipUsersInfoDao.loadUsers(registerInfo.getUserMac()) == null) {// 添加用户
				VipUsersInfo vipUser = new VipUsersInfo();
				vipUser.setCity(registerInfo.getCity());
				vipUser.setUserName(registerInfo.getUsername());
				vipUser.setUserMac(registerInfo.getUserMac());
				vipUsersInfoDao.addUsers(vipUser);
			}
			// 同城市下，同类型vip账号可分配人数<等待人数，则不再排队
			List<?> list = vipCountsInfoDao.listCountsByCityAndViptype(
					registerInfo.getCity(), registerInfo.getVipType());
			List<?> list2 = waitUsersInfoDao.listWaitUsers(
					registerInfo.getCity(), registerInfo.getVipType());
			if (list.size() * 4 <= list2.size()) {// 只排队账号*4的人数
				getVipState = WAIT_TABLE_FULL;
				System.out.println("无可用账号，且等待表满");
				return false;
			}
			// 进入等待表
			addToWaitUsersInfo(registerInfo);
			getVipState = IN_WAIT_TABLE;
			System.out.println("无可用账号，添加到等待用户表中");
			return false;
		}

		// 更新userInfo
		if (vipUsersInfoDao.loadUsers(registerInfo.getUserMac()) == null) {// 添加用户
			VipUsersInfo vipUser = new VipUsersInfo();
			vipUser.setCity(registerInfo.getCity());
			vipUser.setUserName(registerInfo.getUsername());
			vipUser.setUserMac(registerInfo.getUserMac());
			VipCount vipCount = new VipCount(vipCountsInfo.getVipName(),
					vipCountsInfo.getVipPwd(), "未知",
					registerInfo.getRegisterTime(),
					registerInfo.getDeadlineTime(), "yes");

			if (registerInfo.getVipType().equalsIgnoreCase("tencent")) {
				vipUser.setTencentVipInfo(vipCount);
			} else if (registerInfo.getVipType().equalsIgnoreCase("iqiyi")) {
				vipUser.setIqiyiVipInfo(vipCount);
			} else if (registerInfo.getVipType().equalsIgnoreCase("youku")) {
				vipUser.setYoukuVipInfo(vipCount);
			}
			vipUsersInfoDao.addUsers(vipUser);
		} else {// 更新用户
			VipUsersInfo vipUser = vipUsersInfoDao.loadUsers(registerInfo
					.getUserMac());
			VipCount vipCount = new VipCount(vipCountsInfo.getVipName(),
					vipCountsInfo.getVipPwd(), "未知",
					registerInfo.getRegisterTime(),
					registerInfo.getDeadlineTime(), "yes");

			if (registerInfo.getVipType().equalsIgnoreCase("tencent")) {
				vipUser.setTencentVipInfo(vipCount);
			} else if (registerInfo.getVipType().equalsIgnoreCase("iqiyi")) {
				vipUser.setIqiyiVipInfo(vipCount);
			} else if (registerInfo.getVipType().equalsIgnoreCase("youku")) {
				vipUser.setYoukuVipInfo(vipCount);
			}
			vipUsersInfoDao.updateUsersWithId(vipUser);
		}

		// 更新vipCountsInfo
		VipUser vipUser2 = new VipUser(registerInfo.getUsername(),
				registerInfo.getUserMac(), "未知",
				registerInfo.getRegisterTime(), registerInfo.getDeadlineTime());
		updateAddVipCountsInfo(vipCountsInfo, registerInfo.getCity(), vipUser2);

		// vipCountsInfo.setCity(city);// 更新城市标签
		// // 更新分配人数
		// int newAssignedNum = Integer.parseInt(vipCountsInfo.getAssignedNum())
		// + 1;
		// vipCountsInfo.setAssignedNum(String.valueOf(newAssignedNum));
		//
		// switch (vipCountsInfo.getFirstIndexAssignedUserIsNull()) {
		// case 1:
		// vipCountsInfo.setAssignedUserInfo1(new VipUser(username, userMac,
		// "未知"));
		// break;
		// case 2:
		// vipCountsInfo.setAssignedUserInfo2(new VipUser(username, userMac,
		// "未知"));
		// break;
		// case 3:
		// vipCountsInfo.setAssignedUserInfo3(new VipUser(username, userMac,
		// "未知"));
		// break;
		// case 4:
		// vipCountsInfo.setAssignedUserInfo4(new VipUser(username, userMac,
		// "未知"));
		// break;
		// case 5:
		// vipCountsInfo.setAssignedUserInfo5(new VipUser(username, userMac,
		// "未知"));
		// break;
		// case -1:
		// System.out.println("无可分配位置");
		// break;
		// default:
		// break;
		// }
		//
		// vipCountsInfoDao.updateVipCount(vipCountsInfo);

		return true;
	}

	public Boolean logoutVip(String username, String userMac, String vipType) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		// 清空vipCountsInfo的信息
		VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(userMac);
		if (vipUsersInfo == null) {
			System.out.println("无账号");
			return false;
		}
		VipCountsInfo vipCountsInfo = vipCountsInfoDao.loadVipCount(
				vipUsersInfo.getVipCountByType(vipType).getName(), vipType);
		if (vipCountsInfo != null) {
			System.out.println(vipCountsInfo.toString());
			updateDeleteVipCountsInfo(vipCountsInfo, userMac);
		}

		// update自己的vipUsersInfo
		if (vipType.equalsIgnoreCase("tencent")) {
			vipUsersInfo.setTencentUseState("退出");
		} else if (vipType.equalsIgnoreCase("iqiyi")) {
			vipUsersInfo.setIqiyiUseState("退出");
		} else if (vipType.equalsIgnoreCase("youku")) {

			vipUsersInfo.setYoukuUseState("退出");
		}

		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		return true;
	}

	public VipCount loginVip(String username, String userMac, String vipType) {
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(userMac);
		WaitUsersInfoDao waitUsersInfoDao = new WaitUsersInfoDao();
		if (vipUsersInfo == null) {
			getVipState = NO_REGISTER;
			System.out.println("未注册为会员用户");
			return null;
		}
		System.out.println(username + vipType + "会员");
		System.out.println("vipUsersInfo" + vipUsersInfo.toString());

		VipCount vipCount = null;
		if (vipUsersInfo.getVipCountByType(vipType).getIsvip() == null
				|| vipUsersInfo.getVipCountByType(vipType).getIsvip()
						.equalsIgnoreCase("no")) {// 未分配到账号
			if (waitUsersInfoDao.loadUsers(userMac, vipType) == null) {// 未处于排队队列,==null会不会报异常
				if (vipType.equals("tencent")) {
					getVipState = NO_REGISTER_TENCENT_VIP;
				} else if (vipType.equals("iqiyi")) {
					getVipState = NO_REGISTER_IQIYI_VIP;
				} else {
					getVipState = NO_REGISTER_YOUKU_VIP;
				}
				System.out.println("未注册" + vipType + "会员");
				return null;
			} else {// 已处于排队队列中，分配账号
				RegisterUsersInfo registerInfo = new RegisterUsersInfo(
						username, userMac, null, vipType, vipUsersInfo
								.getVipCountByType(vipType).getRegisterTime(),
						vipUsersInfo.getVipCountByType(vipType)
								.getDeadlineTime(), vipUsersInfo.getCity());
				vipCount = getVipCount(registerInfo);
				if (vipCount != null) {// 分配成功，从等待表移除
					waitUsersInfoDao.deleteUsers(waitUsersInfoDao.loadUsers(
							userMac, vipType));

				} else {// 分配失败，在等待表中
					getVipState = IN_WAIT_TABLE;
					return null;
				}
			}

		} else {// 已分配到账号

			if (vipUsersInfo.getVipCountByType(vipType).getName() == null
					|| vipUsersInfo.getVipCountByType(vipType).getUseState() == null) {
				System.out.println("数据error");
				return null;
			}
			if (waitUsersInfoDao.loadUsers(userMac, vipType) != null) {// 分配到的账号因退出被别人占用，且未能成功切换到账号，已处于排队队列中，重新分配账号

				RegisterUsersInfo registerInfo = new RegisterUsersInfo(
						username, userMac, null, vipType, vipUsersInfo
								.getVipCountByType(vipType).getRegisterTime(),
						vipUsersInfo.getVipCountByType(vipType)
								.getDeadlineTime(), vipUsersInfo.getCity());
				vipCount = getVipCount(registerInfo);
				if (vipCount != null) {// 分配成功，从等待表移除
					waitUsersInfoDao.deleteUsers(waitUsersInfoDao.loadUsers(
							userMac, vipType));

				}
			} else if (vipUsersInfo.getVipCountByType(vipType).getName() != null
					&& !vipUsersInfo.getVipCountByType(vipType).getUseState()
							.equalsIgnoreCase("退出")) { // 有会员账号,且不为退出状态，直接获取
				if (vipType.equalsIgnoreCase("tencent")) {
					vipCount = vipUsersInfo.getVipCountByType("tencent");
				} else if (vipType.equalsIgnoreCase("iqiyi")) {
					vipCount = vipUsersInfo.getVipCountByType("iqiyi");
				} else if (vipType.equalsIgnoreCase("youku")) {
					vipCount = vipUsersInfo.getVipCountByType("youku");
				}
			} else {// 有会员账号，但为退出状态，视为切换会员账号
				Date loginTime;
				if (vipType.equalsIgnoreCase("tencent")) {
					loginTime = vipUsersInfo.getTencentLoginTime();
				} else if (vipType.equalsIgnoreCase("iqiyi")) {
					loginTime = vipUsersInfo.getIqiyiLoginTime();
				} else {
					loginTime = vipUsersInfo.getYoukuLoginTime();
				}

				try {
					if (StringAndDate.minutesBetween(loginTime, StringAndDate.getNowDate()) < 10) {// 前后间隔时间小于10分钟切换账号
						vipCount = changeVipCount2(username, userMac, vipType);
						if (vipCount == null && switchTimes != OUT_OF_SWITCH_TIMES) {// 所有账号用完，进入排队
							changeVipState = CHANGE_VIP_FAIL;
							waitVipCount(vipUsersInfo, vipType, username, userMac);

						} else {
							changeVipState = CHANGE_VIP_SUCCESS;
						}
					} else {// 前后间隔时间大于10分钟，登录原账号，如果被占用，则切换
						VipCount oldVipCount = vipUsersInfo
								.getVipCountByType(vipType);
						if (getOldVipCount(oldVipCount, vipType, vipUsersInfo)) {// 未被占用
							vipCount = oldVipCount;
						} else {// 被被占用，切换
							vipCount = changeVipCount2(username, userMac, vipType);
							if (vipCount == null
									&& switchTimes != OUT_OF_SWITCH_TIMES) {// 所有账号用完，进入排队
								changeVipState = CHANGE_VIP_FAIL;
								waitVipCount(vipUsersInfo, vipType, username,
										userMac);
							} else {
								changeVipState = CHANGE_VIP_SUCCESS;
							}
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// 设置为在线状态,并写入登录时间
		vipCount.setUseState("在线");
		if (vipType.equalsIgnoreCase("tencent")) {
			vipUsersInfo.setTencentVipInfo(vipCount);
			vipUsersInfo.setTencentLoginTime(StringAndDate.getNowDate());
		} else if (vipType.equalsIgnoreCase("iqiyi")) {
			vipUsersInfo.setIqiyiVipInfo(vipCount);
			vipUsersInfo.setIqiyiLoginTime(StringAndDate.getNowDate());
		} else {
			vipUsersInfo.setYoukuVipInfo(vipCount);
			vipUsersInfo.setYoukuLoginTime(StringAndDate.getNowDate());
		}
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		return vipCount;
	}

	public VipCount changeVipCount2(String username, String userMac,// 第二次方案下的更换账号
			String vipType) {
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(userMac);
		if (vipUsersInfo == null
				|| vipUsersInfo.getVipCountByType(vipType).getName() == null) {
			System.out.println("未购买会员");
			return null;
		}
		// 更新更换vipCounts的次数记录
		if (vipUsersInfo.getUseChangeDay() == null) {// 没有记录的时候，首次填入信息
			vipUsersInfo.setUseChangeDay(StringAndDate.getNowDateShort());
			vipUsersInfo.setUseChangeTimes("1");
		} else {
			try {
				if (StringAndDate.daysBetween(vipUsersInfo.getUseChangeDay(), StringAndDate.getNowDateShort()) > 0) {// 有记录，但是已经过来一天
					vipUsersInfo.setUseChangeDay(StringAndDate.getNowDateShort());
					vipUsersInfo.setUseChangeTimes("1");

				} else if (StringAndDate.daysBetween(vipUsersInfo.getUseChangeDay(), StringAndDate.getNowDateShort()) == 0) { // 有记录，但是同一天
					if (vipUsersInfo.getUseChangeTimes().equalsIgnoreCase("5")) {
						System.out.println("今日更换次数大于5");
						switchTimes = OUT_OF_SWITCH_TIMES;
						return null;
					}
					vipUsersInfo.setUseChangeTimes(String.valueOf(1 + Integer
							.parseInt(vipUsersInfo.getUseChangeTimes())));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		// 按优先分配同city，分配数小于4的账号
		VipCountsInfo vipCountsInfoNew = vipCountsInfoDao
				.changeVipCountByAssignedNumStep1(vipUsersInfo
						.getVipCountByType(vipType).getName(), vipUsersInfo
						.getCity(), vipType);

		// 次分配同city，分配数=4，changTime小的
		if (vipCountsInfoNew == null) {
			vipCountsInfoNew = vipCountsInfoDao
					.changeVipCountByChangeTimesStep2(vipUsersInfo
							.getVipCountByType(vipType).getName(), vipUsersInfo
							.getCity(), vipType);
		}
		if (vipCountsInfoNew == null) {
			String vipName = vipUsersInfo.getVipCountByType(vipType).getName();
			if (vipCountsInfoDao.loadVipCount(vipName, vipType)
					.getAssignedNum().equals("5")) {
				System.out.println("无可切换会员");
				return null;
			} else {// 返回原来的账号
				return vipUsersInfo.getVipCountByType(vipType);
			}

		}
		// 更新原来占有vipCountsInfo,只需更新切换次数
		VipCountsInfo vipCountsInfoOld = vipCountsInfoDao.loadVipCount(
				vipUsersInfo.getVipCountByType(vipType).getName(), vipType);
		if (vipCountsInfoOld.getChangedTimes() == null) {
			vipCountsInfoOld.setChangedTimes("0");
		}
		vipCountsInfoOld.setChangedTimes(String.valueOf(1 + Integer
				.parseInt(vipCountsInfoOld.getChangedTimes())));
		vipCountsInfoDao.updateVipCountWithId(vipCountsInfoOld);

		// 更新新占有vipCountsInfo
		VipCount vipCountOld = vipUsersInfo.getVipCountByType(vipType);
		updateAddVipCountsInfo(
				vipCountsInfoNew,
				null,
				new VipUser(username, userMac, "未知", vipCountOld
						.getRegisterTime(), vipCountOld.getDeadlineTime()));

		// 更新userInfo并返回账号
		VipCount vipCountNew = null;
		vipCountNew = new VipCount(vipCountsInfoNew.getVipName(),
				vipCountsInfoNew.getVipPwd(), "未知",
				vipCountOld.getRegisterTime(), vipCountOld.getDeadlineTime(),
				"yes");
		if (vipType.equalsIgnoreCase("tencent")) {
			vipUsersInfo.setTencentVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("iqiyi")) {

			vipUsersInfo.setIqiyiVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("youku")) {

			vipUsersInfo.setYoukuVipInfo(vipCountNew);
		}
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		return vipCountNew;
	}

	public VipCount changeVipCount(String username, String userMac,// 第一次的方案的切换账号
			String vipType) {
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfo vipUsersInfo = vipUsersInfoDao.loadUsers(userMac);
		if (vipUsersInfo == null
				|| vipUsersInfo.getVipCountByType(vipType).getName() == null) {
			System.out.println("未购买会员");
			return null;
		}
		// 更新更换vipCounts的次数记录
		if (vipUsersInfo.getUseChangeDay() == null) {// 没有记录的时候，首次填入信息
			vipUsersInfo.setUseChangeDay(StringAndDate.getNowDateShort());
			vipUsersInfo.setUseChangeTimes("1");
		} else {
			try {
				if (StringAndDate.daysBetween(vipUsersInfo.getUseChangeDay(), StringAndDate.getNowDateShort()) > 0) {
					vipUsersInfo.setUseChangeDay(StringAndDate.getNowDateShort());
					vipUsersInfo.setUseChangeTimes("1");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (StringAndDate.daysBetween(vipUsersInfo.getUseChangeDay(), StringAndDate.getNowDateShort()) == 0) {
					if (vipUsersInfo.getUseChangeTimes().equalsIgnoreCase("5")) {
						System.out.println("今日更换次数大于5");
						return null;
					}
					vipUsersInfo.setUseChangeTimes(String.valueOf(1 + Integer
							.parseInt(vipUsersInfo.getUseChangeTimes())));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		// 按优先分配同city，分配数小于4的账号
		VipCountsInfo vipCountsInfoNew = vipCountsInfoDao
				.changeVipCountByAssignedNumStep1(vipUsersInfo
						.getVipCountByType(vipType).getName(), vipUsersInfo
						.getCity(), vipType);

		// 次分配同city，分配数=4，changTime小的
		if (vipCountsInfoNew == null) {
			vipCountsInfoNew = vipCountsInfoDao
					.changeVipCountByChangeTimesStep2(vipUsersInfo
							.getVipCountByType(vipType).getName(), vipUsersInfo
							.getCity(), vipType);
		}
		if (vipCountsInfoNew == null) {
			System.out.println("无可切换会员");
			return null;
		}
		// 更新原来占有vipCountsInfo
		VipCountsInfo vipCountsInfoOld = vipCountsInfoDao.loadVipCount(
				vipUsersInfo.getVipCountByType(vipType).getName(), vipType);
		if (vipCountsInfoOld.getChangedTimes() == null) {
			vipCountsInfoOld.setChangedTimes("0");
		}
		vipCountsInfoOld.setChangedTimes(String.valueOf(1 + Integer
				.parseInt(vipCountsInfoOld.getChangedTimes())));
		updateDeleteVipCountsInfo(vipCountsInfoOld, userMac);

		// 更新新占有vipCountsInfo
		VipCount vipCountOld = vipUsersInfo.getVipCountByType(vipType);
		updateAddVipCountsInfo(
				vipCountsInfoNew,
				null,
				new VipUser(username, userMac, "未知", vipCountOld
						.getRegisterTime(), vipCountOld.getDeadlineTime()));

		// 更新userInfo并返回账号
		VipCount vipCountNew = null;
		vipCountNew = new VipCount(vipCountsInfoNew.getVipName(),
				vipCountsInfoNew.getVipPwd(), "未知",
				vipCountOld.getRegisterTime(), vipCountOld.getDeadlineTime(),
				"yes");
		if (vipType.equalsIgnoreCase("tencent")) {
			vipUsersInfo.setTencentVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("iqiyi")) {

			vipUsersInfo.setIqiyiVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("youku")) {

			vipUsersInfo.setYoukuVipInfo(vipCountNew);
		}
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);

		return vipCountNew;
	}

	// vipUserInfo变更会员(add delete)
	public void updateChangeVipUserInfo(VipUsersInfo vipUsersInfo,
			String vipType, VipCount vipCountNew) {
		if (vipType.equalsIgnoreCase("tencent")) {
			vipUsersInfo.setTencentVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("iqiyi")) {
			vipUsersInfo.setIqiyiVipInfo(vipCountNew);
		} else if (vipType.equalsIgnoreCase("youku")) {

			vipUsersInfo.setYoukuVipInfo(vipCountNew);
		}
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		vipUsersInfoDao.updateUsersWithId(vipUsersInfo);
	}

	// vipCountsInfo减少用户
	public void updateDeleteVipCountsInfo(VipCountsInfo vipCountsInfo,
			String userMac) {
		Boolean delete = false;

		if (vipCountsInfo.getAssignedUsermac1() != null) {
			if (vipCountsInfo.getAssignedUsermac1().equalsIgnoreCase(userMac)) {
				vipCountsInfo.setAssignedUserInfo1(new VipUser(null, null,
						null, null, null));
				delete = true;
			}

		}
		if (vipCountsInfo.getAssignedUsermac2() != null) {
			if (vipCountsInfo.getAssignedUsermac2().equalsIgnoreCase(userMac)) {
				vipCountsInfo.setAssignedUserInfo2(new VipUser(null, null,
						null, null, null));
				delete = true;
			}
		}
		if (vipCountsInfo.getAssignedUsermac3() != null) {
			if (vipCountsInfo.getAssignedUsermac3().equalsIgnoreCase(userMac)) {
				vipCountsInfo.setAssignedUserInfo3(new VipUser(null, null,
						null, null, null));
				delete = true;
			}
		}
		if (vipCountsInfo.getAssignedUsermac4() != null) {
			if (vipCountsInfo.getAssignedUsermac4().equalsIgnoreCase(userMac)) {
				vipCountsInfo.setAssignedUserInfo4(new VipUser(null, null,
						null, null, null));
				delete = true;
			}
		}
		if (vipCountsInfo.getAssignedUsermac5() != null) {
			if (vipCountsInfo.getAssignedUsermac5().equalsIgnoreCase(userMac)) {
				vipCountsInfo.setAssignedUserInfo5(new VipUser(null, null,
						null, null, null));
				delete = true;
			}
		}
		// 更新分配人数
		if (delete) {
			int newAssignedNum = Integer.parseInt(vipCountsInfo
					.getAssignedNum()) - 1;
			newAssignedNum = newAssignedNum > 0 ? newAssignedNum : 0;
			vipCountsInfo.setAssignedNum(String.valueOf(newAssignedNum));
		}
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		vipCountsInfoDao.updateVipCountWithId(vipCountsInfo);
	}

	// vipCountsInfo增加用户
	public void updateAddVipCountsInfo(VipCountsInfo vipCountsInfo,
			String city, VipUser vipUser) {
		// 更新vipCountsInfo
		if (city != null) {
			vipCountsInfo.setCity(city);// 更新城市标签
		}
		// 更新分配人数
		int newAssignedNum = Integer.parseInt(vipCountsInfo.getAssignedNum()) + 1;
		vipCountsInfo.setAssignedNum(String.valueOf(newAssignedNum));

		switch (vipCountsInfo.getFirstIndexAssignedUserIsNull()) {
		case 1:
			vipCountsInfo.setAssignedUserInfo1(vipUser);
			break;
		case 2:
			vipCountsInfo.setAssignedUserInfo2(vipUser);
			break;
		case 3:
			vipCountsInfo.setAssignedUserInfo3(vipUser);
			break;
		case 4:
			vipCountsInfo.setAssignedUserInfo4(vipUser);
			break;
		case 5:
			vipCountsInfo.setAssignedUserInfo5(vipUser);
			break;
		case -1:
			System.out.println("无可分配位置");
			break;
		default:
			break;
		}
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		vipCountsInfoDao.updateVipCountWithId(vipCountsInfo);
	}

	public VipCount getVipCount(RegisterUsersInfo registerInfo) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		WaitUsersInfoDao waitUsersInfoDao = new WaitUsersInfoDao();
		VipUsersInfo user = vipUsersInfoDao
				.loadUsers(registerInfo.getUserMac());

		if (user == null) {
			System.out.println("无用户");
			return null;
		}
		// 优先分配同城市，且已分配人数少的账号
		VipCountsInfo vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
				registerInfo.getVipType(), registerInfo.getCity(), "0");
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "1");
		}
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "2");
		}
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao.getVipCountWithCity(
					registerInfo.getVipType(), registerInfo.getCity(), "3");
		}

		// 次分配,从未分配的账号，并修改城市标签
		if (vipCountsInfo == null) {
			vipCountsInfo = vipCountsInfoDao
					.getVipCountWithoutCity(registerInfo.getVipType());
		}
		if (vipCountsInfo == null) {
			// 未分配成功
			// 同城市下，同类型vip账号可分配人数<等待人数，则不再排队
			List<?> list = vipCountsInfoDao.listCountsByCityAndViptype(
					registerInfo.getCity(), registerInfo.getVipType());
			List<?> list2 = waitUsersInfoDao.listWaitUsers(
					registerInfo.getCity(), registerInfo.getVipType());
			if (list.size() > list2.size()) {
				getVipState = WAIT_TABLE_FULL;
				System.out.println("无可用账号，且等待表满");
				return null;
			} else {
				// 进入等待表
				addToWaitUsersInfo(registerInfo);
				getVipState = IN_WAIT_TABLE;
				System.out.println("无可用账号，添加到等待用户表中");
				return null;
			}

		}

		// 更新userInfo
		// 更新用户
		VipUsersInfo vipUser = vipUsersInfoDao.loadUsers(registerInfo
				.getUserMac());
		VipCount vipCount = new VipCount(vipCountsInfo.getVipName(),
				vipCountsInfo.getVipPwd(), "未知",
				registerInfo.getRegisterTime(), registerInfo.getDeadlineTime(),
				"yes");

		if (registerInfo.getVipType().equalsIgnoreCase("tencent")) {
			vipUser.setTencentVipInfo(vipCount);
		} else if (registerInfo.getVipType().equalsIgnoreCase("iqiyi")) {
			vipUser.setIqiyiVipInfo(vipCount);
		} else if (registerInfo.getVipType().equalsIgnoreCase("youku")) {
			vipUser.setYoukuVipInfo(vipCount);
		}
		vipUsersInfoDao.updateUsersWithId(vipUser);

		// 更新vipCountsInfo
		VipUser vipUser2 = new VipUser(registerInfo.getUsername(),
				registerInfo.getUserMac(), "未知",
				registerInfo.getRegisterTime(), registerInfo.getDeadlineTime());
		updateAddVipCountsInfo(vipCountsInfo, registerInfo.getCity(), vipUser2);

		return vipCount;
	}

	// 记录到等待用户表中
	public void addToWaitUsersInfo(RegisterUsersInfo registerInfo) {
		WaitUsersInfo waitUsersInfo = new WaitUsersInfo(registerInfo);
		;
		WaitUsersInfoDao waitUsersInfoDao = new WaitUsersInfoDao();
		waitUsersInfoDao.addUsers(waitUsersInfo);
	}

	// 前后间隔时间大于10分钟，登录原账号，如果被占用，则返回false
	public boolean getOldVipCount(VipCount vipCount, String vipType,
			VipUsersInfo vipUsersInfo) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		VipCountsInfo vipCountsInfo = vipCountsInfoDao.loadVipCount(
				vipCount.getName(), vipType);
		if (vipCountsInfo == null) {
			return false;
		}
		if (Integer.parseInt(vipCountsInfo.getAssignedNum()) >= 5) {// 账号被分完
			return false;
		} else {
			// 更新vipCountsInfo
			VipUser vipUser2 = new VipUser(vipUsersInfo.getUserName(),
					vipUsersInfo.getUserMac(), "未知",
					vipCount.getRegisterTime(), vipCount.getDeadlineTime());
			updateAddVipCountsInfo(vipCountsInfo, vipUsersInfo.getCity(),
					vipUser2);
		}
		return true;
	}

	public void waitVipCount(VipUsersInfo vipUsersInfo, String vipType,
			String username, String userMac) {
		VipCountsInfoDao vipCountsInfoDao = new VipCountsInfoDao();
		WaitUsersInfoDao waitUsersInfoDao = new WaitUsersInfoDao();
		// 同城市下，同类型vip账号可分配人数<等待人数，则不再排队
		List<?> list = vipCountsInfoDao.listCountsByCityAndViptype(
				vipUsersInfo.getCity(), vipType);
		List<?> list2 = waitUsersInfoDao.listWaitUsers(vipUsersInfo.getCity(),
				vipType);
		if (list.size() * 4 <= list2.size()) {// 只排队账号*4的人数
			getVipState = WAIT_TABLE_FULL;
			System.out.println("无可用账号，且等待表满");
		} else {
			// 进入等待表
			RegisterUsersInfo registerInfo = new RegisterUsersInfo(username,
					userMac, null, vipType, vipUsersInfo.getVipCountByType(
							vipType).getRegisterTime(), vipUsersInfo
							.getVipCountByType(vipType).getDeadlineTime(),
					vipUsersInfo.getCity());
			addToWaitUsersInfo(registerInfo);
			getVipState = IN_WAIT_TABLE;
			System.out.println("无可用账号，添加到等待用户表中");
		}
	}

	public VipUsersInfo getVipUsersInfo(String userMac) {
		VipUsersInfoDao vipUsersInfoDao = new VipUsersInfoDao();
		return vipUsersInfoDao.loadUsers(userMac);

	}

	public boolean checkTable(String tableName) {
		boolean result = false;
		// String sql="SELECT"
		return result;
	}
}
