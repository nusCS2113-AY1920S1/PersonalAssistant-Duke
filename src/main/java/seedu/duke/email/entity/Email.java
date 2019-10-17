package seedu.duke.email.entity;

import seedu.duke.Duke;
import seedu.duke.email.EmailFormatParser;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Email {
    protected String filepath;
    protected String subject;
    protected EmailFormatParser.Sender sender;
    protected LocalDateTime receivedDateTime;
    protected String body;
    protected Boolean hasHtml;
    protected ArrayList<String> tags = new ArrayList<>();
    protected String rawJson;

    //@FXML
    //private WebView webView;

    public Email(String subject) {
        this.subject = subject;
        this.filepath = getEmailFilePath();
    }

    /**
     * Detailed constructor of Email class with more paramaters.
     *
     * @param subject          subject of the email
     * @param sender           the sender of the email
     * @param receivedDateTime the date and time when the email is received
     * @param body             the body of the email
     */
    public Email(String subject, EmailFormatParser.Sender sender, LocalDateTime receivedDateTime, String body,
                 String rawJson) {
        this.subject = subject;
        this.sender = sender;
        this.receivedDateTime = receivedDateTime;
        this.body = body;
        this.rawJson = rawJson;
    }

    /**
     * Get title of this email.
     *
     * @return title of this email.
     */
    public String getSubject() {
        return this.subject;
    }

    public LocalDateTime getReceivedDateTime() {
        return this.receivedDateTime;
    }

    /**
     * Adds tag to this email object.
     * @param tag Tag to categorise email
     */
    public void addTag(String tag) {
        if (this.tags.contains(tag.strip())) {
            return;
        }
        this.tags.add(tag.strip());
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getRawJson() {
        return this.rawJson;
    }

    public String getSenderString() {
        return this.sender.toString();
    }

    /**
     * Get the pathname for this email.
     *
     * @return pathname for this email.
     */
    public String getEmailFilePath() {
        return getFolderDir() + File.separator + this.subject;
    }

    ///**
    // * Show this email on browser. To be replaced by JavaFx code for UI display.
    // *
    // * @throws IOException if fails to load the filepath or open the browser.
    // */
    //@FXML
    //public String getShowEmailPath() throws IOException {
    //    File emailFile = new File(this.filepath);
    //    //Desktop.getDesktop().browse(emailFile.toURI());
    //    String path = emailFile.toURI().toURL().toString();
    //    return path;
    //}

    /**
     * Get the pathname of the data/emails folder.
     *
     * @return the pathname of the data/emails folder.
     */
    private static String getFolderDir() {
        String dir;
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "emails";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "data" + File.separator + "emails";
        } else {
            dir = "emails";
        }
        return dir;
    }

    /**
     * Outputs a string with all the information of this email to be stored in a file for future usage. The
     * subject of the email is hashed and combined with date time to produce the filename.
     *
     * @return a string with all the information of this email.
     */
    public String toFileString() {
        String fileString = this.subject + " | ";  // to add on info such as tags.
        return fileString;
    }

    public String getFilename() {
        String filename = shaHash(this.subject) + "-" + this.getDateTimePlainString() + ".htm";
        return filename;
    }

    private String getDateTimeString() {
        return EmailFormatParser.formatEmailDateTime(receivedDateTime);
    }

    private String getDateTimePlainString() {
        return EmailFormatParser.formatEmailDateTimePlain(receivedDateTime);
    }

    /**
     * Helper function for the email to be printed in command line.
     *
     * @return a string capturing the email info
     */
    public String toCliString() {
        String output = this.subject + "\n\t" + "From: " + this.sender.toString() + "\n\t"
                + "ReceivedDateTime: " + getDateTimeString() + "\n\t" + "Body: " + body.substring(0, 30)
                + "...\n";
        return output;
    }

    public String getBody() {
        return body;
    }

    private String shaHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Change this to UTF-16 if needed
            md.update(input.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();

            String hex = String.format("%064x", new BigInteger(1, digest));
            return hex;
        } catch (NoSuchAlgorithmException e) {
            Duke.getUI().showError("Hashing email name error");
        }
        return input;
    }

    /**
     * Converts information about email to string that will be displayed to user.
     * @return string that will be displayed in GUI
     */
    public String toGuiString() {
        String guiStr = this.subject;
        if (tags.size() > 0) {
            guiStr += "\n";
            for (String tag : tags) {
                guiStr += " #" + tag;
            }
        }
        return guiStr;
    }
}
