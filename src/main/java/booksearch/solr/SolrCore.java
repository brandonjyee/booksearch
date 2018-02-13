package booksearch.solr;

public enum SolrCore {
	BOOK_SEARCH,
	EMOTION_THESAURUS;
	
	public String getKey() {
		return name();
	}
	
	public String getCoreName() {
		return ""; // TODO
	}
}
