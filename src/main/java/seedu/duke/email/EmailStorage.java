package seedu.duke.email;

import org.json.JSONException;
import seedu.duke.Duke;
import seedu.duke.common.network.Http;
import seedu.duke.email.entity.Email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.email.EmailContentParser.allKeywordInEmail;

/**
 * Handles loading and saving of emails from local storage.
 */
public class EmailStorage {

    /**
     * Get the pathname of the data/email.txt.
     *
     * @return pathname of the email.txt file.
     */
    public static String getEmailIndexDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "email.txt";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "email.txt";
        } else {
            dir = "." + File.separator + "data" + File.separator + "email.txt";
        }
        return dir;
    }

    /**
     * Get the pathname of the data/emails/ folder, in which all the html files are saved.
     *
     * @return pathname of the data/emails/ folder.
     */
    public static String getFolderDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "emails" + File.separator;
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "emails" + File.separator;
        } else {
            dir = "." + File.separator + "data" + File.separator + "emails" + File.separator;
        }
        return dir;
    }

    private static String getUserInfoDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "user.txt";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "user.txt";
        } else {
            dir = "." + File.separator + "data" + File.separator + "user.txt";
        }
        return dir;
    }

    /**
     * To implement with code to fetch emails from online server to local storage. May need to sync the
     * current email list with local storage after that by calling syncEmailListWithHtml().
     */
    public static void syncWithServer() {
        EmailList serverEmailList = Http.fetchEmail(60);
        for (Email serverEmail : serverEmailList) {
            boolean exist = false;
            for (Email localEmail : Duke.getModel().getEmailList()) {
                if (localEmail.getSubject().equals(serverEmail.getSubject())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                allKeywordInEmail(serverEmail);
                Duke.getModel().getEmailList().add(serverEmail);
            }
        }
        Duke.getModel().updateGuiEmailList();
        saveEmails(Duke.getModel().getEmailList());
    }

    /**
     * To save the information for the emailList including subject and tags(not implemented yet) for each
     * email before exiting the app.
     *
     * @param emailList the emailList to be saved before exiting the app.
     */
    public static void saveEmails(EmailList emailList) {
        try {
            prepareFolder();
            String folderDir = getFolderDir();
            String indexDir = getEmailIndexDir();
            File indexFile = new File(indexDir);
            indexFile.createNewFile();
            FileOutputStream indexOut = new FileOutputStream(indexFile, false);
            String content = "";
            for (Email email : emailList) {
                content += email.toIndexJson().toString() + "\n";
            }
            indexOut.write(content.getBytes());
            indexOut.close();
            for (Email email : emailList) {
                if (email.getBody() != null) {
                    File emailSource = new File(folderDir + email.toFilename());
                    emailSource.createNewFile();
                    FileOutputStream emailOut = new FileOutputStream(emailSource, false);
                    emailOut.write(email.getRawJson().getBytes());
                    emailOut.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Duke.getUI().showError("Write to output file IO exception!");
        } catch (JSONException e) {
            Duke.getUI().showError("Email index formatting exception!");
        }
    }

    private static void prepareFolder() throws IOException {
        File emailFolder = new File(getFolderDir());
        if (!emailFolder.exists()) {
            emailFolder.mkdir();
        }
        File indexFile = new File(getEmailIndexDir());
        indexFile.createNewFile();
    }

    /**
     * Get emailList according to previously saved information about emails from the data/email.txt at the
     * start of the app.
     *
     * @return EmailList created from data/email.txt.
     */
    public static EmailList readEmailFromFile() {
        EmailList emailList = new EmailList();
        try {
            prepareFolder();
            String indexDir = getEmailIndexDir();
            File indexFile = new File(indexDir);
            FileInputStream indexIn = new FileInputStream(indexFile);
            Scanner scanner = new Scanner(indexIn);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                Email indexEmail = EmailFormatParser.parseIndexJson(input);
                String filename = indexEmail.toFilename();

                String fileDir = getFolderDir() + filename;
                File emailFile = new File(fileDir);
                FileInputStream emailIn = new FileInputStream(emailFile);
                Scanner emailScanner = new Scanner(emailIn);
                String emailContent = "";
                while (emailScanner.hasNextLine()) {
                    emailContent += emailScanner.nextLine();
                }
                Email fileEmail = EmailFormatParser.parseRawJson(emailContent);
                for (Email.Tag tag : indexEmail.getTags()) {
                    fileEmail.addTag(tag);
                }
                emailList.add(fileEmail);
            }
            Duke.getUI().showMessage("Saved email file successfully loaded...");
            indexIn.close();
        } catch (FileNotFoundException e) {
            // It is acceptable if there is no save file. Empty list returned
            return emailList;
        } catch (IOException e) {
            Duke.getUI().showError("Read save file IO exception");
        } catch (EmailFormatParser.EmailParsingException e) {
            Duke.getUI().showError("Email save file is in wrong format");
        }
        return emailList;
    }

    /**
     * Saves authorisation token for user account.
     *
     * @param token Authorisation token
     */
    public static void saveRefreshToken(String token) {
        try {
            prepareFolder();
            File userInfoFile = new File(getUserInfoDir());
            FileOutputStream out = new FileOutputStream(userInfoFile, false);
            out.write(token.getBytes());
            out.close();
        } catch (IOException e) {
            Duke.getUI().showError("Save refresh token failed");
        }
    }

    /**
     * Read token from info file.
     *
     * @return refresh token
     */
    public static String readRefreshToken() {
        String token = "";
        try {
            prepareFolder();
            File userInfoFile = new File(getUserInfoDir());
            userInfoFile.createNewFile();
            FileInputStream in = new FileInputStream(userInfoFile);
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                token += scanner.next();
            }
        } catch (FileNotFoundException e) {
            Duke.getUI().showError("User info file not found");
        } catch (IOException e) {
            Duke.getUI().showError("Read user info file IO Exception");
        }
        return token;
    }
}
