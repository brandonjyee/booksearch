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

import booksearch.emotion_thesaurus.Sections;

public class EmotionThesaurusManager {
	public static void main(String[] args) throws IOException {
		//readFile(file);
		//File file = new File("./databanks/emotion_thesaurus/Agitation.txt");
	}
	
	public static Map<String, List<String>> readFile(File emotionFile) throws IOException {
		// emotion; definition; physical signals; internal sensations; mental responses; acute or long-term cues;
		// related; suppressed cues; writer tip
		FileReader fr = new FileReader(emotionFile);
		BufferedReader br = new BufferedReader(fr);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String emotionName = emotionFile.getName().replace(".txt", "");
		map.put(Sections.EMOTION.toString(), Arrays.asList(emotionName));
		try {
			String line;
			List<String> lines = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				} else if (line.startsWith("DEFINITION:")) {
					lines = new ArrayList<String>();
					map.put(Sections.DEFINITION.toString(), lines);
				} else if (line.startsWith("POLARITY:")) {
					lines = new ArrayList<String>();
					map.put(Sections.POLARITY.toString(), lines);
				} else if (line.startsWith("PHYSICAL SIGNALS:")) {
					lines = new ArrayList<String>();
					map.put(Sections.PHYSICAL_SIGNALS.toString(), lines);
				} else if (line.startsWith("INTERNAL SENSATIONS:")) {
					lines = new ArrayList<String>();
					map.put(Sections.INTERNAL_SENSATIONS.toString(), lines);
				} else if (line.startsWith("MENTAL RESPONSES:")) {
					lines = new ArrayList<String>();
					map.put(Sections.MENTAL_RESPONSES.toString(), lines);
				} else if (line.startsWith("CUES OF ACUTE")) {
					lines = new ArrayList<String>();
					map.put(Sections.ACUTE_OR_LONG_TERM_CUES.toString(), lines);
				} else if (line.startsWith("MAY")) {
					lines = new ArrayList<String>();
					map.put(Sections.RELATED_EMOTIONS.toString(), lines);
				} else if (line.startsWith("CUES OF")) {
					lines = new ArrayList<String>();
					map.put(Sections.SUPPRESSED_CUES.toString(), lines);
				} else if (line.startsWith("WRITER")) {
					lines = new ArrayList<String>();
					map.put(Sections.WRITER_TIP.toString(), lines);
				}
				lines.add(line);
			}
//			List<String> physSignals = map.get("physical_signals");
//			for (String physSignal : physSignals) {
//				System.out.println(physSignal);
//			}
//			System.out.println();
//			System.out.println("MENTAL RESPONSES:");
//			List<String> mentalResps = map.get("mental_responses");
//			for (String mentalResp : mentalResps) {
//				System.out.println(mentalResp);
//			}
		} finally {
			br.close();
			fr.close();
		}
		return map;
	}
}
