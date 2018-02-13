package booksearch.servlet;

// Import required java libraries
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import booksearch.BookManager;
import booksearch.book.Book;
import booksearch.solr.JoinType;
import booksearch.solr.SolrCore;
import booksearch.solr.SolrManager;
import booksearch.util.Util;

// Extend HttpServlet class
public class BookQuery extends HttpServlet {
	private static final long serialVersionUID = -7305699688471791965L;

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
		//String[] bookNames = {"BTY", "Damaged", "Wrecked"};
		String[] emptyArray = new String[0];
		request.setAttribute("bookNames", bookNames.toArray(emptyArray));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/solrquery.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Handles a search query and displays result
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder ERROR = new StringBuilder();
		String queryTermsStr = request.getParameter("query");
		if (queryTermsStr.equals("")) {
			ERROR.append("Error: Empty query string");
		}
		
		List<String> terms = Arrays.asList(queryTermsStr.split(" "));
		String queryStr = Util.listToSolrQueryStr("paragraph", JoinType.AND, terms);
		List<String> books = Arrays.asList(request.getParameterValues("books"));
		queryStr += " AND " + "(" + Util.listToSolrQueryStr("book", JoinType.OR, books) + ")";
		System.out.println("queryString: " + queryStr);
		SolrQuery query = new SolrQuery();
		query.setQuery(queryStr);
		query.setRows(100);		// Limit to a max of 100 results
		// Field for highlighting
		query.setHighlight(true);
		query.setHighlightFragsize(0);
		query.setHighlightSimplePre("{|"); //"<span style=\"background-color: #FFFF00\">"); //"<em>");
		query.setHighlightSimplePost("|}"); //"</span>"); //"</em>");
		query.addHighlightField("*");
		// http://localhost:8080/solr/collection1/select?q=paragraph%3Astrength&wt=json&indent=true&hl=true&hl.fl=*&hl.simple.pre=%3Cem%3E&hl.simple.post=%3C%2Fem%3E&hl.fragsize=0&hl.fl=*
		
		SolrClient solrClient = SolrManager.getInstance().getSolrClient(SolrCore.BOOK_SEARCH.getKey());
		
		QueryResponse qr;
		try {
			qr = solrClient.query(query);
			Map<String, Map<String, List<String>>> highlighting = qr.getHighlighting();
			
			SolrDocumentList results = qr.getResults();
			long numFound = results.getNumFound();
			System.out.println("num results: " + numFound);
			List<String> resultEntries = new ArrayList<String>();
			for (SolrDocument doc : results) {
				String docId = (String) doc.getFieldValue("id");
				
				StringBuilder sb = new StringBuilder();
				//String paragraph = StringEscapeUtils.escapeHtml4((String) doc.getFieldValue("paragraph"));
				//sb.append(paragraph);
				String highlightedParagraph = StringEscapeUtils.escapeHtml4(highlighting.get(docId).get("paragraph").get(0));
				// Must add highlight styling after escaping html, not before
				highlightedParagraph = highlightedParagraph.replace("{|", "<span style=\"background-color: #FFFF00\">")
															.replace("|}", "</span>");
				sb.append(highlightedParagraph);
				sb.append(" (");
				String book = StringEscapeUtils.escapeHtml4((String) doc.getFieldValue("book"));
				sb.append(book);
				sb.append(", ");
				String id = StringEscapeUtils.escapeHtml4(docId);
				sb.append(id);
				sb.append(")");
				//System.out.println("sb: " + sb.toString());
				resultEntries.add(sb.toString());
			}
			String[] emptyArray = new String[0];
			request.setAttribute("numResults", Long.valueOf(numFound)); //string, obj
			request.setAttribute("resultEntries", resultEntries.toArray(emptyArray));
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		request.setAttribute("error", ERROR.toString());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/solrresponse.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void destroy() {
		// do nothing.
	}
}
