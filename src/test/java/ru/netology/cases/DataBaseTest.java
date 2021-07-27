package ru.netology.cases;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.helpers.DbHelper;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.helpers.CardHelper.getCardInfoWithApprovedCardNumber;
import static ru.netology.helpers.CardHelper.getCardInfoWithDeclinedCardNumber;
import static ru.netology.helpers.DbHelper.*;

@Feature("База данных")
@Story("Перегруз и проверка данных")
public class DataBaseTest {
    private MainPage mainPage;
    private final String url = System.getProperty("app.url");

    private final int amount = 4500000;
    private final String approvedStatus = "APPROVED";
    private final String declinedStatus = "DECLINED";

    @BeforeEach
    public void openPage() {
        open(url);
        mainPage = new MainPage();
    }

    @Test
    @DisplayName("После успешной заявки \"Оплата по карте\", данные о платеже со статусом APPROVED сохраняются в базе данных")
    public void shouldApprovedOrderByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Оплата по карте\", со статусом Approved", () ->
                mainPage.selectOrderByCard()
                        .setCardFields(getCardInfoWithApprovedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        step("Проверить, что в таблицах появились данные об операции", () -> {
            String actualStatus = getPaymentStatusInfo();
            assertEquals(approvedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
        });
    }

    @Test
    @DisplayName("После успешной заявки \"Купить в кредит\", данные о платеже со статусом APPROVED сохраняются в базе данных")
    public void shouldApprovedCreditByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Купить в кредит\", со статусом Approved", () ->
                mainPage.selectOrderByCredit()
                        .setCardFields(getCardInfoWithApprovedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        step("Проверить, что в таблицах появились данные об операции", () -> {
            String actualStatus = getCreditStatusInfo();
            assertEquals(approvedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
        });
    }

    @Test
    @DisplayName("После отклоненной заявки \"Оплата по карте\", данные о заявке со статусом DECLINED сохраняются в базе данных")
    public void shouldStoreDeclinedOrderByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Оплата по карте\", со статусом Declined", () ->
                mainPage.selectOrderByCard()
                        .setCardFields(getCardInfoWithDeclinedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        String actualStatus = getPaymentStatusInfo();
        assertEquals(declinedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
    }

    @Test
    @DisplayName("После отклоненной заявки \"Купить в кредит\", данные о заявке со статусом DECLINED сохраняются в базе данных")
    public void shouldStoreDeclinedCreditByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Купить в кредит\", со статусом Declined", () ->
                mainPage.selectOrderByCredit()
                        .setCardFields(getCardInfoWithDeclinedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        String actualStatus = getCreditStatusInfo();
        assertEquals(declinedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
    }

    @Test
    @DisplayName("После успешной заявки \"Оплата по карте\" сумма платежа равна сумме платежа в базе данных")
    public void shouldEqualsAmountsApprovedOrderByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Оплата по карте\", со статусом Approved", () ->
                mainPage.selectOrderByCard()
                        .setCardFields(getCardInfoWithApprovedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        step("Проверить, что в таблицах появились данные об операции", () -> {
            String actualStatus = getPaymentStatusInfo();
            assertEquals(approvedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
        });
        step("Проверить, что стоимость заявки совпадает с суммой в таблице", () -> {
            int actualAmount = getPaymentAmountInfo();
            assertEquals(amount, actualAmount, "Суммы на сайте и в БД не сходятся!");
        });
    }

    @Test
    @Issue("5")
    @DisplayName("После успешной заявки \"Купить в кредит\" сумма платежа равна сумме платежа в базе данных")
    public void shouldEqualsAmountsApprovedCreditByCardIntoDatabase() {
        step("Очистить базу данных", DbHelper::clearTable);
        step("Оформить заявку \"Купить в кредит\", со статусом Approved", () ->
                mainPage.selectOrderByCredit()
                        .setCardFields(getCardInfoWithApprovedCardNumber())
                        .clickContinue()
                        .checkNotificationIsVisible());
        step("Проверить, что в таблицах появились данные об операции", () -> {
            String actualStatus = getCreditStatusInfo();
            assertEquals(approvedStatus, actualStatus, "Статус не соответствует ожидаемому значению");
        });
        step("Проверить, что стоимость заявки совпадает с суммой в таблице", () -> {
            int actualAmount = getCreditAmountInfo();
            assertEquals(amount, actualAmount, "Суммы на сайте и в БД не сходятся!");
        });
    }
}
