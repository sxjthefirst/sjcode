#!/usr/bin/env python
'''A function that takes an input array of integers and a desired target_sum, and then returns combinations (of any length) that add up to that target_sum.
Example function and inputs:
calculate_combinations([5, 5, 15, 10], target_sum=15)
Example returns:
15
5+10
5+10'''

def calculate_combinations(combo, target_sum):
 if len(combo) < 2: 
   print "List must have at least two numbers"
   return -1
 #s=sum(combo)
 #if s<target_sum:
 # print "The sum of all the numbers in the list is less than the target sum %d there are no combinations which will have the desired sum" %target_sum
 # return -1
 #if still a valid list use the subset sum logic 
 print "Processing list: ", combo
 subset_sum(combo,target_sum)
 
#Sum all subsets and print those that match target
def subset_sum(numbers, target, partial=[]):
    s = sum(partial)

    # check if the partial sum is equals to target
    if s == target: 
        print "sum(%s)=%s" % (partial, target)
    if s >= target:
        return  # if we reach the number why bother to continue
    for i in range(len(numbers)):
        n = numbers[i]
        remaining = numbers[i+1:]
        subset_sum(remaining, target, partial + [n]) 

if __name__ == "__main__":
	calculate_combinations([5, 5, 15, 10], 15)
	calculate_combinations([5, 5, -15, 10], 15)
	calculate_combinations([5, 5, 1, 0], 15)
	calculate_combinations([5], 15)
	calculate_combinations([], 15)
	calculate_combinations([9,8, 0, 99, 10, 10, 10 , 10 , 10, 10, 1,1,1,1,1,4,44,4,4, 5, 5, 15, 10], 15)
