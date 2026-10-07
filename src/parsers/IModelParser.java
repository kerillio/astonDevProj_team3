package parsers;


// ПО СТРОКЕ С КОНСОЛИ БУДЕМ ВОЗВРАЩАТЬ ВАЛИДИРОВАННЫЙ ОБЪЕКТ
public interface IModelParser<T> {
    T parse(String line);
}