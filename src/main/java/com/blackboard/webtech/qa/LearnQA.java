package com.blackboard.webtech.qa;

/**
 * Author: Friedrich "Fred" Clausen (friedrich.clausen@blackboard.com)
 * Date: 9/16/13
 * Time: 2:31 PM
 * 
 * Work in progress.
 * 
 * Things to enhance with
 * - Detect if not english and attempt to set bbsupport user to
 *   use english as language
 * - Use proper logging framework
 * LIMITATIONS
 * - Only works on US english
 */

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class LearnQA {
    public static void main(String[] args) {
    	
    	String username = System.getProperty("learnqa.user");
    	String password = System.getProperty("learnqa.password");
    	if (username == null || password == null) {
    		System.err.println("ERROR: Please set the \"learnqa.user\" and \"learnqa.password\" system properties.");
    		System.exit(1);
    	}

    	WebDriver driver = new FirefoxDriver();
    	// Wait max 5 seconds for an element to become visible
    	driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
    	// TODO: Pass through as file containing list
    	String url = "https://webtech-test.blackboard.com/";
    	
    	// NOTE: Not doing any assertions yet - just building the basic test flow first
    	LearnLoginPage loginPage = new LearnLoginPage(driver, url);
    	LearnHomePage homePage = loginPage.loginAs(username, password);
    	LearnSystemAdminPage adminPage = homePage.getSystemAdminPage();
    	LearnCourseLifecycle courseController = adminPage.getCoursesPage();
    	courseController.createCourse();
    	// Implement enrollInCourse(), deleteCourse(), searchCourse()
    }
    
}
