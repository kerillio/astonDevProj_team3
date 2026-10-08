package strategies.sort;

import comparators.CarComparators;
import models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.AppState;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EvenNumericSortStrategyTest {

    private EvenNumericSortStrategy<Car> strategy;
    private AppState state;

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    private Car car5;
    private Car car6;
    private Car car7;

    @BeforeEach
    void setUp() {
        strategy = new EvenNumericSortStrategy<>();

        state = new AppState();
        state.setSelectedField("power");

        car1 = Car.builder().power(5).model("BMW").year(2020).build();
        car2 = Car.builder().power(8).model("BMW").year(2020).build();
        car3 = Car.builder().power(3).model("BMW").year(2020).build();
        car4 = Car.builder().power(2).model("BMW").year(2020).build();
        car5 = Car.builder().power(6).model("BMW").year(2020).build();
        car6 = Car.builder().power(7).model("BMW").year(2020).build();
        car7 = Car.builder().power(4).model("BMW").year(2020).build();
    }


    // ПРОВЕРЯЕТ, ЧТО ЧЁТНЫЕ ЗНАЧЕНИЯ СОРТИРУЮТСЯ,
    // А НЕЧЁТНЫЕ ОСТАЮТСЯ НА ИСХОДНЫХ ПОЗИЦИЯХ
    @Test
    void sortKeepsOddElementsInOriginalPositions() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car1); // 5  - НЕЧЁТНОЕ
        cars.add(car2); // 8  - ЧЁТНОЕ
        cars.add(car3); // 3  - НЕЧЁТНОЕ
        cars.add(car4); // 2  - ЧЁТНОЕ
        cars.add(car5); // 6  - ЧЁТНОЕ
        cars.add(car6); // 7  - НЕЧЁТНОЕ
        cars.add(car7); // 4  - ЧЁТНОЕ

        strategy.sort(cars, CarComparators.BY_POWER, state);

        // НЕЧЁТНЫЕ ОБЪЕКТЫ ДОЛЖНЫ ОСТАТЬСЯ НА СВОИХ ИНДЕКСАХ
        assertSame(car1, cars.get(0));
        assertSame(car3, cars.get(2));
        assertSame(car6, cars.get(5));

        // ЧЁТНЫЕ ОБЪЕКТЫ ДОЛЖНЫ БЫТЬ ОТСОРТИРОВАНЫ ПО ВОЗРАСТАНИЮ
        assertEquals(2, cars.get(1).getPower());
        assertEquals(4, cars.get(3).getPower());
        assertEquals(6, cars.get(4).getPower());
        assertEquals(8, cars.get(6).getPower());
    }


    // ПРОВЕРЯЕТ, ЧТО ЕСЛИ ВСЕ ЗНАЧЕНИЯ ЧЁТНЫЕ,
    // ТО КОЛЛЕКЦИЯ ПОЛНОСТЬЮ СОРТИРУЕТСЯ
    @Test
    void sortAllEvenElements() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        Car first = Car.builder().power(8).model("BMW").year(2020).build();
        Car second = Car.builder().power(2).model("BMW").year(2020).build();
        Car third = Car.builder().power(6).model("BMW").year(2020).build();
        Car fourth = Car.builder().power(4).model("BMW").year(2020).build();

        cars.add(first);
        cars.add(second);
        cars.add(third);
        cars.add(fourth);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertEquals(2, cars.get(0).getPower());
        assertEquals(4, cars.get(1).getPower());
        assertEquals(6, cars.get(2).getPower());
        assertEquals(8, cars.get(3).getPower());
    }


    // ПРОВЕРЯЕТ, ЧТО ЕСЛИ ВСЕ ЗНАЧЕНИЯ НЕЧЁТНЫЕ,
    // ТО ИХ ПОРЯДОК ВООБЩЕ НЕ МЕНЯЕТСЯ
    @Test
    void sortDoesNotMoveOddElements() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        Car first = Car.builder().power(9).model("BMW").year(2020).build();
        Car second = Car.builder().power(3).model("BMW").year(2020).build();
        Car third = Car.builder().power(7).model("BMW").year(2020).build();

        cars.add(first);
        cars.add(second);
        cars.add(third);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertSame(first, cars.get(0));
        assertSame(second, cars.get(1));
        assertSame(third, cars.get(2));
    }


    // ПРОВЕРЯЕТ РАБОТУ С ПОВТОРЯЮЩИМИСЯ ЧЁТНЫМИ ЗНАЧЕНИЯМИ
    @Test
    void sortWorksWithDuplicateEvenValues() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        Car first = Car.builder().power(6).model("BMW").year(2020).build();
        Car second = Car.builder().power(4).model("BMW").year(2020).build();
        Car third = Car.builder().power(6).model("BMW").year(2020).build();
        Car fourth = Car.builder().power(2).model("BMW").year(2020).build();

        cars.add(first);
        cars.add(second);
        cars.add(third);
        cars.add(fourth);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertEquals(2, cars.get(0).getPower());
        assertEquals(4, cars.get(1).getPower());
        assertEquals(6, cars.get(2).getPower());
        assertEquals(6, cars.get(3).getPower());
    }


    // ПРОВЕРЯЕТ, ЧТО ПУСТОЙ СПИСОК НЕ ВЫЗЫВАЕТ ОШИБКУ
    @Test
    void emptyListDoesNotThrow() {
        ArrayList<Car> cars = new ArrayList<>();

        assertDoesNotThrow(() -> strategy.sort(cars, CarComparators.BY_POWER, state));

        assertTrue(cars.isEmpty());
    }


    // ПРОВЕРЯЕТ СПИСОК ИЗ ОДНОГО НЕЧЁТНОГО ЭЛЕМЕНТА
    @Test
    void oneOddElementDoesNotChange() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car1);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertSame(car1, cars.get(0));
    }


    // ПРОВЕРЯЕТ, ЧТО СТРАТЕГИЯ МОЖЕТ РАБОТАТЬ С ДРУГИМ ЧИСЛОВЫМ ПОЛЕМ
    @Test
    void sortByYearKeepsOddYearsInPlace() throws InterruptedException {
        EvenNumericSortStrategy<Car> yearStrategy = new EvenNumericSortStrategy<>();

        AppState yearState = new AppState();
        yearState.setSelectedField("year");

        Car first = Car.builder().power(100).model("BMW").year(2021).build();
        Car second = Car.builder().power(100).model("BMW").year(2024).build();
        Car third = Car.builder().power(100).model("BMW").year(2019).build();
        Car fourth = Car.builder().power(100).model("BMW").year(2020).build();
        Car fifth = Car.builder().power(100).model("BMW").year(2022).build();

        ArrayList<Car> cars = new ArrayList<>();

        cars.add(first);
        cars.add(second);
        cars.add(third);
        cars.add(fourth);
        cars.add(fifth);

        yearStrategy.sort(cars, CarComparators.BY_YEAR, yearState);

        // НЕЧЁТНЫЕ ГОДЫ ОСТАЮТСЯ НА СВОИХ МЕСТАХ
        assertSame(first, cars.get(0));
        assertSame(third, cars.get(2));

        // ЧЁТНЫЕ ГОДЫ СОРТИРУЮТСЯ
        assertEquals(2020, cars.get(1).getYear());
        assertEquals(2022, cars.get(3).getYear());
        assertEquals(2024, cars.get(4).getYear());
    }
}