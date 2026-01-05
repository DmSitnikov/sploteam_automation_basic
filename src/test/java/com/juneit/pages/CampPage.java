package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CampPage {

    private WebDriver driver;
    public CampPage(WebDriver driver) { this.driver = driver; }

//METHODS
public WebElement getCampButton() {
    WebElement campButton = driver.findElement(By.className(CAMP_BUTTON_CLASS));
    return campButton;
}

public WebElement getCampYear() {
    WebElement campYear = driver.findElement(By.xpath(CAMP_YEAR_PATH));
    return campYear;
    }
public List<WebElement> getCampMonth() {
    List<WebElement> campMonth = driver.findElements(By.className(CAMP_MONTH_CLASS));
    return campMonth;
    }

public List<WebElement> getPepsiCamps() {
    List<WebElement> pepsiCamps = driver.findElements(By.xpath("//div[contains(text(),'Pepsi')]"));
    return pepsiCamps;
    }

public List<WebElement> getCampContainerCard() {
    List<WebElement> campContainerCard = driver.findElements(By.className(CAMP_CARD_CONTAINER_CLASS));
    return campContainerCard;
    }


//LOCATORS
public static final String CAMP_BUTTON_CLASS = "CampsButton_button__2sQY1";
public static final String CAMP_YEAR_PATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div[1]/div[3] ";
public static final String CAMP_MONTH_CLASS = "NavLink_navLink__text__zfi3X";
public static final String CAMP_CARD_CONTAINER_CLASS = "CampCard_container__1BHZa";
public static final String MONTH_NO_CAMP_TEXT_CLASS = "CampsSearchPage_searchResults__Yio_Q";
public static final String PEPSI_LOGO_CLASS = "CampCard_logo__img__2r6nR";
}