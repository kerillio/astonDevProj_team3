package comparators;
import models.Student;

import java.util.Comparator;

public final class StudentComparators {

    // < 0  object1 должен идти раньше object2
    // = 0  равны по этому полю
    // > 0  object1 должен идти после object2

    public static final Comparator<Student> BY_GROUP_NUMBER = Comparator.comparing(Student::getGroupNumber);

    public static final Comparator<Student> BY_AVERAGE_GRADE = Comparator.comparingDouble(Student::getAverageGrade);

    public static final Comparator<Student> BY_RECORD_BOOK_NUMBER = Comparator.comparing(Student::getRecordBookNumber);

    public static final Comparator<Student> BY_ALL = BY_GROUP_NUMBER.thenComparing(BY_AVERAGE_GRADE).thenComparing(BY_RECORD_BOOK_NUMBER);
}