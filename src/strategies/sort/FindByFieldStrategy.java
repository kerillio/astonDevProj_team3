package strategies.sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import models.*;
import ui.AppState;

public class FindByFieldStrategy<T extends ICustomModel> implements SortStrategy<T> {
    @Override
    public void sort(ArrayList<T> arr, Comparator<T> comparator, AppState state) throws InterruptedException {


        //РАЗБИВАЕМ СПИСОК НА ДВЕ ЧАСТИ
        ArrayList<T> firstArr = new ArrayList<>();
        ArrayList<T> secondArr = new ArrayList<>();

        firstArr.addAll(arr.subList(0, arr.size()/2));
        secondArr.addAll(arr.subList(arr.size()/2+1, arr.size()));

        //ЧИСТИМ ИСХОДНЫЙ СПИСОК
        arr.clear();

        //КАЖДЫЙ ПОТОК РАБОТАЕТ С СВОЕЙ КОПИЕЙ ЧАСТИ ИСХОДНОГО МАССИВА
        Runnable firstHalf = () -> {

            firstArr.stream()
                    .filter(item -> item.getFieldValueByFieldName(state.getSelectedField())
                    .equalsIgnoreCase(state.getFieldParameterToFind()))
                    .forEach(item -> arr.add(item));
        };

        Runnable secondHalf = () -> {
            secondArr.stream()
                    .filter(item -> item.getFieldValueByFieldName(state.getSelectedField())
                    .equalsIgnoreCase(state.getFieldParameterToFind()))
                    .forEach(item -> arr.add(item));

        };


        //ВЫЗОВ ПОТОКОВ
        Thread t1 = new Thread(firstHalf);
        Thread t2 =  new Thread(secondHalf);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }



}
