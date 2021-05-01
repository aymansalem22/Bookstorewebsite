package com.bookstore.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.UserDAO;
import com.bookstore.entity.Users;

public class UserServices {
	
	private EntityManager entityManager;
	private UserDAO userDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserServices(EntityManager entityManager,
			HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager=entityManager;
		userDAO = new UserDAO(entityManager);
	}

	public void listuser() throws ServletException, IOException {

		listUser(null);
	}

	public void listUser(String message) throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();
		request.setAttribute("listUsers", listUsers);
		if (message != null) {
			request.setAttribute("message", message);

		}
		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);

	}

	public void createUser() throws ServletException, IOException {

		String email = request.getParameter("email");// refer to attribute name=email in user_form
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users existUser = userDAO.findByEmail(email);
		if (existUser != null) {
			String message = "Could not create user. A user with email " + email + " already exists";
			request.setAttribute("message", message);
			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);

		} else {

			Users newUser = new Users(email, fullName, password);
			userDAO.create(newUser);
			listUser("New user created successfully");
		}

	}

	public void editUser() throws ServletException, IOException {
		// also we apply the assignment 1
		int userId = Integer.parseInt(request.getParameter("id"));// we get it from link of hypertext in bar browser
		Users user = userDAO.get(userId);
		String destPage = "user_form.jsp";
		if (user == null) {
			destPage = "message.jsp";
			String errorMessage = "Could not find user with ID " + userId;
			request.setAttribute("message", errorMessage);
		} else {

			request.setAttribute("user", user);

		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);
	}

	public void updateUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");// *this name "email" from the name of inputfield in user_form
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");
		/// System.out.println(userId+" , " +email+" , "+fullName+" , "+password);//to
		/// confirm in console it work well

		// to check before update the user if the email already exist with another user
		Users userById = userDAO.get(userId);
		Users userByEmail = userDAO.findByEmail(email);
		if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {
			String message = "could not update user. user with email " + email + " already exists.";
			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("message.jsp");
			requestDispatcher.forward(request, response);

		} else {

			Users user = new Users(userId, email, fullName, password);
			userDAO.update(user);

			String message = "User has been updated successfully";
			listUser(message);

		}
	}

	public void deleteUser() throws ServletException, IOException {

		// we apply  Assignment 2: Update Delete User Feature
		//we apply also Assignment 3: Prevent the default admin user id 1 from being deleted
		

		int userId = Integer.parseInt(request.getParameter("id"));

		Users user = userDAO.get(userId);
		String message = "User has been deleted successfully";
		if (userId == 1) {
			message = "The default admin user account cannot be deleted.";

			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
			return;
		}

		if (user == null) {
			message = "Could not find user with ID " + userId + ", or it might have been deleted by another admin";

			request.setAttribute("message", message);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		} else {
			userDAO.delete(userId);
			listUser(message);
		}

	}

}
