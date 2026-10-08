package ui.screens;

import listFillers.CarListFiller;
import listFillers.ListFiller;
import listFillers.StudentListFiller;
import listFillers.UserListFiller;
import models.ICustomModel;
import ui.AppState;
import ui.FillMethod;
import ui.Screen;
import ui.ScreenId;
import java.util.Scanner;

// ЭКРАН ВВОДА НА ЗАПРОС КОЛИЧЕСТВА ЭЛЕМЕНТОВ В КОЛЛЕКЦИИ
public final class ListLengthScreen implements Screen {

    private final CarListFiller carListFiller;
    private final StudentListFiller studentListFiller;
    private final UserListFiller userListFiller;

    public ListLengthScreen(CarListFiller carListFiller, StudentListFiller studentListFiller, UserListFiller userListFiller){
        this.carListFiller = carListFiller;
        this.studentListFiller = studentListFiller;
        this.userListFiller = userListFiller;
    }
    @Override
    public ScreenId show(AppState state, Scanner scanner) {
        while (true) {
            clearConsole();
            int availableSize = selectListFiller(state).countLines();
            // ПОКАЗЫВАЕМ ТЕКУЩЕЕ ЗАПОЛНЕНИЕ ПОЛЬЗОВАТЕЛЮ
            System.out.println("Выбран класс: " + state.getSelectedClass());
            System.out.println("Способ заполнения: " + state.getFillMethod());
            System.out.println("Тип сортировки: " + state.getSortType());


            // ПОДСКАЗКИ ПОЛЬЗОВАТЕЛЮ
            System.out.print("Введите длину списка");
            System.out.println("[Количество строк в файле - " + availableSize + "]:");

            System.out.println("Или введите:");
            System.out.println("назад");
            System.out.println("выход");

            // ОБРАБАТЫВАЕМ ВВОД ПОЛЬЗОВАТЕЛЯ
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("назад")) {
                return ScreenId.BACK;
            }

            if (input.equalsIgnoreCase("выход")) {
                return ScreenId.EXIT;
            }

            try {
                int length = Integer.parseInt(input);

                // КОЛЛЕКЦИЯ НЕ ДОЛЖНА БЫТЬ ПУСТОЙ ИЛИ ОТРИЦАТЕЛЬНОГО РАЗМЕРА
                if (length <= 0) {
                    System.out.println("Длина списка должна быть больше 0");
                    continue;
                }

                // ДЛЯ FILE И RANDOM ПРОВЕРЯЕМ, ЧТО ПОЛЬЗОВАТЕЛЬ
                // НЕ ЗАПРОСИЛ БОЛЬШЕ ЭЛЕМЕНТОВ, ЧЕМ ЕСТЬ СТРОК В ФАЙЛЕ
                if (state.getFillMethod() != FillMethod.MANUAL) {
                    if (length > availableSize) {
                        System.out.println("Доступно только " + availableSize + " элементов");
                        continue;
                    }
                }

                // ОБНОВЛЯЕМ ИНФОРМАЦИЮ О РАЗМЕРЕ ЗАПОЛНЯЕМОЙ КОЛЛЕКЦИИ
                state.setListLength(length);

                // ПРИ РУЧНОМ ЗАПОЛНЕНИИ СНАЧАЛА ПЕРЕХОДИМ К ВВОДУ ОБЪЕКТОВ РУКАМИ
                if (state.getFillMethod() == FillMethod.MANUAL) {
                    return ScreenId.MANUAL_INPUT;
                }

                // ДЛЯ ФАЙЛА И РАНДОМА СРАЗУ ПЕРЕХОДИМ В ВЫБОРУ ПОЛЯ СОРТИРОВКИ
                return ScreenId.FIELD_SELECT;

            } catch (NumberFormatException e) {
                System.out.println("Введите целое число, \"назад\" или \"выход\"");
            }
        }
    }
    private ListFiller<? extends ICustomModel> selectListFiller(AppState state){
        return switch (state.getSelectedClass()) {
            case "Car" -> carListFiller;
            case "Student" -> studentListFiller;
            case "User" -> userListFiller;
            default -> throw new IllegalStateException("Неизвестный класс: " + state.getSelectedClass());
        };
    }
    private void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
}