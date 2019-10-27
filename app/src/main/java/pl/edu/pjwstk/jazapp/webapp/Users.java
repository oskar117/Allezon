package pl.edu.pjwstk.jazapp.webapp;

import java.util.*;

public class Users {

    private static List<User> users = new ArrayList<User>();

    Users() {
        users.add(new User("kamil", "Daszke", "daszkan@wp.pl", "daszke", "Kamil", "01-02-2003"));
    }

    public void registerUser(String username, String surname, String email, String password, String name, String date) {
        users.add(new User(username, surname, email, password, name, email));
    }

    public boolean userExists(String nickname) {

        for(int x = 0; x < users.size(); x++) {
            if (users.get(x).getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public String getPassword(String nickname) {
        for(int x = 0; x < users.size(); x++) {
            if (users.get(x).getUsername().equals(nickname)) {
                return users.get(x).getPassword();
            }
        }
        return "";
    }
}
