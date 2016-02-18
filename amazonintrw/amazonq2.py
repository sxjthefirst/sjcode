#!/usr/bin/env python
import math
from collections import deque

#Defintion of class Point
class Point:
 def __init__(self, x, y):
  self.x = x
  self.y = y


 def getX(self):
  return self.x
 def getY(self):
  return self.y
 def __str__(self):
  return "("+ str(self.x) +","+str(self.y)+")"
#End definition of class Point

root=Point(0,0) #Root of a 2D plane 0,0 aka origin
K=10 #K is the number of points we want

#Method to get distance between two points
def pointDistance(p1, p2):
  return math.sqrt(math.pow((p1.getX() - p2.getX()),2) + math.pow((p1.getY() - p2.getY()),2));
 
#Method to see which of two points is closest to root 
def closerToRoot(p1, p2):
 if (pointDistance(p1,root) < pointDistance(p2,root)): 
	return p1
 else:
	return p2 

#Convert a string to a Point string is in format "(x,y)"
def fromString(str1):
  x1,y1=str1.split(",")
  x = float(x1[1:])
  y = float(y1[:-1])
  return Point(x,y)

#### MAIN Program ####
if __name__ == "__main__":
 #Create a FIFO queue and add an arbitrarily large value
 points=deque([],K)
 points.append(Point(1000000,1000000))
 #Open the file and read character by character
 f=open('really_big_file.dat')
 while True:
	tmp=""
 	ch=f.read(1)
	if not ch: #If EOF stop
	      break
	if ch == '(': #for every expression
		while ch != ')':
			tmp=tmp+ch
			ch=f.read(1)
		current=fromString(tmp+")")
		#If there are any points closer to root add it to the queue
		if pointDistance(current,root) < pointDistance(points[0],root):
			points.append(current)
			#for p in points: print p    
 f.close()
 print "Closest " + str(K) + " points:"
 for p in points: print p    
