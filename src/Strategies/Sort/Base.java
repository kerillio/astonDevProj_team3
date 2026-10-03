package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Base extends Abstract {
	public <T> void sort(ArrayList<T> arr, ArrayList<Comparator<T>> comparators) {
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
