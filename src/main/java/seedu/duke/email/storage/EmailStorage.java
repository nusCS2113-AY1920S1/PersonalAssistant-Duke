package seedu.duke.email.storage;

import org.json.JSONException;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.common.storage.StorageHelper;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.email.EmailList;
import seedu.duke.email.entity.Email;
import seedu.duke.ui.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static seedu.duke.email.parser.EmailContentParseHelper.allKeywordInEmail;

/**
 * Handles loading and saving of emails from local storage.
 */
public class EmailStorage {
    private static String indexFilename = "email.txt";
    private static String userFilename = "user.txt";

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
        for (Email email : emailList) {
            if (email.getBody() != null) {
                saveEmailToFolder(email);
            }
        }
    }

    private static void saveEmailToFolder(Email email) throws IOException {
        Path emailPath = StorageHelper.prepareEmailPath(email.toFilename());
        StorageHelper.saveToFile(emailPath, email.getRawJson());
    }

    private static void saveEmailListToIndex(EmailList emailList) throws IOException, JSONException {
        String content = prepareEmailListIndexString(emailList);
        Path indexPath = StorageHelper.prepareDataPath(indexFilename);
        StorageHelper.saveToFile(indexPath, content);
    }

    private static String prepareEmailListIndexString(EmailList emailList) throws JSONException {
        String content = "";
        for (Email email : emailList) {
            content += email.toIndexJson().toString() + System.lineSeparator();
        }
        return content;
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
            Path indexPath = StorageHelper.prepareDataPath(assignIndexDirIfNotExist(indexDir));
            List<String> emailStringList = StorageHelper.readLinesFromFile(indexPath);
            for (int i = 0; i < emailStringList.size(); i++) {
                readAndAddEmailWithIndexString(emailList, emailStringList.get(i));
            }
            UI.getInstance().showMessage("Saved email file successfully loaded...");
        } catch (IOException e) {
            UI.getInstance().showError("Read save file IO exception");
        } catch (EmailFormatParseHelper.EmailParsingException e) {
            UI.getInstance().showError("Email save file is in wrong format");
        }
        return emailList;
    }

    private static String assignIndexDirIfNotExist(String indexDir) {
        if ("".equals(indexDir)) {
            return indexFilename;
        }
        return indexDir;
    }

    private static void readAndAddEmailWithIndexString(EmailList emailList, String input)
            throws EmailFormatParseHelper.EmailParsingException {
        Email indexEmail = EmailFormatParseHelper.parseIndexJson(input);
        String emailFilename = indexEmail.toFilename();
        try {
            Email fileEmail = readEmailFromFolder(indexEmail, emailFilename);
            emailList.add(fileEmail);
        } catch (IOException e) {
            UI.getInstance().showDebug("Email file not found for " + indexEmail.getSubject());
        }
    }

    private static Email readEmailFromFolder(Email indexEmail, String emailFilename)
            throws IOException, EmailFormatParseHelper.EmailParsingException {
        Path emailPath = StorageHelper.prepareEmailPath(emailFilename);
        String emailContent = StorageHelper.readFromFile(emailPath);
        Email fileEmail = parseEmailFromFolder(indexEmail, emailContent);
        return fileEmail;
    }

    private static Email parseEmailFromFolder(Email indexEmail, String emailContent)
            throws EmailFormatParseHelper.EmailParsingException {
        Email fileEmail = EmailFormatParseHelper.parseRawJson(emailContent);
        for (Email.Tag tag : indexEmail.getTags()) {
            fileEmail.addTag(tag);
        }
        fileEmail.setUpdatedOn(indexEmail.getUpdatedOn());
        return fileEmail;
    }

    /**
     * Saves authorisation token for user account.
     *
     * @param token Authorisation token
     */
    public static void saveRefreshToken(String token) {
        try {
            Path userPath = StorageHelper.prepareDataPath(userFilename);
            StorageHelper.saveToFile(userPath, token);
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
        Path userPath = StorageHelper.prepareDataPath(userFilename);
        return StorageHelper.readFromFile(userPath);
    }
}
