import java.util.*;

 public class TUI {

    //пока примерные названия. по мере заполнения классов необходимо их изменить List.of("Автобус", "Пользователь", "Студент", "Автомобиль", "Бочка", "Выход")
    private static final ArrayList<String> classNameList = new ArrayList<>();
    //"Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"
    private static final ArrayList<String> fillMethodList = new ArrayList<>(List.of("Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"));
    //"","","","","",
    private static final ArrayList<String> objectFieldsList = new ArrayList<>();

    private static ArrayList<Integer> screenHistory = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static boolean exitFlag = false;
    private static int currentScreen = 0;

    private static String userClassChoice;
    private static String userFillMethodChoice;
    private static String userFieldToSortChoice;
    private static int userListLengthChoice;

    private static HashMap<String, AbstractCustomClass> classPool = new HashMap<>();
    private static ArrayList<AbstractCustomClass> chosenClassList = new ArrayList<>();



    public static void TUI_cycle() {
        screenHistory.addLast(1);
        fillClassPool();
        classNameList.addAll(classPool.keySet());
        classNameList.addLast("Выход");

        while (!exitFlag) {
            try {
                switch (screenHistory.get(currentScreen)) {
                    case 1:
                        firstScreenSwitch();
                        break;
                    case 2:
                        secondScreenSwitch();
                        break;
                    case 3:
                        thirdScreenSwitch(classPool.get(userClassChoice));
                        break;
                    default:
                        continue;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Main switch out of bound");
                currentScreen--;
            }
        }
    }

    private static void fillClassPool() {
        classPool.put("Test", new TestClass());
    }


    private static void firstScreenSwitch() {
        firstScreenPrint();
        try {
            int userInput = sc.nextInt() - 1;

            userClassChoice = classNameList.get(userInput);
            //Выход если выбран выход
            if (userClassChoice.toLowerCase(Locale.ROOT).contentEquals("выход")) Runtime.getRuntime().exit(0);

            currentScreen++;
            screenHistory.addLast(2);

            //логика кнопки "назад"
//            if (screenHistory.size() <= currentScreen){
//                screenHistory.set(currentScreen, 2);
//            } else {

//            }


        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
            return;
        }
    }

    private static void secondScreenSwitch() {
        secondScreenPrint();
        try {
            int userInput = sc.nextInt() - 1;

            //Выход если выбран выход
            if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("выход")) {
                Runtime.getRuntime().exit(0);
            }else if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("назад")) {
                currentScreen--;
                screenHistory.removeLast();
                return;
            } else {
                userFillMethodChoice = fillMethodList.get(userInput);
                currentScreen++;
                screenHistory.addLast(3);
            }
        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
            return;
        }


    }

    private static void thirdScreenSwitch(AbstractCustomClass obj) {
        thirdScreenPrint(obj);
        try {
            int userInput = sc.nextInt() - 1;
            //Выход если выбран выход
            if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("выход")) {
                Runtime.getRuntime().exit(0);
            } else if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("назад")) {
                currentScreen--;
                screenHistory.removeLast();
                return;
            } else {
                userFieldToSortChoice = objectFieldsList.get(userInput);
                currentScreen++;
                screenHistory.addLast(4);
            }
        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
            sc.next();
        }
    }

    private static void fourthScreenSwitch() {
        fourthScreenPrint();
        int userInput = chosenClassList.size();
        try {
            userInput = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введите целое число");
        }

        if (userInput == 0) {

        }


    }


    private static void firstScreenPrint() {
        clearConsole();
        System.out.println("Выберите класс для сортировки (Введите число)");
        for (int i = 0; i < classNameList.size(); i++) {
            System.out.println((i+1) + ". " + classNameList.get(i));
        }


    }

    private static void secondScreenPrint() {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice + "\n");
        System.out.println("Выберите метод заполнения массива класса (Введите число)");
        for (int i = 0; i < fillMethodList.size(); i++) {
            System.out.println((i+1) + ". " + fillMethodList.get(i));
        }
    }

    private static void thirdScreenPrint(AbstractCustomClass obj) {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice);
        System.out.println("Выбран метод заполнения: " + userFillMethodChoice + "\n");
        System.out.println("Выберите поле для сортировки:");
        int i = 1;
        objectFieldsList.clear();
        objectFieldsList.addAll(obj.getFields());
        objectFieldsList.addLast("Назад");
        objectFieldsList.addLast("Выход");

        for (String s : objectFieldsList) {
            System.out.println(i + ". " + s);
            i++;
        }
    }

    private static void fourthScreenPrint () {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice);
        System.out.println("Выбран метод заполнения: " + userFillMethodChoice);
        System.out.println("Выбрано поле для сортировки: " + userFieldToSortChoice + "\n");
        System.out.print("Выберите длину готового списка. По умолчанию сортируется весь диапазон(" + chosenClassList.size() + " строк).\n Чтобы перейти назад введите \"0\", чтобы выйти введите \"-1\"\n");
    }

    private static void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }



}