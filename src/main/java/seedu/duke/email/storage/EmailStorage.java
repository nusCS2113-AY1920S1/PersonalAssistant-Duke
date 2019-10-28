package seedu.duke.email.storage;

import org.json.JSONException;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;
import seedu.duke.ui.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static seedu.duke.email.parser.EmailContentParseHelper.allKeywordInEmail;

/**
 * Handles loading and saving of emails from local storage.
 */
public class EmailStorage {

    /**
     * Get the pathname of the data/email.txt.
     *
     * @return pathname of the email.txt file.
     */
    public static String getDataDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data";
        } else {
            dir = "." + File.separator + "data";
        }
        return dir + File.separator;
    }

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
        EmailList updatedEmailList = combineServerAndLocalEmailList(serverEmailList);
        Model.getInstance().setEmailList(updatedEmailList);
        saveEmails(updatedEmailList);
    }

    private static EmailList combineServerAndLocalEmailList(EmailList serverEmailList) {
        EmailList modelEmailList = Model.getInstance().getEmailList();
        for (Email serverEmail : serverEmailList) {
            if (!emailRepeated(serverEmail)) {
                allKeywordInEmail(serverEmail);
                modelEmailList.add(serverEmail);
            }
        }
        return modelEmailList;
    }

    private static boolean emailRepeated(Email serverEmail) {
        boolean exist = false;
        EmailList modelEmailList = Model.getInstance().getEmailList();
        for (Email localEmail : modelEmailList) {
            if (localEmail.getSubject().equals(serverEmail.getSubject())
                    && serverEmail.getReceivedDateTime().equals(localEmail.getReceivedDateTime())) {
                exist = true;
                break;
            }
        }
        return exist;
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
            saveEmailListToIndex(emailList);
            saveEmailListToFolder(emailList);
        } catch (IOException e) {
            e.printStackTrace();
            UI.getInstance().showError("Write to output file IO exception!");
        } catch (JSONException e) {
            UI.getInstance().showError("Email index formatting exception!");
        }
    }

    private static void saveEmailListToFolder(EmailList emailList) throws IOException {
        String folderDir = getFolderDir();
        for (Email email : emailList) {
            if (email.getBody() != null) {
                saveEmailToFolder(folderDir, email);
            }
        }
    }

    private static void saveEmailToFolder(String folderDir, Email email) throws IOException {
        File emailSource = new File(folderDir + email.toFilename());
        emailSource.createNewFile();
        FileOutputStream emailOut = new FileOutputStream(emailSource, false);
        emailOut.write(email.getRawJson().getBytes());
        emailOut.close();
    }

    private static void saveEmailListToIndex(EmailList emailList) throws IOException, JSONException {
        String indexDir = getEmailIndexDir();
        File indexFile = new File(indexDir);
        indexFile.createNewFile();
        FileOutputStream indexOut = new FileOutputStream(indexFile, false);
        String content = prepareEmailListIndexString(emailList);
        indexOut.write(content.getBytes());
        indexOut.close();
    }

    private static String prepareEmailListIndexString(EmailList emailList) throws JSONException {
        String content = "";
        for (Email email : emailList) {
            content += email.toIndexJson().toString() + "\n";
        }
        return content;
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
    public static EmailList readEmailFromFile(String indexDir) {
        EmailList emailList = new EmailList();
        try {
            prepareFolder();
            indexDir = assignIndexDirIfNotExist(indexDir);
            File indexFile = new File(indexDir);
            FileInputStream indexIn = new FileInputStream(indexFile);
            Scanner scanner = new Scanner(indexIn);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                readEmailWithIndexString(emailList, input);
            }
            UI.getInstance().showMessage("Saved email file successfully loaded...");
            indexIn.close();
        } catch (FileNotFoundException e) {
            // It is acceptable if there is no save file. Empty list returned
            UI.getInstance().showMessage("Email file not found. Empty email list is used.");
            return new EmailList();
        } catch (IOException e) {
            UI.getInstance().showError("Read save file IO exception");
        } catch (EmailFormatParseHelper.EmailParsingException e) {
            UI.getInstance().showError("Email save file is in wrong format");
        }
        return emailList;
    }

    private static String assignIndexDirIfNotExist(String indexDir) {
        if ("".equals(indexDir)) {
            return getEmailIndexDir();
        }
        return indexDir;
    }

    private static void readEmailWithIndexString(EmailList emailList, String input)
            throws EmailFormatParseHelper.EmailParsingException, FileNotFoundException {
        Email indexEmail = EmailFormatParseHelper.parseIndexJson(input);
        String filename = indexEmail.toFilename();
        //try {
        Email fileEmail = readEmailFromFolder(indexEmail, filename);
        emailList.add(fileEmail);
        //} catch (FileNotFoundException e) {
        //    UI.getInstance().showError("An email file not found.");
        //}
    }

    private static Email readEmailFromFolder(Email indexEmail, String filename)
            throws FileNotFoundException, EmailFormatParseHelper.EmailParsingException {
        String emailContent = readEmailContentFromFolder(filename);
        Email fileEmail = parseEmailFromFolder(indexEmail, emailContent);
        return fileEmail;
    }

    private static Email parseEmailFromFolder(Email indexEmail, String emailContent)
            throws EmailFormatParseHelper.EmailParsingException {
        Email fileEmail = EmailFormatParseHelper.parseRawJson(emailContent);
        for (Email.Tag tag : indexEmail.getTags()) {
            fileEmail.addTag(tag);
        }
        return fileEmail;
    }

    private static String readEmailContentFromFolder(String filename) throws FileNotFoundException {
        String fileDir = getFolderDir() + filename;
        System.out.println(fileDir);
        File emailFile = new File(fileDir);
        FileInputStream emailIn = new FileInputStream(emailFile);
        Scanner emailScanner = new Scanner(emailIn);
        String emailContent = "";
        while (emailScanner.hasNextLine()) {
            emailContent += emailScanner.nextLine();
        }
        return emailContent;
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
            UI.getInstance().showError("Save refresh token failed");
        }
    }

    /**
     * Read token from info file.
     *
     * @return refresh token
     */
    public static String readRefreshToken() {
        try {
            prepareFolder();
            return readRefreshTokenContent();
        } catch (FileNotFoundException e) {
            UI.getInstance().showError("User info file not found");
            return "";
        } catch (IOException e) {
            UI.getInstance().showError("Read user info file IO Exception");
            return "";
        }
    }

    private static String readRefreshTokenContent() throws IOException {
        String token = "";
        File userInfoFile = new File(getUserInfoDir());
        userInfoFile.createNewFile();
        FileInputStream in = new FileInputStream(userInfoFile);
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) {
            token += scanner.next();
        }
        return token;
    }
}
