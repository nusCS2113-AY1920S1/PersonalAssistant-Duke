package seedu.duke.email.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.common.storage.TimestampHelper;
import seedu.duke.email.parser.EmailFormatParseHelper;
import seedu.duke.ui.UI;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    protected ArrayList<Tag> tags = new ArrayList<>();
    private String subject;
    private EmailFormatParseHelper.Sender sender;
    private LocalDateTime receivedDateTime;
    private LocalDateTime updatedOn;
    private String body;
    private String rawJson;

    public Email(String subject) {
        this.subject = subject;
    }

    /**
     * Detailed constructor of Email class with more parameters.
     *
     * @param subject          subject of the email
     * @param sender           the sender of the email
     * @param receivedDateTime the date and time when the email is received
     * @param body             the body of the email
     * @param rawJson          the raw json of the email when retrieved from the Outlook server
     */
    public Email(String subject, EmailFormatParseHelper.Sender sender, LocalDateTime receivedDateTime, String body,
                 String rawJson) {
        this.subject = subject;
        this.sender = sender;
        this.receivedDateTime = receivedDateTime;
        this.body = body;
        this.rawJson = rawJson;
    }

    /**
     * Alternative constructor for Email, used with the information retrieved from the index file.
     *
     * @param subject          subject of the
     * @param sender           the sender of the email
     * @param receivedDateTime the date and time when the email is received
     * @param updatedOn        the time when the email keywords are last updated
     * @param tags             list of tags of the email
     */
    public Email(String subject, EmailFormatParseHelper.Sender sender, LocalDateTime receivedDateTime,
                 LocalDateTime updatedOn, ArrayList<Tag> tags) {
        this.subject = subject;
        this.sender = sender;
        this.receivedDateTime = receivedDateTime;
        this.updatedOn = updatedOn;
        this.tags = tags;
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

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    /**
     * Sets the updatedOn to current time.
     */
    public void updateTimestamp() {
        updatedOn = TimestampHelper.getDateTime();
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Add tag from string if not exist.
     *
     * @param keyword keyword of the tag
     */
    public boolean addTag(String keyword) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().toUpperCase().equals(keyword.toUpperCase())) {
                UI.getInstance().showError("Tag \'" + keyword + "\' already exists.");
                return false;
            }
        }
        this.tags.add(new Tag(keyword));
        return true;
    }

    /**
     * Add tag from keywordPair if not exist.
     *
     * @param keywordPair keywordPair of the tag
     * @param relevance   relevance of the tag
     */
    public void addTag(KeywordPair keywordPair, int relevance) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(keywordPair.getKeyword())) {
                UI.getInstance().showError("Tag already exists.");
                return;
            }
        }
        this.tags.add(new Tag(keywordPair, relevance));
    }

    /**
     * Add tag to tag list if keyword not exist.
     *
     * @param newTag the new tag to be added to the list
     */
    public void addTag(Tag newTag) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(newTag.getKeywordPair().getKeyword())) {
                UI.getInstance().showError("Tag already exists.");
                return;
            }
        }
        this.tags.add(newTag);
    }

    /**
     * Removes the tag with the given keyword in the keyword pair.
     *
     * @param keyword contained by the deleted tag
     */
    public void removeTag(String keyword) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(keyword)) {
                this.tags.remove(tag);
                return;
            }
        }
    }

    /**
     * Highlights the email with all the tags. Also, longer expression will have a higher priority to be
     * colored currently.
     *
     * @return email body after the coloring
     */
    public String highlightOnTag() {
        ArrayList<String> expressions = getAllExpressions();
        String output = toWebViewString();
        expressions.sort((ex1, ex2) -> ex1.length() >= ex2.length() ? -1 : 1);
        output = addHighlightToExpressions(output, expressions);
        return output;
    }

    private String toWebViewString() {
        String output = "";
        output += "<h3>" + this.subject + "</h3>";
        output += "<h5 style=\"color:gray\">" + this.sender.toWebViewString() + "</h5>";
        output += "<h5 style=\"color:gray\">" + this.getReceivedDateTime() + "</h5>";
        output += bodyWithoutAttachmentImage();
        return output;
    }

    private String bodyWithoutAttachmentImage() {
        //String newBody = body.replaceAll("(width=\"\\d+\" height=\"\\d+\")|(width:[\\d.]+\\w*;"
        //                + "\\s*height:[\\d.]+)",
                String newBody = body.replaceAll("<img",
                "<img style=\"display: none;\"");
        System.out.println(newBody);
        return newBody;
    }

    private ArrayList<String> getAllExpressions() {
        ArrayList<String> expressions = new ArrayList<>();
        for (Tag tag : this.tags) {
            if (tag.getKeywordPair().getKeyword().equals("Spam")) {
                continue;
            }
            for (String expression : tag.getKeywordPair().getExpressions()) {
                expressions.add(expression);
            }
        }
        return expressions;
    }

    private String addHighlightToExpressions(String emailContent, ArrayList<String> expressions) {
        String content = emailContent;
        for (String expression : expressions) {
            Pattern colorPattern = Pattern.compile("(^|\\W)(?!<mark style=\"color:black;"
                            + "background-color:yellow\">)(" + expression + ")(?!</mark>)(\\W|$)",
                    Pattern.CASE_INSENSITIVE);
            Matcher colorMatcher = colorPattern.matcher(content);
            while (colorMatcher.find()) {
                content = colorMatcher.replaceFirst("$1<mark style=\"color:black;background-color:yellow\">"
                        + expression + "</mark>$3");
                colorMatcher = colorPattern.matcher(content);
            }
            //content = colorMatcher.replaceAll("$1<mark style=\"color:black;background-color:yellow\">"
            //        + expression + "</mark>$3");
        }
        return content;
    }

    public String getRawJson() {
        return this.rawJson;
    }

    public String getSenderString() {
        return this.sender.toString();
    }

    public String toFilename() {
        String filename = shaHash(this.subject) + "-" + this.getDateTimePlainString() + ".htm";
        return filename;
    }

    /**
     * Formats the email object to a json object to be saved to index file.
     *
     * @return a json object containing all the parsed information of the email object
     */
    public JSONObject toIndexJson() throws JSONException {
        JSONObject indexJson = new JSONObject();
        indexJson.put("subject", this.subject);
        indexJson.put("sender", this.sender.toString());
        indexJson.put("receivedDateTime", this.getDateTimeString());
        indexJson.put("updatedOn", TimestampHelper.formatDateTime(this.updatedOn));
        JSONArray tagArray = prepareTagJsonArray();
        indexJson.put("tags", tagArray);
        return indexJson;
    }

    private JSONArray prepareTagJsonArray() throws JSONException {
        JSONArray tagArray = new JSONArray();
        for (Tag tag : this.tags) {
            tagArray.put(tag.toJsonObject());
        }
        return tagArray;
    }

    private String getDateTimeString() {
        return EmailFormatParseHelper.formatEmailDateTime(receivedDateTime);
    }

    private String getDateTimePlainString() {
        return EmailFormatParseHelper.formatEmailDateTimePlain(receivedDateTime);
    }

    /**
     * Helper function for the email to be printed in command line.
     *
     * @return a string capturing the email info
     */
    public String toCliString() {
        String output = this.subject + System.lineSeparator() + "\t" + "From: " + this.sender.toString()
                + System.lineSeparator() + "\tReceivedDateTime: " + getDateTimeString()
                + System.lineSeparator() + "\t" + "Body: " + body.substring(0, 30) + "..."
                + System.lineSeparator();
        return output;
    }

    public String getBody() {
        return body;
    }

    public String getShaHash() {
        return shaHash(this.subject);
    }

    private String shaHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Change this to UTF-16 if needed
            //md.update(input.getBytes(StandardCharsets.UTF_16));
            md.update(input.getBytes());
            byte[] digest = md.digest();

            String hex = String.format("%064x", new BigInteger(1, digest));
            return hex;
        } catch (NoSuchAlgorithmException e) {
            UI.getInstance().showError("Hashing email name error");
        }
        return input;
    }

    /**
     * Converts information about email to string that will be displayed to user.
     *
     * @return string that will be displayed in GUI
     */
    public String toGuiString() {
        String guiStr = this.subject;
        if (tags.size() > 0) {
            guiStr += System.lineSeparator();
            for (Tag tag : tags) {
                guiStr += " #" + tag.getKeywordPair().getKeyword();
            }
        }
        return guiStr;
    }

    /**
     * Tag of an email with both a keyword pair and a score indicating its relevance.
     */
    public static class Tag {
        private static final int INFINITY = 0x3f3f3f;
        private KeywordPair keywordPair;
        private int relevance = INFINITY;

        public Tag(KeywordPair keywordPair, int relevance) {
            this.keywordPair = keywordPair;
            this.relevance = relevance;
        }

        public Tag(String keyword) {
            this.keywordPair = new KeywordPair(keyword);
        }

        /**
         * Initialize from a json object in the same structure as the json output.
         *
         * @param json json object containing the information of this tag, in the same format as the json
         *             output from toJsonObject()
         */
        public Tag(JSONObject json) throws JSONException {
            String keyword = json.getString("keyword");
            JSONArray expressions = json.getJSONArray("expressions");
            ArrayList<String> expressionList = new ArrayList<>();
            for (int i = 0; i < expressions.length(); i++) {
                expressionList.add(expressions.getString(i));
            }
            int relevance = json.getInt("relevance");


            this.keywordPair = new KeywordPair(keyword, expressionList);
            this.relevance = relevance;
        }

        public KeywordPair getKeywordPair() {
            return this.keywordPair;
        }

        public int getRelevance() {
            return relevance;
        }

        public void setRelevance(int relevance) {
            this.relevance = relevance;
        }

        /**
         * Converts tag to a json object for storage purpose.
         *
         * @return formatting result in JSONObject
         */
        public JSONObject toJsonObject() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("keyword", this.keywordPair.getKeyword());
            JSONArray expressionArray = new JSONArray();
            for (String expression : this.keywordPair.getExpressions()) {
                expressionArray.put(expression);
            }
            json.put("expressions", expressionArray);
            json.put("relevance", this.relevance);
            return json;
        }
    }
}
