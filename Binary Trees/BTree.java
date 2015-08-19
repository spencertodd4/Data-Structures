/*
 * BTree.java
 * Suraj Rampure
 * This class constructs and implements binary trees, a recursive method of data storage.
 * Methods include add, find, depth, countLeaves, height, isAncestor, delete, isBalanced
 * and toString
 */

class BTree {
	
	// From the root node we can access any of the other nodes
	private BNode root;

	// Constants for the three different toString methods
	public static final int IN = 0, PRE = 1, POST = 2;

	// Constructor method
	public BTree () {
		root = null;
	}

	// Returns the root
	public BNode getRoot () {
		return root;
	}




	// add methods
	// Adding an integer – Method that calls recursion
	public void add (int n) {
		if (root == null) {
			root = new BNode (n, null);	
		}

		else {
			add (n, root);
		}
	}

	// Adding an integer – recursive, to find the correct position for the new node
	private void add (int n, BNode branch) {

		// If the integer to be added is less than the current node, it must go to the left of it
		if (n < branch.getVal()) {
			if (branch.getLeft() == null) {
				branch.setLeft(new BNode(n, branch));
			}

			else {
				add (n, branch.getLeft());
			}
		}

		// If the integer to be added is greater than the current node, it must go to the right of it
		else if (n > branch.getVal()) {
			if (branch.getRight() == null) {
				branch.setRight(new BNode(n, branch));
			}

			else {
				add (n, branch.getRight());
			}
		}
	}

	// Adding another BST to this tree – calls recursive method
	public void add (BTree newTree) {
		add (newTree, newTree.root);
	}

	// Adding another BST to this tree – recursively traverses through the parameter tree
	// and adds all nodes from the parameter tree to this tree
	private void add (BTree newTree, BNode node) {
		add (node.getVal());

		if (node.getLeft() != null) {
			add (newTree, node.getLeft());
		}
		
		if (node.getRight() != null) {
			add (newTree, node.getRight());
		}
	}




	// find method
	// Finds an integer – calls recursive method
	public BNode find (int n) {
		return find (n, root);
	}

	// Finds a node with the given value – recursively traverses through the
	// tree until the node with the value is found
	public BNode find (int n, BNode branch) {
		if (branch.getVal() == n) {
			return branch;
		}

		else if (branch.getLeft() == null && branch.getRight() == null) {
			return null;
		}

		else {
			if (n > branch.getVal()) {
				return find (n, branch.getRight());
			}

			else {
				return find (n, branch.getLeft());
			}
		}
	}




	// depth method
	// Depth method – takes in an integer and calls recursive method
	public int depth (int n) {
		return depth (n, 0, root) + 1;
	}

	// Depth method – Works similar to find, but returns the number of times
	// the function was called – effectively, the depth of the node
	private int depth (int n, int level, BNode node) {
		if (node.getVal() == n) {
			return level;
		}


		else if (node.getLeft() == null && node.getRight() == null) {
			return -1;
		}

		else {
			if (n < node.getVal()) {
				return depth (n, level + 1, node.getLeft());
			}

			else {
				return depth (n, level + 1, node.getRight());
			}
		}
	}




	// countLeaves method
	// CountLeaves – calls recursive method
	public int countLeaves () {
		return countLeaves(root) -1;
	}

	// CountLeaves – Recursively calls the right and left branch until they are null,
	// returns 1 once it reaches a null node – effectively, this provides a recursive counter
	public int countLeaves (BNode branch) {
		if (branch == null) {
			return 1;
		}

		else {
			return countLeaves(branch.getLeft()) + countLeaves(branch.getRight());
		}
	}




	// height methods
	// Height – calls recursive method starting at the root
	public int height () {
		return height(root);
	}

	// Height – Recursive method that works similarly to depth
	public int height (BNode branch) {

		if (branch == null) {
			return 0;
		}

		else {
			return Math.max (height (branch.getLeft()), height (branch.getRight())) + 1;
		}
	}

	// MinimumHeightOfNull – Finds the minimum height of a null pointer (used for isBalanced)
	private int minimumHeightOfNull (BNode branch) {
		if (branch == null) {
			return 0;
		}

		else {
			return Math.min (minimumHeightOfNull (branch.getLeft()), minimumHeightOfNull (branch.getRight())) + 1;
		}
	}




	// delete method
	// Delete – Integer – Finds the integer to be deleted, does nothing
	// if the integer does not exist in the tree
	public void delete (int f) {
		BNode b = find(f);

		if (b != null) {
			delete(b);
		}
	}

	public void delete (BNode b) {
		BNode parent = b.getParent();

		// Empty tree
		if (root == null) {
			return;
		}

		// Case 1: Leaf node
		if (b.getLeft() == null && b.getRight() == null) {

			if (b != root) {
				if (b == parent.getLeft()) {
					parent.setLeft(null);
				}

				else {
					parent.setRight(null);
				}
			}

			else {
				root = null;
			}
			
		}

		// Case 2: One child
		else if (b.getLeft() == null) {
			BNode tmp = b;
			b = b.getRight();

			if (tmp != root) {
				b.setParent(parent);

				if (tmp == parent.getLeft()) {
					parent.setLeft(b);
				}

				else {
					parent.setRight(b);
				}
			}

			else {
				root = b;
			}
			
		}

		else if (b.getRight() == null) {
			BNode tmp = b;
			b = b.getLeft();

			if (tmp != root) {
				b.setParent(parent);
				if (tmp == parent.getLeft()) {
					parent.setLeft(b);
				}

				else {
					parent.setRight(b);
				}
			}

			else {
				root = b;
			}
			
		}

		// Case 3: The node to remove has two children
		else {
			BNode tmp = getMinimum(b.getRight());
			b.setVal(tmp.getVal());
			delete(tmp);
		}

	}




	// Minimum and Maximum methods
	public BNode getMinimum () {
		return getMinimum (root);
	}

	// Finds the minimum value starting from a node
	// by going to the furthest left node
	public BNode getMinimum (BNode start) {
		BNode node = start;

		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		
		return node;
	}

	public BNode getMaximum () {
		return getMaximum(root);
	}

	// Finds the maximum value starting from a node
	// by going to the furthest right node
	public BNode getMaximum (BNode start) {
		BNode node = start;

		while (node.getRight() != null) {
			node = node.getRight();
		}

		return node;
	}




	// isBalanced – Check if the current tree is a balanced tree
	// BSTs are balanced if null pointers only exist in the bottom two depths
	// This method finds the minimum depth of a null pointer and subtracts it from the height.
	// if this is more than two then the tree is unbalanced
	public boolean isBalanced () {
		int diff = height() - minimumHeightOfNull(root);

		if (diff > 2) {
			return false;
		}

		return true;
	}




	// isAncestor – checks if the node whose key is the first integer
	// is an ancestor of the node whose key is the second integer
	public boolean isAncestor (int ancestor, int descendent) {
		return find (descendent, find(ancestor)) != null;
	}




	// toString methods
	public String toString () {
		return "{ " + toString(root, IN) + "}";
	}

	public String toString (int order) {
		return "{ " + toString(root, order) + "}";
	}

	// Works for in, pre and post ordered traversals
	public String toString (BNode branch, int order) {
		if (branch == null) {
			return "";
		}

		else {
			if (order == IN) {
				return toString(branch.getLeft(), IN) + branch + toString(branch.getRight(), IN);
			}

			else if (order == PRE) {
				return branch + toString(branch.getLeft(), PRE) + toString(branch.getRight(), PRE);
			}

			else if (order == POST) {
				return toString(branch.getLeft(), POST) + toString(branch.getRight(), POST) + branch;
			}
			
		}

		return "";
	}

}