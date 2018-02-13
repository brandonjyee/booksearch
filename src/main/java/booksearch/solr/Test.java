package booksearch.solr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {
	public static void main(String[] args) throws IOException {
		InputStream is = ClassLoader.getSystemResourceAsStream("book/Bared_to_You.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		//BufferedInputStream bis = new BufferedInputStream(is);
		String line;
		long numLines = 0L;
		while ((line = br.readLine()) != null) {
			numLines++;
			System.out.println(line);
		}
		System.out.println("numLines: " + numLines);
		System.out.println("line: " + line);
	}
}
