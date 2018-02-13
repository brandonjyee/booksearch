package booksearch.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booksearch.book.Book;
import booksearch.book.Chapter;
import booksearch.book.Paragraph;
import booksearch.book.Sentence;

public class Queries {
	public static void main(String[] args) {
		// Book, query
		// Avg: Sentences per paragraph, length of sentences, words per paragraph
		// Num chapters, num sentences, num paragraphs, 
		// Generate word chart => identify key words, remove stop words
		// Generate word frequency
		// Create custom annotator for dialogue, etc?
		// use statistical stuff?
		// pick out dialogue tags
		// common word pairings (adjectives, adverbs)
		// Lucene for stemming (lemmatisation?); Wordnet for thesaurus; stop-word list
		
		// Search for words over book => identify paragraphs, sentences, chapters, scenes?
	}
	
	// Consider caching results
	public static long computeNumSentences(Book book) {
		long count = 0;
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			List<Paragraph> paragraphs = chapter.getParagraphs();
			for (Paragraph p : paragraphs) {
				List<Sentence> sentences = p.getSentences();
				count += sentences.size();
			}
		}
		return count;
	}
	
	public static long computeNumParagraphs(Book book) {
		long count = 0;
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			List<Paragraph> paragraphs = chapter.getParagraphs();
			count += paragraphs.size();
		}
		return count;
	}
	
	public static double avgSentencesPerParagraph(Book book) {
		long numParagraphs = computeNumParagraphs(book);
		long numSentences = computeNumSentences(book);
		return numSentences / (double) numParagraphs;
	}
	
	/**
	 * 
	 * @param book
	 * @return Map of <adverb, count> entries
	 */
	public static Map<String, Integer> getAdverbs(Book book) {
		Map<String, Integer> adverbs = new HashMap<String, Integer>();
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			List<Paragraph> paragraphs = chapter.getParagraphs();
			for (Paragraph p : paragraphs) {
				List<Sentence> sentences = p.getSentences();
				for (Sentence sentence : sentences) {
					List<String> tokens = sentence.getTokens();
					for (String token : tokens) {
						// Make token lowercase
						token = token.toLowerCase();
						// This is the test for whether a token is an adverb
						// **Note there are numerous adverbs that don't end in -ly like 'quite'
						if (token.endsWith("ly")) {
							Integer count = adverbs.get(token);
							if (count == null) {
								adverbs.put(token, Integer.valueOf(1));
							} else {
								adverbs.put(token, Integer.valueOf(count + 1));
							}
						}
					}
				}
			}
		}
		return adverbs;
	}
	
	/**
	 * 
	 * @param book
	 * @return Map of <word, count> entries
	 */
	public static Map<String, Long> getWordFrequencies(Book book) {
		Map<String, Long> words = new HashMap<String, Long>();
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			List<Paragraph> paragraphs = chapter.getParagraphs();
			for (Paragraph p : paragraphs) {
				List<Sentence> sentences = p.getSentences();
				for (Sentence sentence : sentences) {
					List<String> tokens = sentence.getTokens();
					for (String token : tokens) {
						// Make token lowercase
						token = token.toLowerCase();
						// This is the test for whether a token is an adverb
						Long count = words.get(token);
						if (count == null) {
							words.put(token, Long.valueOf(1));
						} else {
							words.put(token, Long.valueOf(count + 1));
						}
					}
				}
			}
		}
		return words;
	}
	
	// Proportion Queries
	
	
	
	// List of chapter beginnings
	// List of chapter endings
	// N-gram span for specified word
}
