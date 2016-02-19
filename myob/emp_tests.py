#!/usr/bin/env python3

#Import python modules
import unittest
import sys
from io import StringIO
from decimal import *
getcontext().prec=2 #Set precision

#Import modules to test`
from emp import printAll
from emp import search
from emp import generatePayslip
from emp import printPayslip

class PayslipTestCase(unittest.TestCase):
	def testPrintAll(self):
		''' Test if the convenience method to read and print the CSV file works '''
		saved_stdout = sys.stdout
		try:
			out = StringIO()
			sys.stdout = out
			printAll()
			output = out.getvalue().strip()
			self.assertIsNotNone(output)
			self.assertIn("Ryan",output)
		finally:
			sys.stdout = saved_stdout

	def testPrintPayslip(self):
		''' Test if the we can print the payslip '''
		search_emp=["David","Rudd"]
		emp_details=search(search_emp,"01 March – 31 March")
		saved_stdout = sys.stdout
		try:
			out = StringIO()
			sys.stdout = out
			payslip=generatePayslip(emp_details)
			printPayslip(payslip)
			output = out.getvalue().strip()
			self.assertIsNotNone(output)
			self.assertIn("David Rudd",output)
		finally:
			sys.stdout = saved_stdout

	def testNoEmployee(self):
		''' Test for employee record not found '''
		search_emp=["Anon","Ymous"]
		emp_details=search(search_emp,"01 March – 31 March")
		self.assertFalse(emp_details)	

	def testNoPay(self):
		''' Test for existing employee for whom no pay record not found '''
		search_emp=["Bryan","Chen"]
		emp_details=search(search_emp,"01 April – 30 April")
		self.assertFalse(emp_details)	
	
	def testNoTax(self):
		''' Test for employee who gets 0 tax '''
		search_emp=["Bryan","Chen"]
		emp_details=search(search_emp,"01 March – 31 March")
		payslip=generatePayslip(emp_details)
		self.assertEqual(payslip[5],0)	
		
	def testBracket1(self):
		''' Test for employee who is in the lowest taxable bracket '''
		search_emp=["Tim","Tam"]
		emp_details=search(search_emp,"01 March – 31 March")
		payslip=generatePayslip(emp_details)
		self.assertEqual(payslip[5],Decimal('0.016'))	
		

if __name__ == '__main__':
    unittest.main()

