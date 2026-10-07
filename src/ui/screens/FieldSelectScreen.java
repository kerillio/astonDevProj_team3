package ui.screens;

import models.ICustomModel;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// ЭКРАН ВЫБОРА ПОЛЯ, ПО КОТОРОМУ БУДЕТ ВЫПОЛНЯТЬСЯ СОРТИРОВКА
public final class FieldSelectScreen implements Screen {

    private final Map<String, ICustomModel> models;

    public FieldSelectScreen(Map<String, ICustomModel> models) {
        this.models = models;
    }

    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        while (true) {
            clearConsole();

            // ПОЛУЧАЕМ МОДЕЛЬ ПО НАЗВАНИЮ КЛАССА (СТРОКИ)
            ICustomModel model = models.get(state.getSelectedClass());

            if (model == null) {
                throw new IllegalStateException("Не найдена модель: " + state.getSelectedClass());
            }

            // ПОЛУЧАЕМ СПИСОК ПОЛЕЙ КОНКРЕТНОЙ МОДЕЛИ
            List<String> fields = model.getFields();

            System.out.println("Выбран класс: " + state.getSelectedClass());
            System.out.println("Тип сортировки: " + state.getSortType());
            System.out.println("Длина списка: " + state.getListLength());
            System.out.println();

            System.out.println("Выберите поле для сортировки:");

            // ВЫВОДИМ ВСЕ ДОСТУПНЫЕ ПОЛЯ ДЛЯ СОРТИРОВКИ
            for (int i = 0; i < fields.size(); i++) {
                System.out.println((i + 1) + ". " + fields.get(i));
            }

            int allFieldsChoice = fields.size() + 1;
            int backChoice = fields.size() + 2;
            int exitChoice = fields.size() + 3;

            System.out.println(allFieldsChoice + ". Все поля");
            System.out.println(backChoice + ". Назад");
            System.out.println(exitChoice + ". Выход");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                // ВЫБОР СОРТИРОВКИ СРАЗУ ПО ВСЕМ ПОЛЯМ
                if (choice == allFieldsChoice) {
                    state.setSelectedField("ALL");
                    return ScreenId.SORTED_LIST;
                }

                if (choice == backChoice) {
                    return ScreenId.BACK;
                }

                if (choice == exitChoice) {
                    return ScreenId.EXIT;
                }

                if (choice < 1 || choice > fields.size()) {
                    System.out.println("Введите значение внутри диапазона");
                    continue;
                }

                // СОХРАНЯЕМ КОНКРЕТНОЕ ПОЛЕ ВЫБРАННОЕ ПОЛЬЗОВАТЕЛЕМ
                state.setSelectedField(fields.get(choice - 1));
                return ScreenId.SORTED_LIST;

            } catch (NumberFormatException e) {
                System.out.println("Введите число");
            }
        }
    }

    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}