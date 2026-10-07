package ui.screens;

import catalogs.Catalog;
import models.Car;
import models.Student;
import models.User;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;

import java.util.Scanner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RecordToFileScreen implements Screen {
    private static final String RECORD_FILE_PATH = "src/files/Records";
    String sessionMetadata = "--- " + LocalDateTime.now().toString() + " ---\n";
    Path path = Paths.get(RECORD_FILE_PATH);
    List<String> linesRecord;



    @Override
    public ScreenId show(AppState state, Scanner scanner) {
            clearConsole();
            state.getSortedData().forEach(System.out::println);

        try {
            // В ЗАВИСИМОСТИ ОТ ВЫБРАННОГО КЛАССА ЗАПУСКАЕМ НУЖНУЮ ОБРАБОТКУ
            switch (state.getSelectedClass()) {
                case "Car":
                    carRecord((Catalog<Car>) state.getSortedData());
                    break;

                case "Student":
                    studentRecord((Catalog<Student>) state.getSortedData());
                    break;

                case "User":
                    userRecord((Catalog<User>) state.getSortedData());
                    break;

                default:
                    throw new IllegalStateException("Неизвестный класс: " + state.getSelectedClass());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

            System.out.println("Сохраняем в файл.....");

            return navigation(scanner);
    }

    // ОБРАБАТЫВАЕТ ДЕЙСТВИЯ ПОЛЬЗОВАТЕЛЯ ПОСЛЕ ПРОСМОТРА РЕЗУЛЬТАТА
    private ScreenId navigation(Scanner scanner) {
        while (true) {
                System.out.println();
                System.out.println("1. В главное меню");
                System.out.println("2. Выход");

            switch (scanner.nextLine().trim()) {
                case "1":
                    return ScreenId.HOME;
                case "2":
                    return ScreenId.EXIT;
                default:
                    System.out.println("Некорректный выбор");

            }
        }


    }

    private void recordFile(){
        Path path = Paths.get(RECORD_FILE_PATH);
        try {
            Files.writeString(path, "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            Files.writeString(path, sessionMetadata, StandardCharsets.UTF_8 ,StandardOpenOption.APPEND);
            Files.write(path, linesRecord, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Нет файла для записи или нет прав на создания файла.");
        }
   }


    private void carRecord(Catalog<Car> cars) {
        linesRecord = cars.stream()
                .map(car -> String.format("%d;%s;%d", car.getPower(), car.getModel(), car.getYear()))
                .collect(Collectors.toList());
        recordFile();
    }

    private void studentRecord(Catalog<Student> students) {
        linesRecord = students.stream()
                .map(student -> String.format("%s;%.1f;%s", student.getGroupNumber(), student.getAverageGrade(), student.getRecordBookNumber()))
                .collect(Collectors.toList());
        recordFile();
    }

    private void userRecord(Catalog<User> users) {
        linesRecord = users.stream()
                .map(user -> String.format("%s;%s;%s", user.getName(), user.getPassword(), user.getEmail()))
                .collect(Collectors.toList());
        recordFile();
    }




    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}

