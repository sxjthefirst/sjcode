/*
Question from: http://hire.jobvite.com/CompanyJobs/Careers.aspx?k=Job&c=q8Z9VfwV&j=oLYtVfwR

1. Is Person immutable?
Ans: No. The members of Person are immutable but Person objects can be modified by assignment
2. A Person is equal to another if all three fields are equal. Implement equals and hash code methods.
Ans: Done. See Person class below 
3. Implement a java.util.Comparator that compares name, then birth, and then id.
Ans: Done. See PeopleComparator class
4. We have a Person table in our UI that displays each field in a column. The user can sort the table by any column, ascending or descending. 
The sort is stable. For example, if the table starts out sorted by name, and then the user sorts by birth date, the table will be sorted by birth date and then name. 
Write code that can sort a java.util.List by any ordering of ascending/descending fields.
Ans: Done . See ColumnComparator class and ColumnListener classes
 */

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class square {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Create some ppl
		Date dt = new Date(234567810);
		Date dt1 = new Date(234967801);
		Person p1 = new Person("SJ", dt, 100);
		Person p2 = new Person("SJ", dt, 100);
		Person p3 = new Person("SA", dt, 100);
		//Compare them
		System.out.println("Equal operator comparision " + (p1 == p2));//false
		System.out.println("Equal operator comparision " + (p1 == p3));//false
		System.out.println("equals() function comparision " + (p1.equals(p2)));//true
		System.out.println("equals() function comparision " + (p1.equals(p3)));//false
		//Create more people
		Person p4 = new Person("sj", dt, 100);
		Person p5 = new Person("SJ", dt1, 101);
		Person p6 = new Person("SA", dt, 101);
		Person p7 = new Person("AJ", dt1, 100);
		Person p8 = new Person("aj", dt, 100);
		p1 = p7;//Assignment works so person is mutable. QED

		//An array of peeps
		Person parr[] = new Person[8];
		parr[0] = p1;
		parr[1] = p2;
		parr[2] = p3;
		parr[3] = p4;
		parr[4] = p5;
		parr[5] = p6;
		parr[6] = p7;
		parr[7] = p8;

		//print, sort, print again
		System.out.println("\nArray before sort");
		for (Person item : parr) {
			System.out.println(item);
		}
		Arrays.sort(parr, new PeopleComparator());
		System.out.println("\nArray after sort");
		for (Person item : parr) {
			System.out.println(item);
		}

		//******************************
		//GUI: Create a sortable JTable
		//******************************
		//Data for the table
		String[] columnNames = {"Name",
				"Date of Birth",
		"ID"};
		SortableTableModel stm= new SortableTableModel();
		Object perobj[][]=new Object[8][3];
		for (int i=0; i<parr.length; i++)
		{
			perobj[i][0]=parr[i].name;
			perobj[i][1]=parr[i].birth;
			perobj[i][2]=parr[i].id;
		}
		stm.setDataVector(perobj, columnNames);

		//Table and Header
		JTable jt= new JTable(stm);
		JTableHeader jhd=jt.getTableHeader();
		jhd.addMouseListener(new ColumnListener(jt));
		jhd.setReorderingAllowed(false);
		jhd.setResizingAllowed(true);
		jhd.setEnabled(true);
		
		//Frame
		JFrame jf = new JFrame("Square-Jobvite Demo");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(jhd, BorderLayout.NORTH);
		jf.getContentPane().add(jt, BorderLayout.CENTER);
		jf.setSize(400, 260);
		jf.setVisible(true);
	}
}
/**
 * The Table model for the sortable table 
 * Inherits from DefaultTableModel
 */
class SortableTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	@Override
	public int getColumnCount() {
		return columnIdentifiers.size();
	}
	@Override
	public int getRowCount() {
		return dataVector.size();
	}
	@Override
	public Object getValueAt(int row, int col) {
		return super.getValueAt(row, col);
	}
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int col)
	{
		switch (col)
		{
		case 0: return String.class;//Name
		case 1: return Date.class;//Dob
		case 2: return Integer.class;//Id
		default: return Object.class;
		}
	}
	
}

/**
 * Event listener for when a column header is clicked
 */
class ColumnListener extends MouseAdapter {
	protected JTable table;
	protected int sortCol = 0;
	protected boolean isSortAsc = true;

	public ColumnListener(JTable t) {
		table = t;		
	}

	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e) {
		//Get the column at the mouse click location
		TableColumnModel colModel = table.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
		int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
	
		if (sortCol == modelIndex)//if the same column is clicked again
			isSortAsc = !isSortAsc;//reverse the sort order
		else
			sortCol = modelIndex;
		//Get the data and sort 
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Vector vector = model.getDataVector();
		ColumnComparator cc = new ColumnComparator(modelIndex,isSortAsc);
		Collections.sort(vector, cc);
		//ensure the table is redrawn with the new sort order
		table.repaint();
	}

}
/**
 * Defines a person
 **/
class Person {
	final String name;
	final Date birth;
	final int id;

	/** Constructs a person. No arguments are null.
	 **/
	Person(String name, Date birth, int id) {
		this.name = name;
		this.birth = birth;
		this.id = id;
	}
	
	/** Override hashcode **/
	public int hashCode() {
		String s = new String(id + name +  birth);
		return s.hashCode();
	}
	/** Override toString(). Returns a "|" separated  string in name|dob|id format **/
	public String toString() {
		return (name + "|" + birth + "|" + id);
	}
	/** Override equals. All member variables must be equal for objects
to be equal **/
	public boolean equals(Person p) {
		if (!(this.name).equals(p.name))
			return false;
		else if ((this.id) != (p.id))
			return false;
		else if (!(this.birth).equals(p.birth))
			return false;
		else
			return true;
	}
}

/** comparator class to sort/compare Person objects **/
class PeopleComparator implements Comparator<Object> {

	public int compare(Object per1, Object per2) {
		//System.out.println("Comparing peeps...");
		String per1name = ((Person) per1).name;
		int per1id = ((Person) per1).id;
		Date per1birth = ((Person) per1).birth;

		String per2name = ((Person) per2).name;
		int per2id = ((Person) per2).id;
		Date per2birth = ((Person) per2).birth;
	//	System.out.println(per1+"--"+per2);
		if (per1name.compareTo(per2name) > 0)
			return 1;
		if (per1birth.after(per2birth))
			return 1;
		if (per1id > per2id)
			return 1;
		
		return 0;
	}

}
/**
 *  Sort a table based on a specified column
 */
class ColumnComparator implements Comparator<Object>
{
	private int column;
	private boolean isAscending;
	private boolean isIgnoreCase;
	private boolean isNullsLast = true;

	/*
	 *  The specified column will be sorted using default sort properties.
	 */
	ColumnComparator(int column)
	{
		this(column, true);
	}

	/*
	 *  The specified column will be sorted in the specified order
	 *  with the remaining default properties
	 */
	ColumnComparator(int column, boolean isAscending)
	{
		this(column, isAscending, false);
	}

	/*
	 *  The specified column will be sorted in the specified order and
	 *  case sensitivity with the remaining default properties
	 */
	ColumnComparator(int column, boolean isAscending, boolean isIgnoreCase)
	{
		setColumn( column );
		setAscending( isAscending );
		setIgnoreCase( isIgnoreCase );
	}

	/*
	 *  Set the column to be sorted
	 */
	public void setColumn(int column)
	{
		this.column = column;
	}

	/*
	 *  Set the sort order
	 */
	public void setAscending(boolean isAscending)
	{
		this.isAscending = isAscending;
	}

	/*
	 *  Set whether case should be ignored when sorting Strings
	 */
	public void setIgnoreCase(boolean isIgnoreCase)
	{
		this.isIgnoreCase = isIgnoreCase;
	}

	/*
	 *  Set nulls position in the sort order
	 */
	public void setNullsLast(boolean isNullsLast)
	{
		this.isNullsLast = isNullsLast;
	}

	/*
	 *  Implement the Comparable interface
	 */
	@SuppressWarnings("unchecked")
	public int compare(Object a, Object b)
	{
		//  The object to be sorted must be a List or an Array
		Object o1 = null;
		Object o2 = null;

		if (a instanceof List)
		{
			List list1 = (List)a;
			List list2 = (List)b;
			o1 = list1.get(column);
			o2 = list2.get(column);
		}
	
		if (a.getClass().isArray())
		{
			Object[] a1 = (Object[])a;
			Object[] a2 = (Object[])b;
			o1 = a1[column];
			o2 = a2[column];
		}

		// Treat empty strings like nulls
		if (o1 instanceof String && ((String)o1).length() == 0)
		{
			o1 = null;
		}
		if (o2 instanceof String && ((String)o2).length() == 0)
		{
			o2 = null;
		}
		
		// Handle sorting of null values
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return isNullsLast ? 1 : -1;
		if (o2 == null) return isNullsLast ? -1 : 1;

		//  Compare objects
		Object c1;
		Object c2;
		if (isAscending)
		{
			c1 = o1;
			c2 = o2;
		}
		else
		{
			c1 = o2;
			c2 = o1;
		}

		if (c1 instanceof Comparable)
		{	
			if (c1 instanceof String &&  isIgnoreCase)
				return ((String)c1).compareToIgnoreCase((String)c2);
			else
				return ((Comparable)c1).compareTo(c2);
		}
		else  // Compare as a String
		{
			System.out.println("string");
			if (isIgnoreCase)
				return c1.toString().compareToIgnoreCase(c2.toString());
			else
				return c1.toString().compareTo(c2.toString());
		}
	}
}