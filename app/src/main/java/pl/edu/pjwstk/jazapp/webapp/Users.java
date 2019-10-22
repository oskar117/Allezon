package pl.edu.pjwstk.jazapp.webapp;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private static Map<String, String> users = new HashMap<String, String>();

    Users() {
        users.put("kamil", "daszke");
    }

    public void registerUser(String nickname, String password) {
        users.put(nickname, password);
    }

    public boolean userExists(String nickname) {

        if(users.get(nickname) == null) {
            return false;
        } else {
            return true;
        }

    }

    public String getPassword(String nickname) {
        return users.get(nickname);
    }

}
