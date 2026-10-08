package listFillers;

import catalogs.Catalog;
import models.Student;
import parsers.IModelParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;



public final class StudentListFiller implements ListFiller<Student> {


    private static final String STUDENT_DATA_PATH = "src/files/StudentList";


    private final IModelParser<Student>  parser;

    public StudentListFiller(IModelParser<Student> parser) {
        this.parser = parser;
    }

    private List<String> readFileLines() {
        Path studentPath = Paths.get(STUDENT_DATA_PATH);

        try {
            return Files.readAllLines(studentPath); // ВОТ ТУТ МЫ ЧИТАЕМ ФАЙЛ
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }
    }


    // ЗАПОЛНЯЕТ КАТАЛОГ СЛУЧАЙНЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА
    @Override
    public Catalog<Student> randomFiller(int size) {
        List<String> lines = readFileLines();

        Collections.shuffle(lines);

        Catalog<Student> students = new Catalog<>();

        for (int i = 0; i < size; i++) {
            students.add(parser.parse(lines.get((int) Math.floor(Math.random() * lines.size()))));
        }

        return students;
    }

    // ЗАПОЛНЯЕТ КАТАЛОГ ПЕРВЫМИ ОБЪЕКТАМИ ИЗ ФАЙЛА В ИСХОДНОМ ПОРЯДКЕ
    @Override
    public Catalog<Student> fileFiller(int size) {
        List<String> lines = readFileLines();

        validateSize(size, lines.size());

        Catalog<Student> students = new Catalog<>();

        lines.stream().limit(size).forEach(l -> students.add(parser.parse(l)));

        return students;
    }

    // ПРОВЕРКА РАЗМЕРА ВВОДА В ДИАПАЗОНЕ
    private void validateSize(int size, int availableSize){
        if (size <= 0 || size > availableSize) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }
    }
}