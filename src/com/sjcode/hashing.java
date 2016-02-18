package com.sjcode;

public class hashing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    
		String s1=new String("Sridhar");
		System.out.println(s1.hashCode());
		String s2=new String("sridhar");
		System.out.println(s2.hashCode());
		Hash h1=new Hash();
	    h1.str="Sridhar";
	    System.out.println(h1.hashCode());
	    h1.str="Sridharan";
	    System.out.println(h1.hashCode());
	    h1.str="Sridhar";
	    System.out.println(h1.hashCode());
	    h1.str="SridhaR";
	    System.out.println(h1.hashCode());
	    h1.str="sridh";
	    System.out.println(h1.hashCode());
	}
}
class Hash
{
	 public String str;
	 @Override
	 public int hashCode() {
		 int hash=0;
		 int n=str.length();
		 for (int i=0;i<n;i++)
			 hash=31*hash+str.charAt(i);
		   //hash=hash+str.charAt(i)*(31^(n-1+i));
		 return hash;
	 }

}
