package booksearch.book;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private String title;
	private int num;
	private Book parent;
	private List<Paragraph> paragraphs; // Consider line breaks. Chapter units?
	
	public Chapter(int num) {
		this.num = num;
		paragraphs = new ArrayList<Paragraph>();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Book getParent() {
		return parent;
	}

	public void setParent(Book parent) {
		this.parent = parent;
	}
	
	public void add(Paragraph p) {
		paragraphs.add(p);
	}
	
	public Paragraph getParagraph(int num) {
		return paragraphs.get(num - 1);
	}
	
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}
}
