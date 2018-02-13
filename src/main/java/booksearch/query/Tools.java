package booksearch.query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import booksearch.BookManager;
import booksearch.book.Book;

public class Tools {
	public static void main(String[] args) throws IOException {
		Book book = BookManager.getInstance().getBook("Bared_To_You");
		Map<String, Long> wordFreqs = Queries.getWordFrequencies(book);
		// Alphabetize
		Set<String> words = wordFreqs.keySet();
		List<String> wordList = new ArrayList<String>(words);
		Collections.sort(wordList);
		
//		SortedSet<Entry<String, Long>> sortedEntries = Util.entriesSortedByValues(wordFreqs, new Comparator<Long>() {
//            @Override public int compare(Long c1, Long c2) {
//            	int comparison = c1.compareTo(c2);
//				return (comparison == 0) ? -1 : comparison;
//            }           
//        });
		
		FileWriter fw = new FileWriter(new File("./databanks/trigger-words.txt"));
		BufferedWriter bw = new BufferedWriter(fw);
		try {
//			for (Entry<String, Long> entry : sortedEntries) {
//				System.out.println(entry.getKey() + "|" + entry.getValue());
//				bw.write(entry.getKey());
//				bw.newLine();
//			}
			System.out.println("numWords: " + wordList.size());
			for (String word : wordList) {
				System.out.println(word);
				bw.write(word);
				bw.newLine();
			}
		} finally {
			bw.close();
			fw.close();
		}
	}
}
