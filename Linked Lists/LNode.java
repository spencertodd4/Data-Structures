class LNode {
	
	private int val;
	private LNode prev;
	private LNode next;
	
	public LNode (int v, LNode p, LNode n) {
		val = v;
		prev = p;
		next = n;
		
	}
	
	public int getVal () {
		return val;
	}
	
	public LNode getPrev () {
		return prev;
	}
	
	public LNode getNext () {
		return next;
	}

	public void setVal (int v) {
		val = v;
	}
	
	public void setPrev (LNode p) {
		prev = p;
	}
	
	public void setNext (LNode n) {
		next = n;
	}
	
	public String toString () {
		return "" + val;
	}

}
