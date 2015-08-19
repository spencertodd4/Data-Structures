/*
 * LList.java
 * Suraj Rampure
 * Linked Lists Assignment
 * This class provides doubly linked list functionality, in which each node stores a value
 * and a reference to the previous and next node in the list
 * 
 * This class has methods for LIFO and FIFO functionality
 */

class LList {
	
	private LNode head;
	private LNode tail;
	
	
	
	// Constructor method
	public LList () {
		head = null;
		tail = null;
	}
	
	
	
	// LIFO functionality
	// push method – adds an element to the top of the list
	public void push (int n) {
		LNode tmp = new LNode(n, null, head);
		
		if (head == null) {
			tail = tmp;
		}
		
		else {
			head.setPrev(tmp);		
		}
		head = tmp;

	}
	
	// pop method – removes the head from the list and returns it
	public LNode pop () {
		LNode t = null;
		
		if (head != null) {
			t = head;
			head = head.getNext();
		}		
		return t;
	}

	
	
	// FIFO functionality
	// enqueue method – adds an element to the end of the list
	public void enqueue (int n) {
		LNode tmp = new LNode(n, tail, null);
		
		if (tail == null) {
			head = tmp;
		}
		
		else {
			tail.setNext(tmp);
		}
		
		tail = tmp;
	}
	
	// dequeue – same as pop
	public LNode dequeue () {
		return pop();
	}
	
	
	
	// sortedInsert – inserts a value in the correct sorted order (head is smallest, tail is largest)
	// assuming the list is already sorted
	public void sortedInsert(int n) {

		// 4 cases
		
		// 1. The list is empty – just push the value
		if (head == null) {
			push(n);
		}
		
		// 2. The value is less than the head
		// The head should become the new value, so we just push it
		else if (n <= head.getVal()) {
			push(n);
		}
		
		// 3. The value is greater than the tail
		// We just add the value to the end of the list
		else if (n >= tail.getVal()) {
			enqueue(n);
		}
		
		// 4. The value fits in between two values already in the list
		// We loop through the list and find the first node whose value is greater than our value
		// and insert a new node right before it
		else {
			LNode tmp = head.getNext();
			
			boolean flag = true;
			
			while (flag) {
				if (n <= tmp.getVal()) {
					LNode insert = new LNode(n, tmp.getPrev(), tmp);
					tmp.setPrev(insert);
					tmp.getPrev().getPrev().setNext(insert);
					flag = false;
				}
					
				if (tmp.getNext() == null) {
					flag = false;
				}
				
				else {
					tmp = tmp.getNext();
				}
			}
		}
	}
	
	
	
	// reverse – Reverses the order of the nodes by creating a new list and
	// using pop() on the current list and pushing those values to the new list
	// until our current list is empty, and setting the current list to the new list
	public void reverse () {
		
		LList newList = new LList();
		LNode tmp = head;
		
		while (tmp != null) {
			tmp = pop();
			newList.push(tmp.getVal());
			tmp = tmp.getNext();
		}
		
		head = newList.getHead();
		tail = newList.getTail();
		
	}


	
	// Delete methods
	// delete (LNode) – deletes a single node
	public void delete (LNode node) {
		
		if (node.getPrev() == null && node.getNext() == null) {
			head = null;
			tail = null;
		}
		
		else if (node.getPrev() == null) {
			LNode tmp = head.getNext();
			tmp.setPrev(null);
			head = node.getNext();
			
		}
		
		else if (node.getNext() == null) {
			LNode tmp = tail.getPrev();
			tmp.setNext(null);
			tail = node.getPrev();
		}
		
		else {
			
			LNode p = node.getPrev();
			LNode n = node.getNext();

			p.setNext(n);
			n.setPrev(p);
		}
	}

	// delete (int) – takes in an integer, finds its corresponding LNode and
	// calls (delete (LNode) on that LNode
	public void delete (int n) {
		LNode tmp = head;
		
		while (tmp != null && tmp.getVal() != n) {
			tmp = tmp.getNext();
		}

		delete(tmp);
	}

	// deleteAt – deletes the node at the given index
	public void deleteAt(int index) {
		LNode tmp = head;
		
		for (int i = 0; i < index; i ++) {
			tmp = tmp.getNext();
		}
		
		delete(tmp);
	}

	// removeDuplicates – removes duplicate values from the list
	// in a O(n^2) manner
	public void removeDuplicates () {
		
		LNode tmp = head;
		
		while (tmp != null) {
			LNode check = tmp.getNext();
			
			while (check != null) {
				
				if (tmp.getVal() == check.getVal()) {
					
					delete(check);
				}
				
				check = check.getNext();
			}
			
			tmp = tmp.getNext();
		}
	}

	

	// Other miscellanous methods
	// size – returns the length of the list
	public int size() {
		LNode tmp = head;
		
		int len = 0;
		while (tmp != null) {
			len ++;
			tmp = tmp.getNext();
		}
		return len;
	}
	
	// clone – returns an identical linked list (creates a new one with the same values in the same order)
	public LList clone() {
		LList returnList = new LList();
		
		LNode tmp = head;
		
		while (tmp != null) {
			returnList.enqueue(tmp.getVal());
			tmp = tmp.getNext();
		}
		
		return returnList;
	}

	// nodeAtIndex – Returns the note that is at the given index
	public LNode nodeAtIndex (int index) {
		LNode tmp = head;
		for (int i = 0; i < index; i ++) {
			tmp = tmp.getNext();
		}
		
		return tmp;
	}
	
	// fromArray – creates a new linked list from values in an array
	public void fromArray (int [] vals) {
		for (int i = 0; i < vals.length; i ++) {
			enqueue(vals[i]);
		}
	}
	
	
	
	// Getters
	public LNode getHead() {
		return head;
	}
	public LNode getTail() {
		return tail;
	}

	
	
	// toString – prints out the linked list by looping through and adding the value of each node
	public String toString () {
		String ans = "{";
		LNode tmp = head;
		
		while (tmp != null) {
			
			ans += tmp + ", ";
			
			tmp = tmp.getNext();
		}
		
		if (ans.contains(",")) {
			ans = ans.substring(0, ans.lastIndexOf(","));
		}
		
		return ans + "}";
	}


}
