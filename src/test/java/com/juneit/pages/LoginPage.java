package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LoginPage {

    private WebDriver driver;
    public LoginPage(WebDriver driver) { this.driver = driver; }

//METHODS
public WebElement getSignInButton() {
    WebElement signInButton = driver.findElement(By.className(SIGNIN_BUTTON_CLASS));
    return signInButton;
}
public WebElement getSignInRestore() {
     WebElement signInRestore = driver.findElement(By.className(SIGNIN_RESTORE_OPTION_CLASS));
     return signInRestore;
    }

public WebElement getSignInEmailInput() {
     WebElement signInEmailInput = driver.findElement(By.xpath(SIGNIN_EMAIL_INPUT_XPATH));
     return signInEmailInput;
    }

public WebElement getSignInSendEmailButton() {
     WebElement signInSendEmailButton = driver.findElement(By.xpath(SIGNIN_SEND_EMAIL_BUTTON_XPATH));
     return signInSendEmailButton;
    }

public WebElement getSignInSubmitText() {
     WebElement signInSubmitText = driver.findElement(By.xpath(SIGNIN_SUBMIT_TEXT_XPATH));
     return signInSubmitText;
    }

public WebElement getSignInModalCloseButton() {
     WebElement signInModalCloseButton = driver.findElement(By.className(SIGNIN_MODAL_CLOSE_PANEL_CLASS));
     return signInModalCloseButton;
    }

    public WebElement getLoginEmailInput() {
        WebElement loginEmailInput = driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH));
        return loginEmailInput;
    }

    public WebElement getLoginPasswordInput() {
        WebElement loginPasswordInput = driver.findElement(By.name(LOGIN_PASSWORD_INPUT_FORM_NAME));
        return loginPasswordInput;
    }

    public WebElement getLoginButton() {
        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH));
        return loginButton;
    }

//LOCATORS
    public static final String SIGNIN_BUTTON_CLASS = "header__signIn";
    public static final String SIGNIN_RESTORE_OPTION_CLASS = "signIn__restore-pass";
    public static final String SIGNIN_EMAIL_INPUT_XPATH = "/html/body/div[3]/div/div/div/form/div[2]/input";
    public static final String SIGNIN_SEND_EMAIL_BUTTON_XPATH = "/html/body/div[3]/div/div/div/form/button";
    public static final String SIGNIN_SUBMIT_TEXT_XPATH = "/html/body/div[3]/div/div/p";
    public static final String SIGNIN_MODAL_CLOSE_PANEL_CLASS = "modal__close";
    public static final String LOGIN_EMAIL_INPUT_FORM_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[1]/input";
    public static final String LOGIN_PASSWORD_INPUT_FORM_NAME = "password";
    public static final String LOGIN_BUTTON_FORM_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/button";
}
