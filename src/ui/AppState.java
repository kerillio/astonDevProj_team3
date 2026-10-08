package ui;

import catalogs.Catalog;
import models.ICustomModel;
import strategies.sort.SortStrategy;

import java.util.ArrayList;
import java.util.List;

// СОДЕРЖИТ СОСТОЯНИЯ ВВЕДЕННЫЕ ПОЛЬЗОВАТЕЛЕМ
public final class AppState {
    private String selectedClass;   // Car / Student / User
    private FillMethod fillMethod; // FILE / RANDOM / MANUAL
    private String selectedField; // ВЫБРАННОЕ ПОЛЕ ДЛЯ СОРТИРОВКИ (ТАКЖЕ ВКЛЮЧАЕТ В СЕБЯ ALL)
    private int listLength;     // РАЗМЕР КОЛЛЕКЦИИ УКАЗАННЫЙ ПОЛЬЗОВАТЕЛЕМ
    private SortType sortType; // DEFAULT / EVEN_NUMERIC /find_by_field
    private final List<String> manualInputLines = new ArrayList<>();// СТРОКИ ПОЛЬЗОВАТЕЛЬСКОГО ВВОДА
    private Catalog<? extends ICustomModel> sortedData;
    private SortStrategy<? extends ICustomModel> sortStrategy;
    private String fieldParameterToFind;

    // ПОЛЬЗОВАТЕЛЬСКИЙ ВВОД
    public List<String> getManualInputLines() {
        return List.copyOf(manualInputLines);
    }

    public void addManualInputLine(String line) {
        manualInputLines.add(line);
    }

    public void clearManualInputLines() {
        manualInputLines.clear();
    }

    // ТИП СОРТИРОВКИ
    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    // ВЫБРАННЫЙ КЛАСС
    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    // СПОСОБ ЗАПОЛНЕНИЯ
    public FillMethod getFillMethod() {
        return fillMethod;
    }

    public void setFillMethod(FillMethod fillMethod) {
        this.fillMethod = fillMethod;
    }

    // ПОЛЯ ДЛЯ СОРТИРОВКИ
    public String getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(String selectedField) {
        this.selectedField = selectedField;
    }


    // ДЛИНА СПИСКА
    public int getListLength() {
        return listLength;
    }

    public void setListLength(int listLength) {
        this.listLength = listLength;
    }

    public Catalog<? extends ICustomModel> getSortedData(){
        return sortedData;
    }


    public void setSortedData(Catalog<? extends ICustomModel> sortedData){
        this.sortedData = sortedData;
    }


    // ЧИСТКА СЫЛОК / СОСТОЯНИЯ
    public void reset() {
        selectedClass = null;
        fillMethod = null;
        selectedField = null;
        listLength = 0;
        sortType = null;
        manualInputLines.clear();
    }

    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public String getFieldParameterToFind() {
        return fieldParameterToFind;
    }

    public void setFieldParameterToFind(String fieldParameterToFind) {
        this.fieldParameterToFind = fieldParameterToFind;
    }
}