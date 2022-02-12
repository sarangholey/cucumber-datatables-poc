package com.qa.automation.stepdefs;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class products {

	WebDriver driver;
	WebDriverWait wait;
	Actions act;
	String base_url = "http://automationpractice.com/";
	int implicit_wait_timeout_in_sec = 20;
	String expectedPageTitle = "My Store";

	// *********** Cucumber Hooks Start ******************** //
	@Before
	public void setUp()
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, 20);
		act = new Actions(driver);
	}

	@After
	public void tearDown()
	{
		driver.quit();
	}
	// *********** Cucumber Hooks ends ******************** //
	
	// Background - Keyword form feature file
	@Given("User navigated to the home application url")
	public void user_navigated_to_the_home_application_url() {
		driver.get(base_url);
		Assert.assertEquals(expectedPageTitle, driver.getTitle());
		WebElement aapLogo = driver.findElement(By.xpath("//img[@class='logo img-responsive']"));
		Assert.assertEquals(true, aapLogo.isDisplayed());
	}

	// *************** First Scenario step defs start **************** //
	@Given("User able to see the searchbox")
	public void user_able_to_see_the_searchbox() {
		System.out.println("User navigated to the home application url");
		WebElement searchBox = driver.findElement(By.xpath("//input[@id='search_query_top']"));
		Assert.assertEquals(true, searchBox.isDisplayed());
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		WebElement searchBtn = driver.findElement(By.xpath("//button[@name='submit_search']"));
		Assert.assertEquals(true, searchBtn.isDisplayed());    
	}


	@When("User add the products with defined name listed below")
	public void user_add_the_products_with_defined_name_listed_below(List<String> productList) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		for (int i=1; i<=productList.size()-1;i++){
			searchAndAddProducts(productList.get(i), i);
			System.out.println( i + " - No. Product searched & added : " + productList.get(i).toString());
		}
		WebElement closeButtonPopup = driver.findElement(By.xpath("//span[@title='Close window']"));
		wait.until(ExpectedConditions.elementToBeClickable(closeButtonPopup));
		closeButtonPopup.click();
	}

	// Common Method for iteration
	public void searchAndAddProducts(String prodName, int prodIndex)
	{
		WebElement searchBox = driver.findElement(By.xpath("//input[@id='search_query_top']"));
		searchBox.sendKeys(prodName);
		WebElement searchBtn = driver.findElement(By.xpath("//button[@name='submit_search']"));
		searchBtn.click();

		WebElement quickViewBtn = driver.findElement(By.xpath("//a/span[text()='Quick view']"));

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", quickViewBtn);
		js.executeScript("arguments[0].click();", quickViewBtn);


		WebElement addCartBtn = driver.findElement(By.xpath("//button/span[text()='Add to cart']"));
		wait.until(ExpectedConditions.elementToBeClickable(addCartBtn));
		act.click(addCartBtn).build().perform();
		
		WebElement searchBox1 = driver.findElement(By.xpath("//input[@id='search_query_top']"));
		searchBox1.clear();

	}

	@Then("User cart is updated with the products and quantity as {int}")
	public void user_cart_is_updated_with_the_products_and_quantity_as(int productCount) {
		System.out.println(productCount);
		WebElement ProdCount = driver.findElement(By.xpath("//a[@title='View my shopping cart']/span[1]"));
		
		int numberProdCount = Integer.parseInt(ProdCount.getText());
		
		Assert.assertEquals(productCount, numberProdCount);
		
	}
	// *************** First Scenario step defs end **************** //
	
	// *************** Second Scenario step defs start **************** //
	@Given("User able to see footer links section of name {string}")
	public void user_able_to_see_footer_links_section_of_name(String footerSectionName) {
		WebElement myAccFooterSection = driver.findElement(By.xpath("//a[text()='My account']"));

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", myAccFooterSection);
		Assert.assertEquals(footerSectionName, myAccFooterSection.getText());
	    
	}

	@When("All {int} links under {string} section is displayed")
	public void all_links_under_section_is_displayed(Integer linkCount, String footerSectionName) {
		WebElement myAccFooterSection = driver.findElement(By.xpath("//a[text()='My account']"));
		Assert.assertEquals(footerSectionName, myAccFooterSection.getText());
		List<WebElement> linksUnderMyAcc = driver.findElements(By.xpath("//a[text()='My account']/parent::h4/following-sibling::div//li/a"));
		Assert.assertEquals(String.valueOf(linkCount), String.valueOf(linksUnderMyAcc.size()));
	}
	
	@Then("User click on footerLinkText and verify the associated footerLinkUrlText")
	public void user_click_on_footer_link_text_and_verify_the_associated_footer_link_url_text(List<Map<String,String>> data) {
		
		// Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		for (int i=0; i<=data.size()-1;i++) {
			ClickOnFooterLinkAndVerify(data, i);
        }
		
	}
	
	// Common Method to Iterated
	public void ClickOnFooterLinkAndVerify(List<Map<String,String>> data, int index) {
        String FooterTextHyperLink = data.get(index).get("FooterLinkText");
        String LinkUrlText = data.get(index).get("FooterLinkUrlText");
  
        
        WebElement link = driver.findElement(By.xpath("//a[text()='My account']/parent::h4/following-sibling::div//li/a[text()='"+FooterTextHyperLink+"']"));
        link.click();
        
        if(driver.getCurrentUrl().contains(LinkUrlText)) {
        	Assert.assertTrue(true);
        }
        else {
        	Assert.fail();
		}
        driver.navigate().back();
	}
	// *************** Second Scenario step defs end **************** //
}
