package booksearch.util;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import booksearch.solr.JoinType;

public class Util {
	
	public static <K,V> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map, final Comparator<? super V> comp) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
        					new Comparator<Map.Entry<K,V>>() {
        							@Override public int compare(Entry<K, V> e1, Entry<K, V> e2) {
        								return comp.compare(e2.getValue(), e1.getValue());
        							}
        					});
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
	
//	public static String removePunctuation(String s) {
//		StringBuilder sb = new StringBuilder();
//		char[] chars = s.toCharArray();
//		for (int i = 0; i < chars.length; i++) {
//			char c = chars[i];
//			if (i == (chars.length - 1)) {
//				// Only remove period if it's the last character. *TO FIX* Not perfect but ok for now
//				// Should account for abbreviation, time
//				if (c != '.') {
//					sb.append(c);
//				}
//			} else if (c != ',' && 
//				//c != '.' && // Unless it's time... a.m. p.m. or abbreviation Mr. Smith
//				c != ':' && 
//				c != ';' &&
//				c != '?' &&
//				c != '!' &&
//				c != '�' &&		// em-dash
//				c != '\'' &&	// generic single-quote
//				c != '�' &&		// opening single-quote
//				c != '�' &&		// closing single-quote
//				c != '"' &&		// generic double-quotes
//				c != '�' &&		// opening double-quotes
//				c != '�'		// closing double-quotes
//				) {
//				sb.append(c);
//			}
//		}
//		return sb.toString().trim();
//	}
	
	/**
	 * 
	 * @param field
	 * @param termJoinType "AND" or "OR"
	 * @param terms
	 * @return
	 */
	public static String listToSolrQueryStr(String field, JoinType termJoinType, List<String> terms) {
		StringBuilder sb = new StringBuilder();
		for (String term : terms) {
			sb.append(field);
			sb.append(":");
			sb.append(term);
			sb.append(" ");
			sb.append(termJoinType.toString());
			sb.append(" ");
		}
		sb.delete(sb.length() - (termJoinType.toString().length() + 2), sb.length());
		return sb.toString();
	}
	
	public static String listToSolrQueryStr(List<String> fields, JoinType termJoinType, List<String> terms) {
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append("(");
			sb.append(listToSolrQueryStr(field, termJoinType, terms));
			sb.append(") OR ");
		}
		sb.delete(sb.length() - 4, sb.length());
		return sb.toString();
	}
	
	public static String listToString(List<String> list) {
		if (list == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
			sb.append(" \n ");
		}
		sb.delete(sb.length() - 3, sb.length());
		return sb.toString();
	}
	
	public static File[] getResourceFolderFiles(String folder) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(folder);
		String path = url.getPath();
		return new File(path).listFiles();
	}
}
