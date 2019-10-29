package user;

import ui.Ui;

import java.io.*;
import java.util.Scanner;

public class Login {

    private static String currentUser;
    /**
     * verfiyLogin verifies if the email and password input by user is a registered account.
     * @param email for login
     * @param password for login
     * @param filePath of members login and user info
     * @throws FileNotFoundException when file to refer to is not found
     */
    public static boolean verifyLogin(String email, String password, String filePath) throws IOException {
        BufferedReader reader;
        boolean found = false;
        String tempEmail;
        String tempPassword;
        reader = new BufferedReader(new FileReader(filePath));

        String line = reader.readLine();

        while (line != null) {
            String[] parts = line.split("[|]");
            tempEmail = parts[0];
            tempPassword = parts[1];
            if (email.trim().equals(tempEmail.trim()) && password.trim().equals(tempPassword.trim())) {
                found = true;
                setCurrentUser(tempEmail);
            }
            line = reader.readLine();
        }
        reader.close();
        return found;
    }


    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String email) {
        currentUser = email;
    }

    /**
     * checkExistance checks if the email supplied exists in the user list or not.
     * @param email email input
     * @param filePath the file we are referring to
     * @return boolean value "found" if email ca be already found in system
     * @throws IOException when line read error occurs
     */
    public static boolean checkExistence(String email,String filePath) throws IOException {
        BufferedReader reader;
        boolean found = false;
        reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null){
            String[] parts = line.split("[|]");
            if(parts[0].trim().equals(email.trim())) {
                found = true;
            }
            line = reader.readLine();
        }
        reader.close();
        return found;
    }

    public static boolean checkUsername(String username, String filePath) throws IOException {
        BufferedReader reader;
        boolean exists = true;
        reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null){
            String[] parts = line.split(" \\| ");
            if(!parts[0].trim().equals(username.trim())) {
                exists = false;
            }
            line = reader.readLine();
        }
        reader.close();
        return exists;
    }

    public static User getUser(String username, String filePath) throws IOException {
        BufferedReader reader;
        boolean exists = true;
        reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while (line != null){
            String[] parts = line.split(" \\| ");
            if (parts[0].trim().equals(username.trim())) {
                User validUser = new User(parts[0], parts[1], parts[2]);
                setCurrentUser(parts[0]);
                return validUser;
            }
            line = reader.readLine();
        }
        reader.close();
        return new Guest("guest");
    }
}
