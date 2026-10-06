package Models;

import ListFillers.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AbstractCustomClass{

    //Стратегия заполнения
    ListFiller listFillerStrategy;

    public List<AbstractCustomClass> listFill(String fillMethod) {
        return listFillerStrategy.listFill(fillMethod);
    }

    //должен вернуть список полей
    public ArrayList<String> getFields() {
        return null;
    }

    //кастомные парсеры
//    public abstract ArrayList<AbstractCustomClass> readFromFile();

    //Вернет поле для сортировки
//    public abstract Comparator<AbstractCustomClass> comparing(int i);

    //Comparator

}
