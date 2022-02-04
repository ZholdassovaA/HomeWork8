package lesson8;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;

public class TestOzon {
    @DisplayName("Поиск бренда Adidas на сайте Озон")
    @Tag("blocker")
    @Test
    void searchAdidasTest() {
        open("https://www.ozon.ru/");
        $(".f9j4").click();
        $(".f9j4 [type = 'text']").setValue("Adidas").pressEnter();
        $$(".widget-search-result-container.a9x2").shouldHave(texts("Adidas"));
    }

    static Stream<Arguments> testWithMethodSource() {
        return Stream.of(
                Arguments.of("Adidas", "Футбольный мяч adidas"),
                Arguments.of("Chanel", "Chanel Coco Mademoiselle")
        );
    }

    @MethodSource
    @Tag("blocker")
    @DisplayName("Поиск бренда Adidas на сайте Озон")
    @ParameterizedTest(name = "Поиск на сайте Озон {0} и проверка отображения товаров {1}")
    void testWithMethodSource (String SearchBrand, String expectedResult) {
        open("https://www.ozon.ru/");
        $(".f9j4").setValue(SearchBrand);
        $(".f9j4 [type = 'text']").click();
        $$("widget-search-result-container.a9x2").find(Condition.text(expectedResult)).shouldBe(Condition.visible);
    }

    //@ValueSource (strings = {"Adidas", "Chanel"})
    @CsvSource({
            "Adidas,Футбольный мяч adidas\"Adidas\"",
            "Chanel,Chanel Coco Mademoiselle"
    })
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск бренда {1} на сайте Озон")
    void searchChanelTest(String SearchBrand, String expectedResult) {
        open("https://www.ozon.ru/");
        $(".f9j4").click();
        $(".f9j4 [type = 'text']").setValue(SearchBrand).pressEnter();
        $$(".widget-search-result-container.a9x2").shouldHave(texts(expectedResult));
    }

    @EnumSource(SearchBrand.class)
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск бренда {0} на сайте Озон")
    void searchOzonTest(SearchBrand searchBrand ) {
        open("https://www.ozon.ru/");
        $(".f9j4").click();
        $(".f9j4 [type = 'text']").setValue(searchBrand.name()).pressEnter();
        $$(".container.a3o9").shouldHave(texts(searchBrand.name()));
    }
}
