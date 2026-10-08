package strategies.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.ToIntFunction;

import models.*;
import ui.AppState;

public class EvenNumericSortStrategy<T extends ICustomModel> implements SortStrategy<T> {
	// EVEN SORT STRATEGY = СОРТИРОВКА ТОЛЬКО ЧЁТНЫХ ЗНАЧЕНИЙ
	private final ToIntFunction<T> intGetter;

	public EvenNumericSortStrategy(ToIntFunction<T> intGetter) {
		this.intGetter = intGetter;
	}

	@Override
	public void sort(ArrayList<T> arr, Comparator<T> comparator, AppState state) throws InterruptedException {
		// Создаем список, который будем сортировать
		ArrayList<T> movable = new ArrayList<>();

		for (T item : arr) {
			// Отбираем только четные элементы списка и добавляем к списку, который будем сортировать
			int value = intGetter.applyAsInt(item);
			if (value % 2 == 0) movable.add(item);
		}

		// Сортируем список
		SortStrategy<T> sorter = new DefaultSortStrategy<>();
		sorter.sort(movable, comparator, state);

		// Заполняем изначальный список отсортированными значениями
		int ind = 0;
		for (int i = 0; i < arr.size(); i++) {
			int value = intGetter.applyAsInt(arr.get(i));
			if (value % 2 == 0) {
				arr.set(i, movable.get(ind));
				ind++;
			}
		}
	}
}
