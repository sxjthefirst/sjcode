package com.sjcode;

//import java.util.LinkedHashSet;
import java.util.Random;
//import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class randset {

	/**
	 * @author stackoverflow + me !
	 */
	public static void main(String[] args) {
		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order.
		//Set<Integer> generated = new LinkedHashSet<Integer>();
		//SortedSet to auto sort.
		SortedSet<Integer> generated = new TreeSet<Integer>();
		int numbersNeeded=10000;
		int max=1000000000;
		while (generated.size() < numbersNeeded)
		{
		    Integer next = rng.nextInt(max) + 1;
		    System.out.println(next);
		    // As we're adding to a set, this will automatically do a containment check
		    generated.add(next);
		}
		System.out.println("size="+generated.size()+generated.toString() );
	}

}
