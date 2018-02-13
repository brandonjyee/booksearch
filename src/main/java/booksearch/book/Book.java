package booksearch.book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import booksearch.BookManager;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Book {
	// Fields
	private String title;
	private String author;
	private List<Chapter> chapters;// = new ArrayList<Chapter>();

	public Book() {
		chapters = new ArrayList<Chapter>();
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public Chapter getChapter(int num) {
		return chapters.get(num - 1);
	}
	// Chapter
	// Paragraph
	// Sentence
	// Word

	// Operations
	// num: words, sentences, paragraphs, chapters, etc
	// TextIterator
	// get: Chapter, Sentence, Word, etc
	// Labels
	// IM, dialogue, hero's dialogue, heroine dialogue, physical beat, scenes, etc
	public static Book getBook(String path) throws IOException {
		return getBookFromFilesystem(path, "No_Title", "No_Author");
	}
	
	public static Book getBook(String path, String title, String author) throws IOException {
		Book book = null;
		try {
			book = getBookFromFilesystem(path, title, author);
		} catch (IOException ioe) {
			// If getting the book from the filesystem fails, try to pull it from the classloader
			book = getBookFromClassloader(path, title, author);
		}
		return book;
	}
	
	
	public static Book getBookFromFilesystem(String filepath, String title, String author) throws IOException {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		parseChaptersAndParagraphs(book, filepath);
		parseSentences(book);
		return book;
	}
	
	protected static Book getBookFromClassloader(String resourcePath, String title, String author) throws IOException {
		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		InputStream is = BookManager.class.getClassLoader().getResourceAsStream(resourcePath);
		InputStreamReader isr = new InputStreamReader(is);
		try {
			parseChaptersAndParagraphs(book, isr);
			parseSentences(book);
		} finally {
			isr.close();
		}
		return book;
	}
	
	protected static void parseChaptersAndParagraphs(Book book, Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		try {
			List<Chapter> chapters = book.getChapters();
			//System.out.println(chapters.size());
			int chapterNum = 1;
			int paragraphNum = 1;
			Chapter currentChapter = null;
			Paragraph currentParagraph = null;

			String line;
			// Read each line of the book. Each line is a paragraph
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();				
				// Check if this is a chapter line
				if ((line.startsWith("chapter") || line.startsWith("epilogue") || line.startsWith("prologue"))  
						&& line.length() <= 30) {
					currentChapter = new Chapter(chapterNum);
					currentChapter.setParent(book);
					chapters.add(currentChapter);
					chapterNum++;
				} else if (line.equals("")) {
					// Check if this is an empty line
					// NOOP
				} else {
					// This is a paragraph line
					currentParagraph = new Paragraph(line, paragraphNum);
					currentParagraph.setParent(currentChapter);
					currentChapter.add(currentParagraph);
					paragraphNum++;
				}
			}
		} finally {
			br.close();
		}
	}
	
	protected static void parseChaptersAndParagraphs(Book book, String textFile) throws IOException {
		FileReader fr = new FileReader(textFile);
		try {
			parseChaptersAndParagraphs(book, fr);
		} finally {
			fr.close();
		}
	}
	
	protected static void parseSentences(Book book) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit");	
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		int sentenceNum = 1;
		List<Chapter> chapters = book.getChapters();
		// Parse chapters
		for (Chapter chapter : chapters) {
			List<Paragraph> paragraphs = chapter.getParagraphs();
			// Parse paragraphs
			for (Paragraph p : paragraphs) {
				Annotation doc = new Annotation(p.getText());
				pipeline.annotate(doc);
				
				List<CoreMap> sentences = doc.get(SentencesAnnotation.class);
				// Parse sentences
				for(CoreMap sentence: sentences) {
					Sentence s = new Sentence(sentence.toString());
					s.setParent(p);
					s.setNum(sentenceNum);
					sentenceNum++;
					p.add(s);
					
					// Parse tokens in each sentence
					for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
						// This is the text of the token
						String word = token.get(TextAnnotation.class);
						s.add(word);
					}
				}
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
