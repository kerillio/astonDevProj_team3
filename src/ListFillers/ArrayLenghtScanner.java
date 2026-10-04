package ListFillers;

import java.util.Scanner;

public class ArrayLenghtScanner {

    private static final Scanner SCANNER = new Scanner(System.in);

    public int scanSize() {


        System.out.print("Введите ограничение на длину списка: ");

        String sizeInput = SCANNER.nextLine();
        int size = Integer.parseInt(sizeInput);


        if (size < 0) {
            System.out.println("Некорректный размер списка: " + size);
        }

        return size;
    }

}
