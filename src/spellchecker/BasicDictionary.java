package spellchecker;

import static sbcc.Core.*;

import java.io.*;
import java.util.*;

public class BasicDictionary implements Dictionary {
	BinaryTree dictionaryTree = new BinaryTree();
	BinaryTree tempTree = new BinaryTree();
	List<String> importedFile;


	@Override
	public void importFile(String filename) {

		try {
			importedFile = readFileAsLines(filename);
			List<String> importedFileClean = new ArrayList<String>();

			for (String x : importedFile) {
				String myString = x.toLowerCase().trim();
				importedFileClean.add(myString);
			}

			dictionaryTree.root = dictionaryTree.balanceTree(importedFileClean, 0, importedFileClean.size() - 1);

		} catch (IOException e) {
			println("Could not import file");
		}
	}


	@Override
	// Loads from previously saved file in preorder using read file as lines. No
	// need for balancing so this will be a simple insert of each element in order.
	// Easy.
	public void load(String filename) {
		try {
			dictionaryTree.root = null;
			List<String> loadList = new ArrayList(readFileAsLines(filename));

			for (int x = 0; x < loadList.size(); x++) {
				dictionaryTree.insert(loadList.get(x));
			}

			// dictionaryTree.balanceTree(dictionaryTree.getRoot());
		}

		catch (IOException e) {
			println("Could not load from this path.");
		}
	}


	@Override
	// Saves file using btree.getSave() which builds a string from AL with line
	// separators
	public void save(String filename) {
		try {
			dictionaryTree.savePreOrder();

			File file = new File(filename);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(dictionaryTree.getStringPreOrder());
			out.close();
		}

		catch (IOException e) {
			println("Could not save file given this path");
		}
	}


	@Override
	// Searches for given word. If found, returns null, if not this inserts the new
	// word in a temp tree, saves the tree inorder, and uses the index of the word
	// to return the words before and after.
	public String[] find(String word) {
		String cleanText = word.toLowerCase().trim();
		BinaryTreeNode searchNode = new BinaryTreeNode(cleanText);
		boolean found = dictionaryTree.search(searchNode, dictionaryTree.getRoot());

		if (found == true) {
			return null;
		} else {
			String[] searchData = new String[2];
			dictionaryTree.savePreOrder();

			BinaryTree tempTree = new BinaryTree(dictionaryTree.listPreOrder);
			tempTree.insert(word);
			tempTree.saveInOrder();
			// tempTree.printTreeInOrder(tempTree.getRoot());

			int indexOfWord = tempTree.listInOrder.indexOf(cleanText);
			// println(tempTree.listInOrder.indexOf(cleanText));

			if (indexOfWord - 1 >= 0) {
				searchData[0] = tempTree.listInOrder.get(indexOfWord - 1);
			} else {
				searchData[0] = "";
			}

			if (indexOfWord < tempTree.listInOrder.size() - 1) {
				searchData[1] = tempTree.listInOrder.get(indexOfWord + 1);
			} else {
				searchData[1] = "";
			}

			return searchData;
		}
	}


	@Override
	// Searches for word and inserts if it is not found.
	public void add(String word) {
		BinaryTreeNode newNode = new BinaryTreeNode(word);

		if (!dictionaryTree.search(newNode, dictionaryTree.getRoot())) {
			dictionaryTree.insert(word);
		} else {
			println("Word already exists in dictionary");
		}

	}


	@Override
	public BinaryTreeNode getRoot() {
		return dictionaryTree.getRoot();
	}


	@Override
	public int getCount() {
		dictionaryTree.savePreOrder();
		int count = dictionaryTree.listPreOrder.size();
		return count;
	}

}
