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
     * doesExist checks if the email supplied exists in the user list or not.
     * @param username username input
     * @return boolean value "found" if email ca be already found in system
     */
    public boolean doesExist(String username) {
        boolean found = false;

        for (User i : this) {
            if (i.username.equals(username)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public boolean addUser(String username) {
        if (this.doesExist(username)) {
            return false;
        } else {
            User user = new User(username);
            this.add(user);
            return true;
        }
    }

    /**
     * Remove user from userlist.
     * @param username input username to remove
     */
    public void removeUser(String username) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).username.equals(username)) {
                this.remove(i);
                break;
            }
        }
    }

    /**
     *
     * @param username to login with
     * @return true if login is successful
     */
    public boolean login(String username) {
        if (this.doesExist(username)) {
            loginStatus = true;
            setCurrentUser(username);
            return true;
        } else return false;
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
