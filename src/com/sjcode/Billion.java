package com.sjcode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.BitSet;
import java.util.Scanner;

public class Billion {

	/**
	 * http://stackoverflow.com/questions/7153659/find-an-integer-not-among-four-billion-given-ones
	 */
	public static void main(String[] args) {
	   Billion b=new Billion();
	   try{
		   b.bitsetWay();
		  // b.F();
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}
	
	void bitsetWay() throws FileNotFoundException
	{
	    String path=System.getProperty("user.dir")+"/com/sjcode/";
	    Scanner in = new Scanner(new FileReader(path+"a.txt"));
	    BitSet btpos= new BitSet(Integer.MAX_VALUE/2);
	    BitSet btneg= new BitSet(Integer.MAX_VALUE/2);
	    System.out.println(Integer.MAX_VALUE);
	    while(in.hasNextInt()){
	        int n = in.nextInt();
	        System.out.println(n);
	        if (n<0)
	        {
	        	btneg.set(-1*n);
	        }
	        else
	        {
	        	btpos.set(n);
	        }
	    }
	    in.close();
	    System.out.println(btpos.nextClearBit(0) + " : " + btpos);
	    //ignore btneg[0] since we treat 0 as +ve
	    System.out.println(btneg.nextClearBit(1) + " : " + btneg);
	}
	
	int radix = 8;
	byte[] bitfield = new byte[0xffffffff/radix];
    void F() throws FileNotFoundException{
    	String path=System.getProperty("user.dir")+"/src/com/sjcode/";
	    Scanner in = new Scanner(new FileReader(path+"a.txt"));

	    while(in.hasNextInt()){
	    	
	        int n = in.nextInt();
	        System.out.println(n);
	        bitfield[n/radix] |= (1 << (n%radix));
	    }

	    for(int i = 0; i< bitfield.length; i++){
	        for(int j =0; j<radix; j++){
	            if( (bitfield[i] & (1<<j)) == 0) System.out.print(i*radix+j);
	        }
	    }
	}

}
