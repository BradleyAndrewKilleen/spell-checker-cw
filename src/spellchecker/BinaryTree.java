package spellchecker;

import static sbcc.Core.*;

import java.util.*;

public class BinaryTree {
	BinaryTreeNode root;
	// This is used to import the sorted list passed from BD. Inserting this this
	// will result in a very unbalanced tree. Use balance function on this to return
	// preorder.
	List<String> listPreOrder = new ArrayList();
	List<String> listInOrder = new ArrayList();


	public BinaryTree() {

	}


	public BinaryTree(BinaryTreeNode root) {
		this.root = root;
	}


	public BinaryTree(List<String> listPreOrder) {
		for (String x : listPreOrder) {
			insert(x);
		}
	}


	public BinaryTreeNode balanceTree(List<String> listInOrder, int start, int end) {
		if (start > end)
			return null;

		int mid = (start + end) / 2;
		BinaryTreeNode myNode = new BinaryTreeNode(listInOrder.get(mid));

		myNode.left = balanceTree(listInOrder, start, mid - 1);
		myNode.right = balanceTree(listInOrder, mid + 1, end);

		return myNode;
	}


	public void insert(String text) {
		String cleanText = text.toLowerCase().trim();
		BinaryTreeNode newNode = new BinaryTreeNode(cleanText);
		if (root == null) {
			root = newNode;
		} else {
			addNode(newNode, root);
		}

	}


	public void savePreOrder() {
		listPreOrder.clear();
		if (root == null) {
			println("This tree is empty");
		} else {
			saveTreePreOrder(root);
		}
	}


	private void saveTreePreOrder(BinaryTreeNode cursor) {
		if (cursor == null)
			return;

		listPreOrder.add(cursor.value);
		saveTreePreOrder(cursor.left);
		saveTreePreOrder(cursor.right);
	}


	public String getStringPreOrder() {
		StringBuilder sb = new StringBuilder();

		for (String x : listPreOrder) {
			sb.append(x);
			sb.append(System.getProperty("line.separator"));
		}

		return sb.toString();
	}


	public void saveInOrder() {
		listInOrder.clear();
		if (root == null) {
			println("This tree is empty");
		} else {
			saveTreeInOrder(root);
		}
	}


	private void saveTreeInOrder(BinaryTreeNode cursor) {
		if (cursor == null)
			return;

		saveTreeInOrder(cursor.left);
		listInOrder.add(cursor.value);
		saveTreeInOrder(cursor.right);
	}


	public void printTreeInOrder(BinaryTreeNode cursor) {
		if (cursor == null) {
			return;
		}

		printTreeInOrder(cursor.left);
		println(cursor.value);
		printTreeInOrder(cursor.right);
	}


	// This returns null if it is NOT in the tree, and true if it is!
	public boolean search(BinaryTreeNode input, BinaryTreeNode cursor) {

		if (cursor == null) {
			return false;
		} else {
			int compareValue = compareNode(input, cursor);

			if (compareValue == 0) {
				return true;
			} else if (compareValue == -1) {
				return search(input, cursor.left);
			} else if (compareValue == 1) {
				return search(input, cursor.right);
			} else {
				return false;
			}
		}
	}


	private int compareNode(BinaryTreeNode input, BinaryTreeNode current) {

		if (input.value.compareToIgnoreCase(current.value) < 0) {
			return -1;
		}

		else if (input.value.compareToIgnoreCase(current.value) > 0) {
			return 1;
		}

		else {
			return 0;
		}

	}


	private void addNode(BinaryTreeNode newNode, BinaryTreeNode cursor) {
		try {
			int compareValue = compareNode(newNode, cursor);

			if (compareValue == -1) {
				if (cursor.left == null) {
					cursor.left = newNode;
				} else {
					addNode(newNode, cursor.left);
				}
			}

			else if (compareValue == 1) {
				if (cursor.right == null) {
					cursor.right = newNode;
				} else {
					addNode(newNode, cursor.right);
				}
			}

			// Circumstance where compare yeilds zero which means the word is found
			else {
				println("This node already exists in the tree");
			}
		} catch (NullPointerException n) {
			println("You can't pass this a null node dummy. How did you even do that?");
		}
	}


	public BinaryTreeNode getRoot() {
		return root;
	}
}
