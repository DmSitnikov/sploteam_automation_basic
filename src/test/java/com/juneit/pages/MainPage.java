package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;
    public MainPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public WebElement getMoreGamesButton() {
        WebElement MoreGamesButton = driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS));
        return MoreGamesButton;
    }

    public WebElement getSignInButton() {
        WebElement signInButton = driver.findElement(By.className(SIGNIN_BUTTON_CLASS));
        return signInButton;
    }
    //LOCATORS
    public static final String SIGNIN_BUTTON_CLASS = "header__signIn";
    public static final String MORE_GAMES_BUTTON_CLASS = "games-list__item_more-games";
}
