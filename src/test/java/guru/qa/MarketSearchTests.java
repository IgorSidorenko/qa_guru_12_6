package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.DisplayName;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@DisplayName ("Параметризованные тесты")
public class MarketSearchTests {
    @ValueSource(strings = {
            "Ray-ban",
            "Polaroid"
    })

    @ParameterizedTest(name = "Проверка поиска очков по ключевым словам {0}")
    void marketSearchTest(String testData) {
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(testData);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }
    @CsvSource(value = {
            "Ray-ban", "Солнцезащитные очки",
            "Polaroid", "Солнцезащитные очки"
    })
    @ParameterizedTest(name = "Проверка поиска очков по слову {0}, ожидаем результат: {1}")
    void marketSearchComplexTest(String testData) {
        Selenide.open("https://www.wildberries.ru");
        $("#searchInput").setValue(testData);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }

    static Stream<Arguments> methodSourceMarketSearchComplexTest() {
        return Stream.of(
                Arguments.of("Ray-ban", "Солнцезащитные очки"),
                Arguments.of("Polaroid", "Солнцезащитные очки")
        );
    }

    @MethodSource("methodSourceMarketSearchComplexTest")
    @ParameterizedTest
    void methodSourceMarketSearchComplexTest(String testData, String result) {
        Selenide.open("https://www.wildberries.ru/");
        $("#searchInput").setValue(testData);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name")
                .find(Condition.text(result))
                .shouldBe(visible);
    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }
}




