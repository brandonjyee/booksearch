package booksearch.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import booksearch.BookManager;
import booksearch.book.Book;
import booksearch.query.Queries;

public class BookStatistics extends HttpServlet {
	private static final long serialVersionUID = -2327679983887988435L;

	@Override
	public void init() throws ServletException {
		// Do required initialization
	}

	/**
	 * Displays query page
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		
		// Output list of books
		Collection<Book> books = BookManager.getInstance().getAllBooks();
		List<String> bookNames = new ArrayList<String>();
		for (Book book : books) {
			bookNames.add(book.getTitle());
		}
		String[] emptyArray = new String[0];
		request.setAttribute("bookNames", bookNames.toArray(emptyArray));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/bookstats.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Handles a search query and displays result
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//StringBuilder ERROR = new StringBuilder();
		String bookName = request.getParameter("book");
		Book book = BookManager.getInstance().getBook(bookName);
		long numSents = Queries.computeNumSentences(book);
		long numPars = Queries.computeNumParagraphs(book);
		double avgSentPerPar = numSents / (double) numPars;
		String[] outputArray = {"Title: " + book.getTitle(), 
					"Author: " + book.getAuthor(),
					"Num Chapters: " + book.getChapters().size(),
					"Num Paragraphs: " + numPars,
					"Num Sentences: " + numSents,
					"Avg Number of Sentences Per Paragraph: " + avgSentPerPar
					};
		request.setAttribute("outputArray", outputArray);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/basicoutput.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void destroy() {
		// do nothing.
	}
}
