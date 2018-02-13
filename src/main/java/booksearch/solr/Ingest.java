package booksearch.solr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import booksearch.BookManager;
import booksearch.EmotionThesaurusManager;
import booksearch.book.Book;
import booksearch.book.Chapter;
import booksearch.book.Paragraph;
import booksearch.emotion_thesaurus.Sections;
import booksearch.util.Util;

public class Ingest {
//	private SolrClient solrClient;
	
	public Ingest() {
		
	}
	
	public static void main(String[] args) throws SolrServerException, IOException {
//		SolrManager solrManager = SolrManager.getInstance();
		//SolrServer server = solrManager.getServer(SolrCore.BOOK_SEARCH.getKey());
//		SolrServer server = solrManager.getServer(SolrCore.EMOTION_THESAURUS.getKey());
		

		//sendAllBooks_BookQuery(server);
		//Book book = BookManager.getInstance().getBook("Damaged");
		//sendBook(server, book);
		//bookQuery();
		//sendEmotion_EmoThesaurus(server);
		
		setupCore_EmoThesaurus();
		//setupCore_BookQuery();
		//mytest();
	}
	
	public static void setupCore_BookQuery() throws SolrServerException, IOException {
		SolrManager solrManager = SolrManager.getInstance();
		SolrClient solrClient = solrManager.getSolrClient(SolrCore.BOOK_SEARCH.getKey());
		System.out.println("Deleting BOOK_SEARCH core...");
		solrManager.deleteAll(SolrCore.BOOK_SEARCH.getKey());
		System.out.println("Deleted BOOK_SEARCH core.");
		System.out.println("Starting sending books to BOOK_SEARCH core...");
		sendAllBooks_BookQuery(solrClient);
		System.out.println("Finished sending books to BOOK_SEARCH core.");
	}
	
	public static void setupCore_EmoThesaurus() throws SolrServerException, IOException {
		SolrManager solrManager = SolrManager.getInstance();
		SolrClient solrClient = solrManager.getSolrClient(SolrCore.EMOTION_THESAURUS.getKey());
		solrManager.deleteAll(SolrCore.EMOTION_THESAURUS.getKey());
		sendEmotion_EmoThesaurus(solrClient);
	}
	
	public static void sendEmotion_EmoThesaurus(SolrClient server) throws SolrServerException, IOException {
		File emotionDir = new File("./databanks/emotion_thesaurus");
		File[] emotionFiles = emotionDir.listFiles();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (File emotionFile : emotionFiles) {
			//System.out.println(emotionFile.getName());
			Map<String, List<String>> content = EmotionThesaurusManager.readFile(emotionFile);
			// emotion; definition; physical signals; internal sensations; mental responses; acute or long-term cues;
			// related; suppressed cues; writer tip
			SolrInputDocument doc = new SolrInputDocument();
			String id = content.get(Sections.EMOTION.toString()).get(0);
			doc.addField("id", id);
			doc.addField("emotion", Util.listToString(content.get(Sections.EMOTION.toString())));
			doc.addField("definition", Util.listToString(content.get(Sections.DEFINITION.toString())));
//			String physicalSignals = Util.listToString(content.get(Sections.PHYSICAL_SIGNALS.toString()));
			doc.addField("physical_signals", Util.listToString(content.get(Sections.PHYSICAL_SIGNALS.toString())));
			doc.addField("internal_sensations", Util.listToString(content.get(Sections.INTERNAL_SENSATIONS.toString())));
			doc.addField("mental_responses", Util.listToString(content.get(Sections.MENTAL_RESPONSES.toString())));
			doc.addField("acute_or_long-term_cues", Util.listToString(content.get(Sections.ACUTE_OR_LONG_TERM_CUES.toString())));
			doc.addField("related", Util.listToString(content.get(Sections.RELATED_EMOTIONS.toString())));
			doc.addField("suppressed_cues", Util.listToString(content.get(Sections.SUPPRESSED_CUES.toString())));
			doc.addField("writer_tip", Util.listToString(content.get(Sections.WRITER_TIP.toString())));
			docs.add(doc);
		}
		server.add(docs);
		server.commit();
	}
	
	public static void sendAllBooks_BookQuery(SolrClient server) throws SolrServerException, IOException {
		Collection<Book> books = BookManager.getInstance().getAllBooks();
		for (Book book : books) {
			System.out.println("Sending book " + book.getTitle() + "...");
			sendBook(server, book);
			System.out.println("Finished sending book " + book.getTitle() + ".");
		}
	}
	
	private static void sendTest(SolrClient server) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		
		doc.addField("id", "BTY-1");
		doc.addField("book", "Bared To You");
		doc.addField("author", "Sylvia Day");
		doc.addField("paragraph", "Cary had green eyes and he tackled Gideon.");
		
		/*doc.addField("id", "SOLR1000");
		doc.addField("name", "Solr, the Enterprise Search Server");
		doc.addField("manu", "Apache Software Foundation");
		doc.addField("cat", "software");
		doc.addField("cat", "search");
		doc.addField("features", "Advanced Full-Text Search Capabilities using Lucene");
		doc.addField("features", "Optimized for High Volume Web Traffic");
		doc.addField("features", "Standards Based Open Interfaces - XML and HTTP");
		doc.addField("price", Integer.valueOf(0));
		doc.addField("popularity", Integer.valueOf(10));
		doc.addField("inStock", Boolean.TRUE);
		doc.addField("incubationdate_dt", "2006-01-17T00:00:00.000Z");
		*/

		/*
		  <field name="features">Comprehensive HTML Administration Interfaces</field>
		  <field name="features">Scalability - Efficient Replication to other Solr Search Servers</field>
		  <field name="features">Flexible and Adaptable with XML configuration and Schema</field>
		  <field name="features">Good unicode support: h&#xE9;llo (hello with an accent over the e)</field>
		  */
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc);
		server.add(docs);
		server.commit();
		
	}
	
/*	private static void mytest() {
		List<String> terms = new ArrayList<String>();
		terms.add("bite");
		terms.add("strength");
		List<String> searchFields = Arrays.asList("emotion", "definition", "physical_signals",
				"internal_sensations", "mental_responses", "acute_or_long-term_cues",
				"related", "suppressed_cues", "writer_tip");
		String queryStr = Util.listToSolrQueryStr(searchFields, JoinType.AND, terms);
		System.out.println(queryStr);
	}*/
	
	public static void bookQuery() throws SolrServerException, IOException {
		SolrClient solrClient = SolrManager.getInstance().getSolrClient(SolrCore.BOOK_SEARCH.getKey());
		
		SolrQuery query = new SolrQuery();
		//query.setQuery("*:*"); 	// The 'all' query
		//query.addSort("price", SolrQuery.ORDER.asc);
		//query.setQuery("paragraph:bite");
		//query.add("paragaph:bite");
		//query.setParam("", values)
		
		List<String> terms = new ArrayList<String>();
		terms.add("bite");
		terms.add("strength");
		String queryStr = Util.listToSolrQueryStr("paragraph", JoinType.AND, terms);
		query.setQuery(queryStr);
		query.setRows(100);
		query.setHighlight(true);
		query.setHighlightFragsize(0);
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		query.addHighlightField("*");
		//query.seth
		// http://localhost:8080/solr/collection1/select?q=paragraph%3Abite+AND+paragraph%3Astrength&wt=json&indent=true&hl=true&hl.fl=*&hl.simple.pre=%3Cem%3E&hl.simple.post=%3C%2Fem%3E&hl.fragsize=0
		
		QueryResponse response = solrClient.query(query);
		// <docId, <docField, snippets>>
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		
		SolrDocumentList results = response.getResults();
		long numFound = results.getNumFound();
		System.out.println("num results: " + numFound);
		
		List<String> resultEntries = new ArrayList<String>();
		for (SolrDocument doc : results) {
			String docId = (String) doc.getFieldValue("id");
			
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getFieldValue("paragraph"));
			sb.append(" (");
			sb.append(doc.getFieldValue("book"));
			sb.append(", ");
			sb.append(doc.getFieldValue("id"));
			sb.append(")");
			
			
			System.out.println("sb: " + sb.toString());
			String snippet = highlighting.get(docId).get("paragraph").get(0);
			System.out.println("snippet: " + snippet);
			resultEntries.add(sb.toString());
		}
	}
	
	public static void sendBook(SolrClient server, Book book) throws SolrServerException, IOException {
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			// Create a batch of paragraph documents per chapter
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			List<Paragraph> paragraphs = chapter.getParagraphs();
			for (Paragraph p : paragraphs) {
				SolrInputDocument doc = new SolrInputDocument();
				// TODO fix ID
				String id = book.getTitle() + "-" + p.getNum();
				doc.addField("id", id);
				doc.addField("book", book.getTitle());
				doc.addField("author", book.getAuthor());
				doc.addField("paragraph", p.getText());
				docs.add(doc);
			}
			server.add(docs);
		}
		server.commit();
	}
}
