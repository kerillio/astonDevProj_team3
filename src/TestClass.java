import Models.AbstractCustomClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.stream;

public class TestClass extends AbstractCustomClass {
    private int age;
    private String name;
    private String password;


    public ArrayList<String> getFields() {
        return new ArrayList<String>(List.of("age", "name", "password"));
    }


    public ArrayList<AbstractCustomClass> readFromFile() {
        return null;
    }

    public void manualFill(int age, String name, String password) {
        this.age = age;
        this.name = name;
        this.password = password;
    }


}
