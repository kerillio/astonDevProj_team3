package validation;

import models.Student;

public final class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Не указан студент");
        }

        validateGroupNumber(student.getGroupNumber());
        validateAverageGrade(student.getAverageGrade());
        validateRecordBookNumber(student.getRecordBookNumber());
    }

    // ВАЛИДАЦИЯ ПО НОМЕРУ ГРУППЫ
    private void validateGroupNumber(String groupNumber) {
        if (groupNumber == null || groupNumber.isBlank())
            throw new IllegalArgumentException("Не указан номер группы");

        groupNumber = groupNumber.trim();

        if (groupNumber.length() > 50)
            throw new IllegalArgumentException("Номер группы не может содержать более 50 символов");
    }

    // ВАЛИДАЦИЯ ПО СРЕДНЕМУ БАЛЛУ
    private void validateAverageGrade(double averageGrade) {
        if (averageGrade < 0 || averageGrade > 5)
            throw new IllegalArgumentException("Средний балл должен быть в диапазоне от 0 до 5");
    }

    // ВАЛИДАЦИЯ ПО НОМЕРУ ЗАЧЁТНОЙ КНИЖКИ
    private void validateRecordBookNumber(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.isBlank())
            throw new IllegalArgumentException("Не указан номер зачётной книжки");

        recordBookNumber = recordBookNumber.trim();

        if (recordBookNumber.length() > 50)
            throw new IllegalArgumentException("Номер зачётной книжки не может быть более 50 символов");
    }
}
