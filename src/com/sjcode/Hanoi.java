package com.sjcode;

import java.util.Date;

public class Hanoi {
 static int disks= 10;
 
 public static void main(String args[]) {
	 Date start=new Date();
	 doTowers(disks,'A','B','C');
	 
	 System.out.println(start + " to " + (new Date()));
 }
 
 private static void doTowers(int topN, char from, char inter, char to) {
	if (topN==1)
		System.out.println("Disk 1 from " + from + " to "+ to);
	else
	{
		doTowers(topN-1, from,to, inter); //from --> inter
		System.out.println("Disk " + topN +" from " + from + " to "+ to);
		doTowers(topN-1, inter, from,to); //inter-> to
	}
 }
}
