package com.blackboard.webtech.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LearnSystemAdminPage {
	@FindBy(how = How.CLASS_NAME, using = "pageTitle")
	private WebElement pageTitle;
	@FindBy(how = How.ID, using = "nav_list_courses")
	private WebElement courseLink;
	
	private WebDriver driver;
	
	public LearnSystemAdminPage(WebDriver driver) {
		this.driver = driver;
		driver.switchTo().frame("contentFrame");
		PageFactory.initElements(driver, this);
		
		String headingText = pageTitle.findElement(By.tagName("h1")).getText();
		System.out.println("DEBUG: The heading text is : " + headingText);
		if (! headingText.contains("Administrator Panel")) {
			System.out.println("ERROR: This is not the admin page");
			throw new IllegalStateException("This is not the System Admin page");
		}
	}
	
	public LearnCourseLifecycle getCoursesPage() {
		courseLink.click();
		return new LearnCourseLifecycle(driver);
		
	}

}
