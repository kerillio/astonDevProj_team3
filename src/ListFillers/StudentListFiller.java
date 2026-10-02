package ListFillers;

import Models.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StudentListFiller implements ListFiller{
    List<Student> studentList = new ArrayList<>();

    @Override
    public List<Student> fileFiller() {
        Path studentPath = Paths.get("src/Files/StudentList");

        List<String> studentLineList;

        {
            try {
                studentLineList = Files.readAllLines(studentPath);
            } catch (IOException e) {
                throw new RuntimeException("Нет подходящей БД");
            }
        }

        Pattern studentPattern = Pattern.compile("^(\\d+);(\\d+.\\d+);(\\d{4})$");
        for (String s : studentLineList) {
            Matcher matcher = studentPattern.matcher(s);
            matcher.matches();
            studentList.add(Student.builder().groupNumber(matcher.group(1)).averageGrade(Double.parseDouble(matcher.group(2))).recordBookNumber(matcher.group(3)).build());
        }
        return studentList;
    }



    @Override
    public List manualFiller() {
        return null;
    }

    @Override
    public List randomfiller() {
        return null;
    }
}
