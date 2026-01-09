package com.juneit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EditPage {

    private WebDriver driver;
    public EditPage(WebDriver driver) { this.driver = driver; }

    //METHODS
    public WebElement getEditNameInput() {
        WebElement editNameInput = driver.findElement(By.xpath(EDIT_NAME_INPUT_XPATH));
        return editNameInput;
    }

    public WebElement getEditSaveButton() {
        WebElement editSaveButton = driver.findElement(By.xpath(EDIT_SAVE_BUTTON_XPATH));
        return editSaveButton;
    }

    public WebElement getEditSaveText() {
        WebElement editSaveText = driver.findElement(By.xpath(EDIT_SAVE_SUCCESS_TEXT_XPATH));
        return editSaveText;
    }

    public WebElement getDeleteNameButton() {
        WebElement deleteNameButton = driver.findElement(By.xpath(DELETE_NAME_BUTTON_XPATH));
        return deleteNameButton;
    }

    public WebElement getCloseSavePanelButton() {
        WebElement closeSavePanelButton = driver.findElement(By.className(CLOSE_SAVE_PANEL_CLASS));
        return closeSavePanelButton;
    }


    //LOCATORS
    public static final String EDIT_NAME_INPUT_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[1]/input";
    public static final String DELETE_NAME_BUTTON_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[1]/span";
    public static final String EDIT_SAVE_BUTTON_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/button";
    public static final String EDIT_SAVE_SUCCESS_TEXT_XPATH = "/html/body/div[3]/div/div/p";
    public static final String CLOSE_SAVE_PANEL_CLASS = "modal__close";
}