package com.bookstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.text.ParseException;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest extends BaseDAOTest {
	private static BookDAO bookDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		bookDAO = new BookDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateBook() throws IOException, ParseException {
		Book newBook = new Book();

		Category category = new Category("Advanced Java");
		category.setCategoryId(1);
		newBook.setCategory(category);

		newBook.setTitle("Effective Java (2nd Edition)");
		newBook.setAuthor("Joshua Block");
		newBook.setDescription(
				"Are you looking for a deeper understanding of the Java™ programming language so that you can write");
		newBook.setPrice(38.87f);
		newBook.setIsbn("0321356683");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/28/2008");
		newBook.setPublishDate(publishDate);

		String imagePath = "D:\\OneDrive - edy\\F\\Programming\\resourseforbookstorewebsiteudemy\\books\\Effective Java.JPG";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);

		Book createBook = bookDAO.create(newBook);

		assertTrue(createBook.getBookId() > 0);
	}

}
