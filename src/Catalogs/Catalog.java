package Catalogs;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;

import Strategies.Sort.SortStrategy;
import Models.AbstractCustomClass;

// Композиция для того, чтобы использовать кастомную сортировку над коллекцией
// Вначале заполняется arrList, потом используется сортировка
public class Catalog extends AbstractList<AbstractCustomClass> {
	private ArrayList<AbstractCustomClass> arrList;
	private Strategies.Sort.SortStrategy sorter;

	public Catalog() {
		this.arrList = new ArrayList<AbstractCustomClass>();
		this.sorter = new Strategies.Sort.DefaultSortStrategy();
	}

	public Catalog(ArrayList<AbstractCustomClass> arr, Strategies.Sort.SortStrategy sorter) {
		this.arrList = arr;
		this.sorter = sorter;
	}

	@Override
	public AbstractCustomClass get(int index) {
		return arrList.get(index);
	}

	@Override
	public int size() {
		return arrList.size();
	}

	@Override
	public boolean add(AbstractCustomClass el) {
		return arrList.add(el);
	}

	@Override
	public AbstractCustomClass set(int index, AbstractCustomClass el) {
		return arrList.set(index, el);
	}

	@Override
	public AbstractCustomClass remove(int index) {
		return arrList.remove(index);
	}

	public void sort(ArrayList<Comparator> comparators) {
		sorter.sort(arrList, comparators);
	}

	public void setArr(ArrayList<AbstractCustomClass> arrList) {
		this.arrList = arrList;
	}

	public void setSorter(Strategies.Sort.SortStrategy sorter) {
		this.sorter = sorter;
	}
}
