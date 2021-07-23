package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.netology.helpers.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.element;

@SuppressWarnings("UnusedReturnValue")
public class OrderPage {
    private final Duration duration = Duration.ofSeconds(10);
    private final SelenideElement buttonLoading = element("button span[class*='spin_visible']");
    private final SelenideElement buttonContinue = element(withText("Продолжить")),
            fieldCardNumber = element("fieldset div:nth-of-type(1) input"),
            fieldMonth = element("fieldset div:nth-of-type(2) > span > span:nth-of-type(1) input"),
            fieldYear = element("fieldset div:nth-of-type(2) > span > span:nth-of-type(2) input"),
            fieldOwner = element("fieldset div:nth-of-type(3) > span > span:nth-of-type(1) input"),
            fieldCvcCvv = element("fieldset div:nth-of-type(3) > span > span:nth-of-type(2) input"),
            errorCardNumber = element("fieldset div:nth-of-type(1) span[class='input__sub']"),
            errorMonth = element("fieldset div:nth-of-type(2) > span > span:nth-of-type(1) span[class='input__sub']"),
            errorYear = element("fieldset div:nth-of-type(2) > span > span:nth-of-type(2) span[class='input__sub']"),
            errorOwner = element("fieldset div:nth-of-type(3) > span > span:nth-of-type(1) span[class='input__sub']"),
            errorCvcCvv = element("fieldset div:nth-of-type(3) > span > span:nth-of-type(2) span[class='input__sub']");
    private final SelenideElement notification = element("div[class*='notification_status']");
    private final SelenideElement messageApproved = element("div[class*='notification_status_ok']");
    private final SelenideElement messageDecline = element("div[class*='notification_status_error']");

    @Step("Заполнить данные карты")
    public OrderPage setCardFields(Card card) {
        fieldCardNumber.setValue(card.getCardNumber());
        fieldMonth.setValue(card.getCardMonth());
        fieldYear.setValue(card.getCardYear());
        fieldOwner.setValue(card.getCardOwner());
        fieldCvcCvv.setValue(card.getCardCvcCvv());
        return OrderPage.this;
    }

    @Step("Нажать на кнопку 'Продолжить'")
    public OrderPage clickContinue() {
        buttonContinue.click();
        return OrderPage.this;
    }

    @Step("Подождать загрузку заявки")
    public OrderPage shouldBeLoaded() {
        buttonLoading.shouldNotBe(visible, Duration.ofSeconds(100));
        return OrderPage.this;
    }

    @Step("Отображается сообщение об отправке заявки")
    public OrderPage checkNotificationIsVisible() {
        shouldBeLoaded();
        notification.shouldBe(visible, duration);
        return OrderPage.this;
    }

    @Step("Отображается сообщение об успешной отправке заявки")
    public OrderPage checkMessageApprovedIsVisible() {
        shouldBeLoaded();
        messageApproved.shouldBe(visible, duration);
        return OrderPage.this;
    }

    @Step("Сообщение об успехе содержит заголовок {title} и текст {text}")
    public OrderPage compareTextMessageApproved(String title, String text) {
        messageApproved.$("*[class*='title']").shouldHave(text(title));
        messageApproved.$("*[class*='content']").shouldHave(text(text));
        return OrderPage.this;
    }

    @Step("Отображается сообщение об ошибке отправки")
    public OrderPage checkMessageDeclinedIsVisible() {
        shouldBeLoaded();
        messageDecline.shouldBe(visible, duration);
        return OrderPage.this;
    }

    @Step("Сообщение об ошибке содержит заголовок {title} и текст {text}")
    public OrderPage compareTextMessageDeclined(String title, String text) {
        messageDecline.$("*[class*='title']").shouldHave(text(title));
        messageDecline.$("*[class*='content']").shouldHave(text(text));
        return OrderPage.this;
    }

    @Step("Сообщения об ошибке или успешной отправке не отображаются")
    public OrderPage checkMessagesNotVisible() {
        shouldBeLoaded();
        messageApproved.shouldBe(not(visible));
        messageDecline.shouldBe(not(visible));
        return OrderPage.this;
    }

    @Step("Отображается ошибка '{error}' в поле ввода номера карты")
    public OrderPage checkErrorCardNumberMessage(String error) {
        errorCardNumber.shouldBe(visible).shouldHave(text(error));
        return OrderPage.this;
    }

    @Step("Отображается ошибка '{error}' в поле ввода месяца")
    public OrderPage checkErrorMonthMessage(String error) {
        errorMonth.shouldBe(visible).shouldHave(text(error));
        return OrderPage.this;
    }

    @Step("Отображается ошибка '{error}' в поле ввода года")
    public OrderPage checkErrorYearMessage(String error) {
        errorYear.shouldBe(visible).shouldHave(text(error));
        return OrderPage.this;
    }

    @Step("Отображается ошибка '{error}' в поле ввода владельца карты")
    public OrderPage checkErrorOwnerMessage(String error) {
        errorOwner.shouldBe(visible).shouldHave(text(error));
        return OrderPage.this;
    }

    @Step("Отображается ошибка '{error}' в поле ввода CVC/CVV")
    public OrderPage checkErrorCvcCvvMessage(String error) {
        errorCvcCvv.shouldBe(visible).shouldHave(text(error));
        return OrderPage.this;
    }

    @Step("В полях ввода отображаются ошибки")
    public OrderPage checkAllFieldsErrorsIsVisible() {
        errorCardNumber.shouldBe(visible);
        errorMonth.shouldBe(visible);
        errorYear.shouldBe(visible);
        errorOwner.shouldBe(visible);
        errorCvcCvv.shouldBe(visible);
        return OrderPage.this;
    }

    @Step("В полях ввода не отображаются ошибки")
    public OrderPage checkAllFieldsErrorsIsNotVisible() {
        errorCardNumber.shouldBe(not(visible));
        errorMonth.shouldBe(not(visible));
        errorYear.shouldBe(not(visible));
        errorOwner.shouldBe(not(visible));
        errorCvcCvv.shouldBe(not(visible));
        return OrderPage.this;
    }

    @Step("В пустых полях отображается ошибка '{error}")
    public OrderPage checkAllEmptyFieldsErrors(String error) {
        checkErrorCardNumberMessage(error);
        checkErrorMonthMessage(error);
        checkErrorYearMessage(error);
        checkErrorOwnerMessage(error);
        checkErrorCvcCvvMessage(error);
        return OrderPage.this;
    }
}
