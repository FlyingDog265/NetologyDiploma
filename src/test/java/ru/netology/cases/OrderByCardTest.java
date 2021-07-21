package ru.netology.cases;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.helpers.CardHelper.getCardInfoWithApprovedCardNumber;

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
    public void shouldApprovedWithValidCard() {
        mainPage.selectOrderByCard()
                .setCardFields(getCardInfoWithApprovedCardNumber())
                .sendData()
                .checkMessageApprovedIsVisible()
                .checkTextMessageApproved("Успешно", "Операция одобрена Банком.");
    }
}
