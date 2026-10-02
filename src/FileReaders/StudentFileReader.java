package FileReaders;

import Models.Car;
import Models.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StudentFileReader {
    List<Student> studentList = new ArrayList<>();

    public List<Student> readStudentFile() {
        Path studentPath = Paths.get("C:\\Users\\MSI\\IdeaProjects\\astonDevProj_team3\\src\\Files\\StudentList");

        List<String> carLineList;

        {
            try {
                carLineList = Files.readAllLines(studentPath);
            } catch (IOException e) {
                throw new RuntimeException("Нет подходящей БД");
            }
        }

        Pattern carPattern = Pattern.compile("^(\\d+);(\\d+.\\d+);(\\d{4})$");
        for (String s : carLineList) {
            Matcher matcher = carPattern.matcher(s);
            matcher.matches();
            studentList.add(Student.builder().groupNumber(matcher.group(1)).averageGrade(Double.parseDouble(matcher.group(2))).recordBookNumber(matcher.group(3)).build());
        }
        return studentList;
    }
}
