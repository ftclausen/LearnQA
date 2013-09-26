package com.blackboard.webtech.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LearnHomePage {
	@FindBy(how = How.CLASS_NAME, using = "frameset")
	private WebElement homePageClass;
	@FindBy(how = How.ID, using = "SystemAdmin.label")
	private WebElement systemAdmin;
	private WebDriver driver;
	
	public LearnHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
		By homePageTag = By.tagName("body");
		System.out.println("DEBUG: This page has class : " + homePageClass.getAttribute("class"));
		if (! "frameset".equals(driver.findElement(homePageTag).getAttribute("class"))) {
			throw new IllegalStateException("This is not the Learn home page");
		}
	}
	
	public LearnSystemAdminPage getSystemAdminPage() {
		driver.switchTo().frame("navFrame");
		systemAdmin.findElement(By.tagName("a")).click();
		return new LearnSystemAdminPage(driver);
		
	}
		
}
