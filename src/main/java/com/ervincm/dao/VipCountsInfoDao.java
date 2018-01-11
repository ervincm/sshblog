package com.ervincm.dao;

import com.ervincm.db.HibernateSessionFactory;
import com.ervincm.entity.VipCountsInfo;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Iterator;
import java.util.List;


public class VipCountsInfoDao {
	public void addVipCount(VipCountsInfo vipCountsInfo) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try{  //新数据进行保存操作
			session.save(vipCountsInfo);

        }catch(ConstraintViolationException e){  //唯一键重复时
            session.clear();

        }
		
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public void deleteVipCount(VipCountsInfo vipCountsInfo) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		VipCountsInfo count = loadVipCount(vipCountsInfo.getVipName(),vipCountsInfo.getType());
		if (count != null) {
			session.delete(count);
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	// 更新，不需要知道ID
	public void updateVipCount(VipCountsInfo vipCountsInfo) {
		VipCountsInfo u = loadVipCount(vipCountsInfo.getVipName(),vipCountsInfo.getType());
		if (vipCountsInfo.getVipName() != null) {
			u.setVipName(vipCountsInfo.getVipName());
		}
		if (vipCountsInfo.getVipPwd() != null) {
			u.setVipPwd(vipCountsInfo.getVipPwd());
		}
		if (vipCountsInfo.getCity() != null) {
			u.setCity(vipCountsInfo.getCity());
		}
		if (vipCountsInfo.getType() != null) {
			u.setType(vipCountsInfo.getType());
		}
		if (vipCountsInfo.getAssignedNum() != null) {
			u.setAssignedNum(vipCountsInfo.getAssignedNum());
		}
		if (vipCountsInfo.getUsingNum() != null) {
			u.setUsingNum(vipCountsInfo.getUsingNum());
		}
		if (vipCountsInfo.getChangedTimes() != null) {
			u.setChangedTimes(vipCountsInfo.getChangedTimes());
		}
		if (vipCountsInfo.getCanChange() != null) {
			u.setCanChange(vipCountsInfo.getCanChange());
		}
		if (vipCountsInfo.getVipRegisterTime() != null) {
			u.setVipRegisterTime(vipCountsInfo.getVipRegisterTime());
		}
		if (vipCountsInfo.getVipDeadlineTime() != null) {
			u.setVipDeadlineTime(vipCountsInfo.getVipDeadlineTime());
		}

		if (vipCountsInfo.getAssignedUsername1() != null) {

			u.setAssignedUserInfo1(vipCountsInfo.getAssignedUserInfo1());
		}
		if (vipCountsInfo.getAssignedUsername2() != null) {
			u.setAssignedUserInfo2(vipCountsInfo.getAssignedUserInfo2());
		}
		if (vipCountsInfo.getAssignedUsername3() != null) {
			u.setAssignedUserInfo3(vipCountsInfo.getAssignedUserInfo3());
		}
		if (vipCountsInfo.getAssignedUsername4() != null) {
			u.setAssignedUserInfo4(vipCountsInfo.getAssignedUserInfo4());
		}
		if (vipCountsInfo.getAssignedUsername5() != null) {
			u.setAssignedUserInfo5(vipCountsInfo.getAssignedUserInfo5());
		}

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public void updateVipCountWithId(VipCountsInfo vipCountsInfo) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(vipCountsInfo);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public VipCountsInfo loadVipCount(String name, String vipType) {// 根据账号名获得信息
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		SQLQuery query = session
				.createSQLQuery("SELECT * FROM VipCountsInfo WHERE vipName = :name and type = :vipType");
		query.setParameter("name", name);
		query.setParameter("vipType", vipType);
		query.addEntity(VipCountsInfo.class);
		List<?> list = null;
		list = query.list();
		VipCountsInfo vipCountsInfo = null;
		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipCountsInfo = (VipCountsInfo) it.next();
			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipCountsInfo;
	}

	public VipCountsInfo getVipCountWithCity(String vipType, String city,
                                             String assignedNum) {// 按相同城市，且已分配人数查找可用vip账号
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String sql;
		sql = "SELECT * FROM VipCountsInfo where type = :vipType and city = :city and assignedNum = :assignedNum";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("assignedNum", assignedNum);
		query.setString("city", city);
		query.setString("vipType", vipType);
		query.addEntity(VipCountsInfo.class);
		List<?> list = null;
		list = query.list();
		VipCountsInfo vipCount = null;
		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipCount = (VipCountsInfo) it.next();

			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipCount;
	}

	public VipCountsInfo getVipCountWithoutCity(String vipType) {// 分配未分配过的账号
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String sql;
		sql = "SELECT * FROM VipCountsInfo where type = :vipType  and assignedNum = :assignedNum";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("assignedNum", "0");// 未分配过的账号
		query.setString("vipType", vipType);
		query.addEntity(VipCountsInfo.class);
		List<?> list = null;
		list = query.list();
		VipCountsInfo vipCount = null;
		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipCount = (VipCountsInfo) it.next();
				
			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipCount;
	}

	public void updateAssigndeNum(VipCountsInfo vipCount) {// 更新账号已分配的人数
		int newAssignedNum = Integer.parseInt(vipCount.getAssignedNum()) + 1;
		vipCount.setAssignedNum(String.valueOf(newAssignedNum));
		updateVipCount(vipCount);
	}

	public void updateChangedTimes(VipCountsInfo vipCount) {// 更新账号因同时使用人数大于2，更换次数
		int newChangedTime = Integer.parseInt(vipCount.getChangedTimes()) + 1;
		vipCount.setAssignedNum(String.valueOf(newChangedTime));
		updateVipCount(vipCount);
	}

	public VipCountsInfo changeVipCountByAssignedNumStep1(String vipName,
                                                          String city, String vipType) {// 账号目前有两人使用时，查找同city下分配数小于4的账号，且不为原来账号
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String sql;
		sql = "SELECT * FROM VipCountsInfo where assignedNum<4 and city = :city and type= :vipType and vipName <> :vipName order by assignedNum";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("city", city);
		query.setString("vipName", vipName);
		query.setString("vipType", vipType);
		query.addEntity(VipCountsInfo.class);
		List<?> list = null;
		list = query.list();
		VipCountsInfo vipCount = null;
		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipCount = (VipCountsInfo) it.next();
				
			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipCount;
		
	}

	public VipCountsInfo changeVipCountByChangeTimesStep2(String vipName,
                                                          String city, String vipType) {// 按账号目前有两人使用时，且未找到分配数小于4的账号，分配同city下分配数等于4，changTime小的账号，且不为原来账号
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		String sql;
		sql = "SELECT * FROM VipCountsInfo where assignedNum=4 and city = :city and  type= :vipType and vipName <> :vipName order by changedTimes";
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("city", city);
		query.setString("vipName", vipName);
		query.setString("vipType", vipType);
		query.addEntity(VipCountsInfo.class);
		List<?> list = null;
		list = query.list();
		VipCountsInfo vipCount = null;
		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipCount = (VipCountsInfo) it.next();
				
			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipCount;
	}

	public List<?> listCountsByCityAndViptype(String city,String vipType) {// ��ʾ��Ա�б�
		Session session = HibernateSessionFactory.getSession();
		SQLQuery query = session
				.createSQLQuery("select * from VipCountsInfo where city = :city and  type= :vipType order by id");
		query.addEntity(VipCountsInfo.class);
		query.setString("city", city);
		query.setString("vipType", vipType);
		List<?> list = query.list();
		HibernateSessionFactory.closed(session);
		return list; 
	}
	
	public List<?> listCounts() {// ��ʾ��Ա�б�
		Session session = HibernateSessionFactory.getSession();// ���Session����
		SQLQuery query = session
				.createSQLQuery("select * from VipCountsInfo order by id");// ִ�в�ѯ
		query.addEntity(VipCountsInfo.class);
		List<?> list = query.list();// ��ò�ѯ�б�
		HibernateSessionFactory.closed(session);
		return list; // ������Ա�б�
	}
}
