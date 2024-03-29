package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookServices {
	private EntityManager entityManager;
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	

	public BookServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		bookDAO = new BookDAO();
		categoryDAO=new CategoryDAO();
	}

	public void Listbooks() throws ServletException, IOException {
	Listbooks(null);

	}
	
	public void Listbooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);

		if(message != null) {
			request.setAttribute("message", message);
		}
		
		String listPage = "book_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);

	}
	
	public void showBookNewForm() throws ServletException, IOException{
		List<Category> listCategory=categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);
		
		String newPage="book_form.jsp";
		RequestDispatcher requestDispatcher=request.getRequestDispatcher(newPage);
		requestDispatcher.forward(request, response);
	}

	public void createBook() throws ServletException, IOException{
		Integer categoryId=Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		
		Book existBook=bookDAO.findByTitle(title);
		
		if(existBook !=null) {
			String message= "Could not create new book because the title '"
		              + title + "' already exists.";
			Listbooks(message);
			return;
		}
		
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));
		
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		Date PublishDate=null;
		try {
		   PublishDate=dateFormat.parse(request.getParameter("publishDate"));
		}catch(ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing publish date (format is MM/dd/yyyy)");
		}
		
		System.out.println("Category ID: "+categoryId);
		System.out.println("title : "+title);
		System.out.println("author : "+author);
		System.out.println("description : "+description);
		System.out.println("isbn : "+isbn);
		System.out.println("price : "+price);
		System.out.println("publishDate: "+PublishDate);
		
		Book newBook = new Book();
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setDescription(description);
		newBook.setIsbn(isbn);
		newBook.setPublishDate(PublishDate);
		newBook.setPrice(price);
		
		Category category=categoryDAO.get(categoryId);
		newBook.setCategory(category);
		
		Part part = request.getPart("bookImage");
		
		if(part != null && part.getSize() >0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int)size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			newBook.setImage(imageBytes);
		}
		
		Book createdBook=bookDAO.create(newBook);
		
		if(createdBook.getBookId() >0) {
			String message = "A new book has been created successfully.";
			Listbooks(message);
		}
		
	}

}
























