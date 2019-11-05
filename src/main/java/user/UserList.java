package user;

import java.util.ArrayList;

//@@ AmirAzhar
public class UserList extends ArrayList<User> {
    private boolean loginStatus = false;
    private String currentUser;

    /**
     *
     * @param loader
     */
    public UserList(ArrayList<String> loader) {
        for (String line : loader) {
            this.add(new User(line));
        }
        loginStatus = false;
        currentUser = null;
    }

    public UserList() {
        super();
    }

    /**
     * checkExistence checks if the email supplied exists in the user list or not.
     * @param userList ArrayList of registered usernames
     * @param username username input
     * @return boolean value "found" if email ca be already found in system
     */
    public static boolean checkExistence(UserList userList, String username) {
        boolean found = false;

        for (User i : userList) {
            if (i.username.toUpperCase().trim().equals(username.toUpperCase())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     *
     * @param userList list of registered users
     * @param username input username to remove
     */
    public static void removeUser(UserList userList, String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).username.equals(username)) {
                userList.remove(i);
                break;
            }
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        loginStatus = false;
    }

    public void setCurrentUser(String username) {
        currentUser = username;
    }

    public void login() {
        loginStatus = true;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }
}
