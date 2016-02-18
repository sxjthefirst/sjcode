import junit.framework.TestCase;


public class TestTest extends TestCase {

	public TestTest(String name) {
			super(name);
			System.out.println("Start time " + System.currentTimeMillis());
	}
	public void test001() 
	{
		SJFrame sf=new SJFrame();
		sf.setVisible(true);
		assertEquals(sf.getTitle(),"Java with SJ");
	}
}
