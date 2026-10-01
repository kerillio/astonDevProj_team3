package Validation;

public interface Validator<T> {

    void validate(T object);
}