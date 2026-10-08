package listFillers;

import catalogs.Catalog;
import models.Car;
import parsers.IModelParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class CarListFiller implements ListFiller<Car> {
    private static final String CAR_DATA_PATH = "src/files/CarList";
    private final IModelParser<Car> parser;

    public CarListFiller(IModelParser<Car> parser) {
        this.parser = parser;
    }


    private List<String> readFileLines() {
        Path path = Path.of(CAR_DATA_PATH);
        try {
            return new ArrayList<>(java.nio.file.Files.readAllLines(path)); // ВОТ ТУТ МЫ ЧИТАЕМ ФАЙЛ
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл", e);
        }
    }

    // ЗАПОЛНЯЕТ КАТАЛОГ СЛУЧАЙНЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА
    @Override
    public Catalog<Car> randomFiller(int size) {
        List<String> lines = readFileLines();

//        validateSize(size, lines.size());

        Collections.shuffle(lines);

        Catalog<Car> cars = new Catalog<Car>();

        for (int i = 0; i < size; i++) {
            cars.add(parser.parse(lines.get((int) Math.floor(Math.random() * lines.size()))));
        }



        return cars;
    }

    // ЗАПОЛНЯЕТ КАТАЛОГ ПЕРВЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА В ИСХОДНОМ ПОРЯДКЕ
    @Override
    public Catalog<Car> fileFiller(int size) {
        List<String> lines = readFileLines();

        validateSize(size, lines.size());

        Catalog<Car> cars = new Catalog<Car>();


//        for (int i = 0; i < size; i++) {
//            cars.add(parser.parse(lines.get(i)));
//        }

        lines.stream().limit(size).forEach(l -> cars.add(parser.parse(l)));

        return cars;
    }

    // ПРОВЕРКА РАЗМЕРА ВВОДА В ДИАПАЗОНЕ
    private void validateSize(int size, int availableSize){
        if (size <= 0 || size > availableSize) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }
    }
}