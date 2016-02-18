package com.sjcode;
/*
 * Java passes by value
 */


class Point {
	public int x,y;

	Point(int arg1, int arg2)
	{
		x=arg1;
		y=arg2;
	}
}

public class Arguments {

public static void tricky(Point arg1, Point arg2, String str)
{
  arg1.x = 100;
  arg1.y = 100;
  System.out.println("In method X1: " + arg1.x + " Y: " +arg1.y);
  System.out.println("In method X2: " + arg2.x + " Y: " +arg2.y);
  Point temp = arg1;
  arg1 = arg2;
  arg2 = temp;
  System.out.println("In method X1: " + arg1.x + " Y: " +arg1.y);
  System.out.println("In method X2: " + arg2.x + " Y: " +arg2.y);
  
  System.out.println("In method str "+ str);
  str=new String("heyy");
  System.out.println("In method str "+ str);
}

public static void main(String [] args)
{
  Point pnt1 = new Point(0,0);
  Point pnt2 = new Point(0,0);
  System.out.println("X1: " + pnt1.x + " Y: " +pnt1.y); 
  System.out.println("X2: " + pnt2.x + " Y: " +pnt2.y);
  String str="hello";
  System.out.println("str: "+ str);
  tricky(pnt1,pnt2, str);
  System.out.println("X1: " + pnt1.x + " Y:" + pnt1.y); 
  System.out.println("X2: " + pnt2.x + " Y: " +pnt2.y);
  System.out.println("str: "+ str);
}
}
