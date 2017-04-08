package com.archide.hsb.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.model.LoginUsersEntity;
import com.archide.hsb.model.PlacedOrdersEntity;
import com.archide.hsb.model.ReservedTableEntity;

@Service
public class SchedulerService {
	
	private static final Logger logger = Logger.getLogger(OrdersService.class);
	
	@Autowired
	private OrdersDao ordersDao;
	
	//@Scheduled(fixedDelay = 10000)
	public void removeSessionTimeoutUser(){
		Session session = null;
		try{
			session = ordersDao.openSession();
			session.getTransaction().begin();
			long time = ServiceUtil.getCurrentGmtTime() - 21600000;
			List<LoginUsersEntity> loginUsersEntities = ordersDao.getLoginUsersEntity(session, time);
			for(LoginUsersEntity loginUsersEntity : loginUsersEntities){
				PlacedOrdersEntity placedOrdersEntity = ordersDao.getPlacedOrders(session, loginUsersEntity.getMobileNumber(), loginUsersEntity.getTableNumber());
				if(placedOrdersEntity == null){
					session.delete(loginUsersEntity);
				}
			}
			session.getTransaction().commit();
		}catch(Exception e){
			if(session != null){
				session.getTransaction().rollback();
			}
			logger.error("Error in removeSessionTimeoutUser", e);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	@Scheduled(fixedDelay = 10000)
	public void removeReservedTable(){
		Session session = null;
		try{
			session = ordersDao.openSession();
			session.getTransaction().begin();
			long time = ServiceUtil.getCurrentGmtTime() - (10000 * 6 * 10);
			ordersDao.deleteReservedTable(session, time);
		}catch(Exception e){
			if(session != null){
				session.getTransaction().rollback();
			}
			logger.error("Error in removeSessionTimeoutUser", e);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
}
