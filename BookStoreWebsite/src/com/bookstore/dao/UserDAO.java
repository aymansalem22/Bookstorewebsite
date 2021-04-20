package com.bookstore.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.bookstore.entity.Users;

public class UserDAO extends JpaDAO<Users> implements GenericDAO<Users> {

	public UserDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public Users create(Users user) {
		
		return super.create(user);
	}



	@Override
	public Users update(Users user) {
		// TODO Auto-generated method stub
		return super.update(user);
	}

	@Override
	public Users get(Object userID) {
		
		return super.find(Users.class, userID);
	}
	
	
	public Users findByEmail(String email) {
		//"email" refer to query =:email"
		List<Users> listUsers= super.findWithNamedQuery("Users.findByEmail","email",email);
		if(listUsers!=null && listUsers.size()>0) {
			return listUsers.get(0);
		}
		return null;
		 
		
		
	}

	@Override
	public void delete(Object userId) {
		super.delete(Users.class, userId);
		
	}

	@Override
	public List<Users> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Users.findAll");
	}

	@Override
	public long count() {
	
		return super.countWithNamedQuery("Users.countAll");
	}
	
	

	
	
	
	
	
	
	
}
