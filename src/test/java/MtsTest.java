import home.MtsHomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class MtsTest {

    private WebDriver driver;
    private MtsHomePage mtsHomePage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.mts.by");
        mtsHomePage = new MtsHomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    //Проверка наличия раздела "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check title Payment Section")
    public void checkTitlePaymentSection() {
        assertTrue(mtsHomePage.isTitlePaymentDisplayed());
    }

    //Проверка иконок платежных систем в разделе "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check icons for pay Payment Section")
    public void checkIconsForPayPaymentSection() {
        String[] expectedIcons = {"Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт"};
        assertTrue(mtsHomePage.areIconsDisplayed(expectedIcons));
    }

    //Проверка ссылки "Подробнее о сервисе"
    @Test
    @DisplayName("Check link about service")
    public void checkLinkAboutService() {
        mtsHomePage.clickMoreAboutService();

        String expectedUrl = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
        assertEquals(mtsHomePage.getCurrentUrl(), expectedUrl, "Переход на страницу 'Подробнее о сервисе' не произошел.");
    }

    //Тест на проверку и заполнение полей в разделе "Онлайн пополнение без комиссии" с проверкой всплывающего окна
    @Test
    @DisplayName("Test Form Online Payment")
    public void testFormOnlinePayment() {
        mtsHomePage.fillOnlinePaymentForm("297777777", "100", "ivanov2000@gmail.com");

        mtsHomePage.switchToIframe();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='app-wrapper']")));

        assertTrue(mtsHomePage.isPopupDisplayed(), "Всплывающее окно не отображается.");

        driver.switchTo().defaultContent();
    }

    //Тест на проверку заполнения полей при выборе меню "Домашний интернет" в блоке "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check placeholders for internet service fields")
    public void checkPlaceholdersForInternetService() {
        mtsHomePage.selectPaymentOption("Домашний интернет");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-internet")));

        String[] expectedPlaceholders = {
                "Номер абонента",
                "Сумма",
                "E-mail для отправки чека"
        };

        String[] actualPlaceholders = {
                mtsHomePage.getPlaceholderForInternetPhone(),
                mtsHomePage.getPlaceholderForInternetSum(),
                mtsHomePage.getPlaceholderForInternetEmail()
        };

        for (int i = 0; i < expectedPlaceholders.length; i++) {
            assertEquals(actualPlaceholders[i], expectedPlaceholders[i]);
        }
    }

    //Тест на проверку заполнения полей при выборе меню "Услуги связи" в блоке "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check placeholders for connection service fields")
    public void checkPlaceholdersForConnectionService() {
        mtsHomePage.selectPaymentOption("Услуги связи");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-connection")));

        String[] expectedPlaceholders = {
                "Номер телефона",
                "Сумма",
                "E-mail для отправки чека"
        };

        String[] actualPlaceholders = {
                mtsHomePage.getPlaceholderForConnectionPhone(),
                mtsHomePage.getPlaceholderForConnectionSum(),
                mtsHomePage.getPlaceholderForConnectionEmail()
        };

        for (int i = 0; i < expectedPlaceholders.length; i++) {
            assertEquals(actualPlaceholders[i], expectedPlaceholders[i]);
        }
    }

    //Тест на проверку заполнения полей при выборе меню "Рассрочка" в блоке "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check placeholders for instalment service fields")
    public void checkPlaceholdersForInstalmentService() {
        // Select "Рассрочка"
        mtsHomePage.selectPaymentOption("Рассрочка");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-instalment")));

        String[] expectedPlaceholders = {
                "Номер счета на 44",
                "Сумма",
                "E-mail для отправки чека"
        };

        String[] actualPlaceholders = {
                mtsHomePage.getPlaceholderForScoreInstalment(),
                mtsHomePage.getPlaceholderForInstalmentSum(),
                mtsHomePage.getPlaceholderForInstalmentEmail()
        };

        for (int i = 0; i < expectedPlaceholders.length; i++) {
            assertEquals(actualPlaceholders[i], expectedPlaceholders[i],
                    "Placeholder для поля " + (i == 0 ? "номер счета на 44" : i == 1 ? "сумма" : "E-mail") + " неверен.");
        }
    }

    //Тест на проверку заполнения полей при выборе меню "Рассрочка" в блоке "Онлайн пополнение без комиссии"
    @Test
    @DisplayName("Check placeholders for arrears service fields")
    public void checkPlaceholdersForArrearsService() {
        mtsHomePage.selectPaymentOption("Задолженность");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pay-arrears")));

        String[] expectedPlaceholders = {
                "Номер счета на 2073",
                "Сумма",
                "E-mail для отправки чека"
        };

        String[] actualPlaceholders = {
                mtsHomePage.getPlaceholderForScoreArrears(),
                mtsHomePage.getPlaceholderForArrearsSum(),
                mtsHomePage.getPlaceholderForArrearsEmail()
        };

        for (int i = 0; i < expectedPlaceholders.length; i++) {
            assertEquals(actualPlaceholders[i], expectedPlaceholders[i]);
        }
    }

    //Проверка содержания  всплывающего окна после заполнения всех полей
    @Test
    @DisplayName("Test Form Online Payment With Parameters")
    public void testFormOnlinePaymentWithParameters() {
        String phoneNumber = "297777777";
        String amount = "100";
        String email = "ivanov2000@gmail.com";

        mtsHomePage.fillOnlinePaymentForm(phoneNumber, amount, email);

        mtsHomePage.switchToIframe();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pay-description__cost")));

        // Проверка введеной суммы в верхнем блоке
        String expectedAmountText = "100.00 BYN"; // Adjusted to match expected format
        WebElement costElement = driver.findElement(By.className("pay-description__cost"));

        String actualAmountText = costElement.getText().replaceAll("[^0-9.]", ""); // Keep numbers and decimal point

        assertEquals(actualAmountText, expectedAmountText.replace(" BYN", ""), "Введенная сумма не соответствует отображаемой.");

        // Проверка введенной суммы на кнопке
        WebElement paymentButton = driver.findElement(By.cssSelector("button.colored.disabled"));

        assertTrue(paymentButton.isDisplayed(), "Кнопка с классом 'colored disabled' не отображается.");

        String expectedButtonText = "Оплатить 100.00 BYN";
        assertEquals(paymentButton.getText().trim(), expectedButtonText, "Текст кнопки не соответствует ожидаемому.");

        //Проверка введенного номера телефона
        String expectedPhoneNumber = "375" + phoneNumber;
        WebElement descriptionElement = driver.findElement(By.className("pay-description__text"));

        assertTrue(descriptionElement.getText().contains(expectedPhoneNumber),
                "Введенный номер телефона не соответствует отображаемому.");

        //Проверка плейсхолдеров (Номер карты, срок действия, CVC, имя держателя) для реквизитов карты
        String[] expectedLabels = {
                "Номер карты",
                "Срок действия",
                "CVC",
                "Имя держателя (как на карте)"
        };

        By[] labelLocators = {
                By.xpath("//label[contains(text(), 'Номер карты')]"),
                By.xpath("//label[contains(text(), 'Срок действия')]"),
                By.xpath("//label[contains(text(), 'CVC')]"),
                By.xpath("//label[contains(text(), 'Имя держателя (как на карте)')]")
        };

        for (int i = 0; i < expectedLabels.length; i++) {
            String expectedLabel = expectedLabels[i];
            By labelLocator = labelLocators[i];

            WebElement labelElement = driver.findElement(labelLocator);

            wait.until(ExpectedConditions.visibilityOf(labelElement));

            String labelText = labelElement.getText();

            assertEquals(labelText, expectedLabel,
                    "Label для поля '" + expectedLabel + "' неверен.");
        }

        //Проверка наличия иконок платежных систем
        WebElement iconsContainer = driver.findElement(mtsHomePage.getPaymentIconsContainer());

        wait.until(ExpectedConditions.visibilityOf(iconsContainer));

        List<WebElement> icons = iconsContainer.findElements(By.tagName("img"));

        assertFalse(icons.isEmpty(), "Иконки платежных систем не найдены.");

        List<String> expectedIconSources = Arrays.asList(
                "https://checkout.bepaid.by/widget_v2/assets/images/payment-icons/card-types/visa-system.svg",
                "https://checkout.bepaid.by/widget_v2/assets/images/payment-icons/card-types/mastercard-system.svg",
                "https://checkout.bepaid.by/widget_v2/assets/images/payment-icons/card-types/belkart-system.svg",
                "https://checkout.bepaid.by/widget_v2/assets/images/payment-icons/card-types/maestro-system.svg",
                "https://checkout.bepaid.by/widget_v2/assets/images/payment-icons/card-types/mir-system-ru.svg"
        );

        for (WebElement icon : icons) {
            String iconSrc = icon.getAttribute("src");
            assertTrue(expectedIconSources.contains(iconSrc),
                    "Найдена иконка с неожиданным источником: " + iconSrc);
        }

        driver.switchTo().defaultContent();
    }
    }

