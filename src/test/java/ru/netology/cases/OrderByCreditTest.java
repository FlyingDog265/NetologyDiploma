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

@Feature("Покупка тура в кредит")
public class OrderByCreditTest {
    private MainPage mainPage;
    private final String url = System.getProperty("app.url");

    @BeforeEach
    public void openPage() {
        open(url);
        mainPage = new MainPage();
    }

    @Test
    @Story("Отправка данных")
    @DisplayName("Заявка \"Кредит по данным карты\", заполненная валидными данными карты со статусом Approved успешно одобрена банком")
    public void shouldApprovedWithApprovedCard() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithApprovedCardNumber())
                .clickContinue()
                .checkMessageApprovedIsVisible()
                .compareTextMessageApproved("Успешно", "Операция одобрена Банком.");
    }

    @Test
    @Story("Отправка данных")
    @Issue("9")
    @DisplayName("Заявка \"Кредит по данным карты\", заполненная данными карты со статусом Declined отклонена банком")
    public void shouldDeclinedWithDeclinedCard() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithDeclinedCardNumber())
                .clickContinue()
                .checkMessageDeclinedIsVisible()
                .compareTextMessageDeclined("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @Story("Отправка данных")
    @DisplayName("Заявка \"Кредит по данным карты\", заполненная невалидными данными карты, отклонена банком")
    public void shouldDeclinedWithInvalidCard() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithUnknownCardNumber())
                .clickContinue()
                .checkMessageDeclinedIsVisible()
                .compareTextMessageDeclined("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    @Story("Валидация полей")
    @Issue("11")
    @DisplayName("Ошибки валидации не отображаются при заполнении полей валидными данными")
    public void shouldNotVisibleValidatedErrorsAfterFillValidData() {
        mainPage.selectOrderByCredit()
                .clickContinue()
                .checkAllFieldsErrorsIsVisible()
                .setCardFields(getCardInfoWithApprovedCardNumber())
                .checkAllFieldsErrorsIsNotVisible();
    }

    @Test
    @Story("Валидация полей")
    @Issue("10")
    @DisplayName("Пустая форма заявки \"Кредит по данным карты\" не отправляется в банк")
    public void shouldNotSendEmptyFields() {
        mainPage.selectOrderByCredit()
                .clickContinue()
                .checkMessagesNotVisible()
                .checkAllEmptyFieldsErrors("Поле обязательно для заполнения");
    }


    @Test
    @Story("Валидация полей")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется с невалидным значением в поле \"Номер карты\"")
    public void shouldNotSendWithShortCardNumber() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithIncorrectCardNumber())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorCardNumberMessage("Неверный формат");
    }

    @Test
    @Story("Валидация полей")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется со значением года прошлого периода в поле \"Год\"")
    public void shouldNotSendWithPastPeriod() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithTooSmallYear())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorYearMessage("Истёк срок действия карты");
    }

    @Test
    @Story("Валидация полей")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется со значением года далекого будущего периода в поле \"Год\"")
    public void shouldNotSendWithNextPeriod() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithTooBigYear())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorYearMessage("Неверно указан срок действия карты");
    }

    @Test
    @Story("Валидация полей")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется со значением месяца, больше \"12\", в поле \"Месяц\"")
    public void shouldNotSendWithTooBigMonth() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithTooBigMonth())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorMonthMessage("Неверно указан срок действия карты");
    }

    @Test
    @Story("Валидация полей")
    @Issue("8")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется со значением месяца, меньше \"01\", в поле \"Месяц\"")
    public void shouldNotSendWithTooSmallMonth() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithTooSmallMonth())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorMonthMessage("Неверно указан срок действия карты");
    }

    @Test
    @Story("Валидация полей")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется с невалидным значением в поле \"CVC/CVV\"")
    public void shouldNotSendWithIncorrectCvc() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithIncorrectCvc())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorCvcCvvMessage("Неверный формат");
    }

    @Test
    @Story("Валидация полей")
    @Issue("7")
    @DisplayName("Заявка \"Кредит по данным карты\" не отправляется с символами на кириллице в поле \"Владелец\"")
    public void shouldNotSendWithCyrillicOwner() {
        mainPage.selectOrderByCredit()
                .setCardFields(getCardInfoWithIncorrectOwnerByCyrillic())
                .clickContinue()
                .checkMessagesNotVisible()
                .checkErrorOwnerMessage("Неверный формат");
    }
}
