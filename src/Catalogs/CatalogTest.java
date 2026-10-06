package Catalogs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import Models.Car;
import Comparators.CarComparators;

public class CatalogTest {
	public static void main(String[] args) {
		System.out.println("## Тестируем Catalog");
		if (testCatalogSort()) {
			System.out.println("PASSED");
		} else {
			System.out.println("FAILED");
		}
	}

	public static boolean testCatalogSort() {
		Catalog<Car> catalog = new Catalog<Car>();
		Car.Builder builder = Car.builder();
		builder.power(1000);
		builder.model("Model1");
		builder.year(1999);
		catalog.add(builder.build());
		builder.power(1000);
		builder.model("Model2");
		builder.year(1998);
		catalog.add(builder.build());
		builder.power(2000);
		builder.model("Model3");
		builder.year(2012);
		catalog.add(builder.build());
		builder.power(2000);
		builder.model("Model");
		builder.year(2011);
		catalog.add(builder.build());
		Catalog<Car> catalogE = new Catalog<Car>();
		builder.power(1000);
		builder.model("Model2");
		builder.year(1998);
		catalogE.add(builder.build());
		builder.power(1000);
		builder.model("Model1");
		builder.year(1999);
		catalogE.add(builder.build());
		builder.power(2000);
		builder.model("Model");
		builder.year(2011);
		catalogE.add(builder.build());
		builder.power(2000);
		builder.model("Model3");
		builder.year(2012);
		catalogE.add(builder.build());
		ArrayList<Comparator<Car>> cArr = new ArrayList<Comparator<Car>>();
		cArr.add(CarComparators.BY_POWER);
		cArr.add(CarComparators.BY_YEAR);
		cArr.add(CarComparators.BY_MODEL);
		catalog.sort(cArr);
		return catalog.equals(catalogE);
	}
}
