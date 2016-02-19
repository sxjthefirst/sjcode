#!/usr/bin/env python3
## MYOB Inteview question
## Generate a payslip for input employee details

import csv
import sys
#setup logging behaviour
import logging
logging.basicConfig(stream=sys.stderr, level=logging.INFO )
#Set the decimal precision and rounding behaviour
from decimal import *
getcontext().prec=2
getcontext().rounding = ROUND_UP

#Fields to read from the input
fieldNames=["first_name", "last_name", "annual_salary", "super_rate", "pay_period"]

#Rate slabs - Max amount in this bracket, carry over from previous slab, cents per dollar
taxRates=[(18200,0,0), (37000,0,19), (80000,3572,32.5), (180000,17547,37), (sys.maxsize,54547,45)]

# "print all the employee details in the file"
def printAll():
	with open('emp.csv', newline='') as csvfile:
		empreader = csv.DictReader(csvfile, fieldnames=fieldNames,delimiter=',', quotechar='|')
		try:
			for row in empreader:
				 print(row)
		except csv.Error as e:
			sys.exit('file {}, line {}: {}'.format(filename, reader.line_num, e))

#search by name and pay period
def search(name,start_date):
	with open('emp.csv', newline='') as csvfile:
		empreader = csv.DictReader(csvfile, fieldnames=fieldNames,delimiter=',', quotechar='|')
		try:
			for row in empreader:
				if ((row['first_name']==name[0]) and (row['last_name']==name[1]) and (row['pay_period']==start_date)):
					logging.debug("Row= %s",row)
					return row
		except csv.Error as e:
			sys.exit('file {}, line {}: {}'.format(filename, reader.line_num, e))
	return False

#for a given income amount calculate the tax based on the bracket
def calculateTax(income):
	#Find the relevant tax rate
	bracket=-1
	for tup in taxRates:
		bracket+=1
		logging.debug("bracket= %s", bracket)
		if income<tup[0]:
			break
	
	logging.debug("Tax Bracket = %s", taxRates[bracket])
	bracket_min=Decimal(taxRates[bracket-1][0])
	logging.debug("Tup = %s", tup)
	bracket_max=Decimal(tup[0])
	carryover=Decimal(tup[1])
	tax_rate=Decimal(tup[2])
	taxable_amt=Decimal(income - bracket_min)
	logging.debug("%s %s %s %s %s", taxable_amt, bracket_min, bracket_max,carryover,tax_rate)
	tax=Decimal( carryover + taxable_amt * (tax_rate/100) ) 
	#Return the amount of tax AND the taxable amount so we can show in the pay slip
	return [Decimal(taxable_amt/12), Decimal(tax/12)]

#Calculate and print the payslip
def generatePayslip(row):
	full_name=row['first_name'] +" " + row['last_name']	
	pay_period=row['pay_period']
	annual_salary=Decimal(row['annual_salary'])
	tax_details=calculateTax(annual_salary)
	income_tax=Decimal(tax_details[1])
	taxable_amount=Decimal(tax_details[0])
	gross_income=Decimal(annual_salary/12)
	net_income=gross_income-income_tax
	super_rate=Decimal(row['super_rate'].strip("%"))/100
	super_amount=Decimal(gross_income*super_rate)
	logging.debug("%s %s %s %s %s %s %s %s", full_name, pay_period, annual_salary, gross_income,taxable_amount,income_tax,net_income,super_amount)
	return( full_name, pay_period,annual_salary, gross_income,taxable_amount,income_tax,net_income,super_amount)

def printPayslip(details):
	print("_"*80)
	print('Name          = {}'. format(details[0]) )
	print('Pay Period    = {}'. format(details[1]) )
	print("Annual Salary = {0:.2f}".format(details[2]) )
	print("Gross Income  = {0:.2f}".format(details[3]) )
	print("Taxable Income= {0:.2f}".format(details[4]) )
	print("Income Tax    = {0:.2f}".format(details[5]) )
	print("Net Income    = {0:.2f}".format(details[6]) )
	print("Super Amount  = {0:.2f}".format(details[7]) )
	logging.debug("%s", details)


##Main ##
if __name__ == "__main__":
	start_date="01 March – 31 March"
	search_emps=[["David","Rudd"],["Ryan","Chen"],["Tim","Tam"],["Bryan","Chen"]]
	for search_emp in search_emps:
		emp_details=search(search_emp,start_date)
		if not ( emp_details ):
			print("Unable to find pay details of: " + search_emp[0]+ " " +  search_emp[1] +" for pay period: " + start_date)	
		else:
			payslip=generatePayslip(emp_details)
			printPayslip(payslip)
	#printAll()
