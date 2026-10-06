package ListFillers;

import Models.Car;
import Models.User;
import Validation.UserValidator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserListFiller implements ListFiller {
    private static final Pattern USER_PATTERN = Pattern.compile("^([^;]+);([^;]+);([^;]+)$");
    private static final String USER_FILE_PATH  = "src/Files/UserList";
    private static final Scanner SCANNER = new Scanner(System.in);


    //Вызываем заполнение через этот метод

    @Override
    public List<User> listFill(String fillMethod) {
        if (fillMethod.toLowerCase().contains("файл")) {
            return fileFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("ручн")) {
            return manualFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("рандом")) {
            return randomFiller(ArrayLenghtScanner.scanSize());
        } else {
            System.out.println("Filling error");
            return null;
        }
    }

    private User parseUser(String line) {
        Matcher matcher = USER_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректная строка: " + line);
        }

        return User.builder()
                .name(matcher.group(1))
                .password(matcher.group(2))
                .email(matcher.group(3))
                .build();
    }


    private List<String> readFileLines(){
        Path userPath = Paths.get(USER_FILE_PATH);

        List<String> lines;

        try {
            lines = Files.readAllLines(userPath);
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }

        return lines;
    }

    @Override
    public List<User> manualFiller(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<User> users = new ArrayList<>();
        boolean isFillingFinished = false;

        while (!isFillingFinished) {

            System.out.println("\nВведите данные в формате: Имя;Пароль;Почта");
            System.out.println("Чтобы закончить введите: \"Стоп\"");

            String fillingLine = SCANNER.nextLine();

            if (!fillingLine.equalsIgnoreCase("Стоп")) {
                Matcher matcher = USER_PATTERN.matcher(fillingLine);

                if (!matcher.matches()) {
                    System.out.println("\nНекорректный формат или вводимые данные: " + fillingLine);
                    continue;
                }

                users.add(parseUser(fillingLine));
            }

            if (fillingLine.equalsIgnoreCase("Стоп")) {
                isFillingFinished = true;
            }

            if (users.size() == size) {
                isFillingFinished = true;
            }
        }
        return users;
    }

    @Override
    public List<User> randomFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        Collections.shuffle(lines);

        List<User> users = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            users.add(parseUser(lines.get(i)));
        }

        return users;
    }

    @Override
    public List<User> fileFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<User> users = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            users.add(parseUser(lines.get(i)));
        }

        return users;
    }
}
