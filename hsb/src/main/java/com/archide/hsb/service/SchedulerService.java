package com.archide.hsb.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.archide.hsb.dao.OrdersDao;
import com.archide.hsb.model.LoginUsersEntity;

@Service
public class SchedulerService {
	@Autowired
	private OrdersDao ordersDao;
	
	//@Scheduled(fixedDelay = 10000)
	public void getPaymentStatus(){
		Session session = null;
		try{
			session = ordersDao.openSession();
			long time = ServiceUtil.getCurrentGmtTime() - 21600000;
			List<LoginUsersEntity> loginUsersEntities = ordersDao.getLoginUsersEntity(session, time);
			for(LoginUsersEntity loginUsersEntity : loginUsersEntities){
				//ordersDao.get
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
