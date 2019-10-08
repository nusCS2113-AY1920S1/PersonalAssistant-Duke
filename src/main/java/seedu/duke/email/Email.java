package seedu.duke.email;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import seedu.duke.Duke;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Email {
    protected String filepath;
    protected String subject;
    protected EmailParser.Sender from;
    protected LocalDateTime receivedDateTime;
    protected String body;
    protected Boolean hasHtml;
    protected String tag;

    //@FXML
    //private WebView webView;

    public Email(String subject) {
        this.subject = subject;
        this.filepath = getEmailFilePath();
    }

    /**
     * Detailed constructor of Email class with more paramaters.
     *
     * @param subject subject of the email
     * @param from the sender of the email
     * @param receivedDateTime the date and time when the email is received
     * @param body the body of the email
     */
    public Email(String subject, EmailParser.Sender from, LocalDateTime receivedDateTime, String body) {
        this.subject = subject;
        this.from = from;
        this.receivedDateTime = receivedDateTime;
        this.body = body;
    }

    /**
     * Get title of this email.
     *
     * @return title of this email.
     */
    public String getSubject() {
        return this.subject;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Get the pathname for this email.
     *
     * @return pathname for this email.
     */
    public String getEmailFilePath() {
        return getFolderDir() + File.separator + this.subject;
    }

    /**
     * Show this email on browser. To be replaced by JavaFx code for UI display.
     *
     * @throws IOException if fails to load the filepath or open the browser.
     */
    public void showEmail() throws IOException {
        File emailFile = new File(this.filepath);
        Desktop.getDesktop().browse(emailFile.toURI());
        //System.out.println(emailFile.toURI().toURL().toString());
        //String html = "<html><h1>Hello</h1><h2>Hello</h2></html>";
        //webView.getEngine().loadContent(html);
        //webView.getEngine().load("file:///C:/WSL/CS2113T/main/data/emails"
               // + "/787d13df2c52f920b0b24c6e1e66a5600c88a433d7ab336f46291c5d34b263cb-2019-10-07-143357
        // .htm");
    }

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
        return EmailParser.formatEmailDateTime(receivedDateTime);
    }

    private String getDateTimePlainString() {
        return EmailParser.formatEmailDateTimePlain(receivedDateTime);
    }

    /**
     * Helper function for the email to be printed in command line.
     *
     * @return a string capturing the email info
     */
    public String toCliString() {
        String output = this.subject + "\n\t" + "From: " + this.from.toString() + "\n\t"
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
}
