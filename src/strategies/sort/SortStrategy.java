package strategies.sort;
import java.util.ArrayList;
import java.util.Comparator;
import models.ICustomModel;
import ui.AppState;

// СТРАТЕГИЯ, МЫ В КАТАЛОГЕ(Catalog) БЛАГОДАРЯ ЭТОМ МОЖЕМ ПОДМЕНЯТЬ РЕАЛИЗАЦИИ ЧЕРЕЗ setSorter()
public interface SortStrategy<T extends ICustomModel> {
	void sort(ArrayList<T> arr, Comparator<T> comparator, AppState state) throws InterruptedException;
}
