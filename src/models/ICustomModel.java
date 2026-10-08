package models;

import java.util.ArrayList;

// ИСПОЛЬЗУЕМ ДЛЯ БИНДИНГА С UI
public interface ICustomModel{
    public ArrayList<String> getFields(); // ПОЛУЧИМ ПОЛЯ ПО КОТОРЫМ ЗАХОТИМ СОРТИРОВАТЬ

    public String getFieldValueByFieldName (String fieldName); //получаем значение поля по входящему имени поля. костыль, имитирующий работу метода getClass().getField().toString();
}
