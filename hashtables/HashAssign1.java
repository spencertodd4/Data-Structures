/*
HashAssign1.java
Suraj Rampure
2015-04-13

This class provides a solution to the given problem.
It lists all permutations of a given string that are
in a dictionary, using hash tables.
*/

import java.io.*;
import java.util.*;

public class HashAssign1 {
    public static void main (String [] args) {
        HashTable <String> dictionary = new HashTable <String> ();
        Scanner inFile = null;

        try {
            inFile = new Scanner(new File ("dictionary.txt"));
        }

        catch (IOException ex) {
            System.out.println(ex);
        }

        // Adds the entire dictionary to the hash table
        while (inFile.hasNext()) {
            dictionary.add(inFile.nextLine());
        }

        // Perms stores all the permutations of the given string
        HashTable <String> perms = new HashTable <String> ();

        Scanner kb = new Scanner(System.in);
        String word = kb.nextLine();

        permutations("", word, perms);

        // We only print a permutation if it exists in the dictionary
        // We do this by calling the get method on the hash code of the word
        // There is also a chance that a non-permutation has the same hash code
        // as a permutation, so this is checked for
        for (String p: perms.toArrayList()) {
            if (dictionary.get(p.hashCode()) != null) {
                if (dictionary.get(p.hashCode()).equals(p)) {
                    System.out.println(p);
                }
            }
        }

    }

    // permutations – Adds all permutations of the user's input string to a hash table
    // This works by adding letters one by one from the leftovers of the original string
    // Eventually, the leftover string is blank and the prefix string will consist
    // of a permutation of the original string
    public static void permutations (String pre, String suf, HashTable <String> perms) {
        int len = suf.length();

        if (len == 0) {
            // If there aren't any characters left to add to the front,
            // then the "back" of the string is a permutation and should be added
            if (perms.get(pre.hashCode()) == null) {
                perms.add(pre);
            }
        }

        else {
            for (int i = 0; i < len; i ++) {
                permutations (pre + suf.charAt(i), suf.substring(0, i) + suf.substring(i + 1), perms);
            }
        }
    }


}