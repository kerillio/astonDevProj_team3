package Models;
import ListFillers.CarListFiller;
import ListFillers.ListFiller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class Car implements ICustomModel{
    // ПОЛЯ
    private final int power;
    private final String model;
    private final int year;



    // КОНСТРУКТОР
    private Car(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;

    }

    // ПУБЛИЧНОЕ АПИ
    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    // BUILDER
    public static Builder builder() {
        return new Builder();
    }


    public static Builder builder(Car car) {
        Objects.requireNonNull(car, "Не указана машина");

        return new Builder()
                .power(car.getPower())
                .model(car.getModel())
                .year(car.getYear());
    }

    // ВЫВОД
    @Override
    public String toString() {
        return "Машина" +
                "\n\tмощность: " + power +
                "\n\tмодель: " + model +
                "\n\tгод выпуска: " + year;
    }

    // СРАВНЕНИЕ
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Car car)) {
            return false;
        }

        return power == car.power
                && year == car.year
                && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public  ArrayList<String> getFields() {
        return new ArrayList<>(List.of("power", "year", "model"));
    }



    //Реализация Builder через статический внутренний класс
    // Примечание: для каждого класса мы реализуем свой билдер - у нас нет общих данных
    public static final class Builder {
        private int power;
        private String model;
        private int year;

        private Builder() {
        }

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }
}
