package ListFillers;

import Models.Car;

import java.util.ArrayList;
import java.util.List;

public interface ListFiller<T> {
    List<T> fileFiller(int size);

    List<T>  manualFiller(int size);

    List<T>  randomFiller(int size);
}
