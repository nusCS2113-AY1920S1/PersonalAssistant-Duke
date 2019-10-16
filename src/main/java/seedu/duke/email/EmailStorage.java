package seedu.duke.email;

import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.Duke;
import seedu.duke.common.network.Http;
import seedu.duke.email.entity.Email;
import seedu.duke.task.TaskStorage;

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
    private static String getEmailIndexDir() {
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
    private static String getFolderDir() {
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
     * Get the list of html filenames currently saved in the data/emails folder.
     *
     * @return an array list of strings of html filenames.
     */
    public static ArrayList<String> getHtmlList() {
        ArrayList<String> listOfHtml = new ArrayList<String>();
        File emailFolder = new File(getFolderDir());
        for (File fileEntry : emailFolder.listFiles()) {
            if (fileEntry.isFile()) {
                String emailFileName = fileEntry.getName();
                listOfHtml.add(emailFileName);
            }
        }
        return listOfHtml;
    }

    /**
     * To implement with code to fetch emails from online server to local storage. May need to sync the
     * current email list with local storage after that by calling syncEmailListWithHtml().
     */
    public static void syncWithServer() {
        EmailList serverEmailList = Http.fetchEmail(50);
        for (Email serverEmail : serverEmailList) {
            boolean exist = false;
            for (Email localEmail : Duke.getEmailList()) {
                if (localEmail.getSubject().equals(serverEmail.getSubject())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                Duke.getEmailList().add(serverEmail);
            }
        }
        for (Email email : Duke.getEmailList()) {
            allKeywordInEmail(email);
        }
        saveEmails(Duke.getEmailList());
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
                content += email.getIndexJson().toString() + "\n";
            }
            indexOut.write(content.getBytes());
            indexOut.close();
            for (Email email : emailList) {
                if (email.getBody() != null) {
                    File emailSource = new File(folderDir + email.getFilename());
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

    ///**
    // * To sync the emailList with email html files saved in local storage. To prevent mismatch between
    // * emailList and existing emails in local storage. To be called for execution after fetching html files
    // * from server, to keep emailList updated. Creates a new emailList, which only adds email object from the
    // * input emailList that are present in the html lists, and html files that are not included in the input
    // * emailList.
    // *
    // * @param emailList is the current emailList from Duke to be synced with the html files in local storage.
    // * @return the synced emailList.
    // */
    //public static EmailList syncEmailListWithHtml(EmailList emailList) {
    //    ArrayList<String> htmlList = getHtmlList();
    //    EmailList syncedEmailList = new EmailList();
    //
    //    //This boolean array will auto-initialize to false since boolean's default value is false.
    //    boolean[] inEmailList = new boolean[htmlList.size()];
    //
    //    for (Email email : emailList) {
    //        String subject = email.getSubject();
    //        for (int j = 0; j < htmlList.size(); j++) {
    //            if (htmlList.get(j).equals(subject)) {
    //                inEmailList[j] = true;
    //                // Add the email to syncedEmailList if the email has html file in local storage.
    //                // So email without its html file will not be added.
    //                syncedEmailList.add(email);
    //            }
    //        }
    //    }
    //    for (int j = 0; j < htmlList.size(); j++) {
    //        if (!inEmailList[j]) {
    //            String subject = htmlList.get(j);
    //            // Add the email(previously not in emailList) to syncedEmailList.
    //            syncedEmailList.add(new Email(subject));
    //        }
    //    }
    //    emailList = null; // it will be automatically deleted by the garbage collector.
    //    return syncedEmailList;
    //}

    /**
     * Get emailList according to html files present in local storage. This method is not being used, but may
     * be useful someday so it is kept here.
     *
     * @return EmailList created by according to html files present in local storage.
     */
    public static EmailList readEmailFromHtml() {
        EmailList emailList = new EmailList();
        File emailFolder = new File(getFolderDir());
        for (File fileEntry : emailFolder.listFiles()) {
            if (fileEntry.isFile()) {
                String emailFileName = fileEntry.getName();
                Email email = new Email(emailFileName);
                emailList.add(email);
            }
        }
        return emailList;
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
                Email email = EmailFormatParser.parseIndexJson(input);
                String filename = email.getFilename();

                String fileDir = getFolderDir() + filename;
                File emailFile = new File(fileDir);
                FileInputStream emailIn = new FileInputStream(emailFile);
                Scanner emailScanner = new Scanner(emailIn);
                String emailContent = "";
                while (emailScanner.hasNextLine()) {
                    emailContent += emailScanner.nextLine();
                }
                emailList.add(EmailFormatParser.parseRawJson(emailContent));
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
     * Executed at the start of the app, first to retrieve previously saved information about emails from
     * data/email.txt, then sync the emailList with html files in present in data/emails.
     *
     * @return EmailList created from data/emails.txt and synced with data/emails.
     */
    public static EmailList readEmails() {
        EmailList emailList = readEmailFromFile();
        //EmailList syncedEmailList = syncEmailListWithHtml(emailList);
        //return syncedEmailList;
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
