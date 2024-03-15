package pl.akademiaspecjalistowit.streamapiinpractice.zadanie5;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Zadanie5 {

    /**
     * ZACZNIJ OD NAPISANIA ASERCJI!
     *
     * Masz listę produktów, gdzie każdy produkt zawiera nazwę, cenę oraz kategorię
     * (np. "Elektronika", "Dom", "Ogród").
     * <p>
     * Twoim zadaniem jest wyfiltrowanie wszystkich produktów
     * o cenie wyższej niż określona wartość, na przykład 100 jednostek pieniężnych,
     * przekształcenie ich nazw na wielkie litery oraz pogrupowanie nazw tych produktów według kategorii.
     */

    @Test
    void zadanie5() {
        // given
        List<Product> products = List.of(
            new Product("Telefon", 250.0, "Elektronika"),
            new Product("Laptop", 1200.0, "Elektronika"),
            new Product("Kosiarka", 150.0, "Ogród"),
            new Product("Biurko", 85.0, "Dom"),
            new Product("Ładowarka", 25.0, "Elektronika"),
            new Product("Grill", 300.0, "Ogród"),
            new Product("Lampa", 50.0, "Dom"),
            new Product("Zestaw narzędzi", 200.0, "Ogród"),
            new Product("Fotel", 400.0, "Dom"),
            new Product("Kamera", 150.0, "Elektronika")
        );

        // when
        Map<String, List<String>> productsGroupedByCategory = groupingProductsByCategory(products);
        System.out.println("Produkty pogrupowane według kategorii: " + productsGroupedByCategory);

        // then
        assertThat(productsGroupedByCategory.size()).isEqualTo(3);
        assertThat(productsGroupedByCategory.containsKey("Dom")).isEqualTo(true);
        assertThat(productsGroupedByCategory.containsKey("Ogród")).isEqualTo(true);
        assertThat(productsGroupedByCategory.containsKey("Elektronika")).isEqualTo(true);
        assertThat(productsGroupedByCategory.get("Dom")).contains("FOTEL");
        assertThat(productsGroupedByCategory.get("Ogród")).contains("KOSIARKA", "GRILL", "ZESTAW NARZĘDZI");
        assertThat(productsGroupedByCategory.get("Elektronika")).contains("TELEFON", "LAPTOP", "KAMERA");
    }

    private Map<String, List<String>> groupingProductsByCategory(List<Product> products) {
        return products.stream()
                .filter(getProductPredicate())
                .map(e->new Product(e.getName().toUpperCase(Locale.ROOT), e.getPrice(), e.getCategory()))
                .collect(Collectors.groupingBy(Product::getCategory))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .stream()
                        .map(Product::getName)
                        .collect(Collectors.toList())));
    }

    private static Predicate<Product> getProductPredicate() {
        return e -> e.getPrice() > 100;
    }
}
