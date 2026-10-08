package catalogs;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;

import models.ICustomModel;
import strategies.sort.SortStrategy;
import ui.AppState;

// Композиция для того, чтобы использовать кастомную сортировку над коллекцией
// Вначале заполняется arrList, потом используется сортировка

// ПОДТЯНУЛ ТИПЫ мы теперь уточняем тип
// ICustomModel, потому что при реализации AbstractList<T> (без уточнения T) компилятор считает что в один каталог мы можем положить (CAR, STUDENT, USER)
// А если мы уточняем через T extends ICustomModel то конвертируется что-то на уровне public class Catalog extends AbstractList<Сar> и т.п.
public class Catalog <T extends ICustomModel> extends AbstractList<T> {
	private ArrayList<T> arrList;
	private SortStrategy<T> sorter;

	public Catalog() {
		this.arrList = new ArrayList<T>();
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
	public T  remove(int index) {
		return arrList.remove(index);
	}

	public void sortWithStrategy(Comparator<T> comparator, AppState state) throws InterruptedException {
		sorter = state.getSortStrategy();
		sorter.sort(arrList, comparator, state);
	}

	public void setArr(ArrayList<T> arrList) {
		this.arrList = arrList;
	}

	public void setSorter(SortStrategy<T> sorter) {
		this.sorter = sorter;
	}
}
