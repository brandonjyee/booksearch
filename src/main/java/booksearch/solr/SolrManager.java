package booksearch.solr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import booksearch.util.ConfigProps;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SolrManager {
	private static SolrManager instance = new SolrManager();
	private Map<String, SolrClient> solrClientMap = new HashMap<String, SolrClient>();
	private ConfigProps config = ConfigProps.getInstance();
	
	private SolrManager() {		
		String solrURL = config.get("solr_url");
		/*
	    HttpSolrServer is thread-safe and if you are using the following constructor,
	    you *MUST* re-use the same instance for all requests.  If instances are created on
	    the fly, it can cause a connection leak. The recommended practice is to keep a
	    static instance of HttpSolrServer per solr server url and share it for all requests.
	    See https://issues.apache.org/jira/browse/SOLR-861 for more details
		 */
		log.error("this is a test error");
		solrClientMap.put(SolrCore.BOOK_SEARCH.getKey(), new HttpSolrClient.Builder(solrURL + config.get("booksearch_core")).build());
		solrClientMap.put(SolrCore.EMOTION_THESAURUS.getKey(), new HttpSolrClient.Builder(solrURL + config.get("emothesaurus_core")).build());
	}
	
	public static SolrManager getInstance() {
		return instance;
	}
	
	public SolrClient getSolrClient(String key) {
		return solrClientMap.get(key);
	}
	
	public void deleteAll(String key) throws SolrServerException, IOException {
		SolrClient server = solrClientMap.get(key);
		UpdateResponse resp = server.deleteByQuery( "*:*" );// CAUTION: deletes everything!
		log.info(resp.toString());
		resp = server.commit();
		log.info(resp.toString());
	}
}
