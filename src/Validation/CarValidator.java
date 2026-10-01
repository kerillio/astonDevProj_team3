package Validation;
import Models.Car;
import java.time.Year;

public final class CarValidator implements Validator<Car> {

    @Override
    public void validate(Car car) {
        if (car == null)
            throw new IllegalArgumentException("Автомобиль не указан");

        validatePower(car.getPower());
        validateModel(car.getModel());
        validateYear(car.getYear());
    }

    // ВАЛИДАЦИЯ ПО МОЩНОСТИ
    private void validatePower(int power) {
        if (power <= 0)
            throw new IllegalArgumentException("Мощность должна быть больше 0");

        if (power > 5000)
            throw new IllegalArgumentException("Мощность должна быть не больше 5000");
    }


    // ВАЛИДАЦИЯ ПО МОДЕЛИ
    private void validateModel(String model) {
        if (model == null || model.isBlank())
            throw new IllegalArgumentException("Не указана модель автомобиля");

        model = model.trim();

        if (model.length() > 100)
            throw new IllegalArgumentException("Слишком длинное описание модели");

    }

    // ВАЛИДАЦИЯ ПО ГОДУ ВЫПУСКА
    private void validateYear(int year) {
        int currentYear = Year.now().getValue();

        if (year < 1886)
            throw new IllegalArgumentException("Выпуск начинается с 1886");


        if (year > currentYear + 1)
            throw new IllegalArgumentException("Год выпуска не может быть больше текущего ");
    }
}