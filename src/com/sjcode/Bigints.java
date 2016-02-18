package com.sjcode;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Bigints {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> nums=Arrays.asList(9,2,3,4,8,0,9,1,5,5,5,5,5,5,5,5,5,8,2,3,9);
		getDigits(nums);

	}
	 
	 static void getDigits(List<Integer> arr)
	 {
	    List<Integer> nums= arr;
		//System.out.println(nums);
	    BigInteger out=new BigInteger("0");
	    BigInteger multiplier=new BigInteger("1");
	    BigInteger ten=new BigInteger("10");
	    //multiplier
	    for (int j=1;j<nums.size();j++)
	 		multiplier=multiplier.multiply(ten);
	    
	    
	    for (int i=0;i<nums.size();i++)
	    {
	    	//System.out.println("Multiplier=" + multiplier);
	    	//System.out.println("Out="+out);
	    	//System.out.println(multiplier.multiply(new BigInteger(Long.toString(nums.get(i)))));
	        out= out.add(multiplier.multiply(new BigInteger(Long.toString(nums.get(i)))));
	        multiplier=multiplier.divide(ten);
	    }
	    System.out.println("Input = " + out);
	    out=out.add(new BigInteger("1"));
	    System.out.println("Result= "+out);
	    
	    /* String result="";
	    while(out.intValue()>0)
	    {
	       System.out.println("out is now " + out);
	       result=result+(out.mod(ten)).intValue();
	       out=out.divide(ten);
	    }
	   System.out.println(result); */
	 }
	 
}
