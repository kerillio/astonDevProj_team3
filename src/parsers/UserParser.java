package parsers;

import models.User;
import validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserParser implements IModelParser<User> {

    private static final Pattern USER_PATTERN = Pattern.compile("^([^;]+);([^;]+);([^;]+)$");

    private final Validator<User> validator;

    public UserParser(Validator<User> validator) {
        this.validator = validator;
    }

    @Override
    public User parse(String line) {
        Matcher matcher = USER_PATTERN.matcher(line);

        // ЕСЛИ У НАС ПО РЕГУЛЯРКЕ НЕ ПРОХОДЯТ ДАННЫЕ (ДАННЫЕ ПРИХОДЯТ ТАКЖЕ С ФАЙЛА - МОГУТ БИТЬ БИТЫЕ)
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректные данные пользователя: " + line);
        }

        // СОБИРАЕМ ОБЪЕКТ
        User user = User.builder()
                .name(matcher.group(1))
                .password(matcher.group(2))
                .email(matcher.group(3))
                .build();

        // ПРОВЕРИМ НА КОРРЕКТНОСТЬ ДОПУСТИМЫХ ЗНАЧЕНИЙ
        validator.validate(user);

        return user;
    }
}