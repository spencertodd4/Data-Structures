//JCF.java

import java.util.*;

public class JCF {
    public static void main (String [] args) {

        TreeSet <Integer> picks = new TreeSet <Integer> ();
        while (picks.size() < 6) {
            int p = (int) (Math.random()*49) + 1;
            picks.add(p);
        }
        System.out.println(picks);

        TreeSet <Duck> sord = new TreeSet <Duck> ();
        sord.add(new Duck ("Elisa", "Mallord"));
        sord.add(new Duck ("Richard", "Mallord"));
        sord.add(new Duck ("The ugly duckling", "Swan"));
        System.out.println(sord);

        HashMap <String, String> phone = new HashMap <String, String> ();
        phone.put("Bryan", "555-969-2530");
        phone.put("Elisa", "555-867-5309");
        phone.put("Richard", "1-800-2-300-241");
        System.out.println(phone.get("Richard"));

        for (String name: phone.keySet()) {
            System.out.println(phone.get(name));
        }

        // HashMap uses any immutable class as the key.
    }
}

class Duck implements Comparable <Duck> {
    private String name, species;

    public Duck (String n, String s) {
        name = n;
        species = s;
    }

    public int compareTo (Duck other) {
        return name.compareTo(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString () {
        return "{" + name + ", " + species + "}";
    }
}