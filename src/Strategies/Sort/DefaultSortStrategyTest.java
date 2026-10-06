package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

import Models.AbstractCustomClass;

public class DefaultSortStrategyTest {
	public static final class TestClass extends AbstractCustomClass {
		private int i;
		private String s;

		public static final Comparator CI = Comparator.comparingInt(TestClass::getI);
		public static final Comparator CS = Comparator.comparing(TestClass::getS).reversed();

		public TestClass(int i, String s) {
			this.i = i;
			this.s = s;
		}

		public int getI() {
			return this.i;
		}

		public String getS() {
			return this.s;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			TestClass a = (TestClass) o;
			return i == a.i && s == a.s;
		}

		@Override
		public int hashCode() {
				return Objects.hash(i, s); 
		}

		@Override
		public ArrayList<String> getFields() {
			return new ArrayList<String>();
		};
	}

	public static void main(String[] args) {
		System.out.println("## Тестируем DefaultSort");
		if (testSort()) {
			System.out.println("PASSED");
		} else {
			System.out.println("FAILED");
		}
	}

	public static boolean testSort() {
		DefaultSortStrategy strategy = new DefaultSortStrategy();
		ArrayList<AbstractCustomClass> arr = new ArrayList<AbstractCustomClass>();
		arr.add(new TestClass(1, "s"));
		arr.add(new TestClass(2, "b"));
		arr.add(new TestClass(2, "d"));
		arr.add(new TestClass(2, "a"));
		arr.add(new TestClass(3, "a"));
		arr.add(new TestClass(1, "a"));
		ArrayList<AbstractCustomClass> arrResult = new ArrayList<AbstractCustomClass>();
		arrResult.add(new TestClass(1, "s"));
		arrResult.add(new TestClass(1, "a"));
		arrResult.add(new TestClass(2, "d"));
		arrResult.add(new TestClass(2, "b"));
		arrResult.add(new TestClass(2, "a"));
		arrResult.add(new TestClass(3, "a"));
		ArrayList<Comparator> comparatorsArr = new ArrayList<Comparator>();
		comparatorsArr.add(TestClass.CI);
		comparatorsArr.add(TestClass.CS);
		strategy.sort(arr, comparatorsArr);
		return arr.equals(arrResult);
	}
}
