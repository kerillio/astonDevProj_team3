package strategies.sort;

import comparators.CarComparators;
import models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.AppState;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSortStrategyTest {

    private DefaultSortStrategy<Car> strategy;
    private AppState state;

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;

    @BeforeEach
    void setUp() {
        strategy = new DefaultSortStrategy<>();
        state = new AppState();

        car1 = Car.builder()
                .power(300)
                .model("BMW")
                .year(2020)
                .build();

        car2 = Car.builder()
                .power(100)
                .model("Audi")
                .year(2022)
                .build();

        car3 = Car.builder()
                .power(200)
                .model("Mercedes")
                .year(2019)
                .build();

        car4 = Car.builder()
                .power(100)
                .model("Audi")
                .year(2018)
                .build();
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ ПО МОЩНОСТИ
    @Test
    void sortByPower() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertEquals(car2, cars.get(0));
        assertEquals(car3, cars.get(1));
        assertEquals(car1, cars.get(2));
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ ПО МОДЕЛИ
    @Test
    void sortByModel() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car3);
        cars.add(car1);
        cars.add(car2);

        strategy.sort(cars, CarComparators.BY_MODEL, state);

        assertEquals(car2, cars.get(0));
        assertEquals(car1, cars.get(1));
        assertEquals(car3, cars.get(2));
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ ПО ГОДУ
    @Test
    void sortByYear() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car2);
        cars.add(car1);
        cars.add(car3);

        strategy.sort(cars, CarComparators.BY_YEAR, state);

        assertEquals(car3, cars.get(0));
        assertEquals(car1, cars.get(1));
        assertEquals(car2, cars.get(2));
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ СРАЗУ ПО ВСЕМ ТРЁМ ПОЛЯМ
    @Test
    void sortByAllFields() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);

        strategy.sort(cars, CarComparators.BY_ALL, state);

        assertEquals(car4, cars.get(0));
        assertEquals(car2, cars.get(1));
        assertEquals(car3, cars.get(2));
        assertEquals(car1, cars.get(3));
    }


    // ПРОВЕРЯЕТ, ЧТО УЖЕ ОТСОРТИРОВАННЫЙ СПИСОК НЕ ЛОМАЕТСЯ
    @Test
    void alreadySortedListRemainsSorted() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car2);
        cars.add(car3);
        cars.add(car1);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertEquals(car2, cars.get(0));
        assertEquals(car3, cars.get(1));
        assertEquals(car1, cars.get(2));
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ ПУСТОГО СПИСКА
    @Test
    void emptyListDoesNotThrow() {
        ArrayList<Car> cars = new ArrayList<>();

        assertDoesNotThrow(() -> strategy.sort(cars, CarComparators.BY_POWER, state));

        assertTrue(cars.isEmpty());
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ СПИСКА ИЗ ОДНОГО ЭЛЕМЕНТА
    @Test
    void oneElementListDoesNotChange() throws InterruptedException {
        ArrayList<Car> cars = new ArrayList<>();

        cars.add(car1);

        strategy.sort(cars, CarComparators.BY_POWER, state);

        assertEquals(1, cars.size());
        assertEquals(car1, cars.get(0));
    }
}