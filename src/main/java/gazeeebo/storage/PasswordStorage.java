package gazeeebo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordStorage {
    private String relativePathPasswordResource
            = "Password.txt";

    /**
     * Write the encoded password into the Password.txt file.
     *
     * @param fileContent string to put into the txt file.
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToPasswordFile(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathPasswordResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Read from the Password.txt file, decode the passwords and put it into an array.
     *
     * @return the arrays of password
     * @throws FileNotFoundException catch the error if the read file fails.
     */
    public ArrayList<StringBuilder> readFromPasswordFile()
            throws FileNotFoundException {
        ArrayList<StringBuilder> passwordList = new ArrayList<>();

        File f = new File(relativePathPasswordResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String decodedPassword = sc.nextLine();
            char[] decryption = decodedPassword.toCharArray();
            StringBuilder realPassword = new StringBuilder();
            for (int i = decodedPassword.length() - 1; i >= 0; i--) {
                realPassword.append(decryption[i]);
            }
            passwordList.add(realPassword);
        }
        return passwordList;
    }
}
