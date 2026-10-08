import listFillers.CarListFiller;
import listFillers.StudentListFiller;
import listFillers.UserListFiller;
import models.Car;
import models.ICustomModel;
import models.Student;
import models.User;
import parsers.CarParser;
import parsers.StudentParser;
import parsers.UserParser;
import ui.screens.ClassSelectScreen;
import ui.screens.FillMethodScreen;
import java.util.*;
import ui.screens.*;
import ui.AppState;
import ui.Screen;
import ui.ScreenId;
import validation.CarValidator;
import validation.StudentValidator;
import validation.UserValidator;

// TUI УПРАВЛЯЕТ ПЕРЕХОДАМИ МЕЖДУ ЭКРАНАМИ.
// ЭКРАНЫ ПОЛУЧАЮТ ВВОД ПОЛЬЗОВАТЕЛЯ И СОХРАНЯЮТ ЕГО В APPSTATE.
// ДАННЫЕ ПРОХОДЯТ ЧЕРЕЗ FILLER -> PARSER -> VALIDATOR И ПОПАДАЮТ В CATALOG.
// CATALOG ПЕРЕДАЁТ КОЛЛЕКЦИЮ В ВЫБРАННУЮ СТРАТЕГИЮ СОРТИРОВКИ С НУЖНЫМ COMPARATOR.


// ОБНОВЛЕННАЯ ВЕРСИЯ TUI ЗАПУСКАТЬ ЧЕРЕЗ src/Main
public class TUI_2{



    private final Scanner scanner = new Scanner(System.in);    // ОДИН ОБЩИЙ SCANNER ДЛЯ ВСЕХ ЭКРАНОВ

    // ОБЩЕЕ СОСТОЯНИЕ ТЕКУЩЕГО ПРОХОДА ПОЛЬЗОВАТЕЛЯ,
    // ХРАНИТ ВЫБРАННЫЙ КЛАСС, СПОСОБ ЗАПОЛНЕНИЯ, ТИП СОРТИРОВКИ,
    // ПОЛЕ, ДЛИНУ СПИСКА И ДАННЫЕ РУЧНОГО ВВОДА
    private final AppState state = new AppState();

    private final HashMap<ScreenId, Screen> screens = new HashMap<>(); // ЭКРАНЫ, КАЖДЫЙ ИЗ ЭКРАНОВ ВЫВОДИТ СВОЁ МЕНЮ
    private final Deque<ScreenId> screenHistory = new ArrayDeque<>(); // КАЖДЫЙ ЭКРАН ВОЗВРАЩАЕТ SCREEN ID, ИСПОЛЬЗУЕМ ДЛЯ КНОПКИ НАЗАД
    private final HashMap<String, ICustomModel> models = new HashMap<>(); // МОДЕЛИ-ШАБЛОНЫ

    public TUI_2() {

        //ПАРСЕРЫ (У НАС ВСЕГДА ВАЛИДАТОРЫ ТОЛЬКО В ПАРСЕРАХ, ПАРСИМ -> СРАЗУ ВАЛИДИРУЕМ -> УВЕРЕНЫ ЧТО ОБЪЕКТ ПРАВИЛЬНЫЙ)
        CarParser carParser = new CarParser(new CarValidator());
        StudentParser studentParser = new StudentParser(new StudentValidator());
        UserParser userParser = new UserParser(new UserValidator());

        // ФИЛЛЕРЫ - ОТВЕЧАЮТ ЗА ИСТОЧНИК ДАННЫХ,
        // ПАРСЕРЫ — ЗА ПРЕОБРАЗОВАНИЕ СТРОКИ В ВАЛИДНЫЙ ОБЪЕКТ
        CarListFiller carFiller = new CarListFiller(carParser);
        StudentListFiller studentFiller = new StudentListFiller(studentParser);
        UserListFiller userFiller = new UserListFiller(userParser);

        // СОЗДАЕМ ПУСТЫЕ КЛАССЫ, НАМ ОТ НИХ НУЖЕН ТОЛЬКО ТИП ЧТОБЫ ЧЕРЕЗ getFields() ДОСТАТЬ НУЖНЫЕ ПОЛЯ
        models.put("Car", Car.builder().build());
        models.put("Student", Student.builder().build());
        models.put("User", User.builder().build());

        // РЕГИСТРИРУЕМ ВСЕ ЭКРАНЫ ПРИЛОЖЕНИЯ В СЛОВАРЕ
        //ЭКРАНЫ РАЗМЕЩЕНЫ СВЕРХУ ВНИЗ В ПОРЯДКЕ ИХ ПОЯВЛЕНИЯ У ПОЛЬЗОВАТЕЛЯ
        // ГДЕ ScreenId.CLASS_SELECT - ПЕРВЫЙ ЭКРАН | SORTED_LIST - ПОСЛЕДНИЙ ЭКРАН

        screens.put(ScreenId.CLASS_SELECT, new ClassSelectScreen(List.of("Car", "Student", "User")));
        screens.put(ScreenId.FILL_METHOD, new FillMethodScreen());
        screens.put(ScreenId.SORT_TYPE, new SortTypeScreen());
        screens.put(ScreenId.LIST_LENGTH, new ListLengthScreen());
        screens.put(ScreenId.FIELD_SELECT, new FieldSelectScreen(models));
        screens.put(ScreenId.SORTED_LIST, new SortedListScreen(carFiller, studentFiller, userFiller, carParser, studentParser, userParser));
        screens.put(ScreenId.RECORD_FILE, new RecordToFileScreen());

        // НЕ БЕРЕМ В РАСЧЁТ ПОРЯДКА ЭКРАНОВ (ЗАВИСИТ ОТ ВЫБОРА СПОСОБА ЗАПОЛНЕНИЯ)
        screens.put(ScreenId.MANUAL_INPUT, new ManualInputScreen(carParser, studentParser, userParser));
    }

    // ГЛАВНЫЙ ЦИКЛ ПРИЛОЖЕНИЯ (ЗАПУСКАЕМ В MAIN)
    public void run_cycle() throws InterruptedException {
        ScreenId currentScreen = ScreenId.CLASS_SELECT; // СТАРТОВЫЙ ЭКРАН

        while (true) {
            // ВЫБИРАЕМ НУЖНЫЙ ЭКРАН ИЗ ПУЛА ЭКРАНОВ
            Screen screen = screens.get(currentScreen);

            // ПОКАЗЫВАЕМ МЕНЮ ЭКРАНА И ОБРАБАТЫВАЕМ ВВОД
            ScreenId nextScreen = screen.show(state, scanner);


            // ПОЛЬЗОВАТЕЛЬ ЗАКРЫЛ ПРИЛОЖЕНИЕ
            if (nextScreen == ScreenId.EXIT) {
                return;
            }

            // НАЧАТЬ НОВЫЙ ПРОХОД С ГЛАВНОГО ЭКРАНА
            if (nextScreen == ScreenId.HOME) {
                state.reset();
                screenHistory.clear();
                currentScreen = ScreenId.CLASS_SELECT;
                continue;
            }

            // ВЕРНЁМСЯ К ПРЕДЫДУЩЕМУ ЭКРАНУ
            if (nextScreen == ScreenId.BACK) {
                if (!screenHistory.isEmpty()) {
                    currentScreen = screenHistory.removeLast();
                }

                continue;
            }

            // СОХРАНЯЕМ ТЕКУЩИЙ ЭКРАН В ИСТОРИЮ И ПЕРЕХОДИМ НА НОВЫЙ
            screenHistory.addLast(currentScreen);
            currentScreen = nextScreen;
        }
    }
}
