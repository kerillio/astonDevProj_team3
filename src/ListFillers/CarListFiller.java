package ListFillers;

import Models.Car;
import Validation.CarValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarListFiller implements ListFiller {

    private static final Pattern CAR_PATTERN = Pattern.compile("^(\\d{2,4});([^;]+);(\\d{4})$");
    private static final String CAR_DATA_PATH = "src/Files/CarList";
    private static final Scanner SCANNER = new Scanner(System.in);


    //Вызываем заполнение через этот метод
    @Override
    public List<Car> listFill(String fillMethod) {
        if (fillMethod.toLowerCase().contains("файл")) {
            return fileFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("ручн")) {
            return manualFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("рандом")) {
            return randomFiller(ArrayLenghtScanner.scanSize());
        } else return null;
    }

    private Car parseCar(String line) {
        Matcher matcher = CAR_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректная строка: " + line);
        }

        return Car.builder()
                .power(Integer.parseInt(matcher.group(1)))
                .model(matcher.group(2))
                .year(Integer.parseInt(matcher.group(3)))
                .build();
    }

    private List<String> readFileLines() {
        Path carPath = Paths.get(CAR_DATA_PATH);

        try {
            return Files.readAllLines(carPath);
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }
    }

    @Override
    public List<Car> manualFiller(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<Car> cars = new ArrayList<>();
        boolean isFillingFinished = false;

        while (!isFillingFinished) {

            System.out.println("\nВведите данные в формате: Мощность;МодельМашины;ГодВыпуска");
            System.out.println("Чтобы закончить введите: \"Стоп\"");

            String fillingLine = SCANNER.nextLine();

            if (!fillingLine.equalsIgnoreCase("Стоп")) {
                Matcher matcher = CAR_PATTERN.matcher(fillingLine);

                if (!matcher.matches()) {
                    System.out.println("\nНекорректный формат или вводимые данные: " + fillingLine);
                    continue;
                }

                cars.add(parseCar(fillingLine));
            }

            if (fillingLine.equalsIgnoreCase("Стоп")) {
                isFillingFinished = true;
            }

            if (cars.size() == size) {
                isFillingFinished = true;
            }
        }
        return cars;
    }


    @Override
    public List<Car> randomFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        Collections.shuffle(lines);

        List<Car> cars = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            cars.add(parseCar(lines.get(i)));
        }

        return cars;
    }


    @Override
    public List<Car> fileFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            cars.add(parseCar(lines.get(i)));
        }

        return cars;
    }
}