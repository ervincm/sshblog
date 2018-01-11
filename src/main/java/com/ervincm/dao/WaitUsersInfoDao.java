package com.ervincm.dao;

import com.ervincm.db.HibernateSessionFactory;
import com.ervincm.entity.WaitUsersInfo;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Iterator;
import java.util.List;


public class WaitUsersInfoDao {
	public void addUsers(WaitUsersInfo users) {

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

	public void deleteUsers(WaitUsersInfo users) {
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		WaitUsersInfo waitUsersInfo = loadUsers(users.getUserMac(), users.getVipType());
		if (waitUsersInfo != null) {
			session.delete(waitUsersInfo);
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	// 更新，不需要知道ID
	public void updateUsers(WaitUsersInfo users) {
		WaitUsersInfo u = loadUsers(users.getUserMac(),users.getVipType());
		if (users.getCity() != null) {
			u.setCity(users.getCity());
		}
		if (users.getUserName() != null) {
			u.setUserName(users.getUserName());
		}
		if (users.getUserMac() != null) {
			u.setUserMac(users.getUserMac());
		}
		if (users.getRegisterTime() != null) {
			u.setRegisterTime(users.getRegisterTime());
		}
		if (users.getDeadlineTime() != null) {
			u.setDeadlineTime(users.getDeadlineTime());
		}
		if (users.getVipType() != null) {
			u.setVipType(users.getVipType());
		}
		

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

	public void updateUsersWithId(WaitUsersInfo users) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		session.update(users);
		tx.commit();
		HibernateSessionFactory.closed(session);
	}

//	public WaitUsersInfo loadUsers(String mac) {// 根据mac地址获得用户信息
//		Session session = HibernateSessionFactory.getSession();
//		Transaction tx = session.beginTransaction();
//		SQLQuery query = session
//				.createSQLQuery("SELECT * FROM WaitUsersInfo WHERE userMac = :mac");
//		query.setParameter("mac", mac);
//		query.addEntity(WaitUsersInfo.class);
//		List<?> list = null;
//		list = query.list();
//		WaitUsersInfo waitUsersInfo = null;
//
//		if (list != null) {
//			Iterator<?> it = list.iterator();
//			if (it.hasNext()) {
//				waitUsersInfo = (WaitUsersInfo) it.next();
//
//			}
//		}
//		tx.commit();
//		HibernateSessionFactory.closed(session);
//		return waitUsersInfo;
//	}

	public WaitUsersInfo loadUsers(String mac, String vipType) {// 根据mac地址和vipType获得用户信息
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		SQLQuery query = session
				.createSQLQuery("SELECT * FROM WaitUsersInfo WHERE userMac = :mac and vipType = :vipType");
		query.setParameter("mac", mac);
		query.setParameter("vipType", vipType);
		query.addEntity(WaitUsersInfo.class);
		List<?> list = null;
		list = query.list();
		WaitUsersInfo waitUsersInfo = null;

		if (list != null) {
			Iterator<?> it = list.iterator();
			if (it.hasNext()) {
				waitUsersInfo = (WaitUsersInfo) it.next();

			}
		}
		tx.commit();
		HibernateSessionFactory.closed(session);
		return waitUsersInfo;
	}
	


	public List<?> listWaitUsers(String city,String vipType) {// ��ʾ��Ա�б�
		Session session = HibernateSessionFactory.getSession();
		SQLQuery query = session
				.createSQLQuery("select * from WaitUsersInfo where city = :city and  vipType= :vipType order by id");
		query.addEntity(WaitUsersInfo.class);
		query.setString("city", city);
		query.setString("vipType", vipType);

		List<?> list = query.list();// ��ò�ѯ�б�
		HibernateSessionFactory.closed(session);
		return list; // ������Ա�б�
	}
}
