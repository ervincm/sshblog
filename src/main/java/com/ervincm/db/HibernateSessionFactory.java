package com.ervincm.db;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Map;


public class HibernateSessionFactory {
	static SessionFactory sessionFactory = null;
	static Configuration configuration = null;
	static Session session = null;
	static Transaction transaction = null;
	static ServiceRegistry serviceRegistry = null;
	static {
		try {
			configuration = new Configuration().configure();
		//	configuration.setNamingStrategy(new MyNameStrategy());
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//	session = sessionFactory.openSession();
		//	transaction = session.beginTransaction();
		} catch (HibernateException e) {
			throw new RuntimeException("SessionFactory创建失败", e);
		}
	}

	public static Session getSession() {// 获得session的方法
		Session session = null;
		try {
			if (session == null || session.isOpen() == false)
				session = null;
			session = sessionFactory.openSession();
		
		} catch (HibernateException e) {
			throw new RuntimeException("Session创建失败", e);
		}
		return session;
	}

	public static void closed(Session session) {// 关闭session的方法
		try {
			if (session != null)
			{
			//	transaction.commit();
				session.close();
			//	sessionFactory.close();
			}
				
		} catch (HibernateException e) {
			throw new RuntimeException("Session关闭失败", e);
		}
	}

	public static void main(String[] args) {// 测试方法
		Session session=getSession();
		Transaction tr=session.beginTransaction();
		 String sql = "SELECT username, usermac FROM users";
         SQLQuery query = session.createSQLQuery(sql);
         query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
         List data = query.list();

         for(Object object : data)
         {
            Map row = (Map)object;
            System.out.print("First Name: " + row.get("username")); 
            System.out.println(", Salary: " + row.get("usermac")); 
         }
         tr.commit();

//		VipInfo tencentVipInfo=new VipInfo("e","e","e");
//		Users users=new Users( );
//		users.setTencentVipInfo(tencentVipInfo);
//		session.save(users);
//		tr.commit();
	
	}
}
