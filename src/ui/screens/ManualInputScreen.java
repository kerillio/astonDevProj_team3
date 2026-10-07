package ui.screens;

import models.Car;
import models.Student;
import models.User;
import parsers.IModelParser;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;
import java.util.Scanner;

// ЭКРАН РУЧНОГО ВВОДА ПОЛЬЗОВАТЕЛЕМ
public final class ManualInputScreen implements Screen {
    private final IModelParser<Car> carParser;
    private final IModelParser<Student> studentParser;
    private final IModelParser<User> userParser;

    public ManualInputScreen(IModelParser<Car> carParser, IModelParser<Student> studentParser, IModelParser<User> userParser) {
        this.carParser = carParser;
        this.studentParser = studentParser;
        this.userParser = userParser;
    }

    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        state.clearManualInputLines();

        // ПОЛЬЗОВАТЕЛЬ ВВОДИТ, ПОКА НЕ БУДЕТ ВВЕДЕНО УКАЗАННОЕ КОЛИЧЕСТВО ОБЪЕКТОВ
        while (state.getManualInputLines().size() < state.getListLength()) {
            clearConsole();

            int currentNumber = state.getManualInputLines().size() + 1;

            System.out.println("Выбран класс: " + state.getSelectedClass());
            System.out.println("Необходимо ввести объектов: " + state.getListLength());
            System.out.println("Введено: " + state.getManualInputLines().size());
            System.out.println();

            // ВЫВОДИМ ПОДСКАЗКУ ПОЛЬЗОВАТЕЛЮ
            printInputFormat(state.getSelectedClass());


            System.out.println();
            System.out.println("Введите объект №" + currentNumber);
            System.out.println("Или введите \"назад\" / \"выход\"");

            // ОБРАБАТЫВАЕМ ВВОД ПОЛЬЗОВАТЕЛЯ
            String input = scanner.nextLine().trim();


            if (input.equalsIgnoreCase("назад")) {
                state.clearManualInputLines();
                return ScreenId.BACK;
            }

            if (input.equalsIgnoreCase("выход")) {
                return ScreenId.EXIT;
            }

            try {
                // ПРОВЕРЯЕМ ВВЕДЁННУЮ СТРОКУ ЧЕРЕЗ ПАРСЕР И ВАЛИДАТОР
                validateInput(state.getSelectedClass(), input);
                state.addManualInputLine(input);

            } catch (IllegalArgumentException e) {
                System.out.println();
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Нажмите Enter, чтобы попробовать ещё раз");
                scanner.nextLine();
            }
        }
        // ПОСЛЕ ЗАПОЛНЕНИЯ ПЕРЕХОДИМ К ВЫБОРУ ПОЛЯ СОРТИРОВКИ
        return ScreenId.FIELD_SELECT;
    }

    private void validateInput(String selectedClass, String input) {
        switch (selectedClass) {
            case "Car":
                carParser.parse(input);
                break;

            case "Student":
                studentParser.parse(input);
                break;

            case "User":
                userParser.parse(input);
                break;

            default:
                throw new IllegalStateException("Неизвестный класс: " + selectedClass);
        }
    }

    // ПОДСКАЗКИ ДЛЯ ПОЛЬЗОВАТЕЛЯ
    private void printInputFormat(String selectedClass) {
        switch (selectedClass) {
            case "Car":
                System.out.println("Формат: мощность;модель;год");
                System.out.println("Пример: 150;BMW;2020");
                break;

            case "Student":
                System.out.println("Формат: номерГруппы;среднийБалл;номерЗачётнойКнижки");
                System.out.println("Пример: P41-359;4.3;432-359-4329");
                break;

            case "User":
                System.out.println("Формат: имя;пароль;почта");
                System.out.println("Пример: German;2004220;german@mail.ru");
                break;

            default:
                throw new IllegalStateException("Неизвестный класс: " + selectedClass);
        }
    }

    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}