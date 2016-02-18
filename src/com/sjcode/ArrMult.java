/*
There is an array A[N] of N numbers. 
You have to compose an array Output[N] such that Output[i] will be equal to multiplication of all the elements of A[N] 
except A[i]. 
For example, Output[0] will be multiplication of A[1] to A[N-1] and Output[1] will be multiplication of A[0] and 
from A[2] to A[N-1]. Solve it without division operator and in O(n).
*/
package com.sjcode;

import java.util.Arrays;
public class ArrMult {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.printf("An int[] arises from class %s \n", new int[1].getClass().getName());
		String str="abcccc";
		str="dddd";
		str=new String();
		String str2=new String();
		str2=str;
		System.out.println(str.replaceAll("a", "b"));
		
		System.out.println(str); System.out.println(str2);
		//args[0]="werwr";
		int A[]={1,2,3,4,5,6,7,8,9,10};
		int O[]=new int[10];
		System.out.println("Pre Mult array " + Arrays.toString(A));
		long st=0;
		st=System.nanoTime();
		System.out.println("Start time "+st);
		for (int i=0;i<A.length;i++)
		{
			int P=1;
			for (int j=0;j<A.length;j++)
			{
				if (i!=j)
					P=P*A[j];
			}
		//	System.out.println("P= " + P);
			O[i]=P;
		}
		System.out.println("End time "+ (System.nanoTime() - st));
		System.out.println("O(N^2) Mult Array       " + Arrays.toString(O));
		//------------------
		
		int O1[]={1,1,1,1,1,1,1,1,1,1};
		int len=A.length;
		int p1=1;
		st=System.nanoTime();
		System.out.println("Start time "+st);
		for (int i=0;i<len;i++)
		{
			p1=p1*A[i];
		}
		for (int i=0;i<len;i++)
		{
			O1[i]=p1/A[i];
		}
		System.out.println("End time "+ (System.nanoTime() - st));
		System.out.println("O(N^1) w div Mult Array " + Arrays.toString(O1));
		//------------------
		
		long O2[]={1,1,1,1,1,1,1,1,1,1};
		int len2=A.length;
		int p2=1;
		st=System.nanoTime();
		System.out.println("Start time "+st);
		for (int i=0;i<len2;i++)
		{
			p2=p2*A[i];
		}
		for (int i=0;i<len2;i++)
		{
			O2[i]=p2; //TODO
		}
		System.out.println("End time "+ (System.nanoTime() - st));
		System.out.println("O(N^1)w/o div Mult Array" + Arrays.toString(O2));
	}

}
