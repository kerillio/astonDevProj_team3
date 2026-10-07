package comparators;
import models.User;
import java.util.Comparator;

public final class UserComparators {

    // < 0  object1 должен идти раньше object2
    // = 0  равны по этому полю
    // > 0  object1 должен идти после object2

    public static final Comparator<User> BY_NAME = Comparator.comparing(User::getName);

    public static final Comparator<User> BY_PASSWORD = Comparator.comparing(User::getPassword);

    public static final Comparator<User> BY_EMAIL = Comparator.comparing(User::getEmail);

    public static final Comparator<User> BY_ALL = BY_NAME.thenComparing(BY_PASSWORD).thenComparing(BY_EMAIL);

}