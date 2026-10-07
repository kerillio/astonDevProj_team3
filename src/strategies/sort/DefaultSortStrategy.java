package strategies.sort;

import java.util.ArrayList;
import java.util.Comparator;

import models.*;

public class DefaultSortStrategy<T extends ICustomModel> implements SortStrategy<T> {
	// DEFAULT SORT STRATEGY = СОРТИРОВКА ПУЗЫРЬКОМ
	// Я НЕ СТАЛ НАЗВАНИЕ КЛАССА МЕНЯТЬ, ПОТОМ МОЖНО ЗАРЕНЕЙМИТЬ
	@Override
	public void sort(ArrayList<T> arr, Comparator<T> comparator) {
		for (int i = 0; i < arr.size() - 1; i++) { // <- СОРТИРУЕМ ПУЗЫРЬКОМ
			boolean swapped = false;

			for (int j = 0; j < arr.size() - 1 - i; j++) {
				if (comparator.compare(arr.get(j), arr.get(j + 1)) > 0) { // УСЛОВИЯ СОРТИРОВКИ И ЗНАЧЕНИЯ СМОТРЕТЬ В /COMPORATORS
					T temp = arr.get(j);
					arr.set(j, arr.get(j + 1));
					arr.set(j + 1, temp);

					swapped = true;
				}
			}

			if (!swapped) { // ВСЁ ОТСОРТИРОВАЛИ
				break;
			}
		}
	}
}
