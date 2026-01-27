package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EventsCalendarPage {

    private WebDriver driver;
    public EventsCalendarPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public List<WebElement> getCalendarDate() {
        List<WebElement> calendarDate = driver.findElements(By.className(DATE_CALENDER_CLASS));
        return calendarDate;
    }

    public List<WebElement> getCityFilterOption() {
        List<WebElement> cityFilterOption = driver.findElements(By.className(CALENDER_CITY_FILTER_OPTION_CLASS));
        return cityFilterOption;
    }

    public WebElement getCityDropDown() {
        WebElement cityDropDown = driver.findElement(By.className(CITY_DROPDOWN_CLASS));
        return cityDropDown;
    }

    public WebElement getNoGamesMassage() {
        WebElement noGamesMassage = driver.findElement(By.className(NO_GAMES_MESSAGE_CLASS));
        return noGamesMassage;
    }

    public List<WebElement> getSportArtFilterOption() {
        List<WebElement> sportArtFilterOption = driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS));
        return sportArtFilterOption;
    }

    public List<WebElement> getArenaFilterOption() {
        List<WebElement> arenaFilterOption = driver.findElements(By.className(ARENA_FILTER_CLASS));
        return arenaFilterOption;
    }

    public WebElement getSportArtFilterDropDown() {
        WebElement sportArtFilterDropDown = driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH));
        return sportArtFilterDropDown;
    }

    //LOCATORS
    public static final String DATE_CALENDER_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String CITY_DROPDOWN_CLASS = "location__default";
    public static final String CALENDER_CITY_FILTER_OPTION_CLASS = "location__item";
    public static final String NO_GAMES_MESSAGE_CLASS = "SearchPage_tabPanelContainer__1Lxhs";
    public static final String SPORT_ART_FILTER_XPATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[3]/div[1]/div[2]/div";
    public static final String SPORT_ART_FILTER_OPTION_CLASS = "GrayRoundedSelect_dropdownItem__18xe6";
    public static final String ARENA_FILTER_CLASS = "GrayRoundedSelect_notFlex__j02vB";
    public static final String SPORT_ART_OPTION_ON_GAME_CLASS = "EventCard_sport__2ZcAA";
}
