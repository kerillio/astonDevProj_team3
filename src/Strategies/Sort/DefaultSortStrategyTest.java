package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

public class DefaultSortStrategyTest {
	public static final class TestClass {
		private int i;
		private String s;

		public static final Comparator<TestClass> CI = Comparator.comparingInt(TestClass::getI);
		public static final Comparator<TestClass> CS = Comparator.comparing(TestClass::getS).reversed();

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
		ArrayList<TestClass> arr = new ArrayList<TestClass>();
		arr.add(new TestClass(1, "s"));
		arr.add(new TestClass(2, "b"));
		arr.add(new TestClass(2, "d"));
		arr.add(new TestClass(2, "a"));
		arr.add(new TestClass(3, "a"));
		arr.add(new TestClass(1, "a"));
		ArrayList<TestClass> arrResult = new ArrayList<TestClass>();
		arrResult.add(new TestClass(1, "s"));
		arrResult.add(new TestClass(1, "a"));
		arrResult.add(new TestClass(2, "d"));
		arrResult.add(new TestClass(2, "b"));
		arrResult.add(new TestClass(2, "a"));
		arrResult.add(new TestClass(3, "a"));
		ArrayList<Comparator<TestClass>> comparatorsArr = new ArrayList<Comparator<TestClass>>();
		comparatorsArr.add(TestClass.CI);
		comparatorsArr.add(TestClass.CS);
		strategy.sort(arr, comparatorsArr);
		return arr.equals(arrResult);
	}
}
