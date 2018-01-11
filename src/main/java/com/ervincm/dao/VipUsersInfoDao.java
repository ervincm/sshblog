package com.ervincm.dao;

import com.ervincm.db.HibernateSessionFactory;
import com.ervincm.entity.VipCount;
import com.ervincm.entity.VipUsersInfo;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Iterator;
import java.util.List;


public class VipUsersInfoDao {
	public void addUsers(VipUsersInfo users) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		try{  //新数据进行保存操作
			session.save(users);

        }catch(ConstraintViolationException e){  //唯一键重复时
            session.clear();

        }
		
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public void deleteUsers(VipUsersInfo users) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		VipUsersInfo vipUsersInfo = loadUsers(users.getUserMac());
		if (vipUsersInfo != null) {
			session.delete(vipUsersInfo);
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	// 更新，不需要知道ID
	public void updateUsers(VipUsersInfo users) {
		VipUsersInfo u = loadUsers(users.getUserMac());
		if (users.getCity() != null) {
			u.setCity(users.getCity());
		}
		if (users.getUserName() != null) {
			u.setUserName(users.getUserName());
		}
		if (users.getUserMac() != null) {
			u.setUserMac(users.getUserMac());
		}

		if (users.getTencentVipName() != null) {
			u.setTencentVipInfo(new VipCount(users.getTencentVipName(), users
					.getTencentVipPwd(), users.getTencentUseState(), users
					.getTencentregisterTime(), users.getTencentdeadlineTime(),users.getIstencentVip()));
		}
		if (users.getIqiyiVipName() != null) {
			u.setIqiyiVipInfo(new VipCount(users.getIqiyiVipName(), users
					.getIqiyiVipPwd(), users.getIqiyiUseState(), users
					.getIqiyiregisterTime(), users.getIqiyideadlineTime(),users.getIsiqiyiVip()));
		}
		if (users.getYoukuVipName() != null) {
			u.setYoukuVipInfo(new VipCount(users.getYoukuVipName(), users
					.getYoukuVipPwd(), users.getYoukuUseState(), users
					.getYoukuregisterTime(), users.getYoukudeadlineTime(),users.getIsyoukuVip()));
		}

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public void updateUsersWithId(VipUsersInfo users) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(users);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public VipUsersInfo loadUsers(String mac) {// 根据mac地址获得用户信息
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		SQLQuery query = session
				.createSQLQuery("SELECT * FROM VipUsersInfo WHERE userMac = :mac");
		query.setParameter("mac", mac);
		query.addEntity(VipUsersInfo.class);
		List<?> list = null;
		list = query.list();
		VipUsersInfo vipUsersInfo = null;

		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				vipUsersInfo = (VipUsersInfo) it.next();

			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return vipUsersInfo;
	}

//	public VipCount getUserVipCount(VipUsersInfo users, String type) {//
//		Session session = HibernateSessionFactory.getSession();
//		Transaction tx = session.beginTransaction();
//		String sql;
//		if (type.equalsIgnoreCase("tencent")) {
//			sql = "SELECT tencentVipName, tencentVipPwd,tencentUseState FROM VipUsersInfo where userName = :name and userMac =:mac";
//		} else if (type.equalsIgnoreCase("iqiyi")) {
//			sql = "SELECT iqiyiVipName, iqiyiVipPwd,iqiyiUseState FROM VipUsersInfo where userName = :name and userMac =:mac";
//		} else {
//			sql = "SELECT youkuVipName, youkuVipPwd,youkuUseState  FROM VipUsersInfo where userName = :name and userMac =:mac";
//		}
//
//		SQLQuery query = session.createSQLQuery(sql);
//		query.setString("name", users.getUserName());
//		query.setString("mac", users.getUserMac());
//		
//		List list = null;
//		list = query.list();
//		tx.commit();
//		HibernateSessionFactory.closed(session);
//		if (list != null) {
//			Iterator it = list.iterator();
//			if (it.hasNext()) {
//				VipCount vipInfo = (VipCount) it.next();
//				return vipInfo;
//			}
//		}
//
//		return null;
//	}

	public List<?> listUser() {// ��ʾ��Ա�б�
		Session session = HibernateSessionFactory.getSession();// ���Session����
		SQLQuery query = session
				.createSQLQuery("select * from VipUsersInfo order by id");// ִ�в�ѯ
		query.addEntity(VipUsersInfo.class);
		List<?> list = query.list();// ��ò�ѯ�б�
		HibernateSessionFactory.closed(session);
		return list; // ������Ա�б�
	}
}
