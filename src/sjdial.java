import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
public class sjdial {
	public static void main(String args[])
	{
		JDialog jd = new JDialog();
		jd.setLayout(new GridLayout(3,2));
		jd.add(new JLabel("Mahesh is a techie"));
		jd.add(new JTextField());
		jd.add(new JLabel("Mahesh is a mokkai"));
		jd.add(new JTextField());
			jd.add(new JLabel("Mahesh is a pakki"));
		jd.add(new JTextField());		
		jd.setSize(300, 100);
		jd.setVisible(true);
	}

}
 