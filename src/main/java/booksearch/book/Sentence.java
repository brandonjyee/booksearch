package booksearch.book;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	private int num;
	private Paragraph parent;
	private String text;
	private List<String> tokens = new ArrayList<String>();
	
	public Sentence(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Paragraph getParent() {
		return parent;
	}
	public void setParent(Paragraph parent) {
		this.parent = parent;
	}
	
	public void print() {
		System.out.println(text);
	}
	
	public void add(String token) {
		tokens.add(token);
	}
	
	public List<String> getTokens() {
		return tokens;
	}

}
