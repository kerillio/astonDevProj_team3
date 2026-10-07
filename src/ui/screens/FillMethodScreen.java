package ui.screens;

import ui.AppState;
import ui.FillMethod;
import ui.Screen;
import ui.ScreenId;
import java.util.Scanner;

// ЭКРАН ВЫБОРА СПОСОБА ЗАПОЛНЕНИЯ КОЛЛЕКЦИИ
public final class FillMethodScreen implements Screen {

    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        while (true) {
            clearConsole();

            // ПОКАЗЫВАЕМ РАНЕЕ ВЫБРАННЫЙ КЛАСС
            System.out.println("Выбран класс: " + state.getSelectedClass());
            System.out.println();

            // ВЫВОДИМ ПОДСКАЗКИ ПОЛЬЗОВАТЕЛЮ
            System.out.println("Выберите способ заполнения:");
            System.out.println("1. Из файла");
            System.out.println("2. Рандом");
            System.out.println("3. Заполнение вручную");
            System.out.println("4. Назад");
            System.out.println("5. Выход");

            // ОБРАБАТЫВАЕМ ВВОД ПОЛЬЗОВАТЕЛЯ
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    state.setFillMethod(FillMethod.FILE); // ЗАПОЛНЯЕМ ЧЕРЕЗ ФАЙЛ
                    return ScreenId.SORT_TYPE;

                case "2":
                    state.setFillMethod(FillMethod.RANDOM); // ЗАПОЛНЯЕМ ЧЕРЕЗ РАНДОМ
                    return ScreenId.SORT_TYPE;

                case "3":
                    state.setFillMethod(FillMethod.MANUAL); // ЗАПОЛНЯЕМ ЧЕРЕЗ РУЧКИ ПОЛЬЗОВАТЕЛЯ :)
                    return ScreenId.SORT_TYPE;

                case "4":
                    return ScreenId.BACK;

                case "5":
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
