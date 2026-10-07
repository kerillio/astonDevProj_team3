package comparators;
import models.Car;
import java.util.Comparator;

public final class CarComparators{

    // < 0  object1 должен идти раньше object2
    // = 0  равны по этому полю
    // > 0  object1 должен идти после object2

    public static final Comparator<Car> BY_POWER = Comparator.comparingInt(Car::getPower);

    public static final Comparator<Car> BY_MODEL = Comparator.comparing(Car::getModel);

    public static final Comparator<Car> BY_YEAR = Comparator.comparingInt(Car::getYear);

    public static final Comparator<Car> BY_ALL = BY_POWER.thenComparing(BY_MODEL).thenComparing(BY_YEAR);
}