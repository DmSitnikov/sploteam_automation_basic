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
        WebElement signinButton = driver.findElement(By.className(SIGNIN_BUTTON_CLASS));
        Assert.assertTrue(signinButton.isDisplayed());
        WebElement gamesList = driver.findElement(By.className(COMING_GAMES_TEXT_CLASS));
        Assert.assertTrue(gamesList.isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(volleyball_BUTTON_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(HEADER_LOGO_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(SHOW_MORE_GAMES_XPATH)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(CREATE_YOUR_GAME_XPATH)).isDisplayed());
        String allGamesTitle = driver.findElement(By.className(ALL_GAMES_TITLE_CLASS)).getText();
        assertEquals("Играй, тренируйся\n" +
                "и улучшай свои навыки\n" +
                "в командных видах спорта", allGamesTitle);

        //Home Work 1
        Assert.assertTrue(driver.findElement(By.className(FOOTER_LOGO_XPATH)).isDisplayed());//попробовать через контейнер
        Assert.assertTrue(driver.findElement(By.xpath(RENT_FIELD_XPATH)).isDisplayed());
        String SelecetCity = driver.findElement(By.className(SELECT_CITY_CLASS)).getText();
        assertEquals("Санкт-Петербург", SelecetCity);

        WebElement footballButton = driver.findElement(By.className(FOOTBALL_BUTTON_CLASS));
        Assert.assertTrue(footballButton.isDisplayed());
        String footballText = footballButton.getText();
        assertEquals("Футбол", footballText);

        String cooperatinText = driver.findElement(By.className(COOPERATION_TITLE_CLASS)).getText();
        assertEquals("Приглашаем к сотрудничеству\n" +
                "владельцев площадок и тренеров",cooperatinText);
    }

    @Test
    public void assertCampSection () {
        Assert.assertTrue(driver.findElement(By.className(CAMP_BUTTON_CLASS)).isDisplayed());
        String newThing = driver.findElement(By.className(NEWTHING_TEXT_CLASS)).getText();
        assertEquals("Новинка!", newThing);
        String sportCamp = driver.findElement(By.className(SPORTCAMP_TEXT_CLASS)).getText();
        assertEquals("Спортивные кемпы", sportCamp);
        Assert.assertTrue(driver.findElement(By.className(SPORTCAMP_LOGO_CLASS)).isDisplayed());
    }

    @Test
    public void assertSendRequestSection () {
        Assert.assertTrue(driver.findElement(By.xpath(USER_NAME_XPATH)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.name(EMAIL_PHONE_NAME)).isDisplayed());
        String userNameField = driver.findElement(By.xpath(USER_NAME_TEXT_XPATH)).getText();
        assertEquals("Ваше имя", userNameField);
        String emailPhone = driver.findElement(By.xpath(EMAIL_PHONE_TEXT_XPATH)).getText();
        assertEquals("E-mail или телефон", emailPhone);
        Assert.assertTrue(driver.findElement(By.className(SEND_REQUEST_BUTTON_CLASS)).isDisplayed());
        String cooperationText = driver.findElement(By.className(COOPERATION_TEXT_CLASS)).getText();
        assertEquals("Присоединяйтесь к самой крупной сети игроков, готовых придти к вам уже сегодня!", cooperationText);
    }

    @Test
    public void assertfindAllLinks() {
        List<WebElement> allinks = driver.findElements(By.tagName("a"));
        List<WebElement> externallinks = driver.findElements(By.xpath("//a[contains(@href, 'https')]"));
        System.out.println(allinks.size());
        System.out.println(externallinks.size());
    }

        // LESSON 2
    @Test
    public void assertLoginPositive() {
        login("sytsytnikov@gmail.com", "Password1");
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.findElement(By.className(LOGOUT_BUTTON_CLASS));
        Assert.assertTrue(driver.findElement(By.className(SIGNIN_BUTTON_CLASS)).isDisplayed());
    }

    @Test //Negative test
    public void assertLoginPasswordShortError() {
        login("sytsytnikov@gmail.com", "Pas");
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).isDisplayed();
        Assert.assertTrue(driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).isDisplayed());
        assertEquals("Неверное значение", driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).getText());
    }

    @Test //Negative test
    public void assertLoginEmailSpellingError() {
        login("sysytnikov@gmail.com", "Password1");
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).isDisplayed();
        Assert.assertTrue(driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).isDisplayed());
        assertEquals("Такой логин или пароль не найдены", driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).getText());
    }

    //HOME WORK 2
    @Test //Negative test
    public void assertLoginEmailEmptyFieldError() {
        login("", "Password2");
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).isDisplayed();
        Assert.assertTrue(driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH)).isDisplayed());
        assertEquals("", driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH)).getText());
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).click();
        assertEquals(driver.switchTo().activeElement(), driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH))); // проверяем фокус на активном элементе
    }

    @Test //Negative test
    public void assertLoginEmailNoDotError() {
        login("sytsytnikov@gmailcom", "Password2");
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).isDisplayed();
        Assert.assertTrue(driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).isDisplayed());
        assertEquals("Неверное значение", driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).getText());
    }

    @Test //Negative test
    public void assertLoginLongPasswordError() {
        login("sytsytnikov@gmail.com", "Password1211");
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).isDisplayed();
        Assert.assertTrue(driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).isDisplayed());
        assertEquals("Неверный пароль", driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).getText());
    }

    @Test
    public void assertProfilePagePositive() {
        login("sytsytnikov@gmail.com", "Password2");
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        Assert.assertTrue(driver.findElement(By.className(USER_NAME_PROFILE_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(USER_EMAIL_PROFILE_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(DEPOSIT_BUTTON_PROFILE_CLASS)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.className(EDIT_BUTTON_PROFILE_CLASS)).isDisplayed());
        String OwnDeposit = driver.findElement(By.xpath(OWN_DEPOSIT_TEXT_XPATH)).getText();
        Assert.assertTrue(OwnDeposit.contains("Личный счёт")); //роверяем что нужная нам часть текста находится в этом элементе
        logout();
    }

    @Test
    public void assertEditUserInfo() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        String nameBefore = driver.findElement(By.xpath(USER_INFO_NAME_XPATH)).getAttribute("value");
        Assert.assertTrue(nameBefore.contains("Dmitriy Sitnikov"));
        driver.findElement(By.xpath(DELETE_NAME_BUTTON_XPATH)).click(); //Удаляю имя которое было введено ранее
        driver.findElement(By.xpath(USER_INFO_NAME_XPATH)).sendKeys("Vanja Ivanov");
        assertEquals("Сохранить изменения", driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).getText());
        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
        Assert.assertTrue(driver.findElement(By.xpath(SAVE_SUCCESS_TEXT_XPATH)).isDisplayed());
        assertEquals("Ваш профиль успешно обновлён", driver.findElement(By.xpath(SAVE_SUCCESS_TEXT_XPATH)).getText());
        driver.findElement(By.className(CLOSE_SAVE_PANEL_CLASS)).click();
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.navigate().refresh();
        assertEquals("Vanja Ivanov", driver.findElement(By.className(USER_NAME_HEADER_CLASS)).getText());
        goToEditProfile();
        driver.findElement(By.xpath(DELETE_NAME_BUTTON_XPATH)).click();
        driver.findElement(By.xpath(USER_INFO_NAME_XPATH)).sendKeys("Dmitriy Sitnikov");
        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
        driver.findElement(By.className(CLOSE_SAVE_PANEL_CLASS)).click();
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.navigate().refresh();
        assertEquals("Dmitriy Sitnikov", driver.findElement(By.className(USER_NAME_HEADER_CLASS)).getText());
        logout();
    }

    @Test //Negative test
    public void assertEmptyNameField() {
        login("sytsytnikov@gmail.com", "Password2");
        goToEditProfile();
        driver.findElement(By.xpath(DELETE_NAME_BUTTON_XPATH)).click(); //Удаляю имя которое было введено ранее
        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
        Assert.assertTrue(driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).isDisplayed());
        assertEquals("Выберите значение", driver.findElement(By.className(COMMON_TEXT_ELEMENT_ERROR_CLASS)).getText());
    }

    @Test //TASK with Asterix
    public void assertTopUpBalanceSection() throws InterruptedException {
        login("sytsytnikov@gmail.com", "Password2");
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        TopUpBalance("1");
        //driver.findElement(By.className(CLOSE_NAVIGATE_TO_PROFILE_BUTTON_CLASS)).click(); // Иногда нужен чтобы закрыть всплывающее окно
        String balanceTextAfter = ("Личный счет: 204.00 ₽"); //сумма после пополнения
        String balanceAfter = balanceTextAfter.substring(13, balanceTextAfter.indexOf("₽") - 1);
        Double depositAmountAfter = Double.parseDouble(balanceAfter);
        System.out.println(depositAmountAfter + 100);
        logout();
    }

    @Test //Negative test
    public void assertWrongAmountError() {
        login("sytsytnikov@gmail.com", "Password2");
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.findElement(By.className(ADD_MONEY_BUTTON_CLASS)).click();
        driver.findElement(By.className(INPUT_ADD_MONEY_CLASS)).sendKeys("b");
        driver.findElement(By.cssSelector(TOPUP_BALANCE_BUTTON_CSS)).click();
        assertEquals("Неверное значение", driver.findElement(By.className(WRONG_AMOUNT_FORMAT_ERROR_CLASS)).getText());
        logout();
    }


    @Test //Negative test
    public void assertNoAtEmail() {
        login("sytsytnikovgmail.com", "Password2");
        String emailNoAtError = driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH)).getAttribute("validationMessage");
        assertEquals("Die E-Mail-Adresse muss ein @-Zeichen enthalten. In der Angabe \"sytsytnikovgmail.com\" fehlt ein @-Zeichen.", emailNoAtError);
    }

    @Test
    public void assertCreatedGames() {
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

        driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)).click();
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

    @Test //Negative test
    public void assertSurveySaveButtonError() {
        login("sytsytnikov@gmail.com", "Password2");
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.findElement(By.xpath(PROFILE_TABS_SURVEY_XPATH)).click();
        driver.findElement(By.className(TABS_SURVEY_CITY_CLASS)).click();
        driver.findElements(By.className(SELECT_SURVEY_CITY_CLASS)).get(6).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.className(BRYANSK_CITY_CLASS)), "Брянск"));
        String newCity = driver.findElement(By.className(BRYANSK_CITY_CLASS)).getText();
        System.out.println(newCity);
        driver.findElement(By.className(SURVEY_SAVE_BUTTON_CLASS)).click();
        assertEquals("Ответьте, пожалуйста, на все вопросы", driver.findElement(By.className(SURVEY_SAVE_ERROR_TEXT_CLASS)).getText());
        logout();
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

        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
        driver.findElement(By.className(CLOSE_SAVE_PANEL_CLASS)).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.className(PROFILE_GENDER_VALUE_CLASS), selectedGender)));

        String updatedGender = driver.findElement(By.className(PROFILE_GENDER_VALUE_CLASS)).getText();
        System.out.println("Пол: " + updatedGender);
        logout();
    }

    @Test
    public void assertEvenNumberDates() {
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4)); // assert page loaded

        // применяем арену
        driver.findElement(By.xpath(ARENA_FILTER_XPATH)).click();
        driver.findElements(By.className(GAME_FILTER_OPTION_CLASS)).get(5).click();
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(ARENA_FILTER_XPATH)), "Пляж тест"));

        int datesCount = driver.findElements(By.className(DATE_CALENDAR_CLASS)).size();

        for (int i = 0; i < datesCount; i++) {
            // каждый раз берём элемент заново — чтобы не словить StaleElementReference
            WebElement dateElement = driver.findElements(By.className(DATE_CALENDAR_CLASS)).get(i);
            String rawText = dateElement.getText().trim();

            // пропускаем "Сегодня" и пустые значения
            if (rawText.equalsIgnoreCase("Сегодня") || rawText.isEmpty()) {
                continue;
            }

            // извлекаем только цифры (например "6" из "6" или " 6 ")
            String digits = rawText.replaceAll("\\D+", "");
            if (digits.isEmpty()) {
                continue;
            }

            int day = Integer.parseInt(digits);

            // кликаем только по ЧЁТНЫМ датам
            if (day % 2 != 0) {
                continue;
            }

            // клик по дате
            dateElement.click();

            // ждём, что подгрузятся элементы игр (если гарантированно должны быть — ждём >0)
            //wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(EVENT_TYPE_ON_GAME_CLASS), 0));

            // теперь проверяем игры на выбранную арену
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
        driver.findElement(By.xpath(DATE_WITH_CREATED_GAMES_XPATH)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));// assert page loaded

        // how to get number of options in drop down?
        driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)).click();
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
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
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
        driver.findElement(By.className(MORE_GAMES_BUTTON_CLASS)).click();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));// assert page loaded

        int gamePerDayCount = driver.findElements(By.className(DATE_CALENDER_CLASS)).size();

        // how to get number of options in drop down?
        driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)).click();
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
                driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)).click();
                WebElement gameElement = driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(j);

                gameElement.click();
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className(GAME_FILTER_CLASS), 4));

                int gamesCount = driver.findElements(By.className(EVENT_TYPE_ON_GAME_CLASS)).size();

                    System.out.println("На день " + textDay + " найдено игр: " + gamesCount);
                }

            }
        }

    //DIPLOMA
    @Test
    public void assertRegistrationForm() {
        mainPage.getSignInButton().click();
        authonticationPage.getAuthTab().click();
        authonticationPage.getAuthNameField().isDisplayed();
        authonticationPage.getAuthEmailField().isDisplayed();
        authonticationPage.getAuthPhoneNumberField().isDisplayed();
        authonticationPage.getAuthPasswordField().isDisplayed();
    }

    //NEGATIVE
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
        driver.findElement(By.xpath(SPORT_ART_FILTER_XPATH)).click();
        driver.findElements(By.className(SPORT_ART_FILTER_OPTION_CLASS)).get(0).click();
    }

    private void login(String email, String password) {
        driver.findElement(By.className(SIGNIN_BUTTON_CLASS)).click();
        driver.findElement(By.xpath(LOGIN_EMAIL_INPUT_FORM_XPATH)).sendKeys(email);
        driver.findElement(By.name(LOGIN_PASSWORD_INPUT_FORM_NAME)).sendKeys(password);
        driver.findElement(By.xpath(LOGIN_BUTTON_FORM_XPATH)).click();
    }
    private void logout() {
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.findElement(By.className(LOGOUT_BUTTON_CLASS)).click();
    }

    private void goToEditProfile() {
        driver.findElement(By.className(USER_NAME_HEADER_CLASS)).click();
        driver.findElement(By.className(EDIT_BUTTON_PROFILE_CLASS)).click();
    }

    private void TopUpBalance(String enterTopUpAmount) throws InterruptedException { // улучшение
        String balanceTextBefore = ("Личный счет: 204.00 ₽"); //сумма до пополнения
        String balanceBefore = balanceTextBefore.substring(13, balanceTextBefore.indexOf("₽") - 1);
        Double depositAmountBefore = Double.valueOf(balanceBefore);
        System.out.println(depositAmountBefore);
        driver.findElement(By.className(ADD_MONEY_BUTTON_CLASS)).click();
        driver.findElement(By.className(INPUT_ADD_MONEY_CLASS)).sendKeys(enterTopUpAmount);
        driver.findElement(By.cssSelector(TOPUP_BALANCE_BUTTON_CSS)).click();
        sleep(10000);
        driver.findElement(By.className(PAY_BUTTON_CLASS)).click();
    }


    //LOCATORS TEST1
    public static final String SIGNIN_BUTTON_CLASS = "header__signIn";
    public static final String HEADER_LOGO_CLASS = "header__logo";
    public static final String volleyball_BUTTON_CLASS = "games-list__item_volleyball";
    public static final String COMING_GAMES_TEXT_CLASS = "coming-games__title";
    public static final String MORE_GAMES_BUTTON_CLASS = "games-list__item_more-games";
    public static final String SHOW_MORE_GAMES_XPATH = "//*[@id=\"root\"]/div[2]/section[2]/div/div[3]/a";
    public static final String CREATE_YOUR_GAME_XPATH = "//*[@id=\"root\"]/div[2]/section[3]/div/div[2]/a";
    public static final String ALL_GAMES_TITLE_CLASS = "all-games__title";

    // TEST2 verifyCampSection
    public static final String FOOTER_LOGO_XPATH = "footer__logo";
    public static final String RENT_FIELD_XPATH = "//*[@id=\"root\"]/div[2]/section[4]/div/div[2]/a";
    public static final String SELECT_CITY_CLASS = "location__default";
    public static final String FOOTBALL_BUTTON_CLASS = "games-list__item_football";
    public static final String COOPERATION_TITLE_CLASS = "cooperation__title";
    public static final String CAMP_YEAR_PATH = "//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div[1]/div[3] ";
    public static final String CAMP_MONTH_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String CAMP_BUTTON_CLASS = "CampsButton_button__2sQY1";
    public static final String CAMP_CARD_CONTAINER_CLASS = "CampCard_container__1BHZa";

    public static final String MONTH_NO_CAMP_TEXT_CLASS = "CampsSearchPage_searchResults__Yio_Q";

    public static final String NEWTHING_TEXT_CLASS = "CampsButton_new__1YwW-";
    public static final String SPORTCAMP_TEXT_CLASS = "CampsButton_title__55qxk";
    public static final String SPORTCAMP_LOGO_CLASS = "CampsButton_img__3CoXw";
    public static final String PEPSI_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/div[2]/div/div/div[1]/div[1]/div[4]/div/div/div[2]/div[2]";

    // TEST3 - verifySendRequestSection
    public static final String USER_NAME_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[1]/input";
    public static final String EMAIL_PHONE_NAME = "email_or_phone";
    public static final String USER_NAME_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[1]/label";
    public static final String EMAIL_PHONE_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/section[5]/div/form/div[2]/label";
    public static final String SEND_REQUEST_BUTTON_CLASS = "cooperation-form__submit";
    public static final String COOPERATION_TEXT_CLASS = "cooperation__text";

    public static final String LOGIN_EMAIL_INPUT_FORM_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/div[1]/input";
    public static final String LOGIN_PASSWORD_INPUT_FORM_NAME =  "password";
    public static final String LOGIN_BUTTON_FORM_XPATH = "/html/body/div[3]/div/div/div/div[2]/form/button";
    public static final String USER_NAME_HEADER_CLASS = "profileText__name";
    public static final String LOGOUT_BUTTON_CLASS = "LKLayout_exit__1QjSv";

    public static final String COMMON_TEXT_ELEMENT_ERROR_CLASS = "form_error__2xL0z"; // улучшение

    // User Profile Locators
    public static final String USER_NAME_PROFILE_CLASS = "ProfileCard_name__2yGm-";
    public static final String USER_EMAIL_PROFILE_CLASS = "ProfileCard_contacts__eSdIc";
    public static final String DEPOSIT_BUTTON_PROFILE_CLASS = "header__signIn";
    public static final String EDIT_BUTTON_PROFILE_CLASS = "OrangeLink_orangeLink__34ZRK";
    public static final String OWN_DEPOSIT_TEXT_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div[2]/div/div/div[3]/div[1]";
    public static final String USER_INFO_NAME_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[1]/input";
    public static final String SAVE_BUTTON_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/button";
    public static final String DELETE_NAME_BUTTON_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[1]/span";
    public static final String SAVE_SUCCESS_TEXT_XPATH = "/html/body/div[3]/div/div/p";
    public static final String CLOSE_SAVE_PANEL_CLASS = "modal__close";
    public static final String PROFILE_GENDER_VALUE_CLASS = "Select_controlValue__1mdVP";
    public static final String OPEN_FEMALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div[2]/div[2]";
    public static final String SELECTED_FEMALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div/div[2]";
    public static final String PROFILE_GENDER_TOGGLE_DROPDOWN_CLASS = "Select_toggleIcon__2SB80";
    public static final String SELECTED_MALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div/div[2]";
    public static final String OPEN_MALE_GENDER_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div/div[2]/form/div[2]/div[2]/div[1]";
    //Add Money Case
    public static final String ADD_MONEY_BUTTON_CLASS = "ProfileCard_depositPayment__1R088";
    public static final String INPUT_ADD_MONEY_CLASS = "FormInput_formInputField__1HTcx";
    public static final String TOPUP_BALANCE_BUTTON_CSS = ".ProfileCard_depositPayment__1R088 form:nth-child(1) > button";
    public static final String WRONG_AMOUNT_FORMAT_ERROR_CLASS = "form_error__2xL0z";
    public static final String CLOSE_NAVIGATE_TO_PROFILE_BUTTON_CLASS = "modal__close"; // Селектор для всплывающего окна после перехода со страници оплаты обратно на сплотим
    //PAYMENT GATEWAY LOCATORS
    public static final String NEW_WEBSITE_LOGO_XPATH = "/html/body/header/div/a/img";
    public static final String PAY_BUTTON_CLASS = "r-submit";

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
    public static final String PROFILE_TABS_SURVEY_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div[1]/div/div[4]";
    public static final String TABS_SURVEY_CITY_CLASS = "Select_control__3L5dE";
    public static final String SELECT_SURVEY_CITY_CLASS = "Select_dropdownItem__2T2FU";
    public static final String SURVEY_SAVE_BUTTON_CLASS = "Quiz_quiz__button__1gMQ7";
    public static final String SURVEY_SAVE_ERROR_TEXT_CLASS = "Quiz_quiz__errorBlock__I9tK3";
    public static final String BRYANSK_CITY_CLASS = "Select_select__2JwMt";

    public static final String DATE_CALENDAR_CLASS = "NavLink_navLink__text__zfi3X";
    public static final String NO_GAMES_MESSAGE_CLASS = "SearchPage_tabPanelContainer__1Lxhs";

    public static final String DEPOSIT_CHECKBOX_INPUT_XPATH = "//*[@id=\"root\"]/div[2]/div/div[3]/div[2]/div/div/div[3]/div[2]/label/input";
    public static final String DEPOSIT_CHECKBOX_TEXT_CLASS = "Checkbox_labelText__3VNZc";

    public static final String ARENA_CREATED_GAME_PRICE_CLASS = "EventCard_price__6dgeG";
    public static final String ARENA_CREATED_GAME_TIME_CLASS = "EventCard_mainContent__firstRow__1WLC5";
}