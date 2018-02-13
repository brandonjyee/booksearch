package booksearch.book;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
	private int num;
	private Chapter parent;
	private String text;
	private List<Sentence> sentences;
	
	public Paragraph(String text, int num) {
		this.num = num;
		this.text = text;
		sentences = new ArrayList<Sentence>();
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Chapter getParent() {
		return parent;
	}

	public void setParent(Chapter parent) {
		this.parent = parent;
	}
	
	public void print() {
		System.out.println(text);
	}
	
	public String getText() {
		return text;
	}
	
	public Sentence getSentence(int num) {
		return sentences.get(num - 1);
	}
	
	public void add(Sentence s) {
		sentences.add(s);
	}
	
	public List<Sentence> getSentences() {
		return sentences;
	}
	
//	private void parseSentences(String s) {
//		// period, exclamation, question mark, ellipses.
//		// .�
//		StringBuilder sb; 
//		// CharacterIterator
//	}
	
	
}
