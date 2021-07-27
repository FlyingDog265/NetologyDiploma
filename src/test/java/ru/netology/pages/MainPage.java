package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.elements;

@SuppressWarnings("UnusedReturnValue")
public class MainPage {
    private final SelenideElement header = element("h2[class^='heading']"),
            previewHeader = element("div[class*='cardPreview'] h3"),
            orderTitle = element("button ~ h3"),
            buttonOrder = element(withText("Купить")),
            buttonCredit = element(withText("Купить в кредит"));
    private final ElementsCollection previewList = elements("div[class*='cardPreview'] li");

    @Step("Хедер отображается на главной странице")
    public MainPage checkHeaderIsVisible() {
        header.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    @Step("Хедер содержит текст {text}")
    public MainPage checkHeaderText(String text) {
        header.shouldHave(text(text));
        return this;
    }

    @Step("Заголовок тура содержит текст {title}")
    public MainPage checkTextPreviewHeader(String title) {
        previewHeader.shouldBe(visible)
                .shouldHave(text(title));
        return this;
    }

    @Step("Нажать на кнопку 'Купить'")
    public MainPage clickOrderByCard() {
        buttonOrder.click();
        return this;
    }

    @Step("Нажать на кнопку 'Купить в кредит'")
    public MainPage clickOrderByCredit() {
        buttonCredit.click();
        return this;
    }

    @Step("Заголовок под кнопками содержит текст {text}")
    public MainPage checkUnderButtonsTitle(String text) {
        orderTitle.shouldBe(visible)
                .shouldHave(text(text));
        return this;
    }

    @Step("Проверка текстов списка в презентации тура")
    public MainPage checkTextsPreviewList(String text1, String text2, String text3, String text4) {
        previewList.get(0).shouldHave(text(text1));
        previewList.get(1).shouldHave(text(text2));
        previewList.get(2).shouldHave(text(text3));
        previewList.get(3).shouldHave(text(text4));
        return this;
    }

    @Step("Кнопка 'Купить' отображается на странице")
    public MainPage checkButtonOrderByCardIsVisible() {
        buttonOrder.shouldBe(visible);
        return this;
    }

    @Step("Кнопка 'Купить в кредит' отображается на странице")
    public MainPage checkButtonOrderByCreditIsVisible() {
        buttonCredit.shouldBe(visible);
        return this;
    }

    @Step("Выбрать оплату по карте")
    public OrderPage selectOrderByCard() {
        checkButtonOrderByCardIsVisible();
        clickOrderByCard();
        checkUnderButtonsTitle("Оплата по карте");
        return new OrderPage();
    }

    @Step("Выбрать оплату в кредит")
    public OrderPage selectOrderByCredit() {
        checkButtonOrderByCreditIsVisible();
        clickOrderByCredit();
        checkUnderButtonsTitle("Кредит по данным карты");
        return new OrderPage();
    }
}
