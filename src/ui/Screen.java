package ui;

import java.util.Scanner;

// ОБЩИЙ ИНТЕРФЕЙС ДЛЯ ВСЕХ ЭКРАНОВ
public interface Screen {
    // ПОКАЗЫВАЕТ ЭКРАН, ОБРАБАТЫВАЕТ ВВОД ПОЛЬЗОВАТЕЛЯ И ВОЗВРАЩАЕТ КУДА ПРИЛОЖЕНИЕ ДОЛЖНО ПЕРЕЙТИ ДАЛЬШЕ
    ScreenId show(AppState state, Scanner scanner);
}