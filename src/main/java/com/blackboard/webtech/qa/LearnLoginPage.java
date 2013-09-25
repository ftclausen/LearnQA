package com.blackboard.webtech.qa;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LearnLoginPage {
	@FindBy(how = How.CLASS_NAME, using = "login-page")
	private WebElement loginPageClass;
	@FindBy(how = How.ID, using = "user_id")
	private WebElement usernameBox;
	@FindBy(how = How.ID, using = "password")
	private WebElement passwordBox;
	@FindBy(how = How.NAME, using = "login")
	private WebElement loginButton;
	
	private WebDriver driver;
	private String url;

	protected LearnLoginPage(WebDriver driver, String url) {
		this.driver = driver;
		this.url = url;
		driver.get(url);
		PageFactory.initElements(driver, this);
		
		By loginPageTag = By.tagName("body");
		System.out.println("DEBUG: This page has class : " + loginPageClass.getAttribute("class"));
		if (! "login-page".equals(driver.findElement(loginPageTag).getAttribute("class"))) {
			throw new IllegalStateException("This is not the login page");
		}
	}
	
	public LearnLoginPage typeUsername(String username) {
		System.out.println("DEBUG: Waiting for username box");
		usernameBox.sendKeys(username);
		return this;
	}
	
	public LearnLoginPage typePassword(String password) {
		System.out.println("DEBUG: Typing password");
		passwordBox.sendKeys(password);
		return this;
	}
	
	public LearnHomePage submitLogin() {
		System.out.println("DEBUG: Submitting login");
		loginButton.submit();
		return new LearnHomePage(driver);
	}
	
	public LearnHomePage loginAs(String username, String password) {
		typeUsername(username);
		typePassword(password);
		return submitLogin();
	}
	
}
