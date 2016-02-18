package atlassian;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ConfCloudTests_1 {

	private final static WebDriver driver = new FirefoxDriver();

	@AfterClass public static void afterAllTests() {
		driver.close();
	}

	public static void main(String[] args) {	

	}
	@BeforeTest
	public void before() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://sxjthefirst.atlassian.net/");
	}

	//Test 1: Can user create a new page?
	@Test
	public void canCreatePage() {
		CloudLoginPage page = PageFactory.initElements(driver, CloudLoginPage.class);
		page.typeUsername("sxjthefirst@gmail.com");
		page.typePassword("Atlas123$");
		CloudHomePage home=page.submitLogin();
		String retValue=home.clickCreate();
		Assert.assertTrue(retValue.startsWith("Add Page"));
	}

	//Test 2: can user see restrictions on an existing page
	@Test
	public void canViewPageRestriction() {
		CloudLoginPage page = PageFactory.initElements(driver, CloudLoginPage.class);
		page.typeUsername("sxjthefirst@gmail.com");
		page.typePassword("Atlas123$");

		CloudHomePage home=page.submitLogin();
		WikiPage wiki=home.openWiki();
		String retValue=wiki.listRestrictions();
		Assert.assertTrue(retValue.contains("Administrator"));
	}
	
	//Test 3: can add a new restriction
	@Test
	public void canAddPageRestriction() {
		CloudLoginPage page = PageFactory.initElements(driver, CloudLoginPage.class);
		page.typeUsername("sxjthefirst@gmail.com");
		page.typePassword("Atlas123$");

		CloudHomePage home=page.submitLogin();
		WikiPage wiki=home.openWiki();
		String retValue=wiki.addRestrictions("site-admins");
		Assert.assertTrue(retValue.contains("site-admins"));
	}
}
