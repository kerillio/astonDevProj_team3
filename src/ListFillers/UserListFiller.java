package ListFillers;

import Models.User;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserListFiller implements ListFiller {

    List<User> userList = new ArrayList<>();

    @Override
    public List<User> fileFiller() {
        Path userPath = Paths.get("C:\\Users\\MSI\\IdeaProjects\\astonDevProj_team3\\src\\Files\\UserList");

        List<String> userLineList;

        {
            try {
                userLineList = Files.readAllLines(userPath);
            } catch (IOException e) {
                throw new RuntimeException("Нет подходящей БД");
            }
        }

        Pattern userPattern = Pattern.compile("^([^;]+);([^;]+);([^;]+)$");
        for (String s : userLineList) {
            Matcher matcher = userPattern.matcher(s);
            matcher.matches();
            userList.add(User.builder().name(matcher.group(1)).password(matcher.group(2)).email(matcher.group(3)).build());
        }
        return userList;
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
