package Catalogs;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;

import Strategies.Sort.SortStrategy;

// Композиция для того, чтобы использовать кастомную сортировку над коллекцией
// Вначале заполняется arrList, потом используется сортировка
public class Catalog<T> extends AbstractList<T> {
	private final ArrayList<T> arrList;
	private Strategies.Sort.SortStrategy sorter;

	public Catalog() {
		this.arrList = new ArrayList<T>();
		this.sorter = new Strategies.Sort.DefaultSortStrategy();
	}

	public Catalog(ArrayList<T> arr, Strategies.Sort.SortStrategy sorter) {
		this.arrList = arr;
		this.sorter = sorter;
	}

	@Override
	public T get(int index) {
		return arrList.get(index);
	}

	@Override
	public int size() {
		return arrList.size();
	}

	@Override
	public boolean add(T el) {
		return arrList.add(el);
	}

	@Override
	public T set(int index, T el) {
		return arrList.set(index, el);
	}

	@Override
	public T remove(int index) {
		return arrList.remove(index);
	}

	public void sort(ArrayList<Comparator<T>> comparators) {
		sorter.sort(arrList, comparators);
	}
}
