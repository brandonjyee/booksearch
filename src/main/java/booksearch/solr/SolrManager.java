package booksearch.solr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrManager {
	private static SolrManager instance = new SolrManager();
	private Map<String, SolrClient> solrClientMap = new HashMap<String, SolrClient>();
	
	private SolrManager() {
		String url = "http://localhost:8080/solr/";	// TODO: This fails portability to a PaaS
		/*
	    HttpSolrServer is thread-safe and if you are using the following constructor,
	    you *MUST* re-use the same instance for all requests.  If instances are created on
	    the fly, it can cause a connection leak. The recommended practice is to keep a
	    static instance of HttpSolrServer per solr server url and share it for all requests.
	    See https://issues.apache.org/jira/browse/SOLR-861 for more details
		 */
		solrClientMap.put(SolrCore.BOOK_SEARCH.getKey(), new HttpSolrClient.Builder(url + "book_query").build());
		solrClientMap.put(SolrCore.EMOTION_THESAURUS.getKey(), new HttpSolrClient.Builder(url + "emotion_thesaurus").build());
	}
	
	public static SolrManager getInstance() {
		return instance;
	}
	
	public SolrClient getSolrClient(String key) {
		return solrClientMap.get(key);
	}
	
	public void deleteAll(String key) throws SolrServerException, IOException {
		SolrClient server = solrClientMap.get(key);
		server.deleteByQuery( "*:*" );// CAUTION: deletes everything!
		server.commit();
	}
}
