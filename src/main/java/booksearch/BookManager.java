package booksearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booksearch.book.Book;


/**
 * Not thread safe
 * @author Brandon
 *
 */
public class BookManager {
	private static BookManager instance = new BookManager();
	private Map<String, Book> bookMap = new HashMap<String, Book>();
	
	private BookManager() {
		try {
			// Note: Will try to find book in filesystem first, if can't (like if trying to access fs
			// from a war) then will try to find file from the classloader
			bookMap.put("Bared_To_You", Book.getBook("books/Bared_To_You.txt", "Bared_To_You", "Sylvia Day"));
			bookMap.put("Beautiful_Disaster", Book.getBook("books/Beautiful_Disaster.txt", "Beautiful_Disaster", "Jamie McGuire"));
			bookMap.put("Beautiful_Surrender", Book.getBook("books/Beautiful_Surrender.txt", "Beautiful_Surrender", "Priscilla West"));
			bookMap.put("Damaged", Book.getBook("books/Damaged.txt", "Damaged", "HM Ward"));
			bookMap.put("Forbidden_Surrender", Book.getBook("books/Forbidden_Surrender.txt", "Forbidden_Surrender", "Priscilla West"));
			bookMap.put("Knight_And_Play", Book.getBook("books/Knight_And_Play.txt", "Knight_And_Play", "Kitty French"));
			bookMap.put("Locked", Book.getBook("books/Locked.txt", "Locked", "Maya Cross"));
			bookMap.put("Lockout", Book.getBook("books/Lockout.txt", "Lockout", "Maya Cross"));
			bookMap.put("Real", Book.getBook("books/Real.txt", "Real", "Katy Evans"));
			bookMap.put("Secret_Surrender", Book.getBook("books/Secret_Surrender.txt", "Secret_Surrender", "Priscilla West"));
			bookMap.put("Wrecked", Book.getBook("books/Wrecked.txt", "Wrecked", "Priscilla West"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static BookManager getInstance() {
		return instance;
	}
	
	public Book getBook(String bookName) {
		return bookMap.get(bookName);
	}
	
	/**
	 * Order alphabetically by title
	 * @return
	 */
	public Collection<Book> getAllBooks() {
		List<Book> books = new ArrayList<Book>(bookMap.values());
		
		return books;
	}
}
