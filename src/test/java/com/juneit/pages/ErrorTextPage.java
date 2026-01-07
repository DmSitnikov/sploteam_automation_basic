package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ErrorTextPage {

    private WebDriver driver;
    public ErrorTextPage(WebDriver driver) { this.driver = driver; }


    //METHOD
    public WebElement getErrorText() {
        WebElement errorText = driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS));
        return errorText;
    }

    //LOCATORS
    public static final String COMMON_TEXT_ELEMENT_ERROR_CLASS = "form_error__2xL0z";
}