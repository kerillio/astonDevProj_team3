package ui.screens;

import catalogs.Catalog;
import comparators.CarComparators;
import comparators.StudentComparators;
import comparators.UserComparators;
import listFillers.ListFiller;
import models.Car;
import models.ICustomModel;
import models.Student;
import models.User;
import parsers.IModelParser;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;
import ui.SortType;


import java.util.Comparator;
import java.util.Scanner;

// ЭКРАН СОРТИРОВКИ И ВЫВОДА ИТОГОВОЙ КОЛЛЕКЦИИ (ФИНАЛЬНЫЙ ЭКРАН)
public final class SortedListScreen implements Screen {

    // ЗАПОЛНИТЕЛИ, ОТВЕЧАЮТ ЗА ПОЛУЧЕНИЕ КОЛЛЕКЦИЙ ДЛЯ КАЖДОГО ТИПА МОДЕЛИ
    private final ListFiller<Car> carFiller;
    private final ListFiller<Student> studentFiller;
    private final ListFiller<User> userFiller;

    // ПАРСЕРЫ
    private final IModelParser<Car> carParser;
    private final IModelParser<Student> studentParser;
    private final IModelParser<User> userParser;

    public SortedListScreen(
            ListFiller<Car> carFiller,
            ListFiller<Student> studentFiller,
            ListFiller<User> userFiller,
            IModelParser<Car> carParser,
            IModelParser<Student> studentParser,
            IModelParser<User> userParser
    ) {
        this.carFiller = carFiller;
        this.studentFiller = studentFiller;
        this.userFiller = userFiller;

        this.carParser = carParser;
        this.studentParser = studentParser;
        this.userParser = userParser;
    }

    @Override
    public ScreenId show(AppState state, Scanner scanner) throws InterruptedException {
        clearConsole();

        System.out.println("Результат сортировки");
        System.out.println();

        try {
            // В ЗАВИСИМОСТИ ОТ ВЫБРАННОГО КЛАССА ЗАПУСКАЕМ НУЖНУЮ ОБРАБОТКУ
            switch (state.getSelectedClass()) {
                case "Car":
                    showCars(state);
                    break;

                case "Student":
                    showStudents(state);
                    break;

                case "User":
                    showUsers(state);
                    break;

                default:
                    throw new IllegalStateException("Неизвестный класс: " + state.getSelectedClass());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // ПОСЛЕ ВЫВОДА РЕЗУЛЬТАТА ПОКАЗЫВАЕМ НАВИГАЦИЮ
        return navigation(scanner);
    }

    private void showCars(AppState state) throws InterruptedException {
        Catalog<Car> cars = fillCatalog(carFiller, carParser, state);
        state.setSortedData(cars);

        Comparator<Car> comparator = switch (state.getSelectedField()) {
            case "power" -> {
              if (state.getSortType() == SortType.EVEN_NUMERIC) {
                cars.setSorter(new strategies.sort.EvenNumericSortStrategy<>(Car::getPower));
              }
              yield CarComparators.BY_POWER;
            }
            case "model" -> CarComparators.BY_MODEL;
            case "year" -> {
              if (state.getSortType() == SortType.EVEN_NUMERIC) {
                cars.setSorter(new strategies.sort.EvenNumericSortStrategy<>(Car::getYear));
              }
              yield CarComparators.BY_YEAR;
            }
            case "ALL" -> CarComparators.BY_ALL;
            default -> throw new IllegalArgumentException(
                    "Неизвестное поле автомобиля: " + state.getSelectedField()
            );
        };

        sortAndPrint(cars, comparator, state);
    }

    private void showStudents(AppState state) throws InterruptedException {
        Catalog<Student> students = fillCatalog(studentFiller, studentParser, state);
        state.setSortedData(students);

        // ВЫБИРАЕМ НУЖНЫЙ КОМПАРАТОР В ЗАВИСИМОСТИ ОТ ВЫБРАННОГО ПОЛЯ
        Comparator<Student> comparator = switch (state.getSelectedField()) {
            case "groupNumber" -> StudentComparators.BY_GROUP_NUMBER;
            case "averageGrade" -> StudentComparators.BY_AVERAGE_GRADE;
            case "recordBookNumber" -> StudentComparators.BY_RECORD_BOOK_NUMBER;
            case "ALL" -> StudentComparators.BY_ALL;
            default -> throw new IllegalArgumentException("Неизвестное поле студента: " + state.getSelectedField());
        };

        sortAndPrint(students, comparator, state);
    }

    private void showUsers(AppState state) throws InterruptedException {
        Catalog<User> users = fillCatalog(userFiller, userParser, state);
        state.setSortedData(users);

        Comparator<User> comparator = switch (state.getSelectedField()) {
            case "name" -> UserComparators.BY_NAME;
            case "password" -> UserComparators.BY_PASSWORD;
            case "email" -> UserComparators.BY_EMAIL;
            case "ALL" -> UserComparators.BY_ALL;
            default -> throw new IllegalArgumentException(
                    "Неизвестное поле пользователя: " + state.getSelectedField()
            );
        };

        sortAndPrint(users, comparator, state);
    }

    // ВЫБИРАЕТ СПОСОБ СОЗДАНИЯ КАТАЛОГА
    private <T extends ICustomModel> Catalog<T> fillCatalog(ListFiller<T> filler, IModelParser<T> parser, AppState state) {
        switch (state.getFillMethod()) {
            case FILE:
                return filler.fileFiller(state.getListLength());

            case RANDOM:
                return filler.randomFiller(state.getListLength());

            case MANUAL:
                return fillManualCatalog(parser, state);

            default:
                throw new IllegalStateException("Неизвестный способ заполнения");
        }
    }

    // ПРЕОБРАЗУЕТ СОХРАНЁННЫЕ СТРОКИ РУЧНОГО ВВОДА В ОБЪЕКТЫ МОДЕЛИ (CAR, USER, STUDENT)
    private <T extends ICustomModel> Catalog<T> fillManualCatalog(IModelParser<T> parser, AppState state) {
        Catalog<T> catalog = new Catalog<>();

        for (String line : state.getManualInputLines()) {
            catalog.add(parser.parse(line));
        }

        return catalog;
    }

    //ЗАПУСКАЕТ КАСТОМНУЮ СОРТИРОВКУ ЧЕРЕЗ STRATEGY И ВЫВОДИТ РЕЗУЛЬТАТ
    private <T extends ICustomModel> void sortAndPrint(Catalog<T> catalog, Comparator<T> comparator, AppState state) throws InterruptedException {
        catalog.sortWithStrategy(comparator, state);
        for (int i = 0; i < catalog.size(); i ++) {
            System.out.println(i+1 + ". " + catalog.get(i));
        }
        if (state.getSortType() == SortType.FIND_BY_FIELD) {
            System.out.println("\nВсего найдено значений: " + catalog.size());
        }
    }

    // ОБРАБАТЫВАЕТ ДЕЙСТВИЯ ПОЛЬЗОВАТЕЛЯ ПОСЛЕ ПРОСМОТРА РЕЗУЛЬТАТА
    private ScreenId navigation(Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("1. В главное меню");
            System.out.println("2. Записать отсортированную в файл");
            System.out.println("3. Назад");
            System.out.println("4. Выход");

            switch (scanner.nextLine().trim()) {
                case "1":
                    return ScreenId.HOME;

                case "2":
                    return ScreenId.RECORD_FILE;

                case "3":
                    return ScreenId.BACK;

                case "4":
                    return ScreenId.EXIT;

                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}