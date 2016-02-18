/*
 * Given an array A having first n integers and next n chars. A = i1 i2 i3 ... iN c1 c2 c3 ... cN. Write
an in-place algorithm to rearrange the elements of the array as A = i1 c1 i2 c2 ... cn.
An in-place algorithm is an algorithm which transforms input using a data structure with a
small, constant amount of extra storage space.
*/
package com.sjcode;

import java.util.Arrays;

public class SwapArr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String A[]={"1","2","3","4","5","6","A","B","C","D","E","F"};
		int n=A.length/2;//Assume length is always even
		int counter=0;
		for (int i=0;i<n;i+=1)
		{
			System.out.println("Iter "+i+":" + Arrays.toString(A));
			int j=n+i;
			counter++;
			//int pos=i+1;
			String tmp=A[j];
			System.out.printf("tmp = %s \n", tmp);
			//for(int j=i+2;j<n+i;j++)
			while(j>=n-(i+2))
			{
					
					A[j]=A[j-1];
					System.out.println("i="+i+"j="+j+" "+Arrays.toString(A));
					j--;
				
			}
			//System.out.println("i="+i+"j="+j+" "+Arrays.toString(A));
			A[j-i]=tmp;
		}
		System.out.println("Final Array" + Arrays.toString(A));
		//With second array
		/* String halfA[]=new String[n];
		for (int i=0;i<n;i++)
		{
			System.out.println("Iter "+i+":" + Arrays.toString(A));
			System.out.println("Iter "+i+":" + Arrays.toString(halfA));
			String tmp=A[i+1];
			System.out.println(tmp);
			A[i]=A[n+i];
			halfA[i]=tmp;
		}
		System.out.println("Final Array" + Arrays.toString(A)); */
	}

}
