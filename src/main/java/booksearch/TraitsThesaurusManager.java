package booksearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import booksearch.traits_thesaurus.NegativeTraitsSections;
import booksearch.traits_thesaurus.PositiveTraitsSections;

public class TraitsThesaurusManager {
	public static void main(String[] args) throws IOException {
		//readFile(file);
		//File file = new File("./databanks/emotion_thesaurus/Agitation.txt");
	}
	
	/*TRAIT,
	DEFINITION,
	CATEGORIES,
	SIMILAR_TRAITS,	// Attributes
	POSSIBLE_CAUSES,
	ASSOC_BEHAVIORS,
	ASSOC_THOUGHTS,
	ASSOC_EMOTIONS,
	POS_ASPECTS,
	NEG_ASPECTS,
	EXAMPLE,		// Literature, Film, TV
	TRAITS_IN_CHARS_FOR_CONFLICT,
	CHALLENGING_SCENARIOS
	*/
	public static Map<String, List<String>> readPosTraitFile(File posTraitFile) throws IOException {
		FileReader fr = new FileReader(posTraitFile);
		BufferedReader br = new BufferedReader(fr);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String emotionName = posTraitFile.getName().replace(".txt", "");
		map.put(PositiveTraitsSections.TRAIT.toString(), Arrays.asList(emotionName));
		try {
			String line;
			List<String> lines = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				} else if (line.startsWith("DEFINITION:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.DEFINITION.toString(), lines);
				} else if (line.startsWith("CATEGORIES:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.CATEGORIES.toString(), lines);
				} else if (line.startsWith("SIMILAR ATTRIBUTES:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.SIMILAR_TRAITS.toString(), lines);
				} else if (line.startsWith("POSSIBLE CAUSES:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.POSSIBLE_CAUSES.toString(), lines);
				} else if (line.startsWith("ASSOCIATED BEHAVIORS:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.ASSOC_BEHAVIORS.toString(), lines);
				} else if (line.startsWith("ASSOCIATED THOUGHTS:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.ASSOC_THOUGHTS.toString(), lines);
				} else if (line.startsWith("ASSOCIATED EMOTIONS:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.ASSOC_EMOTIONS.toString(), lines);
				} else if (line.startsWith("POSITIVE ASPECTS:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.POS_ASPECTS.toString(), lines);
				} else if (line.startsWith("NEGATIVE ASPECTS:")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.NEG_ASPECTS.toString(), lines);
				} else if (line.startsWith("EXAMPLE")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.EXAMPLE.toString(), lines);
				} else if (line.startsWith("TRAITS IN")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.TRAITS_IN_CHARS_FOR_CONFLICT.toString(), lines);
				} else if (line.startsWith("CHALLENGING")) {
					lines = new ArrayList<String>();
					map.put(PositiveTraitsSections.CHALLENGING_SCENARIOS.toString(), lines);
				} 
				lines.add(line);
			}
		} finally {
			br.close();
			fr.close();
		}
		return map;
	}
	
	/*
	TRAIT,
	DEFINITION,
	SIMILAR_TRAITS,		// Flaws
	POSSIBLE_CAUSES,
	ASSOC_BEHAVIORS_ATTITUDES,
	ASSOC_THOUGHTS,
	ASSOC_EMOTIONS,
	POS_ASPECTS,
	NEG_ASPECTS,
	EXAMPLE, 			// Film, TV, or Literature
	OVERCOMING_MAJOR_FLAW,
	TRAITS_IN_CHARS_FOR_CONFLICT
	 */
	public static Map<String, List<String>> readNegTraitFile(File negTraitFile) throws IOException {
		FileReader fr = new FileReader(negTraitFile);
		BufferedReader br = new BufferedReader(fr);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String emotionName = negTraitFile.getName().replace(".txt", "");
		map.put(NegativeTraitsSections.TRAIT.toString(), Arrays.asList(emotionName));
		try {
			String line;
			List<String> lines = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				} else if (line.startsWith("DEFINITION:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.DEFINITION.toString(), lines);
				} else if (line.startsWith("SIMILAR ATTRIBUTES:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.SIMILAR_TRAITS.toString(), lines);
				} else if (line.startsWith("POSSIBLE CAUSES:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.POSSIBLE_CAUSES.toString(), lines);
				} else if (line.startsWith("ASSOCIATED BEHAVIORS")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.ASSOC_BEHAVIORS_ATTITUDES.toString(), lines);
				} else if (line.startsWith("ASSOCIATED THOUGHTS:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.ASSOC_THOUGHTS.toString(), lines);
				} else if (line.startsWith("ASSOCIATED EMOTIONS:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.ASSOC_EMOTIONS.toString(), lines);
				} else if (line.startsWith("POSITIVE ASPECTS:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.POS_ASPECTS.toString(), lines);
				} else if (line.startsWith("NEGATIVE ASPECTS:")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.NEG_ASPECTS.toString(), lines);
				} else if (line.startsWith("EXAMPLE")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.EXAMPLE.toString(), lines);
				} else if (line.startsWith("OVERCOMING")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.OVERCOMING_MAJOR_FLAW.toString(), lines);
				} else if (line.startsWith("TRAITS IN")) {
					lines = new ArrayList<String>();
					map.put(NegativeTraitsSections.TRAITS_IN_CHARS_FOR_CONFLICT.toString(), lines);
				} 
				lines.add(line);
			}
		} finally {
			br.close();
			fr.close();
		}
		return map;
	}
}
