package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.emailCommand.ListEmailCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailStorage {
    private static String getSaveEmailDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "email.txt";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "email.txt";
        } else {
            dir = "." + File.separator + "email.txt";
        }
        return dir;
    }

    private static String getFolderDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "emails";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "emails";
        } else {
            dir = "." + File.separator + "emails";
        }
        return dir;
    }

    public static ArrayList<String> getHtmlList() {
        ArrayList<String> listOfHtml = new ArrayList<String>();
        File emailFolder = new File(getFolderDir());
        for (File fileEntry : emailFolder.listFiles()) {
            if(fileEntry.isFile()) {
                String emailFileName = fileEntry.getName();
                listOfHtml.add(emailFileName);
            }
        }
        return listOfHtml;
    }

    // To implement with code to fetch emails from online server to local storage.
    // May need to sync the current email list with local storage after that using syncEmailListWithHtml().
    public static void syncWithServer() {
    }

    // To save the information for the emailList including title and tags(not implemented yet) for each email
    // before exiting the app.
    public static void saveEmails(EmailList emailList) {
        FileOutputStream out;
        try {
            String dir = getSaveEmailDir();
            File file = new File(dir);
            file.createNewFile();
            out = new FileOutputStream(file, false);
            String content = "";
            for (Email email : emailList) {
                content += email.toFileString() + "\n";
            }
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            Duke.getUI().showError("Write to output file IO exception!");
        }
    }

    // To sync the emailList with email html files saved in local storage.
    // To prevent mismatch between emailList and existing emails in local storage.
    // Can be executed after fetching html files from server, to keep emailList updated.
    public static EmailList syncEmailListWithHtml(EmailList emailList) {
        ArrayList<String> htmlList = getHtmlList();
        EmailList syncedEmailList = new EmailList();

        //This boolean array will auto-initialize to false since boolean's default value is false.
        boolean[] inEmailList = new boolean[htmlList.size()];

        for(Email email : emailList) {
            String title = email.getTitle();
            for (int j = 0; j < htmlList.size(); j++) {
                if (htmlList.get(j).equals(title)) {
                    inEmailList[j] = true;
                    // Add the email to syncedEmailList if the email has html file in local storage.
                    // So email without its html file will not be added.
                    syncedEmailList.add(email);
                }
            }
        }
        for (int j = 0; j < htmlList.size(); j++) {
            if (!inEmailList[j]) {
                String title = htmlList.get(j);
                // Add the email(previously not in emailList) to syncedEmailList.
                syncedEmailList.add(new Email(title));
            }
        }
        emailList = null; // it will be automatically deleted by the garbage collector.
        return syncedEmailList;
    }

    // Get emailList according to html files present in local storage.
    public static EmailList readEmailFromHtml() {
        EmailList emailList = new EmailList();
        File emailFolder = new File(getFolderDir());
        for (File fileEntry : emailFolder.listFiles()) {
            if(fileEntry.isFile()) {
                String emailFileName = fileEntry.getName();
                Email email = new Email(emailFileName);
                emailList.add(email);
            }
        }
        return emailList;
    }

    // Get emailList according to previously saved information about emails from local storage at the start
    // of the app.
    public static EmailList readEmailFromFile() {
        EmailList emailList = new EmailList();
        try {
            String dir = getSaveEmailDir();
            FileInputStream in;
            File file = new File(dir);
            in = new FileInputStream(file);
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.length() <= 2) {
                    throw new Storage.StorageException("Invalid Save File!");
                }
                String title = input.split("\\|")[0].strip();
                Email email = new Email(title);
                emailList.add(email);
            }
            Duke.getUI().showMessage("Saved email file successfully loaded...");
            in.close();
        } catch (FileNotFoundException e) {
            // It is acceptable if there is no save file.
            return emailList;
        } catch (IOException e) {
            Duke.getUI().showError("Read save file IO exception");
        } catch (Storage.StorageException e) {
            Duke.getUI().showError(e.toString());
            emailList = new EmailList();
        }
        return emailList;
    }

    // Executed at the start of the app, first to retrieve previously saved information about emails from
    // saved text file, then sync the emailList with html files in present in local storage.
    public static EmailList readEmails() {
        EmailList emailList = readEmailFromFile();
        EmailList syncedEmailList = syncEmailListWithHtml(emailList);;
        return syncedEmailList;
    }
}
