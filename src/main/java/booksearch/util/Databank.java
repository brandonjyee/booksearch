package booksearch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Not currently thread-safe.
 * 
 * @author Brandon
 *
 */
public class Databank {
	private static Databank instance = new Databank();
	private Map<String, List<String>> wordlistMap = new HashMap<String, List<String>>(); 
	
	private Databank() {
		// Read in words
		try {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				File file = new File("./databanks/repetition-stop-words.txt");
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String listName = file.getName();
				System.out.println("listName: " + listName);
				List<String> wordlist = new ArrayList<String>();
				String line;
				while ((line = br.readLine()) != null) {
					line = line.trim();
					wordlist.add(line);
				}
				wordlistMap.put(listName, wordlist);
			} finally {
				br.close();
				fr.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Databank getInstance() {
		return instance;
	}
	
	public List<String> getWordlist(String name) {
		return wordlistMap.get(name);
	}
}
