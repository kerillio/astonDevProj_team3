package Strategies.Sort;
import java.util.ArrayList;
import java.util.Comparator;
import Models.ICustomModel;


public interface SortStrategy<T extends ICustomModel> {

	void sort(ArrayList<T> arr, ArrayList<Comparator<T>> comparators);
}
