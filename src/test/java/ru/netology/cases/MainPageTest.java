package ru.netology.cases;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class MainPageTest {

    @Test
    @Feature("Основной прогон")
    @Story("Главная страница")
    @DisplayName("Страница \"Путешествие дня\" доступна по адресу localhost:8080")
    void shouldOpenOnLocalhost() {
        step("Открыть страницу \"Путешествие дня\"", () ->
                open("http://localhost:8080"));
        step("Проверить отображения хедера", () ->
                element("h2[class^='heading']").shouldHave(text("Путешествие дня")));
    }
}
