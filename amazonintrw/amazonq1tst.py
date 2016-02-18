#!/usr/bin/env python
import unittest
import amazonq1

class ComboTest(unittest.TestCase):
 def test_four_numbers(self):
        calculate_combinations([5, 5, 15, 10], 15)
 def test_negative_numbers(self):
        calculate_combinations([5, 5, -15, 10], 15)
 def test_unreachable_target(self):
        calculate_combinations([5, 5, 1, 0], 15)
 def test_single_number(self):
        calculate_combinations([5], 15)
 def test_empty_list(self):
        calculate_combinations([], 15)
 def test_large_list(self):
        calculate_combinations([9,8, 0, 99, 10, 10, 10 , 10 , 10, 10, 1,1,1,1,1,4,44,4,4, 5, 5, 15, 10], 15)



