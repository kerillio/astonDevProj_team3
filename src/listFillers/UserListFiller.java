package listFillers;

import catalogs.Catalog;

import models.User;
import parsers.IModelParser;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*;


public final class UserListFiller implements ListFiller<User> {

    private static final String USER_FILE_PATH  = "src/files/UserList";
    private final IModelParser<User> parser;


    public UserListFiller(IModelParser<User> parser) {
        this.parser = parser;
    }

    private List<String> readFileLines(){
        Path userPath = Paths.get(USER_FILE_PATH);

        List<String> lines;

        try {
            lines = Files.readAllLines(userPath); // ВОТ ТУТ МЫ ЧИТАЕМ ФАЙЛ
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }

        return lines;
    }

    // ЗАПОЛНЯЕТ КАТАЛОГ СЛУЧАЙНЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА
    @Override
    public Catalog<User> randomFiller(int size) {
        List<String> lines = readFileLines();

//        validateSize(size, lines.size());

        Collections.shuffle(lines);

        Catalog<User> users = new Catalog<User>();

        for (int i = 0; i < size; i++) {
            users.add(parser.parse(lines.get((int) Math.floor(Math.random() * lines.size()))));
        }

        return users;
    }

    // ЗАПОЛНЯЕТ КАТАЛОГ ПЕРВЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА В ИСХОДНОМ ПОРЯДКЕ
    @Override
    public Catalog<User> fileFiller(int size) {
        List<String> lines = readFileLines();

        validateSize(size, lines.size());

        Catalog<User> users = new Catalog<User>();

//        for (int i = 0; i < size; i++) {
//            users.add(parser.parse(lines.get(i)));
//        }

        lines.stream().limit(size).forEach(l -> users.add(parser.parse(l)));

        return users;
    }

    // ПРОВЕРКА РАЗМЕРА ВВОДА В ДИАПАЗОНЕ
    private void validateSize(int size, int availableSize){
        if (size <= 0 || size > availableSize) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }
    }
}
