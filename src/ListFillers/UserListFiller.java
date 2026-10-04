package ListFillers;

import Models.User;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserListFiller implements ListFiller<User> {
    private static final Pattern USER_PATTERN = Pattern.compile("^([^;]+);([^;]+);([^;]+)$");
    private static final String USER_FILE_PATH  = "src/Files/UserList";

    private User parseUser(String line) {
        Matcher matcher = USER_PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректная строка: " + line);
        }

        return User.builder()
                .name(matcher.group(1))
                .password(matcher.group(2))
                .email(matcher.group(3))
                .build();
    }


    private List<String> readFileLines(){
        Path userPath = Paths.get(USER_FILE_PATH);

        List<String> lines;

        try {
            lines = Files.readAllLines(userPath);
        } catch (IOException e) {
            throw new RuntimeException("Нет подходящей БД", e);
        }

        return lines;
    }

    @Override
    public List<User> manualFiller(int size) {
        return null;
    }

    @Override
    public List<User> randomFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        Collections.shuffle(lines);

        List<User> users = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            users.add(parseUser(lines.get(i)));
        }

        return users;
    }

    @Override
    public List<User> fileFiller(int size) {
        List<String> lines = readFileLines();

        if (size <= 0 || size > lines.size()) {
            throw new IllegalArgumentException("Некорректный размер списка");
        }

        List<User> users = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            users.add(parseUser(lines.get(i)));
        }

        return users;
    }
}
