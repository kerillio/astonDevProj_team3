import java.nio.file.Paths;
import java.util.*;


/*import Comparators.CarComparators;
import Comparators.StudentComparators;
import Comparators.UserComparators;
import ListFillers.*;
import Models.*;
import Strategies.*;
import Catalogs.*;*/

public class TUI {

    /*//пока примерные названия. по мере заполнения классов необходимо их изменить List.of("Автобус", "Пользователь", "Студент", "Автомобиль", "Бочка", "Выход")
    private static final ArrayList<String> classNameList = new ArrayList<>();
    //"Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"
    private static final ArrayList<String> fillMethodList = new ArrayList<>(List.of("Заполнить из готового файла","Заполнить вручную","Заполнение рандомно","Назад","Выход"));
    //"","","","","",
    private static final ArrayList<String> objectFieldsList = new ArrayList<>();
    private static final ArrayList<String> chooseRecordList = new ArrayList<>(List.of("Записать в файл","Назад","Выход"));

    private static LinkedList<Integer> screenHistory = new LinkedList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static boolean exitFlag = false;

    private static String userClassChoice;
    private static String userFieldToSortChoice;

    private static HashMap<String, AbstractCustomClass> classPoolMap = new HashMap<>();
    private static Catalog chosenClassList = new Catalog(new ArrayList<AbstractCustomClass>(), new Strategies.Sort.DefaultSortStrategy());
    private static HashMap<String, HashMap<String, Comparator>> classComparatorMap = new HashMap<>();



    public static void TUI_cycle() {
        screenHistory.addLast(1);
        fillClassPool();
        classComparatorMapFill();
        classNameList.addAll(classPoolMap.keySet());
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
                        fieldChooseSwitch(classPoolMap.get(userClassChoice));
                        break;
                    case 4 : classListSortedPrint();
                        break;
                    default:
                        continue;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Main switch out of bound");
            }
        }
    }

    private static void fillClassPool() {
        classPoolMap.put("Student", Student.builder().build());
        classPoolMap.put("Car", Car.builder().build());
        classPoolMap.put("User", User.builder().build());
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


        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
            sc.next();
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
                chosenClassList.addAll(classPoolMap.get(userClassChoice).listFill(fillMethodList.get(userInput)));
                screenHistory.addLast(3);
            }

        } catch (InputMismatchException e) {
            System.out.println("Введите число, а не что-то еще");
            sc.next();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите значение внутри диапазона выбора");
        }


    }

    private static void recordChooseSwitch() {
        recordChoosePrint();

    }

    private static void fieldChooseSwitch(AbstractCustomClass obj) {
        fieldChoosePrint(obj);
        try {
            int userInput = sc.nextInt() - 1;
            //Выход если выбран выход
            if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("выход")) {
                Runtime.getRuntime().exit(0);
            } else if (objectFieldsList.get(userInput).toLowerCase(Locale.ROOT).contentEquals("назад")) {
                screenHistory.removeLast();
            } else {
                userFieldToSortChoice = objectFieldsList.get(userInput);
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
//        System.out.println("Выбран метод заполнения: " + userFillMethodChoice);
        System.out.println("\nВыберите поле для сортировки:");
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

    private static void recordChoosePrint() {
        clearConsole();
        System.out.println("Выбран класс: " + userClassChoice + "\n");
        System.out.println("Класс отсортирован по полю: " + userFieldToSortChoice);
        System.out.println("\nЗаписать отсортированный массив в файл?");
        for (int i = 0; i < chooseRecordList.size(); i++) {
            System.out.println((i+1) + ". " + chooseRecordList.get(i));
        }
    }


    private static void classListSortedPrint() {
        clearConsole();
        chosenClassList.sort(new ArrayList<>(Arrays.asList(classComparatorMap.get(userClassChoice).get(userFieldToSortChoice))));
        chosenClassList.forEach(System.out::println);
        screenHistory.addLast(1);
        chosenClassList.clear();
        pressEnterPrompt();

    }


    private static void pressEnterPrompt() {
        System.out.print("\nPress enter to continue");
        sc.nextLine();
        sc.nextLine();
    }


    private static void clearConsole() {
        System.out.println("\n\n\n\n\n");
    }
*/


}
