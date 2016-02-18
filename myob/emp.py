#!/usr/bin/env python3

import csv
import sys

#Fields to read from the input
fieldNames=["first_name", "last_name", "annual_salary", "super_rate", "payment_start_date"]

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

#search by name and paymnet date
#TODO: date search 
def search(name,start_date):
	with open('emp.csv', newline='') as csvfile:
		empreader = csv.DictReader(csvfile, fieldnames=fieldNames,delimiter=',', quotechar='|')
		try:
			for row in empreader:
				if ((row['first_name']==name[0]) and (row['last_name']==name[1])):
					#print("DEBUG: ", row)
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
		#print("DEBUG: bracket= ", bracket)
		if income<tup[0]:
			break
	
	#print("DEBUG:", taxRates[bracket])
	bracket_min=taxRates[bracket-1][0]
	#print("DEBUG:", tup)
	bracket_max=tup[0]
	carryover=tup[1]
	tax_rate=tup[2]
	taxable_amt=income - bracket_min
	#print("DEBUG: ", taxable_amt, bracket_min, bracket_max,carryover,tax_rate)
	tax=carryover + taxable_amt * (tax_rate/100)  
	#Returb the amount of tax AND the taxable amount so we can show in the pay slip
	return [taxable_amt/12, tax/12]

#Calculate and print the payslip
def generatePayslip(row):
	#print("DEBUG:", row)
	full_name=row['first_name'] +" " + row['last_name']	
	annual_salary=float(row['annual_salary'])
	tax_details=calculateTax(annual_salary)
	income_tax=tax_details[1]
	taxable_amount=tax_details[0]
	gross_income=annual_salary/12
	net_income=gross_income-income_tax
	super_rate=float(row['super_rate'].strip("%"))/100
	super_amount=gross_income*super_rate
	print("_"*80)
	print('Name          = {}'. format(full_name))
	print("Annual Salary = {0:.2f}".format(annual_salary) )
	print("Gross Income  = {0:.2f}".format(gross_income) )
	print("Taxable Income= {0:.2f}".format(taxable_amount) )
	print("Income Tax    = {0:.2f}".format(income_tax) )
	print("Net Income    = {0:.2f}".format(net_income) )
	print("Super Amount  = {0:.2f}".format(super_amount) )
	#print( "DEBUG: ", full_name, gross_income,income_tax,net_income,super_amount)


##Main ##
if __name__ == "__main__":
	search_emps=[["David","Rudd"],["Ryan","Chen"],["Tim","Tam"],["Bryan","Chen"]]
	for search_emp in search_emps:
		emp_details=search(search_emp,"1/1/2015")
		if not ( emp_details ):
			print("Unable to find employee: " + search_emp[0]+ " " +  search_emp[1])	
		else:
			generatePayslip(emp_details)
	#printAll()
