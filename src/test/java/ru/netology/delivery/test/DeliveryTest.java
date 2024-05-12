package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan/replan meeting")
    void shouldSuccessfulPlanReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$(".button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно запланирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(firstMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$(".button").find(exactText("Запланировать")).click();
        $(byText("Необходимо подтверждение")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText("Встреча успешно запланирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(secondMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));
    }
}