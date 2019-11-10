package user;

import java.util.ArrayList;

//@@ AmirAzhar
public class UserList extends ArrayList<User> {
    private boolean loginStatus = false;
    private String currentUser;

    /**
     * loading userlist from file.
     * @param loader load line from file
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
     * @param username username input
     * @return boolean value "found" if email ca be already found in system
     */
    public boolean checkExistence(String username) {
        boolean found = false;

        for (User i : this) {
            if (i.username.equals(username)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Remove user from userlist.
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

    public void login(String username) {
        loginStatus = true;
        setCurrentUser(username);
    }

    public void logout() {
        loginStatus = false;
        setCurrentUser(null);
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String username) {
        currentUser = username;
    }
}
