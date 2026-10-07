package listFillers;

import catalogs.Catalog;
import models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.StudentParser;
import validation.StudentValidator;

import static org.junit.jupiter.api.Assertions.*;

class StudentListFillerTest {

    private StudentListFiller filler;

    @BeforeEach
    void setUp() {
        filler = new StudentListFiller(new StudentParser(new StudentValidator()));
    }

    // fileFiller
    // ПРОВЕРЯЕТ, ЧТО ВОЗВРАЩАЕТСЯ ПРАВИЛЬНЫЙ РАЗМЕР
    @Test
    void fileFillerReturnsCorrectSize() {
        Catalog<Student> catalog = filler.fileFiller(5);
        assertEquals(5, catalog.size());
    }
    // ПРОВЕРЯЕТ, ЧТО НЕ ВОЗВРАЩАЕТ NULL
    @Test
    void fileFillerElementsAreNotNull() {
        Catalog<Student> catalog = filler.fileFiller(5);
        for (Student student : catalog) {
            assertNotNull(student);
        }
    }
    // ПРОВЕРЯЕТ ЧТО РАЗМЕР НЕ 0
    @Test
    void fileFillerThrowsOnZeroSize() {
        assertThrows(IllegalArgumentException.class, () -> filler.fileFiller(0));
    }
    // ПРОВЕРЯЕТ ЧТО РАЗМЕР НЕ -1
    @Test
    void fileFillerThrowsOnNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> filler.fileFiller(-1));
    }
    // ПРОВЕРЯЕТ ЧТО РАЗМЕР НЕ СЛИШКОМ БОЛЬШОЙ
    @Test
    void fileFillerThrowsOnTooBigSize() {
        assertThrows(IllegalArgumentException.class, () -> filler.fileFiller(1000));
    }

    // randomFiller
    // ПРОВЕРЯЕТ, ЧТО ВОЗВРАЩАЕТСЯ ПРАВИЛЬНЫЙ РАЗМЕР
    @Test
    void randomFillerReturnsCorrectSize() {
        Catalog<Student> catalog = filler.randomFiller(5);
        assertEquals(5, catalog.size());
    }
    // ПРОВЕРЯЕТ, ЧТО НЕ ВОЗВРАЩАЕТ NULL
    @Test
    void randomFillerElementsAreNotNull() {
        Catalog<Student> catalog = filler.randomFiller(5);
        for (Student student : catalog) {
            assertNotNull(student);
        }
    }
    // ПРОВЕРЯЕТ ЧТО ГЕНЕРАТОР СЛУЧАЙНЫХ ДАННЫХ ВЫДАЕТ РАЗНЫЕ РЕЗУЛЬТАТЫ
    @Test
    void randomFillerShufflesData() {
        Catalog<Student> reference = filler.randomFiller(5);

        boolean atLeastOneDifferent = false;
        for (int i = 0; i < 30; i++) {
            Catalog<Student> next = filler.randomFiller(5);

            boolean same = true;
            for (int j = 0; j < reference.size(); j++) {
                if (!reference.get(j).equals(next.get(j))) {
                    same = false;
                    break;
                }
            }

            if (!same) {
                atLeastOneDifferent = true;
                break;
            }
        }

        assertTrue(atLeastOneDifferent, "randomFiller должен перемешивать данные");
    }
}