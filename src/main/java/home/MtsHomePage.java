package home;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MtsHomePage extends BasePage {

    private By acceptCookieButton = By.id("cookie-agree");
    private By titlePayment = By.xpath("//section[@class='pay']//h2[contains(text(),'Онлайн пополнение') and contains(., 'без комиссии')]");
    private By iconsContainer = By.xpath("//div[@class='pay__partners']//ul");
    private By moreAboutServiceLink = By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a[contains(text(), 'Подробнее о сервисе')]");
    private By onlinePaymentBlock = By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section");
    private By iframeLocator = By.className("bepaid-iframe");
    private By popupLocator = By.xpath("//div[@class='app-wrapper']");
    private By internetPhoneField = By.id("internet-phone");
    private By internetSumField = By.id("internet-sum");
    private By internetEmailField = By.id("internet-email");
    private By selectHeaderLocator = By.className("select__header"); // Locator for the dropdown header
    private By selectListLocator = By.className("select__list"); // Locator for the dropdown list
    private By connectionPhoneField = By.id("connection-phone");
    private By connectionSumField = By.id("connection-sum");
    private By connectionEmailField = By.id("connection-email");
    private By scoreInstalmentField = By.id("score-instalment");
    private By instalmentSumField = By.id("instalment-sum");
    private By instalmentEmailField = By.id("instalment-email");
    private By scoreArrearsField = By.id("score-arrears");
    private By arrearsSumField = By.id("arrears-sum");
    private By arrearsEmailField = By.id("arrears-email");
    private By cardNumberField = By.id("cc-number");
    private By expirationDateField = By.xpath("//input[@formcontrolname='expirationDate']");
    private By cvcField = By.xpath("//input[@formcontrolname='cvc']");
    private By cardholderNameField = By.xpath("//input[@formcontrolname='holder']");
    private By paymentIconsContainer = By.className("cards-brands__container");

    public MtsHomePage(WebDriver driver) {
        super(driver);
        acceptCookies();
    }

    private void acceptCookies() {
        WebElement button = find(acceptCookieButton);
        if (button.isDisplayed()) {
            button.click();
        }
    }

    public boolean isTitlePaymentDisplayed() {
        return find(titlePayment).isDisplayed();
    }

    public boolean areIconsDisplayed(String[] expectedIcons) {
        WebElement icons = find(iconsContainer);
        for (String text : expectedIcons) {
            WebElement icon = icons.findElement(By.xpath(".//img[@alt='" + text + "']"));
            if (!icon.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void clickMoreAboutService() {
        find(moreAboutServiceLink).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void fillOnlinePaymentForm(String phoneNumber, String amount, String email) {
        WebElement phoneNumberField = find(By.xpath("//*[@id=\"connection-phone\"]"));
        WebElement amountField = find(By.xpath("//*[@id=\"connection-sum\"]"));
        WebElement emailField = find(By.xpath("//*[@id=\"connection-email\"]"));
        WebElement continueButton = find(By.xpath("//*[@id=\"pay-connection\"]/button"));

        phoneNumberField.sendKeys(phoneNumber);
        amountField.sendKeys(amount);
        emailField.sendKeys(email);
        continueButton.click();
            }

    public void switchToIframe() {
        driver.switchTo().frame(find(iframeLocator));
    }

    public boolean isPopupDisplayed() {
        try {
            return find(popupLocator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectPaymentOption(String option) {
        find(selectHeaderLocator).click();

        WebElement selectList = find(selectListLocator);

        WebElement desiredOption = selectList.findElement(By.xpath(".//p[contains(text(), '" + option + "')]"));
        desiredOption.click();
    }

    public String getPlaceholderForInternetPhone() {
        return find(internetPhoneField).getAttribute("placeholder");
    }

    public String getPlaceholderForInternetSum() {
        return find(internetSumField).getAttribute("placeholder");
    }

    public String getPlaceholderForInternetEmail() {
        return find(internetEmailField).getAttribute("placeholder");
    }

    public String getPlaceholderForConnectionPhone() {
        return find(connectionPhoneField).getAttribute("placeholder");
    }

    public String getPlaceholderForConnectionSum() {
        return find(connectionSumField).getAttribute("placeholder");
    }

    public String getPlaceholderForConnectionEmail() {
        return find(connectionEmailField).getAttribute("placeholder");
    }
    public String getPlaceholderForScoreInstalment() {
        return find(scoreInstalmentField).getAttribute("placeholder");
    }

    public String getPlaceholderForInstalmentSum() {
        return find(instalmentSumField).getAttribute("placeholder");
    }

    public String getPlaceholderForInstalmentEmail() {
        return find(instalmentEmailField).getAttribute("placeholder");
    }
    public String getPlaceholderForScoreArrears() {
        return find(scoreArrearsField).getAttribute("placeholder");
    }

    public String getPlaceholderForArrearsSum() {
        return find(arrearsSumField).getAttribute("placeholder");
    }

    public String getPlaceholderForArrearsEmail() {
        return find(arrearsEmailField).getAttribute("placeholder");
    }

        public By getCardNumberField() {
        return cardNumberField;
    }

    public By getExpirationDateField() {
        return expirationDateField;
    }

    public By getCvcField() {
        return cvcField;
    }

    public By getCardholderNameField() {
        return cardholderNameField;
    }

    public By getPaymentIconsContainer() {
        return paymentIconsContainer;
    }


}

