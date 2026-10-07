package validation;
import models.User;
import java.util.regex.Pattern;

public final class UserValidator implements Validator<User> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public void validate(User user) {
        if (user == null)
            throw new IllegalArgumentException("Пользователь не указан");

        validateName(user.getName());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }

    // ВАЛИДАЦИЯ ПО ИМЕНИ
    private void validateName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Имя не указано");

        name = name.trim();

        if (name.length() > 100)
            throw new IllegalArgumentException("Слишком длинное имя");
    }

    // ВАЛИДАЦИЯ ПО ПАРОЛЮ
    private void validatePassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Не указан пароль");

        if (password.length() < 6)
            throw new IllegalArgumentException("Пароль должен быть длиннее 6 символов");

        if (password.length() > 100)
            throw new IllegalArgumentException("Пароль должен содержать менее 100 символов");
    }

    // ВАЛИДАЦИЯ ПО ПОЧТЕ
    private void validateEmail(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Не указана почта");

        email = email.trim();

        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("неправильно указана почта");

        if (email.length() > 200)
            throw new IllegalArgumentException("Почта не может содержать более 200 символов");
    }
}