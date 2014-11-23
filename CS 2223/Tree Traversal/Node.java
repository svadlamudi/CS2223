// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

import java.util.ArrayList;

public class Node {
	
	public String root;
	public Node left;
	public Node right;
	
	/**
	 * 
	 * Primary public contructor
	 * 
	 * @param root
	 * @param left
	 * @param right
	 */
	public Node(String root, Node left, Node right) {
		this.root = root;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * 
	 * Given a binary tree return a list of strings representing the postorder traversal.
	 * 
	 * @param treeRoot
	 * @return
	 */
	public static ArrayList<String> postOrder(Node treeRoot) {
		ArrayList<String> traversal = new ArrayList<String>(0);
		
		if (treeRoot == null) {
			return new ArrayList<String>(0);
		}
		
		traversal.addAll(postOrder(treeRoot.left));
		traversal.addAll(postOrder(treeRoot.right));
		traversal.add(treeRoot.root);
		
		return traversal;
	}
	
	/**
	 * 
	 * Given a binary tree return a list of strings representing the inorder traversal.
	 *
	 * @param treeRoot
	 * @return
	 */
	public static ArrayList<String> inOrder(Node treeRoot) {
		ArrayList<String> traversal = new ArrayList<String>(0);
		
		if (treeRoot == null) {
			return new ArrayList<String>(0);
		}
		
		traversal.addAll(inOrder(treeRoot.left));
		traversal.add(treeRoot.root);
		traversal.addAll(inOrder(treeRoot.right));
		
		return traversal;
	}
	
	/**
	 * 
	 * Given a binary tree print the preorder traversal.
	 *
	 * @param treeRoot
	 */
	public static void printPreOrder(Node treeRoot) {
		if (treeRoot == null) {
			return;
		}
		System.out.print(treeRoot.root + ", ");
		printPreOrder(treeRoot.left);
		printPreOrder(treeRoot.right);
	}
	
	/**
	 * 
	 * Given a binary tree print the postorder traversal.
	 *
	 * @param treeRoot
	 */
	public static void printPostOrder(Node treeRoot) {
		
		if (treeRoot == null) {
			return;
		}
		
		printPostOrder(treeRoot.left);
		printPostOrder(treeRoot.right);
		System.out.print(treeRoot.root + " ");
	}
	
	/**
	 * 
	 * Given a binary tree print the inorder traversal.
	 *
	 * @param treeRoot
	 */
	public static void printInOrder(Node treeRoot) {
		
		if (treeRoot == null) {
			return;
		}
		
		printInOrder(treeRoot.left);
		System.out.print(treeRoot.root + " ");
		printInOrder(treeRoot.right);
	}
}
