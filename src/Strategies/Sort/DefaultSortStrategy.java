package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import Models.*;

public class DefaultSortStrategy implements SortStrategy {
	public void sort(ArrayList<AbstractCustomClass> arr, ArrayList<Comparator> comparators) {
		if (comparators.size() == 0) {
			arr.sort(null);
		} else {
			arr.sort(
					comparators.stream()
						.reduce(Comparator::thenComparing).orElse((a, b) -> 0)
			);
		}
	}
}
