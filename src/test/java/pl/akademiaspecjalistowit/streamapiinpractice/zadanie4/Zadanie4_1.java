package pl.akademiaspecjalistowit.streamapiinpractice.zadanie4;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ZACZNIJ OD NAPISANIA ASERCJI!
 * <p>
 * Mając listę zamówień, znajdź najczęściej występującą nazwę produktu
 */

public class Zadanie4_1 {

    @Test
    void test() {
        // given
        List<Order> orders = List.of(
            new Order(List.of(new Product("Książka", 30.0), new Product("Długopis", 5.0)), "NOWE"),
            new Order(List.of(new Product("Laptop", 2000.0), new Product("Myszka", 100.0)), "WYSŁANE"),
            new Order(List.of(new Product("Telefon", 500.0), new Product("Etui", 50.0)), "NOWE"),
            new Order(List.of(new Product("Etui", 1838.33), new Product("Myszka", 774.17)), "WYSŁANE"),
            new Order(List.of(new Product("Telefon", 547.42), new Product("Kurtka", 375.12)), "WYSŁANE"),
            new Order(List.of(new Product("Kurtka", 1321.38)), "NOWE"),
            new Order(List.of(new Product("Buty", 1709.76)), "NOWE"),
            new Order(List.of(new Product("Długopis", 1015.35)), "DOSTARCZONE"),
            new Order(
                List.of(new Product("Buty", 308.45), new Product("Książka", 1899.55), new Product("Telefon", 1885.82)),
                "WYSŁANE"),
            new Order(List.of(new Product("Etui", 344.15), new Product("Buty", 961.36)), "DOSTARCZONE")
        );

        // when
        List<String> topProducts = showTopProducts(orders);
        System.out.println("Najczęściej występująca w zamówieniach nazwa produktu to: " + topProducts);

        // then
        assertThat(topProducts.size()).isEqualTo(3);
        assertThat(topProducts).contains("Buty", "Etui", "Telefon");
    }

    private List<String> showTopProducts(List<Order> orders) {
        Map<String, Long> wordFrequency = orders
                .stream()
                .map(Order::getProducts)
                .flatMap(List::stream)
                .map(Product::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long maxCount = Collections.max(wordFrequency.values());

        return  wordFrequency
                .entrySet()
                .stream()
                .filter(getEntryPredicate(maxCount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static Predicate<Map.Entry<String, Long>> getEntryPredicate(Long maxCount) {
        return e -> e.getValue() == maxCount;
    }
}
