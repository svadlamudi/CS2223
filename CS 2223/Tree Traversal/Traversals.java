// Name: Sai Kiran Vadlamudi  Username: svadlamudi  Section: B01
// Name: Marilda Bozdo        Username: mbozdo      Section: B01

import java.io.*;
import java.util.regex.*;
import java.util.ArrayList;

public class Traversals {

/* Properties */

	/**
	 * Regex Pattern to find all alphabetic characters in string separated by a space
	 */
	private static final Pattern charFind = Pattern.compile("[A-Za-z]+");

/* Main Method */

	/**
	 * 
	 * Main loop, reads given lines, one or two, and performs traversals.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			// Read in the two lines and mark flags on their success
			Pair<Boolean, ArrayList<String>> lineOne = findAllLetters(in.readLine());
			Pair<Boolean, ArrayList<String>> lineTwo = findAllLetters(in.readLine());
			
			if (lineOne.valueTwo.size() != lineTwo.valueTwo.size()) {
				throw new InvalidTraversal("These traversals are of different lengths.");
			}
			
			// Decorate output
			System.out.println("\n|----------------------|");
			System.out.println("|        Results       |");
			System.out.println("|----------------------|\n");
			
			// Check whether one or two lines were successfully read
			if (lineTwo.valueOne) {				
				// Try to find postorder given preorder and inorder if and only if they were valid traversals.
				try {
					Node binaryTree = pre_in_to_post(lineOne.valueTwo, lineTwo.valueTwo);
					Node.printPostOrder(binaryTree);
					System.out.println();
				} catch (InvalidTraversal it) {
					System.out.println(it.getMessage());
				}
				
				// Try to find inorder given preorder and postorder if and only if they were valid traversals.
				try {
					//Node binaryTreeTwo = pre_post_to_in(lineOne.valueTwo, Node.postOrder(binaryTree));
					Node binaryTreeTwo = pre_post_to_in(lineOne.valueTwo, lineTwo.valueTwo);
					Node.printInOrder(binaryTreeTwo);
					System.out.println();
				} catch (InvalidTraversal it) {
					System.out.println(it.getMessage());
				}
			} else if(lineOne.valueOne) {
				// Try to find the postorder given a valid preorder traversal of a binary search tree.
				try {
					Node binarySearchTree = search_pre_to_post(lineOne.valueTwo);
					Node.printPostOrder(binarySearchTree);
					System.out.println();
				} catch (InvalidTraversal it) {
					System.out.println(it.getMessage());
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Invalid/Empty Lines");
		} catch (InvalidTraversal it) {
			System.out.println(it.getMessage());
		}		
	}

/* Traversal Methods */
	
	/**
	 * 
	 * Return a binary tree given a valid preorder traversal of a binary search tree.
	 *
	 * @param preOrder
	 * @return
	 * @throws InvalidTraversal
	 */
	public static Node search_pre_to_post(ArrayList<String> preOrder) throws InvalidTraversal {
		
		// Instantiate and initialize variables.
		ArrayList<String> leftPreOrder = null;
		ArrayList<String> rightPreOrder = null;
		int splitIndex = 0;
		
		Node treeNode = null;
		
		// End Recursion if no more nodes present.
		if (preOrder.size() == 0) {
			return treeNode;
		} else {
			// Retrieve Root node.
			treeNode = new Node(preOrder.remove(0), null, null);
			
			// Find the right child of the root node.
			for(int i = 0; i < preOrder.size(); i++) {
				if (preOrder.get(i).compareTo(treeNode.root) >= 1) {
					splitIndex = i;
					break;
				}
			}
			
			// Split everything smaller right child into left subtree and remaining into right subtree
			leftPreOrder = new ArrayList<String>(preOrder.subList(0, splitIndex));
			rightPreOrder = new ArrayList<String>(preOrder.subList(splitIndex, preOrder.size()));
			
			// Check for any violations in the given traversal. If any node in the right tree is smaller than right
			// then given traversal cannot be of that of any binary search tree.
			for (int i = 0; i < rightPreOrder.size(); i++) {
				if(rightPreOrder.get(i).compareTo(treeNode.root) <= -1) {
					throw new InvalidTraversal("This cannot be the Preorder traversal of any Binary Search Tree.");
				}
			}
			
			// Assemble the left and right binary search trees.
			treeNode.left = search_pre_to_post(leftPreOrder);
			treeNode.right = search_pre_to_post(rightPreOrder);
		}
		
		// Return the assembled binary search tree
		return treeNode;
	}
	
	/**
	 * 
	 * Return a binary tree given valid preorder and post order traversals.
	 *
	 * @param preOrder
	 * @param inOrder
	 * @return
	 * @throws InvalidTraversal
	 */
	public static Node pre_in_to_post(ArrayList<String> preOrder, ArrayList<String> inOrder) throws InvalidTraversal {
        
		// Instantiate and Initialize variables.
		Node treeNode = null;
        ArrayList<String> leftPreOrder;
        ArrayList<String> rightPreOrder;
        ArrayList<String> leftInOrder;
        ArrayList<String> rightInOrder;
        int inOrderPos;
        int preOrderPos;
        
        // Check that given traversals are not empty.
        if ((preOrder.size() != 0) && (inOrder.size() != 0))
        {
        	// Retrieve root node. First node in a preorder traversal.
            treeNode = new Node(preOrder.get(0), null, null);
            
            // Find root node in inOrder array and split all nodes before into left and all other into right.
            inOrderPos = inOrder.indexOf(preOrder.get(0));
            leftInOrder = new ArrayList<String>(inOrder.subList(0, inOrderPos));
            rightInOrder = new ArrayList<String>(inOrder.subList(inOrderPos + 1, inOrder.size()));
            
            // Check for any violations of the definition of a full tree. Cases where one child tree is empty and the other is not.
            if ((leftInOrder.size() == 0 && rightInOrder.size() > 0) || (leftInOrder.size() > 0 && rightInOrder.size() == 0)) {
            	throw new InvalidTraversal("No full binary tree has these as Preorder and Inorder traversals.");
            }
            
            // Split the preOrder array into correspondence with the inorder array.
            preOrderPos = leftInOrder.size();
            leftPreOrder = new ArrayList<String>(preOrder.subList(1, preOrderPos + 1));
            rightPreOrder = new ArrayList<String>(preOrder.subList(preOrderPos + 1, preOrder.size()));
            
            // Assemble left and right binary trees.
            treeNode.left = pre_in_to_post(leftPreOrder, leftInOrder);
            treeNode.right = pre_in_to_post(rightPreOrder, rightInOrder);
        }
        
        // Return assembled binary tree.
        return treeNode;
    }
	
	/**
	 * 
	 * Return a binary tree given valid preorder and postorder traversals.
	 *
	 * @param preOrder
	 * @param postOrder
	 * @return
	 * @throws InvalidTraversal
	 */
	public static Node pre_post_to_in(ArrayList<String> preOrder, ArrayList<String> postOrder) throws InvalidTraversal {
		
		// Instantiate and initialize variables.
		int splitIndex;
		Node treeNode = null;
		ArrayList<String> leftPostOrder;
		ArrayList<String> rightPostOrder;
		
		// Return node if it has no children.
		if (preOrder.size() == 1) {
			return treeNode = new Node(preOrder.remove(0), null, null);
		} else {
			// Retrieve root node.
			treeNode = new Node(preOrder.remove(0), null, null);
			postOrder.remove(treeNode.root);
			
			// Get index of first node in preorder in postorder to find the splitting point.
			splitIndex = postOrder.indexOf(preOrder.get(0));
			
			if (splitIndex == -1) {
				return treeNode;
			}
			
			// Move all nodes left of first node in preorder into left postorder and everything else into right postorder.
			leftPostOrder = new ArrayList<String>(postOrder.subList(0, splitIndex+1));
			rightPostOrder = new ArrayList<String>(postOrder.subList(splitIndex+1, postOrder.size()));
			
			// Check for violations of the definition of a full tree. Cases where one is empty and the other is not. 
			if ((leftPostOrder.size() == 0 && rightPostOrder.size() > 0) || (leftPostOrder.size() > 0 && rightPostOrder.size() == 0)) {
            	throw new InvalidTraversal("No full binary tree has these as Preorder and Postorder traversals.");
            }
			
			// Assemble the left and right binary trees.
			treeNode.left = pre_post_to_in(preOrder, leftPostOrder);
			treeNode.right = pre_post_to_in(preOrder, rightPostOrder);
		}
		
		// Return assembled binary tree.
		return treeNode;
	}
	
/* Helper Methods */
	
	/**
	 * 
	 * Assume: Each character is separated by a space in the input string.
	 * 	Ex: "H B D E I J" and not "HBDEIJ"
	 * 
	 * Find all alphabetic characters in the given string as an array of characters
	 * converted to string.
	 *
	 * @param inputString
	 * @return
	 */
	public static Pair<Boolean, ArrayList<String>> findAllLetters(String inputString) {
		
		ArrayList<String> letterArray = new ArrayList<String>(0);
		Boolean readLettersInLine = false;		
		Matcher charMatcher = charFind.matcher(inputString);
		
		int loopCounter = 0;		
		while(charMatcher.find() && loopCounter < 101) {
			if(charMatcher.group().length() != 0) {
				letterArray.add(charMatcher.group());
				loopCounter++;
				readLettersInLine = true;
			}
		}
		
		return new Pair<Boolean, ArrayList<String>>(readLettersInLine, letterArray);
	}	
}
