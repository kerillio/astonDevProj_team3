package catalogs;

import comparators.CarComparators;
import models.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {

    private Catalog<Car> catalog;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;

    @BeforeEach
    void setUp() {
        catalog = new Catalog<>();

        car1 = Car.builder()
                .power(100)
                .model("BMW")
                .year(2020)
                .build();

        car2 = Car.builder()
                .power(100)
                .model("Audi")
                .year(2022)
                .build();

        car3 = Car.builder()
                .power(100)
                .model("Audi")
                .year(2018)
                .build();

        car4 = Car.builder()
                .power(200)
                .model("Mercedes")
                .year(2019)
                .build();
    }


    // ПРОВЕРЯЕТ, ЧТО НОВЫЙ КАТАЛОГ ИЗНАЧАЛЬНО ПУСТОЙ
    @Test
    void catalogStartsEmpty() {
        assertEquals(0, catalog.size());
    }


    // ПРОВЕРЯЕТ, ЧТО ЭЛЕМЕНТ ДОБАВЛЯЕТСЯ В КАТАЛОГ
    @Test
    void addAddsElement() {
        catalog.add(car1);

        assertEquals(1, catalog.size());
        assertEquals(car1, catalog.get(0));
    }


    // ПРОВЕРЯЕТ, ЧТО GET ВОЗВРАЩАЕТ ЭЛЕМЕНТ ПО НУЖНОМУ ИНДЕКСУ
    @Test
    void getReturnsCorrectElement() {
        catalog.add(car1);
        catalog.add(car2);

        assertEquals(car2, catalog.get(1));
    }


    // ПРОВЕРЯЕТ, ЧТО SET ЗАМЕНЯЕТ ЭЛЕМЕНТ ПО УКАЗАННОМУ ИНДЕКСУ
    @Test
    void setReplacesElement() {
        catalog.add(car1);

        catalog.set(0, car2);

        assertEquals(car2, catalog.get(0));
    }


    // ПРОВЕРЯЕТ, ЧТО REMOVE УДАЛЯЕТ ЭЛЕМЕНТ ИЗ КАТАЛОГА
    @Test
    void removeDeletesElement() {
        catalog.add(car1);
        catalog.add(car2);

        Car removed = catalog.remove(0);

        assertEquals(car1, removed);
        assertEquals(1, catalog.size());
        assertEquals(car2, catalog.get(0));
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ КАТАЛОГА ПО ОДНОМУ ПОЛЮ
    @Test
    void sortWithStrategySortsByPower() {
        catalog.add(car4);
        catalog.add(car1);
        catalog.add(car3);
        catalog.add(car2);

        catalog.sortWithStrategy(CarComparators.BY_POWER);

        assertEquals(100, catalog.get(0).getPower());
        assertEquals(100, catalog.get(1).getPower());
        assertEquals(100, catalog.get(2).getPower());
        assertEquals(200, catalog.get(3).getPower());
    }


    // ПРОВЕРЯЕТ СОРТИРОВКУ СРАЗУ ПО ВСЕМ ТРЁМ ПОЛЯМ
    @Test
    void sortWithStrategySortsByAllFields() {
        catalog.add(car1);
        catalog.add(car2);
        catalog.add(car4);
        catalog.add(car3);

        catalog.sortWithStrategy(CarComparators.BY_ALL);

        assertEquals(car3, catalog.get(0));
        assertEquals(car2, catalog.get(1));
        assertEquals(car1, catalog.get(2));
        assertEquals(car4, catalog.get(3));
    }


    // ПРОВЕРЯЕТ, ЧТО SETARR ПОЛНОСТЬЮ ЗАМЕНЯЕТ ВНУТРЕННИЙ СПИСОК КАТАЛОГА
    @Test
    void setArrReplacesCatalogData() {
        catalog.add(car1);

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car2);
        cars.add(car3);

        catalog.setArr(cars);

        assertEquals(2, catalog.size());
        assertEquals(car2, catalog.get(0));
        assertEquals(car3, catalog.get(1));
    }


    // ПРОВЕРЯЕТ, ЧТО ЧЕРЕЗ SETSORTER МОЖНО ПОДМЕНИТЬ СТРАТЕГИЮ СОРТИРОВКИ
    @Test
    void setSorterChangesSortStrategy() {
        catalog.add(car1);
        catalog.add(car2);

        catalog.setSorter((arr, comparator) -> {
            Car temp = arr.get(0);
            arr.set(0, arr.get(1));
            arr.set(1, temp);
        });

        catalog.sortWithStrategy(CarComparators.BY_POWER);

        assertEquals(car2, catalog.get(0));
        assertEquals(car1, catalog.get(1));
    }
}