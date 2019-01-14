package spellchecker;

import static sbcc.Core.*;
import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;

public class Main {

	public static void main(String[] args) throws Exception {
		// BasicDictionary bd = new BasicDictionary();
		BasicSpellChecker bsc = new BasicSpellChecker();
		//
		// try {
		// bd.importFile("dict_14.pre");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// //
		// bd.dictionaryTree.printTreeInOrder(bd.dictionaryTree.getRoot());
		//
		// println("-------------------------------");
		//
		// String[] searchResult = bd.find("Cotto");
		//
		// if (searchResult != null) {
		// println("The word before in the list is: " + searchResult[0]);
		// println("The word after in the list is: " + searchResult[1]);
		// } else {
		// println("I found it in the list");
		// }

		// String test = "Astronomers";
		// int compareValue = test.compareToIgnoreCase("Astronomers");
		// println("The comparison value is: " + compareValue);

		// println("----------------------------");
		// bd.tempTree.printTreeInOrder(bd.tempTree.getRoot());
		//
		// bd.save("test.pre");
		//
		// println(bd.getCount());
		// bd.add("mit");
		//
		// bd.dictionaryTree.saveInOrder();
		// bd.dictionaryTree.printTreeInOrder(bd.dictionaryTree.getRoot());

		bsc.importDictionary("dict_14.pre");
		println(bsc.myDict.getCount());
		bsc.myDict.dictionaryTree.printTreeInOrder(bsc.myDict.dictionaryTree.getRoot());
		bsc.loadDocument("small_document.txt");
		println(bsc.importText);
		bsc.spellCheck(true);
		bsc.replaceText(0, 11, "TEST");
		println(bsc.importText);
	}
}
