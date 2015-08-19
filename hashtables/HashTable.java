/*
HashTable.java
Suraj Rampure
2015-04-13

This class provides HashTable implementation.
This HashTable consists of an ArrayList with Linked Lists at each position.
*/

import java.util.*;

public class HashTable <T> {
	private ArrayList <LinkedList <T>> table;
	public int size, maxSize;
	public double maxLoad;

	// Constructor method
	public HashTable () {
		size = 0;
		maxSize = 10;
		maxLoad = 0.6;
		table = newList(10);
	}

	// newList – creates a new ArrayList of LinkedLists of a set size
	// This data structure is our hash table
	private ArrayList <LinkedList <T>> newList (int size) {
		ArrayList <LinkedList <T>> tmp = new ArrayList <LinkedList <T>> ();

		for (int i = 0; i < size; i ++) {
			tmp.add(null);
		}

		return tmp;
	}

	// add – adds a value of type T to the hash table
	public void add (T val) {
		int pos = Math.abs(val.hashCode()) % maxSize;

		if (table.get(pos) == null) {
			table.set(pos, new LinkedList <T> ());
		}

		table.get(pos).add(val);
		size += 1;

		// This in essence is if (getLoad() > maxLoad), however
		// we must account for this because size/maxSize won't always
		// be the exact double that is passed in
		if (getLoad() - maxLoad > 0.01) {
			resize();
		}
	}

	// remove – removes a given value from the hash table
	// by setting the ArrayList at the value's position to null if no collisions
	// or deleting it from the LinkedList if there are collisions
	public void remove (T val) {
		int pos = Math.abs(val.hashCode()) % maxSize;

		if (table.get(pos) != null) {
			if (table.get(pos).size() == 1) {
				table.set(pos, null);
			}

			else {
				table.get(pos).remove(val);
			}
		}
	}

	// get – returns the value with the given hashcode
	public T get (int key) {
		int pos = Math.abs(key) % maxSize;
		if (table.get(pos) != null) {
			for (T val: table.get(pos)) {
				if (val.hashCode() == key) {
					return val;
				}
			}
		}
		return null;
	}

    // getList – returns a LinkedList of all values at a certain spot in the table
    // Got the idea for this from Chris (thanks, Chris).
	public LinkedList<T> getList (int key) {
		int pos = Math.abs(key) % maxSize;

		LinkedList<T> list = table.get(pos);

        if (list != null && list.size() > 0) {
            return list;
        }

        return null;

	}

	// resize – adjusts the size of the hash table
	// by creating a new ArrayList of LinkedLists of the desired
	// size and readding all elements
	public void resize (double load) {
		ArrayList <LinkedList <T>> tmp = table;
		maxSize = (int) ((double) size/load);
		System.out.println(maxSize);
		size = 0;

		table = newList(maxSize);

		for (LinkedList <T> entry : tmp) {
			if (entry != null) {
				for (T val : entry) {
					add(val);
				}
			}
		}
	}

  	// resize – adjusts the size of the hash table
  	// by creating a new ArrayList of LinkedLists of the desired
  	// size and readding all elements
  	public void resize () {
	    ArrayList <LinkedList <T>> tmp = table;
	    maxSize *= 10;
	    size = 0;
	    table = newList(maxSize);

	    for (LinkedList <T> entry : tmp) {
	        if (entry != null) {
	        	for (T val : entry) {
	          		add(val);
	        	}
	      	}
	    }
	}

	// getLoad – returns the load
	public double getLoad () {
        return (double)size / (double)maxSize;
	}

	// setMaxLoad – allows change in the maximum load
	// as long as it is between 0.1 and 0.8
	public void setMaxLoad (double load) {
		if (load >= 0.1 && load <= 0.8) {
            maxLoad = load;
        }
	}

	// setLoad – forces the hash table to have a certain fullness (is that a word?)
	public void setLoad (double load) {
		if (load >= 0.1 && load <= 0.8) {
			if (load >= maxLoad) {
				maxLoad = load;
			}
			else {
				maxLoad = load;
				resize(maxLoad);
			}
		}
	}

	// toArrayList - returns the hash table as an ArrayList
	public ArrayList <T> toArrayList () {
		ArrayList <T> out = new ArrayList <T> ();

		for (int i = 0; i < maxSize; i ++) {
			if (table.get(i) != null) {
				for (int j = 0; j < table.get(i).size(); j ++) {
					out.add(table.get(i).get(j));
				}
			}
		}

		return out;
	}

	// toString method
	public String toString () {
		if (size > 0) {
			String ans = "[";
			for (int i = 0; i < maxSize; i ++) {
				if (table.get(i) != null) {
					for (int j = 0; j < table.get(i).size(); j ++) {
						ans += table.get(i).get(j) + ", ";
					}
				}
			}

			ans = ans.substring(0, ans.lastIndexOf(",")) + "]";

			return ans;

		}

		else {
			return "[]";
		}
	}

}