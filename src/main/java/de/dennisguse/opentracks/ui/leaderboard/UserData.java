package de.dennisguse.opentracks.ui.leaderboard;

import java.util.ArrayList;
import java.util.List;

public class UserData {

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Steve H", "Steamboat Springs", 1, "614.1K", "25.0K"));
        users.add(new User("Joshua Segal", "Bennington, NH", 2, "580.2K", "20.0K"));
        users.add(new User("Mike A", "Sunapee, NH", 3, "506.7K", "22.5K"));
        // Add more users as needed
        return users;
    }
}