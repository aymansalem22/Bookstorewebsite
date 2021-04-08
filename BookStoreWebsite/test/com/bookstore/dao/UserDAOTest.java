package com.bookstore.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static UserDAO userDAO;

	@BeforeClass
	public static void setUpClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
		entityManager = entityManagerFactory.createEntityManager();

		userDAO = new UserDAO(entityManager);

	}

	@Test
	public void testCreateUsers() {
		Users user1 = new Users();
		user1.setEmail("ali@hotmail.cim");
		user1.setFullName("ashraf salem abusafia");
		user1.setPassword("12489");

		EntityManagerFactory entitymanagerfactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
		EntityManager entityManager = entitymanagerfactory.createEntityManager();

		UserDAO userDao = new UserDAO(entityManager);
		user1 = userDao.create(user1);
		entityManager.close();
		entitymanagerfactory.close();

		assertTrue(user1.getUserId() > 0);
	}

	//we add expected to catch the error bc must user2 has values and here doesnt have values ,null 
	@Test(expected=PersistenceException.class)
	public void testCreateUsersFieldNotSet() {
		Users user2 = new Users();
        user2=userDAO.create(user2);
       // assertTrue(user2.getUserId()>0);
	}
	
	
	@Test
	public void testUpdateUsers() {
		Users user1=new Users();
		user1.setUserId(1);
		user1.setEmail("aymanupdate@aaa");
		user1.setFullName("ayman abusafiaa");
		user1.setPassword("secret");
		
		user1 = userDAO.update(user1);
		String expected="secret";
		String actual=user1.getPassword();
		assertEquals(expected, actual);
		
	}
	
	
	@Test
	public void testGetUsersFound() {
		Integer userId=1;
		Users user=userDAO.get(userId);
		if(user!=null) {
		System.out.println(user.getEmail());
		}
		assertNotNull(user);
		
	}
	
	@Test
	public void testGetUsersNotFound() {
		Integer userId=99;
		Users user=userDAO.get(userId);
		assertNull(user);
		
	}
	
	@Test
	public void testDeleteUsers() {
		Integer userId=4;
		userDAO.delete(userId);
		Users user=userDAO.get(userId);
		assertNull(user);
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteNonExistUsers() {
		Integer userId=55;
		userDAO.delete(userId);
		
		
	}
	
	@Test
	public void testDeleteNonExistUserswithoutexception() {
		Integer userId=55;
		userDAO.delete(userId);
		
		//we see the error bc not exsite the user
	}
	
	@Test
	public void testListAll() {
		List<Users> listUsers=userDAO.listAll();
		for(Users user:listUsers) {
			System.out.println(user.getEmail());
		}
		assertTrue(listUsers.size()>0);
	} 
	
	@Test
	public void testCount() {
		long totalUsers=userDAO.count();
		//WE HAVE 3 USERS IN DATABASE TABLE USER
		assertEquals(3, totalUsers);;
		
		
	}
	
	
	@AfterClass
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
