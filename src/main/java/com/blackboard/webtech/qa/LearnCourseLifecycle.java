package com.blackboard.webtech.qa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LearnCourseLifecycle {
	@FindBy(how = How.ID, using = "pageTitleText")
	private WebElement pageTitle;
	@FindBy(how = How.LINK_TEXT, using = "Create Course")
	private WebElement createCourseLink;
	@FindBy(how = How.LINK_TEXT, using = "New")
	private WebElement newCourseMenuOption;
	@FindBy(how = How.ID, using = "courseName")
	private WebElement courseNameInputField;
	@FindBy(how = How.ID, using = "courseId")
	private WebElement courseIdInputField;
	@FindBy(how = How.ID, using = "tinymce")
	private WebElement courseDescription;
	@FindBy(how = How.ID, using = "enrollmentType_S")
	private WebElement selfEnrollmentRadioButton;
	@FindBy(how = How.NAME, using = "bottom_Submit")
	private WebElement submitButton;
	@FindBy(how = How.NAME, using = "courseInfoSearchKeyString")
	private WebElement searchDropDown;
	@FindBy(how = How.ID, using = "courseInfoSearchText")
	private WebElement searchBox;
	@FindBy(how = How.ID, using = "listContainer_databody")
	private WebElement searchResultsTable;
	@FindBy(how = How.TAG_NAME, using = "input")
	private WebElement goButtonCandidate;
	
	private String courseName;
	private String courseId;
	private WebDriver driver;
	
	public LearnCourseLifecycle(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		System.out.println("DEBUG: Page title is : " + pageTitle.getText());
		if (! pageTitle.getText().contains("Courses") ) {
			System.out.println("ERROR: This is not a course management page");
			throw new IllegalStateException("This is not a course management page");
		}
	}
	
	public static String generateCourseName() {
		DateFormat dateFormatCourseName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return("Blackboard Hosting QA Check : " + dateFormatCourseName.format(date));
	}

	public static String generateCourseID() {
		DateFormat dateFormatCourseName = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		Date date = new Date();
		return("bb-hosting-qa-check-" + dateFormatCourseName.format(date));
	}
	
	private void navigateToCourseCreation() {
		System.out.println("DEBUG: Going to make a course");
		System.out.println("DEBUG: Found link text of : " + createCourseLink.getText());
		Actions clickSequenceBuilder = new Actions(driver);
		Action clickSequencePlan = clickSequenceBuilder.moveToElement(createCourseLink)
									.moveToElement(newCourseMenuOption)
									.click()
									.build();
		clickSequencePlan.perform();
	}
	
	private void inputCourseDetails() {
		if (courseName == null) {
			courseName = LearnCourseLifecycle.generateCourseName();
		}
		if (courseId == null) {
			courseId = LearnCourseLifecycle.generateCourseID();
		}
		
		courseNameInputField.sendKeys(courseName);
		courseIdInputField.sendKeys(courseId);
		driver.switchTo().frame("courseDesctext_ifr");
		courseDescription.sendKeys("This is a test created by Blackboard Managed Hosting. Please ignore.");
		driver.switchTo().defaultContent();
		driver.switchTo().frame("contentFrame");
		selfEnrollmentRadioButton.click();
		submitButton.click();
	}
	
	public void createCourse() {
		navigateToCourseCreation();
		inputCourseDetails();
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		if (courseName == null) {
			this.courseName = LearnCourseLifecycle.generateCourseName();
		} else {
			this.courseName = courseName;
		}
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		if (courseId == null) {
			this.courseId = LearnCourseLifecycle.generateCourseID();
		} else {
			this.courseId = courseId;
		}
	}
	
	public boolean isCourseCreated() {
		// Not using annotations to find this one since it may not show up.
		// Also using implicit wait wit the driver so don't need to explicitly 
		// wait here
		// Using findElements instead of findElement since it will return empty list
		// instead of throwing an exception
		return driver.findElements(By.id("goodMsg1")).size() > 0;  
	}
	
	public boolean isCourseSearchable() {
		WebElement goButton = null;
		Select searchOn = new Select(searchDropDown);
		searchOn.selectByValue("CourseId");
		searchBox.sendKeys(courseId);
		List<WebElement> inputs = driver.findElements(By.tagName("input"));
		for (WebElement currentElement : inputs) {
			if (currentElement.getAttribute("value").equals("Go")) {
				goButton = currentElement;
				break;
			}
		}
		goButton.click();
		int resultCount = searchResultsTable.findElements(By.tagName("tr")).size();
		System.out.println("DEBUG: Results found are : " + resultCount);
		return resultCount > 0;
	}
}