package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EventsCalendarPage {

    private WebDriver driver;
    public EventsCalendarPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public List<WebElement> getCalendarDate() {
        List<WebElement> CalendarDate = driver.findElements(By.className(DATE_CALENDER_CLASS));
        return CalendarDate;
    }

    public List<WebElement> getCityFilterOption() {
        List<WebElement> CityFilterOption = driver.findElements(By.className(CALENDER_CITY_FILTER_OPTION_CLASS));
        return CityFilterOption;
    }

    public WebElement getCityDropDown() {
        WebElement CityDropDown = driver.findElement(By.className(CITY_DROPDOWN_CLASS));
        return CityDropDown;
    }

    public WebElement getNoGamesMassage() {
        WebElement NoGamesMassage = driver.findElement(By.className(NO_GAMES_MESSAGE_CLASS));
        return NoGamesMassage;
    }

    //LOCATORS
    public static final String DATE_CALENDER_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String CITY_DROPDOWN_CLASS = "location__default";
    public static final String CALENDER_CITY_FILTER_OPTION_CLASS = "location__item";
    public static final String NO_GAMES_MESSAGE_CLASS = "SearchPage_tabPanelContainer__1Lxhs";
}
