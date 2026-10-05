package ListFillers;

import Models.Car;
import Models.Student;
import Validation.StudentValidator;

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

public final class StudentListFiller implements ListFiller {

    private static final Pattern STUDENT_PATTERN = Pattern.compile("^(\\d+);(\\d+(?:\\.\\d+)?);(\\d{4})$");
    private static final String STUDENT_DATA_PATH = "src/Files/StudentList";
    private static final java.util.Scanner SCANNER = new java.util.Scanner(System.in);


    //Вызываем заполнение через этот метод
    @Override
    public List<Student> listFill(String fillMethod) {
        if (fillMethod.toLowerCase().contains("файл")) {
            return fileFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("ручн")) {
            return manualFiller(ArrayLenghtScanner.scanSize());
        } else if (fillMethod.toLowerCase().contains("рандом")) {
            return randomFiller(ArrayLenghtScanner.scanSize());
        } else return null;
    }

    private Student parseStudent(String line) {
        Matcher matcher = STUDENT_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректная строка: " + line);
        }

        return Student.builder()
                .groupNumber(matcher.group(1))
                .averageGrade(Double.parseDouble(matcher.group(2)))
                .recordBookNumber(matcher.group(3))
                .build();
    }

    private List<String> readFileLines() {
        Path studentPath = Paths.get(STUDENT_DATA_PATH);

        try {
            return Files.readAllLines(studentPath);
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }
    }

    @Override
    public List<Student> manualFiller(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<Student> students = new ArrayList<>();
        boolean isFillingFinished = false;

        while (!isFillingFinished) {

            System.out.println("\nВведите данные в формате: НомерГруппы;СреднийБалл;НомерЗачётки");
            System.out.println("Чтобы закончить введите: \"Стоп\"");

            String fillingLine = SCANNER.nextLine();

            if (!fillingLine.equalsIgnoreCase("Стоп")) {
                Matcher matcher = STUDENT_PATTERN.matcher(fillingLine);

                if (!matcher.matches()) {
                    System.out.println("\nНекорректный формат или вводимые данные: " + fillingLine);
                    continue;
                }

                students.add(parseStudent(fillingLine));
            }

            if (fillingLine.equalsIgnoreCase("Стоп")) {
                isFillingFinished = true;
            }

            if (students.size() == size) {
                isFillingFinished = true;
            }
        }
        return students;
    }

    @Override
    public List<Student> randomFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        Collections.shuffle(lines);

        List<Student> students = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            students.add(parseStudent(lines.get(i)));
        }

        return students;
    }

    @Override
    public List<Student> fileFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            students.add(parseStudent(lines.get(i)));
        }

        return students;
    }
}