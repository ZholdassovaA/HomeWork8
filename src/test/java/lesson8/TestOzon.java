package lesson8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

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

    //@ValueSource (strings = {"Adidas", "Chanel"})
    @CsvSource({
            "Adidas,Футбольный мяч adidas\"Adidas\"",
            "Chanel,Chanel Coco Mademoiselle"
    })
    @Tag("blocker")
    @ParameterizedTest(name = "Поиск бренда {1} на сайте Озон")
    void searchChanelTest(String searchQuery, String expectedResult) {
        open("https://www.ozon.ru/");
        $(".f9j4").click();
        $(".f9j4 [type = 'text']").setValue(searchQuery).pressEnter();
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
