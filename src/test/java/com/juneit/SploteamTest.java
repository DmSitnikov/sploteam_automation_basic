package com.juneit;

import com.juneit.pages.*;
import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SploteamTest {
    private final PropertiesLoader properties = new PropertiesLoader ();
    private final WebDriver driver = new ChromeDriver ();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private AuthonticationPage authonticationPage = new AuthonticationPage(driver);
    private MainPage mainPage = new MainPage(driver);
    private EventsCalendarPage eventsCalendarPage = new EventsCalendarPage(driver);
    private CampPage campPage = new CampPage(driver);
    private LoginPage loginPage = new LoginPage(driver);
    private ErrorTextPage errorTextPage = new ErrorTextPage(driver);
    private ProfilePage profilePage = new ProfilePage(driver);
    private EditPage editPage = new EditPage(driver);

    @Before
    public void setup () {
        driver.manage ().window ().maximize ();
        driver.manage ().timeouts ().implicitlyWait (Duration.ofSeconds (5));
        ((HasAuthentication) driver).register (UsernameAndPassword.of (properties.username,
                properties.password));
        driver.get (properties.baseUrl);
    }


    @After public void
    close () {
        driver.close ();
    }

    @Test
    public void assertMainPageIsLoaded() {
        mainPage.getSignInButton().isDisplayed();
        mainPage.getComingGamesTitle().isDisplayed();
        mainPage.getVolleyballButton().isDisplayed();
        mainPage.getHeaderLogo().isDisplayed();
        mainPage.getMoreGamesButton().isDisplayed();
        mainPage.getCreateYourGameButton().isDisplayed();
        String allGamesTitle = mainPage.getAllGamesTitle().getText();
        assertEquals("Играй, тренируйся\n" +
                "и улучшай свои навыки\n" +
                "в командных видах спорта", allGamesTitle);
    }

    @Test
    public void assertCampSection () {
        mainPage.getCampsButton().isDisplayed();
        String newThing = mainPage.getNewThingText().getText();
        assertEquals("Новинка!", newThing);
        String sportCamp = mainPage.getSportCampsText().getText();
        assertEquals("Спортивные кемпы", sportCamp);
        mainPage.getSportCampsLogo().isDisplayed();
    }

    @Test
    public void assertSendRequestSection () {
        mainPage.getSendRequestUserNameInput().isDisplayed();
        mainPage.getSendRequestEmailOrPhoneInput().isDisplayed();
        String userNameField = mainPage.getSendRequestUserNameText().getText();
        assertEquals("Ваше имя", userNameField);
        String emailPhone = mainPage.getSendRequestEmailOrPhoneText().getText();
        assertEquals("E-mail или телефон", emailPhone);
        mainPage.getSendRequestButton().isDisplayed();
        String cooperationText = mainPage.getCooperationText().getText();
        assertEquals("Присоединяйтесь к самой крупной сети игроков, готовых придти к вам уже сегодня!", cooperationText);
    }

    @Test
    public void assertfindAllLinks() {
        List<WebElement> allinks = driver.findElements(By.tagName("a"));
        List<WebElement> externallinks = driver.findElements(By.xpath("//a[contains(@href, 'https')]"));
        System.out.println(allinks.size());
        System.out.println(externallinks.size());
    }

    @Test
    public void assertLoginPositive() {
        login("sytsytnikov@gmail.com", "Password2");
        mainPage.getLoggedInUserHeader().click();
        profilePage.getLogoutButton().click();
        mainPage.getSignInButton().isDisplayed();
    }

    @Test
    public void assertLoginPasswordShortError() {
        login("sytsytnikov@gmail.com", "Pas");
        errorTextPage.getErrorText().isDisplayed();
        assertEquals("Неверное значение", errorTextPage.getErrorText().getText());
    }

    @Test
    public void assertLoginEmailSpellingError() {
        login("sysytnikov@gmail.com", "Password1");
        errorTextPage.getErrorText().isDisplayed();
        assertEquals("Такой логин или пароль не найдены", errorTextPage.getErrorText().getText());
    }

    @Test
    public void assertLoginEmailEmptyFieldError() {
        login("", "Password2");
        loginPage.getLoginEmailInput().isDisplayed();
        assertEquals("", loginPage.getLoginEmailInput().getText());
        loginPage.getLoginButton().click();
        assertEquals(driver.switchTo().activeElement(), loginPage.getLoginEmailInput()); // проверяем фокус на активном элементе
    }

    @Test
    public void assertLoginEmailNoDotError() {
        login("sytsytnikov@gmailcom", "Password2");
        errorTextPage.getErrorText().isDisplayed();
        assertEquals("Неверное значение", errorTextPage.getErrorText().getText());
    }

    @Test
    public void assertLoginLongPasswordError() {
        login("sytsytnikov@gmail.com", "Password1211");
        errorTextPage.getErrorText().isDisplayed();
        assertEquals("Неверный пароль", errorTextPage.getErrorText().getText());
    }

    @Test
    public void assertProfilePagePositive() {
        login("sytsytnikov@gmail.com", "Password2");
        mainPage.getLoggedInUserHeader().click();
        profilePage.getProfileUserName().isDisplayed();
        profilePage.getProfileUserEmail().isDisplayed();
        profilePage.getDepositButton().isDisplayed();
        profilePage.getEditButton().isDisplayed();
        String OwnDeposit = profilePage.getOwnDepositText().getText();
        Assert.assertTrue(OwnDeposit.contains("Личный счёт")); //проверяем что нужная нам часть текста находится в этом элементе
        logout();
    }

    @Test
    public void assertEditUserInfo() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        String nameBefore = editPage.getEditNameInput().getAttribute("value");
        Assert.assertTrue(nameBefore.contains("Dmitriy Sitnikov"));
        editPage.getDeleteNameButton().click(); //Удаляю имя которое было введено ранее
        editPage.getEditNameInput().sendKeys("Vanja Ivanov");
        assertEquals("Сохранить изменения", editPage.getEditSaveButton().getText());
        editPage.getEditSaveButton().click();
        assertEquals("Ваш профиль успешно обновлён", editPage.getEditSaveText().getText());
        editPage.getCloseSavePanelButton().click();
        mainPage.getLoggedInUserHeader().click();
        driver.navigate().refresh();
        assertEquals("Vanja Ivanov", mainPage.getLoggedInUserHeader().getText());
        goToEditProfile();
        editPage.getDeleteNameButton().click();
        editPage.getEditNameInput().sendKeys("Dmitriy Sitnikov");
        editPage.getEditSaveButton().click();
        editPage.getCloseSavePanelButton().click();
        mainPage.getLoggedInUserHeader().click();
        driver.navigate().refresh();
        assertEquals("Dmitriy Sitnikov", mainPage.getLoggedInUserHeader().getText());
        logout();
    }

    @Test
    public void assertEmptyNameField() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        editPage.getDeleteNameButton().click(); //Удаляю имя которое было введено ранее
        editPage.getEditSaveButton().click();
        errorTextPage.getErrorText().isDisplayed();
        assertEquals("Выберите значение", errorTextPage.getErrorText().getText());
    }

    @Test
    public void assertWrongAmountError() {
        login("sytsytnikov@gmail.com", "Password2");
        mainPage.getHeaderLogo().click();
        driver.findElement(By.className(ADD_MONEY_BUTTON_CLASS)).click();
        driver.findElement(By.className(INPUT_ADD_MONEY_CLASS)).sendKeys("b");
        driver.findElement(By.cssSelector(TOPUP_BALANCE_BUTTON_CSS)).click();
        assertEquals("Неверное значение", driver.findElement(By.className(WRONG_AMOUNT_FORMAT_ERROR_CLASS)).getText());
        logout();
    }


    @Test
    public void assertNoAtEmail() {
        login("sytsytnikovgmail.com", "Password2");
        String emailNoAtError = loginPage.getLoginEmailInput().getAttribute("validationMessage");
        assertEquals("Die E-Mail-Adresse muss ein @-Zeichen enthalten. In der Angabe \"sytsytnikovgmail.com\" fehlt ein @-Zeichen.", emailNoAtError);
    }

    @Test
    public void assertCreatedGames() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        for ( int i = 0; i < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(i).getText());
        }
        System.out.println(driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size());
    }

    @Test
    public void assertPesokArenaFilter() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        driver.findElement(By.xpath(ARENA_FILTER_XPATH)).click();
        driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).get(1).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(ARENA_FILTER_XPATH)), "Песок"));

        for ( int i = 0; i < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(i).getText());
            assertEquals("\"Песок\"", driver.findElements(By.className(ARENA_NAME_ON_GAME_CLASS)).get(i).getText());
        }
        System.out.println(driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size());
    }

    @Test
    public void assertPlayerLightLevelFilter() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        driver.findElement(By.xpath(LEVEL_FILTER_XPATH)).click();
        driver.findElements(By.className(LEVEL_FILTER_OPTION_CLASS)).get(1).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(LEVEL_FILTER_XPATH)), "Лайт"));

        for ( int i = 0; i < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(i).getText());
            assertEquals("Уровень - Лайт", driver.findElements(By.className(LEVEL_OPTION_LIGHT_CLASS)).get(i).getText());
        }
        System.out.println(driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size());
    }

    @Test
    public void assertPlayerLevelSportArtFilter() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        eventsCalendarPage.getCityDropDown().click();
        driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(7).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)), "Пляжный теннис"));

        driver.findElement(By.xpath(LEVEL_FILTER_XPATH)).click();
        driver.findElements(By.className(LEVEL_FILTER_OPTION_CLASS)).get(2).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(LEVEL_FILTER_XPATH)), "Медиум"));

        for ( int i = 0; i < driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Пляжный теннис", driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).get(i).getText());
            assertEquals("Уровень - Медиум", driver.findElements(By.className(LEVEL_OPTION_MEDIUM_CLASS)).get(i).getText());
        }

        System.out.println(driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).size());
    }

    @Test
    public void assertUserProfileGenderPositive() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        String genderBefore = driver.findElement(By.className(PROFILE_GENDER_VALUE_CLASS)).getText();
        Assert.assertTrue(genderBefore.contains("Мужской"));
        System.out.println("Орегинальный пол: " + genderBefore);
        driver.findElement(By.className(PROFILE_GENDER_TOGGLE_DROPDOWN_CLASS)).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(OPEN_FEMALE_GENDER_XPATH)), "Женский"));
        driver.findElement(By.xpath(OPEN_FEMALE_GENDER_XPATH)).click();
        String newGender = driver.findElement(By.xpath(SELECTED_FEMALE_GENDER_XPATH)).getText();
        //Assert.assertEquals("Женский", newGender);
        System.out.println("Новый пол: " + newGender);
        driver.findElement(By.className(PROFILE_GENDER_TOGGLE_DROPDOWN_CLASS)).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(OPEN_MALE_GENDER_XPATH)), "Мужской"));
        driver.findElement(By.xpath(OPEN_MALE_GENDER_XPATH)).click();
        System.out.println("Возврат к орегинальному полу: " + genderBefore);
        logout();
    }

    @Test
    public void assertUserProfileSwitchGender() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        String selectedGender = driver.findElement(By.className(PROFILE_GENDER_VALUE_CLASS)).getText();
        driver.findElement(By.className(PROFILE_GENDER_TOGGLE_DROPDOWN_CLASS)).click();

        if (selectedGender.equals("Мужской")) {
            driver.findElement(By.xpath(OPEN_FEMALE_GENDER_XPATH)).click();
        } else {
            driver.findElement(By.xpath(OPEN_MALE_GENDER_XPATH)).click();
        }

        editPage.getEditSaveButton().click();
        editPage.getCloseSavePanelButton().click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.className(PROFILE_GENDER_VALUE_CLASS), selectedGender)));

        String updatedGender = driver.findElement(By.className(PROFILE_GENDER_VALUE_CLASS)).getText();
        System.out.println("Пол: " + updatedGender);
        logout();
    }

    @Test
    public void assertEvenNumberDates() {
        mainPage.getMoreGamesButton().click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4)); // assert page loaded

        // apply arena filter
        driver.findElement(By.xpath(ARENA_FILTER_XPATH)).click();
        driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).get(5).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(ARENA_FILTER_XPATH)), "Пляж тест"));

        int datesCount = driver.findElements(By.className(DATE_CALENDAR_CLASS)).size();

        for (int i = 0; i < datesCount; i++) {
            // re-fetch the element each time to avoid StaleElementReference
            WebElement dateElement = driver.findElements(By.className(DATE_CALENDAR_CLASS)).get(i);
            String rawText = dateElement.getText().trim();

            // skip "Today" and empty values
            if (rawText.equalsIgnoreCase("Сегодня") || rawText.isEmpty()) {
                continue;
            }

            // extract digits only (e.g. "6" from "6" or " 6 ")
            String digits = rawText.replaceAll("\\D+", "");
            if (digits.isEmpty()) {
                continue;
            }

            int day = Integer.parseInt(digits);

            // click only EVEN dates
            if (day % 2 != 0) {
                continue;
            }

            // click on the date
            dateElement.click();

            // wait for game elements to load (if they are guaranteed — wait for > 0)
            // wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(EVENT_TYPE_ON_GAME_CLASS), 0));

            // now validate games for the selected arena
            int gamesCount = driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size();

            for (int j = 0; j < gamesCount; j++) {
                assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(j).getText());
                assertEquals("\"Пляж тест\"", driver.findElements(By.className(ARENA_NAME_ON_GAME_CLASS)).get(j).getText());
            }

            System.out.println("Всего игр: " + gamesCount);

            String Time = null;
            String Price = null;

            for (int k = 0; i < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); i++) {


                Time = driver.findElements(By.className(ARENA_CREATED_GAME_TIME_CLASS)).get(k).getText();
                Price = driver.findElements(By.className(ARENA_CREATED_GAME_PRICE_CLASS)).get(k).getText();
            }

            System.out.println("Время и Цена: " + Time);
        }
    }

    @Test
    public void assertCreatedGamesForEachDate() {
        mainPage.getMoreGamesButton().click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        for ( int i = 0; i < driver.findElements(By.className(DATE_CALENDAR_CLASS)).size(); i++ ) {

            driver.findElements(By.className(DATE_CALENDAR_CLASS)).get(i).click();

            if (driver.findElements(By.className(DATE_CALENDAR_CLASS)).get(i).getText().equals("Сегодня")) {
                System.out.println("На сегодня игр нет!");
            }

            if (driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).isEmpty()){
                assertEquals("Упс... на этот день нет ни одной игры, но Вы можете найти подходящую игру в другой день.", driver.findElement(By.className(NO_GAMES_MESSAGE_CLASS)).getText());
                System.out.println("На выбранную дату игр нет " + driver.findElements(By.className(DATE_CALENDAR_CLASS)).get(i).getText() + "декабря");
            } else {
                for ( int j = 0; j < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); j++ ) {
                    assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(j).getText());
                }
                System.out.println(driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size());
            }
        }
    }

    @Test
    public void assertFBaseArenaFilterWithIf() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(ARENA_FILTER_XPATH)), "Все арены"));// assert page loaded

        // how to get number of options in drop down?
        driver.findElement(By.xpath(ARENA_FILTER_XPATH)).click();
        int arenaOptionSize = driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).size();

        for (int i = 0; i < arenaOptionSize; i++){
            if (driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).get(i).getText().equals("F-Base")){
                driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).get(i).click();
                break;
            }
        }
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(ARENA_FILTER_XPATH)), "F-Base"));// assert page loaded

        for ( int i = 0; i < driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(i).getText());
            assertEquals("\"F-Base\"", driver.findElements(By.className(ARENA_NAME_ON_GAME_CLASS)).get(i).getText());
        }

        System.out.println(driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size());
    }

    @Test
    public void assertSportArtArenaFilterWithIf() {
        mainPage.getMoreGamesButton().click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));// assert page loaded

        // how to get number of options in drop down?
        eventsCalendarPage.getSportArtFilterDropDown().click();
        int sportArtOptionSize = driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).size();

        for (int i = 0; i < sportArtOptionSize; i++){
            if (driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(i).getText().equals("Футбол")){
                driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(i).click();
                break;
            }
        }
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)), "Футбол"));// assert page loaded

        for ( int i = 0; i < driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).size(); i++ ) {
            assertEquals("Игра", driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).get(i).getText());
            assertEquals("Футбол", driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).get(i).getText());
        }

        System.out.println(driver.findElements(By.className(SPORT_ART_OPTION_ON_GAME_CLASS)).size());
    }

    @Test
    public void checkDepositCheckBox() {
        login("sytsytnikov@gmail.com", "Password2");
        mainPage.getHeaderLogo().click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.className(DEPOSIT_CHECKBOX_TEXT_CLASS)), "Возвращать денежные средства на личный счет"));

        Assert.assertFalse(driver.findElement(By.xpath(DEPOSIT_CHECKBOX_INPUT_XPATH)).isSelected());

        driver.findElement(By.className(DEPOSIT_CHECKBOX_TEXT_CLASS)).click();
        wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath(DEPOSIT_CHECKBOX_INPUT_XPATH), true));

        driver.findElement(By.className(DEPOSIT_CHECKBOX_TEXT_CLASS)).click();
        wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath(DEPOSIT_CHECKBOX_INPUT_XPATH), false));
    }

    @Test
    public void assertAmountOfCampsPerMonth() {
        driver.findElement(By.className(CAMP_BUTTON_CLASS)).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(CAMP_YEAR_PATH)), "2024"));// assert page loaded
        driver.findElement(By.xpath(CAMP_YEAR_PATH)).click();

        int campMonthCount = driver.findElements(By.className(CAMP_MONTH_CLASS)).size();
        String textMonth = null;

        for (int i = 1; i < campMonthCount; i++) {

            WebElement monthElement = driver.findElements(By.className(CAMP_MONTH_CLASS)).get(i);
            textMonth = monthElement.getText().trim();

            monthElement.click();

            int campsCount = driver.findElements(By.className(CAMP_CARD_CONTAINER_CLASS)).size();

            if (driver.findElements(By.className(CAMP_CARD_CONTAINER_CLASS)).isEmpty()) {
                assertEquals("Упс... не удалось найти ни одного кемпа.", driver.findElement(By.className(MONTH_NO_CAMP_TEXT_CLASS)).getText());
                System.out.println("На месяц " + textMonth + " кемпов нет ");
            } else {
                System.out.println("На месяц " + textMonth + " найдено кемпов: " + campsCount);
            }
        }
    }

    @Test
    public void assertSportArtsPerDay() {
        mainPage.getMoreGamesButton().click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));// assert page loaded

        int gamePerDayCount = driver.findElements(By.className(DATE_CALENDER_CLASS)).size();

        // how to get number of options in drop down?
        eventsCalendarPage.getSportArtFilterDropDown().click();
        int sportArtFilter = driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).size();

        for (int i = 1; i < gamePerDayCount; i++) {

            WebElement dayElement = driver.findElements(By.className(DATE_CALENDER_CLASS)).get(i);
            String textDay = dayElement.getText().trim();
            dayElement.click();
            resetSportFilter();

            int gamesWithoutFilter = driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size();

            if (gamesWithoutFilter == 0) {
                assertEquals(
                        "Упс... на этот день нет ни одной игры, но Вы можете найти подходящую игру в другой день.",
                        driver.findElement(By.className(NO_GAMES_MESSAGE_CLASS)).getText());
                System.out.println(driver.findElement(By.className(NO_GAMES_MESSAGE_CLASS)).getText());
                continue; //ПЕРЕХОД К СЛЕДУЮЩЕМУ ДНЮ
            }

            for (int j = 1; j < sportArtFilter; j++){
                eventsCalendarPage.getSportArtFilterDropDown().click();
                WebElement gameElement = driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(j);

                gameElement.click();
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

                int gamesCount = driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size();

                    System.out.println("На день " + textDay + " найдено игр: " + gamesCount);
                }

            }
        }

    @Test
    public void assertRegistrationForm() {
        mainPage.getSignInButton().click();
        authonticationPage.getAuthTab().click();
        authonticationPage.getAuthNameField().isDisplayed();
        authonticationPage.getAuthEmailField().isDisplayed();
        authonticationPage.getAuthPhoneNumberField().isDisplayed();
        authonticationPage.getAuthPasswordField().isDisplayed();
    }

    @Test
    public void assertRegistrationFormWrongEmail() {
        mainPage.getSignInButton().click();
        authonticationPage.getAuthTab().click();
        authonticationPage.getAuthNameField().sendKeys("Dima Popov");
        authonticationPage.getAuthEmailField().sendKeys("sytsytnikov@gmail.com");
        authonticationPage.getAuthPasswordField().sendKeys("Password1");
        authonticationPage.getAuthCreateAccButton().click();
        assertEquals("Аккаунт уже зарегистрирован", authonticationPage.getAuthWrongEmailText().getText());
        loginPage.getSignInModalCloseButton().click();
    }

    @Test
    public void assertChangePasswordOption() {
        mainPage.getSignInButton().click();
        loginPage.getSignInRestore().click();
        loginPage.getSignInEmailInput().sendKeys("sytsytnikov@gmail.com");
        loginPage.getSignInSendEmailButton().click();
        assertEquals("На указанный вами адрес выслано письмо с ссылкой. Откройте почту и пойдите по ссылке для сброса пароля. Если письмо не пришло, проверьте папку «Спам» или попробуйте еще раз.",
                loginPage.getSignInSubmitText().getText());
        loginPage.getSignInModalCloseButton().click();
    }

    @Test
    public void assertNoGamesAfterChangingCity() {
        mainPage.getMoreGamesButton().click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        int CalDate = eventsCalendarPage.getCalendarDate().size();
        for (int i = 0; i < CalDate; i++) {
            eventsCalendarPage.getCalendarDate().get(i).click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));
            if (CalDate >= 1) {
                break;
            }
            eventsCalendarPage.getCityDropDown().click();
            eventsCalendarPage.getCityFilterOption().get(3).click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));
            assertEquals("Упс... на этот день нет ни одной игры, но Вы можете найти подходящую игру в другой день.",
                    eventsCalendarPage.getNoGamesMassage().getText());
        }
        eventsCalendarPage.getCityDropDown().click();
        eventsCalendarPage.getCityFilterOption().get(1).click();
    }

    @Test
    public void assertCampsFilterPepsi() {
        campPage.getCampButton().click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(CAMP_YEAR_PATH)), "2024"));// assert page loaded
        campPage.getCampYear().click();

        int campMonthCount = campPage.getCampMonth().size();
        String textMonth = null;

        for (int i = 1; i < campMonthCount; i++) {

            WebElement monthElement = campPage.getCampMonth().get(i);
            textMonth = monthElement.getText().trim();

            monthElement.click();

            List<WebElement> pepsiCamps = campPage.getPepsiCamps();

            if (pepsiCamps.isEmpty()) {
                continue;
            }
            System.out.println("На месяц " + textMonth + " Pepsi кемп: " + pepsiCamps.size());
        }
    }


    private void resetSportFilter() {
        eventsCalendarPage.getSportArtFilterDropDown().click();
        eventsCalendarPage.getSportArtFilterOption().get(0).click();
    }

    private void login(String email, String password) {
        mainPage.getSignInButton().click();
        loginPage.getLoginEmailInput().sendKeys(email);
        loginPage.getLoginPasswordInput().sendKeys(password);
        loginPage.getLoginButton().click();
    }
    private void logout() {
        mainPage.getLoggedInUserHeader().click();
        profilePage.getLogoutButton().click();
    }

    private void goToEditProfile() {
        mainPage.getLoggedInUserHeader().click();
        profilePage.getEditButton().click();
    }

    // verifyCampSection
    public static final String CAMP_YEAR_PATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div[1]/div[3] ";
    public static final String CAMP_MONTH_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String CAMP_BUTTON_CLASS = "CampsButton_button__2sQY1";
    public static final String CAMP_CARD_CONTAINER_CLASS = "CampCard_container__1BHZa";
    public static final String MONTH_NO_CAMP_TEXT_CLASS = "CampsSearchPage_searchResults__Yio_Q";
    // User Profile Locators
    public static final String PROFILE_GENDER_VALUE_CLASS = "Select_controlValue__1mdVP";
    public static final String OPEN_FEMALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div[2]/div[2]";
    public static final String SELECTED_FEMALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div/div[2]";
    public static final String PROFILE_GENDER_TOGGLE_DROPDOWN_CLASS = "Select_toggleIcon__2SB80";
    public static final String OPEN_MALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div[2]/div[1]";
    //Add Money Case
    public static final String ADD_MONEY_BUTTON_CLASS = "ProfileCard_depositPayment__1R088";
    public static final String INPUT_ADD_MONEY_CLASS = "FormInput_formInputField__1HTcx";
    public static final String TOPUP_BALANCE_BUTTON_CSS = ".ProfileCard_depositPayment__1R088 form:nth-child(1) > button";
    public static final String WRONG_AMOUNT_FORMAT_ERROR_CLASS = "form_error__2xL0z";
    public static final String DATE_CALENDER_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String DATE_WITH_CREATED_GAMES_XPATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div/div[4]/span[2]";
    public static final String ARENA_FILTER_XPATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[3]/div[1]/div[1]/div/div";
    public static final String EVENT_TYPE_ON_GAME_CLASS = "EventCard_eventTypeRow__type__3TN0s";
    public static final String GAME_FILTER_OPTION_CLASS = "GrayRoundedSelect_dropdownItem__18xe6";
    public static final String ARENA_NAME_ON_GAME_CLASS = "EventCard_eventTypeRow__arena__3ljYS";
    public static final String LEVEL_FILTER_XPATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[3]/div[1]/div[3]/div/div";
    public static final String LEVEL_FILTER_OPTION_CLASS = "GrayRoundedSelect_dropdownItem__18xe6";
    public static final String LEVEL_OPTION_LIGHT_CLASS = "//*[@id=\"root\"]/div[2]/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div[1]/div[1]/div[3]";
    public static final String LEVEL_OPTION_MEDIUM_CLASS = "EventCard_level__LxpwV";
    //Filters Locators
    public static final String SPORT_ART_FILTER_XPATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[3]/div[1]/div[2]/div";
    public static final String SPORT_ART_FILTER_OPTION_CLASS = "GrayRoundedSelect_dropdownItem__18xe6";
    public static final String GAME_FILTER_CLASS = "GrayRoundedSelect_notFlex__j02vB";
    public static final String SPORT_ART_OPTION_ON_GAME_CLASS = "EventCard_sport__2ZcAA";
    // Tabs Survey Locators
    public static final String DATE_CALENDAR_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String NO_GAMES_MESSAGE_CLASS = "SearchPage_tabPanelContainer__1Lxhs";
    public static final String DEPOSIT_CHECKBOX_INPUT_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div[2]/div/div/div[3]/div[2]/label/input";
    public static final String DEPOSIT_CHECKBOX_TEXT_CLASS = "Checkbox_labelText__3VNZc";
    public static final String ARENA_CREATED_GAME_PRICE_CLASS = "EventCard_price__6dgeG";
    public static final String ARENA_CREATED_GAME_TIME_CLASS = "EventCard_mainContent__firstRow__1WLC5";
}