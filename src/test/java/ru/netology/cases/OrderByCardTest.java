package ru.netology.cases;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.helpers.CardHelper.*;

@Feature("Основной прогон")
@Story("Покупка тура по карте")
public class OrderByCardTest {
    private MainPage mainPage;
    private final String url = System.getProperty("app.url");

    @BeforeEach
    public void openPage() {
        open(url);
        mainPage = new MainPage();
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\", заполненная валидными данными карты со статусом Approved успешно одобрена банком")
    public void shouldApprovedWithApprovedCard() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithApprovedCardNumber())
                .clickContinue()
                .checkMessageApprovedIsVisible()
                .compareTextMessageApproved("Успешно", "Операция одобрена Банком.");
    }

    @Test
    @Issue("9")
    @DisplayName("Заявка \"Оплата по карте\", заполненная данными карты со статусом Declined отклонена банком")
    public void shouldDeclinedWithDeclinedCard() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithDeclinedCardNumber())
                .clickContinue()
                .checkMessageDeclinedIsVisible()
                .compareTextMessageDeclined("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\", заполненная невалидными данными карты, отклонена банком")
    public void shouldDeclinedWithInvalidCard() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithUnknownCardNumber())
                .clickContinue()
                .checkMessageDeclinedIsVisible()
                .compareTextMessageDeclined("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @Issue("11")
    @DisplayName("Ошибки валидации не отображаются при заполнении полей валидными данными")
    public void shouldNotVisibleValidatedErrorsAfterFillValidData() {
        mainPage.selectOrderByCard()
                .clickContinue()
                .checkAllFieldsErrorsIsVisible()
                .setCardFields(getCardInfoWithApprovedCardNumber())
                .checkAllFieldsErrorsIsNotVisible();
    }

    @Test
    @Issue("10")
    @DisplayName("Пустая форма заявки \"Оплата по карте\" не отправляется в банк")
    public void shouldNotSendEmptyFields() {
        mainPage.selectOrderByCard()
                .clickContinue()
                .checkMessagesNotVisible()
                .checkAllEmptyFieldsErrors("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\" не отправляется с невалидным значением в поле \"Номер карты\"")
    public void shouldNotSendWithShortCardNumber() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithIncorrectCardNumber())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorCardNumberMessage("Неверный формат");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\" не отправляется со значением года прошлого периода в поле \"Год\"")
    public void shouldNotSendWithPastPeriod() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithTooSmallYear())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorYearMessage("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\" не отправляется со значением года далекого будущего периода в поле \"Год\"")
    public void shouldNotSendWithNextPeriod() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithTooBigYear())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorYearMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\" не отправляется со значением месяца, больше \"12\", в поле \"Месяц\"")
    public void shouldNotSendWithTooBigMonth() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithTooBigMonth())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorMonthMessage("Неверно указан срок действия карты");
    }

    @Test
    @Issue("8")
    @DisplayName("Заявка \"Оплата по карте\" не отправляется со значением месяца, меньше \"01\", в поле \"Месяц\"")
    public void shouldNotSendWithTooSmallMonth() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithTooSmallMonth())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorMonthMessage("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Заявка \"Оплата по карте\" не отправляется с невалидным значением в поле \"CVC/CVV\"")
    public void shouldNotSendWithIncorrectCvc() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithIncorrectCvc())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorCvcCvvMessage("Неверный формат");
    }

    @Test
    @Issue("7")
    @DisplayName("Заявка \"Оплата по карте\" не отправляется с символами на кириллице в поле \"Владелец\"")
    public void shouldNotSendWithCyrillicOwner() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithIncorrectOwnerByCyrillic())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorOwnerMessage("Неверный формат");
    }
}
