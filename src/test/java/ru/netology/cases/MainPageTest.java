package ru.netology.cases;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;

@Feature("Основной прогон")
@Story("Главная страница")
public class MainPageTest {
    private MainPage mainPage;
    private final String url = System.getProperty("app.url");

    @BeforeEach
    public void openPage() {
        open(url);
        mainPage = new MainPage();
    }

    @Test
    @DisplayName("Страница \"Путешествие дня\" доступна по адресу localhost:8080")
    void shouldOpenOnLocalhost() {
        mainPage.checkHeaderIsVisible()
                .checkHeaderText("Путешествие дня");
    }

    @Test
    @Issue("1")
    @DisplayName("Презентация главной страницы")
    void shouldCorrectPresentation() {
        mainPage.checkHeaderIsVisible()
                .checkTextPreviewHeader("Марракеш")
                .checkTextsPreviewList("Сказочный Восток", "33 360 миль на карту", "До 7% на остаток по счёту", "Всего 45 000 руб.!")
                .checkButtonOrderByCardIsVisible()
                .checkButtonOrderByCreditIsVisible();
    }
}
