package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.UserRegistrationData;
import ru.netology.data.UserRegistrationDataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UserRegistrationTest {
    @Test
    void rightSignInTest() {
        UserRegistrationData user = UserRegistrationDataGenerator.generateValidActive();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("h2.heading.heading_size_l.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));

    }

    @Test
    void wrongLoginTest() {
        UserRegistrationData user = UserRegistrationDataGenerator.generateInvalidLogin();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void wrongPasswordTest() {
        UserRegistrationData user = UserRegistrationDataGenerator.generateInvalidPassword();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));

    }

    @Test
    void noPasswordTest() {
        UserRegistrationData user = UserRegistrationDataGenerator.generateValidActive();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void blockedUserSignInTest() {
        UserRegistrationData user = UserRegistrationDataGenerator.generateValidBlocked();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));

    }

}
