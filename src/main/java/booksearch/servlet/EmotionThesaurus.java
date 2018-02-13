package booksearch.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import booksearch.solr.JoinType;
import booksearch.solr.SolrCore;
import booksearch.solr.SolrManager;
import booksearch.util.Util;

public class EmotionThesaurus extends HttpServlet {
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

		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/emotionthesaurus_query.jsp");
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
		List<String> searchFields = Arrays.asList("emotion", "definition", "physical_signals",
									"internal_sensations", "mental_responses", "acute_or_long-term_cues",
									"related", "suppressed_cues", "writer_tip");
		String queryStr = Util.listToSolrQueryStr(searchFields, JoinType.AND, terms);
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
		
		SolrClient solrClient = SolrManager.getInstance().getSolrClient(SolrCore.EMOTION_THESAURUS.getKey());
		
		QueryResponse qr;
		try {
			qr = solrClient.query(query);
			Map<String, Map<String, List<String>>> highlighting = qr.getHighlighting();
			
			SolrDocumentList results = qr.getResults();
			long numFound = results.getNumFound();
			System.out.println("num results: " + numFound);
			List<String> emotions = new ArrayList<String>();
			List<String> definitions = new ArrayList<String>();
			List<String> physicalSignals = new ArrayList<String>();
			List<String> internalSensations = new ArrayList<String>();
			List<String> mentalResponses = new ArrayList<String>();
			List<String> acuteOrLongTermCues = new ArrayList<String>();
			List<String> relatedEmotions = new ArrayList<String>();
			List<String> suppressedCues = new ArrayList<String>();
			List<String> writerTips = new ArrayList<String>();
			for (SolrDocument doc : results) {
				String docId = (String) doc.getFieldValue("id");
				System.out.println("docId: " + docId);
				Map<String, List<String>> highlightMap = highlighting.get(docId);
				addResult(emotions, "emotion", doc, highlightMap);
				addResult(definitions, "definition", doc, highlightMap);
				addResult(physicalSignals, "physical_signals", doc, highlightMap);
				addResult(internalSensations, "internal_sensations", doc, highlightMap);
				addResult(mentalResponses, "mental_responses", doc, highlightMap);
				addResult(acuteOrLongTermCues, "acute_or_long-term_cues", doc, highlightMap);
				addResult(relatedEmotions, "related", doc, highlightMap);
				addResult(suppressedCues, "suppressed_cues", doc, highlightMap);
				addResult(writerTips, "writer_tip", doc, highlightMap);
			}
			String[] emptyArray = new String[0];
//			for (String entry : emotions) {
//				System.out.println("resultEntry: " + entry);
//			}
			request.setAttribute("numResults", Long.valueOf(numFound)); 
			request.setAttribute("emotions", emotions.toArray(emptyArray));
			request.setAttribute("definitions", definitions.toArray(emptyArray));
			request.setAttribute("physicalSignals", physicalSignals.toArray(emptyArray));
			request.setAttribute("internalSensations", internalSensations.toArray(emptyArray));
			request.setAttribute("mentalResponses", mentalResponses.toArray(emptyArray));
			request.setAttribute("acuteOrLongTermCues", acuteOrLongTermCues.toArray(emptyArray));
			request.setAttribute("relatedEmotions", relatedEmotions.toArray(emptyArray));
			request.setAttribute("suppressedCues", suppressedCues.toArray(emptyArray));
			request.setAttribute("writerTips", writerTips.toArray(emptyArray));
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		request.setAttribute("error", ERROR.toString());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/emotionthesaurus_results.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addResult(List<String> results, String field, SolrDocument doc, Map<String, List<String>> highlightMap) {
		List<String> snippets = highlightMap.get(field);
		// If there's highlighting on this field, use it
		if (snippets != null && !snippets.isEmpty()) {
			String highlightedSnippet = StringEscapeUtils.escapeHtml4(snippets.get(0));
			// Must add highlight styling after escaping html, not before
			highlightedSnippet = highlightedSnippet.replace("{|", "<span style=\"background-color: #FFFF00\">")
													.replace("|}", "</span>")
													.replace("\n", "<br/>");
			results.add(highlightedSnippet);
		} else {
			// There wasn't highlighting on this field
			String fieldValue = (String) doc.getFieldValue(field);
//			System.out.println("Field Value: " + fieldValue);
//			String escapedFV = StringEscapeUtils.escapeHtml4(fieldValue);
//			System.out.println("escapedFV: " + escapedFV);
//			String test = "hello \n world";
//			System.out.println("test: " + test);
//			String escapedTest = StringEscapeUtils.escapeHtml4(test);
//			System.out.println("escapedTest: " + escapedTest);
//			System.out.println("escapedTest2: " + test.replace("\n", "<br>"));
			results.add(StringEscapeUtils.escapeHtml4(fieldValue).replace("\n", "<br/>"));
		}
	}

	@Override
	public void destroy() {
		// do nothing.
	}
}
