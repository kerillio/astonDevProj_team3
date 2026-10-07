package parsers;

import models.Student;
import validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentParser implements IModelParser<Student> {
    private static final Pattern STUDENT_PATTERN = Pattern.compile("^([^;]+);(\\d+(?:\\.\\d+)?);([^;]+)$");

    private final Validator<Student> validator;

    public StudentParser(Validator<Student> validator) {
        this.validator = validator;
    }

    @Override
    public Student parse(String line) {
        Matcher matcher = STUDENT_PATTERN.matcher(line);

        // ЕСЛИ У НАС ПО РЕГУЛЯРКЕ НЕ ПРОХОДЯТ ДАННЫЕ (ДАННЫЕ ПРИХОДЯТ ТАКЖЕ С ФАЙЛА - МОГУТ БИТЬ БИТЫЕ)
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректные данные студента: " + line);
        }

        // СОБИРАЕМ ОБЪЕКТ
        Student student = Student.builder()
                .groupNumber(matcher.group(1))
                .averageGrade(Double.parseDouble(matcher.group(2)))
                .recordBookNumber(matcher.group(3))
                .build();

        // ПРОВЕРИМ НА КОРРЕКТНОСТЬ ДОПУСТИМЫХ ЗНАЧЕНИЙ
        validator.validate(student);

        return student;
    }
}
