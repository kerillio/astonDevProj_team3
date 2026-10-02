package Models;
import java.util.Objects;
import java.util.regex.Pattern;

public final class User {
    // ПОЛЯ
    private final String name;
    private final String password;
    private final String email;

    // КОНСТРУКТОР
    private User(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    // ПУБЛИЧНОЕ АПИ
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(User user) {

        return new Builder()
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail());
    }

    // ВЫВОД
    @Override
    public String toString() {
        return "Пользователь" +
                "\n\tИмя пользователя: " + name +
                "\n\tПароль: " + password +
                "\n\tПочта: " + email;
    }

    // СРАВНЕНИЕ
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof User user)) {
            return false;
        }

        return name.equals(user.name)
                && password.equals(user.password)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, email);
    }

    //Реализация Builder через статический внутренний класс
    // Примечание: для каждого класса мы реализуем свой билдер - у нас нет общих данных
    public static final class Builder {

        private static final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );

        private String name;
        private String password;
        private String email;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
