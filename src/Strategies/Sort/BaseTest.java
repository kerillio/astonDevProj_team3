package Strategies.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

public class BaseTest {
	public static final class A {
		private int i;
		private String s;

		public static final Comparator<A> CI = Comparator.comparingInt(A::getI);
		public static final Comparator<A> CS = Comparator.comparing(A::getS).reversed();

		public A(int i, String s) {
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
			A a = (A) o;
			return i == a.i && s == a.s;
		}

		@Override
		public int hashCode() {
				return Objects.hash(i, s); 
		}
	}

	public static void main(String[] args) {
		System.out.println("## Тестируем Base");
		if (testSort()) {
			System.out.println("PASSED");
		} else {
			System.out.println("FAILED");
		}
	}

	public static boolean testSort() {
		Base b = new Base();
		ArrayList<A> arr = new ArrayList<A>();
		arr.add(new A(1, "s"));
		arr.add(new A(2, "b"));
		arr.add(new A(2, "d"));
		arr.add(new A(2, "a"));
		arr.add(new A(3, "a"));
		arr.add(new A(1, "a"));
		ArrayList<A> arrR = new ArrayList<A>();
		arrR.add(new A(1, "s"));
		arrR.add(new A(1, "a"));
		arrR.add(new A(2, "d"));
		arrR.add(new A(2, "b"));
		arrR.add(new A(2, "a"));
		arrR.add(new A(3, "a"));
		ArrayList<Comparator<A>> cArr = new ArrayList<Comparator<A>>();
		cArr.add(A.CI);
		cArr.add(A.CS);
		b.sort(arr, cArr);
		return arr.equals(arrR);
	}
}
