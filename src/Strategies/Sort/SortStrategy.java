package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import Models.*;

public interface SortStrategy {
	public abstract void sort(ArrayList<AbstractCustomClass> arr, ArrayList<Comparator> comparators);
}
