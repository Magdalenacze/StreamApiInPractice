package pl.akademiaspecjalistowit.streamapiinpractice.zadanie3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Zadanie3 {

    /**
     * ZACZNIJ OD NAPISANIA ASERCJI!
     *
     * Mając listę pracowników (obiekty z polami name i salary), znajdź pierwszych
     * 5 pracowników z najwyższą pensją i zwróć ich imiona w posortowanej liście alfabetycznie.
     */

    @Test
    void zadanie3() {
        // given
        List<Employee> employees = List.of(
            new Employee("John Doe", 70000),
            new Employee("Jane Smith", 80000),
            new Employee("Ethan Black", 90000),
            new Employee("Emma White", 85000),
            new Employee("Michael Brown", 50000),
                new Employee("Jason Bourne", 45000)
        );

        // when
        List<String> topEarners = sortTopEmployees(employees);
        System.out.println("Top zarabiający pracownicy: " + topEarners);

        // then
        assertThat(topEarners.size()).isEqualTo(5);
        assertThat(topEarners.get(0)).isEqualTo("Emma White");
        assertThat(topEarners.get(1)).isEqualTo("Ethan Black");
        assertThat(topEarners.get(2)).isEqualTo("Jane Smith");
        assertThat(topEarners.get(3)).isEqualTo("John Doe");
        assertThat(topEarners.get(4)).isEqualTo("Michael Brown");
    }

    private List<String> sortTopEmployees(List<Employee> employees) {
        return employees
                .stream()
                .sorted(getEmployeeComparatorForSalary())
                .limit(5)
                .map(Employee::getName)
                .sorted()
                .collect(Collectors.toList());
    }

    private static Comparator<Employee> getEmployeeComparatorForSalary() {
        return (e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary());
    }
}
