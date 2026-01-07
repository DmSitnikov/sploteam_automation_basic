package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePage {

    private WebDriver driver;
    public ProfilePage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public WebElement getLogoutButton() {
        WebElement logoutButton = driver.findElement(By.className(LOGOUT_BUTTON_CLASS));
        return logoutButton;
    }

    public WebElement getProfileUserName() {
        WebElement profileUserName = driver.findElement(By.className(USER_NAME_PROFILE_CLASS));
        return profileUserName;
    }

    public WebElement getProfileUserEmail() {
        WebElement profileUserEmail = driver.findElement(By.className(USER_EMAIL_PROFILE_CLASS));
        return profileUserEmail;
    }

    public WebElement getDepositButton() {
        WebElement depositButton = driver.findElement(By.className(DEPOSIT_BUTTON_PROFILE_CLASS));
        return depositButton;
    }

    public WebElement getEditButton() {
        WebElement editButton = driver.findElement(By.className(EDIT_BUTTON_PROFILE_CLASS));
        return editButton;
    }

    public WebElement getOwnDepositText() {
        WebElement ownDepositText = driver.findElement(By.xpath(OWN_DEPOSIT_TEXT_XPATH));
        return ownDepositText;
    }

    //LOCATORS
    public static final String LOGOUT_BUTTON_CLASS = "LKLayout_exit__1QjSv";
    public static final String USER_NAME_PROFILE_CLASS = "ProfileCard_name__2yGm-";
    public static final String USER_EMAIL_PROFILE_CLASS = "ProfileCard_contacts__eSdIc";
    public static final String DEPOSIT_BUTTON_PROFILE_CLASS = "header__signIn";
    public static final String EDIT_BUTTON_PROFILE_CLASS = "OrangeLink_orangeLink__34ZRK";
    public static final String OWN_DEPOSIT_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div[2]/div/div/div[3]/div[1]";
}