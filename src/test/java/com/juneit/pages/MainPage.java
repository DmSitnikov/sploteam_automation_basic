package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {

    private WebDriver driver;
    public MainPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public WebElement getMoreGamesButton() {
        WebElement MoreGamesButton = driver.findElement(By.xpath(SHOW_MORE_GAMES_XPATH));
        return MoreGamesButton;
    }

    public WebElement getSignInButton() {
        WebElement signInButton = driver.findElement(By.className(SIGNIN_BUTTON_CLASS));
        return signInButton;
    }

    public WebElement getComingGamesTitle() {
        WebElement comingGamesTitle = driver.findElement(By.className(COMING_GAMES_TEXT_CLASS));
        return comingGamesTitle;
    }

    public WebElement getHeaderLogo() {
        WebElement headerLogo = driver.findElement(By.className(HEADER_LOGO_CLASS));
        return headerLogo;
    }

    public WebElement getVolleyballButton() {
        WebElement volleyballButton = driver.findElement(By.className(VOLLEYBALL_BUTTON_CLASS));
        return volleyballButton;
    }

    public WebElement getCreateYourGameButton() {
        WebElement createYourGameButton = driver.findElement(By.xpath(CREATE_YOUR_GAME_XPATH));
        return createYourGameButton;
    }

    public WebElement getAllGamesTitle() {
        WebElement allGamesTitle = driver.findElement(By.className(ALL_GAMES_TITLE_CLASS));
        return allGamesTitle;
    }

    public WebElement getCampsButton() {
        WebElement campsButton = driver.findElement(By.className(CAMP_BUTTON_CLASS));
        return campsButton;
    }

    public WebElement getNewThingText() {
        WebElement newThingText = driver.findElement(By.className(NEWTHING_TEXT_ON_THE_CAMPS_BUTTON_CLASS));
        return newThingText;
    }

    public WebElement getSportCampsText() {
        WebElement sportCampsText = driver.findElement(By.className(SPORTCAMP_TEXT_CLASS));
        return sportCampsText;
    }

    public WebElement getSportCampsLogo() {
        WebElement sportCampsLogo = driver.findElement(By.className(SPORTCAMP_LOGO_CLASS));
        return sportCampsLogo;
    }

    public WebElement getSendRequestUserNameInput() {
        WebElement userNameInput = driver.findElement(By.xpath(USER_NAME_XPATH));
        return userNameInput;
    }

    public WebElement getSendRequestEmailOrPhoneInput() {
        WebElement emailPhoneInput = driver.findElement(By.name(EMAIL_PHONE_NAME));
        return emailPhoneInput;
    }

    public WebElement getSendRequestUserNameText() {
        WebElement userNameText = driver.findElement(By.xpath(USER_NAME_TEXT_XPATH));
        return userNameText;
    }

    public WebElement getSendRequestEmailOrPhoneText() {
        WebElement emailPhoneText = driver.findElement(By.xpath(EMAIL_PHONE_TEXT_XPATH));
        return emailPhoneText;
    }

    public WebElement getSendRequestButton() {
        WebElement sendRequestButton = driver.findElement(By.className(SEND_REQUEST_BUTTON_CLASS));
        return sendRequestButton;
    }

    public WebElement getCooperationText() {
        WebElement cooperationText = driver.findElement(By.className(COOPERATION_TEXT_CLASS));
        return cooperationText;
    }

    public WebElement getLoggedInUserHeader() {
        WebElement loggedInUserHeader = driver.findElement(By.className(USER_NAME_HEADER_CLASS));
        return loggedInUserHeader;
    }

    //LOCATORS
    public static final String SIGNIN_BUTTON_CLASS = "header__signIn";
    public static final String USER_NAME_HEADER_CLASS = "profileText__name";
    public static final String COMING_GAMES_TEXT_CLASS = "coming-games__title";
    public static final String HEADER_LOGO_CLASS = "header__logo";
    public static final String VOLLEYBALL_BUTTON_CLASS = "games-list__item_volleyball";
    public static final String SHOW_MORE_GAMES_XPATH = "//*[@id=\"root\"]/div[2]/section[2]/div/div[3]/a";
    public static final String CREATE_YOUR_GAME_XPATH = "//*[@id=\"root\"]/div[2]/section[3]/div/div[2]/a";
    public static final String ALL_GAMES_TITLE_CLASS = "all-games__title";
    public static final String CAMP_BUTTON_CLASS = "CampsButton_button__2sQY1";
    public static final String NEWTHING_TEXT_ON_THE_CAMPS_BUTTON_CLASS = "CampsButton_new__1YwW-";
    public static final String SPORTCAMP_TEXT_CLASS = "CampsButton_title__55qxk";
    public static final String SPORTCAMP_LOGO_CLASS = "CampsButton_img__3CoXw";
    public static final String USER_NAME_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[1]/input";
    public static final String EMAIL_PHONE_NAME = "email_or_phone";
    public static final String USER_NAME_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[1]/label";
    public static final String EMAIL_PHONE_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[2]/label";
    public static final String SEND_REQUEST_BUTTON_CLASS = "cooperation-form__submit";
    public static final String COOPERATION_TEXT_CLASS = "cooperation__text";
}
