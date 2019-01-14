package spellchecker;

import static sbcc.Core.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class BasicSpellChecker implements SpellChecker {
	BasicDictionary myDict = new BasicDictionary();
	String importText;
	String exportText;
	List<String> regexFinds = new ArrayList();
	List<String> savedDoc = new ArrayList();
	int scCursor = 0;
	int scWordStartIndex = 0;
	int scWordEndIndex = 0;


	@Override
	public void importDictionary(String filename) {
		myDict.importFile(filename);
	}


	@Override
	// The purpose of this is to be fast as it is loading from a preordered list.
	// This is the difference between this and importDictionary
	public void loadDictionary(String filename) throws Exception {
		myDict.load(filename);
	}


	@Override
	public void saveDictionary(String filename) throws Exception {
		myDict.save(filename);
	}


	@Override
	public void loadDocument(String filename) throws Exception {
		importText = readFile(filename);
	}


	@Override
	public void saveDocument(String filename) throws Exception {
		try {
			File file = new File(filename);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(importText);
			out.close();
		}

		catch (IOException e) {
			println("Could not save file given this path");
		}
	}


	@Override
	public String getText() {
		return importText;
	}


	@Override
	public String[] spellCheck(boolean continueFromPrevious) {
		if (continueFromPrevious == false) {
			scCursor = 0;
		}

		if (myDict == null || importText == null) {
			println("There is no dictionary or file");
			return null;
		}

		String expr = "\\b[\\w']+\\b";
		Pattern p = Pattern.compile(expr);
		Matcher m = p.matcher(importText);

		while (m.find(scCursor)) {
			// println("The first find of the selected text is: " + m.group());
			String[] scFindData = myDict.find(m.group().trim());
			String[] scData = new String[4];
			// println("The word before this in the list is: " + scData[0]);
			scWordStartIndex = m.start();
			scWordEndIndex = m.end();
			// println("The start index of the word is: " + scWordStartIndex);
			// println("The end index of the word is: " + scWordEndIndex);

			if (scFindData != null) {
				scData[0] = m.group();
				scData[1] = Integer.toString(m.start());
				scData[2] = scFindData[0];
				scData[3] = scFindData[1];
				scCursor = scWordEndIndex;
				return scData;
			}
			scCursor = scWordEndIndex;
		}

		return null;
	}


	@Override
	public void addWordToDictionary(String word) {
		myDict.add(word);

	}


	@Override
	public void replaceText(int startIndex, int endIndex, String replacementText) {
		String beforeReplace = importText.substring(0, startIndex);
		String afterReplace = importText.substring(endIndex, importText.length());

		StringBuilder sb = new StringBuilder();
		sb.append(beforeReplace);
		sb.append(replacementText);
		sb.append(afterReplace);

		importText = sb.toString();

		scCursor = scCursor - (endIndex - startIndex) + replacementText.length();
	}


	public void printRegexFinds() {
		for (String x : regexFinds) {
			println(x);
		}
	}

}
