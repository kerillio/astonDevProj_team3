package ui.screens;

import strategies.sort.DefaultSortStrategy;
import strategies.sort.FindByFieldStrategy;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;
import ui.SortType;
import java.util.Scanner;

// ЭКРАН ВЫБОРА ТИПА СОРТИРОВКИ
// ПОЗВОЛЯЕТ ВЫБРАТЬ ОБЫЧНУЮ СОРТИРОВКУ ИЛИ ДОПОЛНИТЕЛЬНУЮ СОРТИРОВКУ ПО ЧЁТНЫМ ЗНАЧЕНИЯМ
public final class SortTypeScreen implements Screen {
    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        while (true) {
            clearConsole();

            // ВЫВОДИМ УЖЕ ВЫБРАННЫЕ РАНЕЕ ПАРАМЕТРЫ из APPSTATE
            System.out.println("Выбран класс: " + state.getSelectedClass());
            System.out.println("Способ заполнения: " + state.getFillMethod());
            System.out.println();

            // ВЫВОДИМ ПОДСКАЗКИ ПОЛЬЗОВАТЕЛЮ
            if (state.getSelectedClass().equalsIgnoreCase("car")) {
                System.out.println("Выберите тип сортировки:");
                System.out.println("1. Базовая сортировка");
                System.out.println("2. Сортировка числового поля по чётным значениям");
                System.out.println("3. Поиск по значению выбранного поля, вывод списка и общего количества");
                System.out.println("4. Назад");
                System.out.println("5. Выход");
            } else {
                System.out.println("Выберите тип сортировки:");
                System.out.println("1. Базовая сортировка");
                System.out.println("2. Поиск по значению выбранного поля, вывод списка и общего количества");
                System.out.println("3. Назад");
                System.out.println("4. Выход");
            }
            // СЛУШАЕМ ВВОД ПОЛЬЗОВАТЕЛЯ
            String input = scanner.nextLine();

            // ОБРАБАТЫВАЕМ ВЫБРАННЫЙ ВАРИАНТ ОТВЕТА

            if (state.getSelectedClass().equalsIgnoreCase("car")) {
                switch (input) {
                    case "1":
                        state.setSortType(SortType.DEFAULT); // ВЫБЕРЕМ СТАНДАРТНУЮ КАСТОМНУЮ СОРТИРОВКУ
                        return ScreenId.LIST_LENGTH;

                    case "2":
                        state.setSortType(SortType.EVEN_NUMERIC); // СОРТИРОВКА ИЗ ДОП ЗАДАНИЯ
                        return ScreenId.LIST_LENGTH;

                    case "3":
                        state.setSortType(SortType.FIND_BY_FIELD); // СОРТИРОВКА ИЗ ДОП ЗАДАНИЯ
                        return ScreenId.LIST_LENGTH;

                    case "4":
                        return ScreenId.BACK;

                    case "5":
                        return ScreenId.EXIT;

                    default:
                        System.out.println("Некорректный выбор"); // БУДЕМ ЗАНОВО В WHILE(TRUE)
                }
            } else {
                switch (input) {
                    case "1":
                        state.setSortType(SortType.DEFAULT); // ВЫБЕРЕМ СТАНДАРТНУЮ КАСТОМНУЮ СОРТИРОВКУ
                        return ScreenId.LIST_LENGTH;

                    case "2":
                        state.setSortType(SortType.FIND_BY_FIELD); // ВЫБЕРЕМ СТАНДАРТНУЮ КАСТОМНУЮ СОРТИРОВКУ
                        return ScreenId.LIST_LENGTH;

                    case "3":
                        return ScreenId.BACK;

                    case "4":
                        return ScreenId.EXIT;

                    default:
                        System.out.println("Некорректный выбор"); // БУДЕМ ЗАНОВО В WHILE(TRUE)
                }
            }
        }
    }
    // ЧИСТИМ КОНСОЛЬ
    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}
