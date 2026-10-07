package ui.screens;

import ui.AppState;
import ui.Screen;
import ui.ScreenId;

import java.util.List;
import java.util.Scanner;

// ЭКРАН ВЫБОРА КЛАССА ПО КОТОРОМУ БУДЕМ СОРТИРОВАТЬ
public final class ClassSelectScreen implements Screen {

    private final List<String> classNames;

    public ClassSelectScreen(List<String> classNames) {
        this.classNames = classNames;
    }

    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        while (true) {
            clearConsole();

            System.out.println("Выберите класс для сортировки:");

            // ВЫВОДИМ ВСЕ ДОСТУПНЫЕ КЛАССЫ С НОМЕРАМИ
            // 1. Car
            // 2. Student
            // 3. User
            for (int i = 0; i < classNames.size(); i++) {
                System.out.println((i + 1) + ". " + classNames.get(i));
            }

            // 4. Выход
            System.out.println((classNames.size() + 1) + ". Выход");

            // ВВОД ПОЛЬЗОВАТЕЛЯ
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                // ВЫХОД ИЗ ПРОГРАММЫ
                if (choice == classNames.size() + 1) {
                    return ScreenId.EXIT;
                }

                // НЕКОРРЕКТНЫЙ ВВОД ПОЛЬЗОВАТЕЛЯ
                if (choice < 1 || choice > classNames.size()) {
                    System.out.println("Введите значение внутри диапазона");
                    continue;
                }

                // СОХРАНЯЕМ ШАБЛОН ВЫБРАННОГО КЛАССА ДЛЯ СОРТИРОВКИ В ОБЩЕЕ СОСТОЯНИЕ (APPSTATE)
                state.setSelectedClass(classNames.get(choice - 1));

                // НА СЛЕДУЮЩИЙ ЭКРАН
                return ScreenId.FILL_METHOD;

            } catch (NumberFormatException e) {
                System.out.println("Введите число");
            }
        }
    }

    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}
