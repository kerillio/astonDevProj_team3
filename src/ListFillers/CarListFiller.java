package ListFillers;

import Models.Car;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarListFiller implements ListFiller<Car>{
    List<Car> carList = new ArrayList<>();


    @Override
    public List<Car> fileFiller(int size) {
        Path carPath = Paths.get("src/Files/CarList");

        List<String> carLineList;

        {
            try {
                carLineList = Files.readAllLines(carPath);
            } catch (IOException e) {
                throw new RuntimeException("Нет подходящей БД");
            }
        }

        Pattern carPattern = Pattern.compile("^(\\d{2,4});([^;]+);(\\d{4})$");
        for (String s : carLineList) {
            Matcher matcher = carPattern.matcher(s);
            matcher.matches();
            carList.add(Car.builder().model(matcher.group(2)).power(Integer.parseInt(matcher.group(1))).year(Integer.parseInt(matcher.group(3))).build());
        }
        return carList;
    }




    @Override
    public List<Car> manualFiller(int size) {
        return null;
    }

    @Override
    public List<Car> randomFiller(int size) {
        return null;
    }
}
