class BNode {

	private int val;
	private BNode left, right, parent;

	public BNode (int n, BNode p) {
		val = n;
		left = right = null;
		parent = p;
	}

	public int getVal() {
		return val;
	}

	public BNode getLeft () {
		return left;
	}

	public BNode getRight () {
		return right;
	}

	public BNode getParent () {
		return parent;
	}

	public void setVal (int n) {
		val = n;
	}

	public void setLeft (BNode branch) {
		left = branch;
	}

	public void setRight (BNode branch) {
		right = branch;

	}

	public void setParent (BNode branch) {
		parent = branch;
	}

	public String toString () {
		return "<" + getVal() + "> ";
	}
 
}