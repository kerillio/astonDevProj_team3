package Models;
import ListFillers.ListFiller;
import ListFillers.StudentListFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Student extends AbstractCustomClass{

    // ПОЛЯ
    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    // КОНСТРУКТОР
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageGrade = builder.averageGrade;
        this.recordBookNumber = builder.recordBookNumber;
        this.listFillerStrategy = new StudentListFiller();
    }

    // ПУБЛИЧНОЕ АПИ
    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    // BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Student student) {
        Objects.requireNonNull(student, "Не указан студент");

        return new Builder()
                .groupNumber(student.getGroupNumber())
                .averageGrade(student.getAverageGrade())
                .recordBookNumber(student.getRecordBookNumber());
    }


    // ВЫВОД
    @Override
    public String toString() {
        return "Студент" +
                "\n\tНомер группы: " + groupNumber +
                "\n\tСредний балл: " + averageGrade +
                "\n\tНомер книжки: " + recordBookNumber;
    }


    // СРАВНЕНИЕ
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Student student)) {
            return false;
        }

        return Double.compare(averageGrade, student.averageGrade) == 0 // если 0 значит равны
                && Objects.equals(groupNumber, student.groupNumber)
                && Objects.equals(recordBookNumber, student.recordBookNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }


    public  ArrayList<String> getFields() {
        return new ArrayList<>(List.of("groupNumber", "averageGrade", "recordBookNumber"));
    }


    public ArrayList<AbstractCustomClass> readFromFile() {
        return null;
    }


    //Реализация Builder через статический внутренний класс
    // Примечание: для каждого класса мы реализуем свой билдер - у нас нет общих данных
    public static final class Builder {

        private String groupNumber;
        private double averageGrade;
        private String recordBookNumber;

        private Builder() {}

        public Builder groupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder averageGrade(double averageGrade) {
            this.averageGrade = averageGrade;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
