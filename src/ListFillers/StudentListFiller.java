package ListFillers;

import Models.Student;

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

public final class StudentListFiller implements ListFiller<Student> {

    private static final Pattern STUDENT_PATTERN = Pattern.compile("^(\\d+);(\\d+(?:\\.\\d+)?);(\\d{4})$");
    private static final String STUDENT_DATA_PATH = "src/Files/StudentList";
    private static final Scanner SCANNER = new Scanner(System.in);



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
        return null; // РЕАЛИЗАЦИЯ ПОЛЬЗОВАТЕЛЬСКОГО ВВОДА
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

        List<Student> students = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            students.add(parseStudent(lines.get(i)));
        }

        return students;
    }
}