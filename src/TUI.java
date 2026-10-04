import java.util.*;

import Comparators.CarComparators;
import Comparators.StudentComparators;
import Comparators.UserComparators;
import ListFillers.*;
import Models.*;

public class TUI {

    //пока примерные названия. по мере заполнения классов необходимо их изменить List.of("Автобус", "Пользователь", "Студент", "Автомобиль", "Бочка", "Выход")
    private static final ArrayList<String> classNameList = new ArrayList<>();
    //"Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"
    private static final ArrayList<String> fillMethodList = new ArrayList<>(List.of("Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"));
    //"","","","","",
    private static final ArrayList<String> objectFieldsList = new ArrayList<>();

    private static LinkedList<Integer> screenHistory = new LinkedList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static boolean exitFlag = false;
//    private static int currentScreen = 0;

    private static String userClassChoice;
    private static String userFillMethodChoice;
    private static String userFieldToSortChoice;
    private static int userListLengthChoice;
    private static int userManualFillLengthChoice;

    private static HashMap<String, AbstractCustomClass> classFieldsMap = new HashMap<>();
    private static HashMap<String, ListFiller> classFillMethodMap = new HashMap<>();
    private static ArrayList<AbstractCustomClass> chosenClassList = new ArrayList<>();
    private static HashMap<String, HashMap<String, Comparator>> classComparatorMap = new HashMap<>();



    public static void TUI_cycle() {
        screenHistory.addLast(1);
        fillClassPool();
        classComparatorMapFill();

//        System.out.println(classComparatorMap.toString());

        classNameList.addAll(classFieldsMap.keySet());
        classNameList.add(classNameList.size(), "Выход");

        while (!exitFlag) {
            try {
                switch (screenHistory.getLast()) {
                    case 1:
                        classChooseSwitch();
                        break;
                    case 2:
                        fillMethodChooseSwitch();
                        break;
                    case 3:
                        fieldChooseSwitch(classFieldsMap.get(userClassChoice));
                        break;
                    case 4 : lengthChooseSwitch();
                        break;
                    case 5 : classListSortedPrint();
                    default:
                        continue;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Main switch out of bound");
            }
        }
    }

    private static void fillClassPool() {
//        classFieldsMap.put("Test", new TestClass());
        classFieldsMap.put("Student", Student.builder().build());
        classFieldsMap.put("Car", Car.builder().build());
        classFieldsMap.put("User", User.builder().build());

        classFillMethodMap.put("Student", new StudentListFiller());
        classFillMethodMap.put("Car", new CarListFiller());
        classFillMethodMap.put("User", new UserListFiller());
    }

    private static void classComparatorMapFill () {
        HashMap<String, Comparator> carInnerMap = new HashMap<>();
        carInnerMap.put("power", CarComparators.BY_POWER);
        carInnerMap.put("model", CarComparators.BY_MODEL);
        carInnerMap.put("year", CarComparators.BY_YEAR);
        classComparatorMap.put("Car", carInnerMap);

        HashMap<String, Comparator> userInnerMap = new HashMap<>();
        userInnerMap.put("email", UserComparators.BY_EMAIL);
        userInnerMap.put("password", UserComparators.BY_PASSWORD);
        userInnerMap.put("name", UserComparators.BY_NAME);
        classComparatorMap.put("User", userInnerMap);

        HashMap<String, Comparator> StudentInnerMap = new HashMap<>();
        StudentInnerMap.put("averageGrade", StudentComparators.BY_AVERAGE_GRADE);
        StudentInnerMap.put("groupNumber", StudentComparators.BY_GROUP_NUMBER);
        StudentInnerMap.put("recordBookNumber", StudentComparators.BY_RECORD_BOOK_NUMBER);
        classComparatorMap.put("Student", StudentInnerMap);
    }


    private static void classChooseSwitch() {
        classChoosePrint();
        try {
            int userInput = sc.nextInt() - 1;

            userClassChoice = classNameList.get(userInput);
            //Выход если выбран выход
            if (userClassChoice.toLowerCase(Locale.ROOT).contentEquals("выход")) Runtime.getRuntime().exit(0);

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

    private static void fillMethodChooseSwitch() {
        fillMethodChoosePrint();
        try {
            int userInput = sc.nextInt() - 1;

            //Выход если выбран выход
            if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("выход")) {
                Runtime.getRuntime().exit(0);
            }else if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("назад")) {

                screenHistory.removeLast();
                return;
            } else {
                userFillMethodChoice = fillMethodList.get(userInput);

                screenHistory.addLast(3);
            }

            if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("заполнить вручную")) {
                while (userManualFillLengthChoice == 0){
                    System.out.print("Введите длину списка, который будете вводить:");
                    try {
                        userManualFillLengthChoice = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\nВведите число, а не что-то еще");
                        sc.next();
                    }
                }
            } else if (fillMethodList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("заполнение рандомно")) {
                while (userManualFillLengthChoice == 0) {
                    System.out.print("Введите величину рандомного заполнения списка:");
                    try {
                        userManualFillLengthChoice = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\nВведите число, а не что-то еще");
                        sc.next();
                    }
                }

            }
            fillClassList();
        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
            return;
        }


    }

    private static void fieldChooseSwitch(AbstractCustomClass obj) {
        fieldChoosePrint(obj);
        try {
            int userInput = sc.nextInt() - 1;
            //Выход если выбран выход
            if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("выход")) {
                Runtime.getRuntime().exit(0);
            } else if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("назад")) {
//                currentScreen--;
                screenHistory.removeLast();
                return;
            } else {
                userFieldToSortChoice = objectFieldsList.get(userInput);
//                currentScreen++;
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

    private static void lengthChooseSwitch() {
        lengthChoosePrint();
        int userInput = 0;
        try {
            userInput = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введите целое число");
        }

        if (userInput == 0) {
            screenHistory.removeLast();
        } else if (userInput == -1) {
            Runtime.getRuntime().exit(0);
        } else {
            screenHistory.addLast(5);
            userListLengthChoice = userInput;
        }

    }


    private static void classChoosePrint() {
        clearConsole();
        System.out.println("Выберите класс для сортировки (Введите число)");
        for (int i = 0; i < classNameList.size(); i++) {
            System.out.println((i+1) + ". " + classNameList.get(i));
        }


    }

    private static void fillMethodChoosePrint() {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice + "\n");
        System.out.println("Выберите метод заполнения массива класса (Введите число)");
        for (int i = 0; i < fillMethodList.size(); i++) {
            System.out.println((i+1) + ". " + fillMethodList.get(i));
        }
    }

    private static void fieldChoosePrint(AbstractCustomClass obj) {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice);
        System.out.println("Выбран метод заполнения: " + userFillMethodChoice + "\n");
        System.out.println("Выберите поле для сортировки:");
        int i = 1;
        objectFieldsList.clear();
        objectFieldsList.addAll(obj.getFields());
        objectFieldsList.add(objectFieldsList.size(), "Назад");
        objectFieldsList.add(objectFieldsList.size(), "Выход");

        for (String s : objectFieldsList) {
            System.out.println(i + ". " + s);
            i++;
        }
    }

    private static void lengthChoosePrint () {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice);
        System.out.println("Выбран метод заполнения: " + userFillMethodChoice);
        System.out.println("Выбрано поле для сортировки: " + userFieldToSortChoice + "\n");
        System.out.print("Выберите длину готового списка. По умолчанию сортируется весь диапазон(" + chosenClassList.size() + " строк).\n Чтобы перейти назад введите \"0\", чтобы выйти введите \"-1\"\n");
    }

    private static void classListSortedPrint() {
        clearConsole();
        chosenClassList.stream()
                        .sorted(classComparatorMap.get(userClassChoice).get(userFieldToSortChoice))
                        .limit(userListLengthChoice)
                        .forEach(System.out::println);
        screenHistory.addLast(1);
        chosenClassList.clear();
        pressEnterPrompt();

    }


    private static void pressEnterPrompt() {
        System.out.print("\nPress enter to continue");
        sc.nextLine();
        sc.nextLine();
    }

    private static void fillClassList() {
        //"Заполнить из готового файла","Заполнить вручную","Заполнение рандомно"
        switch (userFillMethodChoice) {
            case "Заполнить из готового файла" : {
                chosenClassList.addAll(classFillMethodMap.get(userClassChoice).fileFiller());
            }
                break;
            case "Заполнить вручную" : {
                chosenClassList.addAll(classFillMethodMap.get(userClassChoice).manualFiller(userManualFillLengthChoice));
                userManualFillLengthChoice = 0;
            }
                break;
            case "Заполнение рандомно" : {
                chosenClassList.addAll(classFillMethodMap.get(userClassChoice).randomFiller(userManualFillLengthChoice));
                userManualFillLengthChoice = 0;
            }
        }

    }


    private static void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }



}