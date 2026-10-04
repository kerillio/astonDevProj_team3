package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;

public interface SortStrategy {
	public abstract <T> void sort(ArrayList<T> arr, ArrayList<Comparator<T>> comparators);
}
