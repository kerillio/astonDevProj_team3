package parsers;

import models.Car;
import validation.Validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarParser implements IModelParser<Car> {

    private static final Pattern CAR_PATTERN = Pattern.compile("^(\\d{2,4});([^;]+);(\\d{4})$");

    private final Validator<Car> validator;

    public CarParser(Validator<Car> validator) {
        this.validator = validator;
    }

    @Override
    public Car parse(String line) {
        Matcher matcher = CAR_PATTERN.matcher(line);


        // ЕСЛИ У НАС ПО РЕГУЛЯРКЕ НЕ ПРОХОДЯТ ДАННЫЕ (ДАННЫЕ ПРИХОДЯТ ТАКЖЕ С ФАЙЛА - МОГУТ БИТЬ БИТЫЕ)
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректные данные автомобиля: " + line);
        }

        // СОБИРАЕМ ОБЪЕКТ
        Car car = Car.builder()
                .power(Integer.parseInt(matcher.group(1)))
                .model(matcher.group(2))
                .year(Integer.parseInt(matcher.group(3)))
                .build();

        // ПРОВЕРИМ НА КОРРЕКТНОСТЬ ДОПУСТИМЫХ ЗНАЧЕНИЙ
        validator.validate(car);

        return car;
    }
}