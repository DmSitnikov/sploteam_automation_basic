package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class AuthonticationPage {

    private WebDriver driver;
    public AuthonticationPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public WebElement getAuthTab() {
        WebElement authTab = driver.findElement(By.cssSelector(AUTH_TABS_CSS));
        return authTab;
    }

    public WebElement getAuthNameField() {
        WebElement authNameField = driver.findElement(By.xpath(AUTH_TABS_NAME_INPUT_XPATH));
        return authNameField;
    }

    public WebElement getAuthEmailField() {
        WebElement authEmailField = driver.findElement(By.xpath(AUTH_TABS_EMAIL_INPUT_XPATH));
        return authEmailField;
    }

    public WebElement getAuthPhoneNumberField() {
        WebElement authPhoneNumberField = driver.findElement(By.xpath(AUTH_TABS_PHONE_NUMBER_INPUT_XPATH));
        return authPhoneNumberField;
    }

    public WebElement getAuthPasswordField() {
        WebElement authPasswordField = driver.findElement(By.xpath(AUTH_TABS_PASSWORD_INPUT_XPATH));
        return authPasswordField;
    }

    public WebElement getAuthCreateAccButton() {
        WebElement authCreateAccButton = driver.findElement(By.xpath(AUTH_TABS_CREATE_ACC_BUTTON_XPATH));
        return authCreateAccButton;
    }

    public WebElement getAuthWrongEmailText() {
        WebElement authWrongEmailText = driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS));
        return authWrongEmailText;
    }


    //LOCATORS
    public static final String AUTH_TABS_CSS = ".auth-tabs > div:nth-child(2)";
    public static final String AUTH_TABS_NAME_INPUT_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[1]/input";
    public static final String AUTH_TABS_EMAIL_INPUT_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[2]/input";
    public static final String AUTH_TABS_PHONE_NUMBER_INPUT_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[3]/input";
    public static final String AUTH_TABS_PASSWORD_INPUT_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[4]/input";
    public static final String AUTH_TABS_CREATE_ACC_BUTTON_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/button";
    public static final String COMMON_TEXT_ELEMENT_ERROR_CLASS = "form_error__2xL0z"; //Аккаунт уже зарегистрирован
}


