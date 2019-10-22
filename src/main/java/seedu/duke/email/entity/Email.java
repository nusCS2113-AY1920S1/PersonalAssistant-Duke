package seedu.duke.email.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.Duke;
import seedu.duke.email.EmailContentParser;
import seedu.duke.email.EmailFormatParser;
import seedu.duke.email.EmailStorage;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    protected ArrayList<Tag> tags = new ArrayList<>();
    private String subject;
    private EmailFormatParser.Sender sender;
    private LocalDateTime receivedDateTime;
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
    public Email(String subject, EmailFormatParser.Sender sender, LocalDateTime receivedDateTime, String body,
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
     * @param tags             list of tags of the email
     */
    public Email(String subject, EmailFormatParser.Sender sender, LocalDateTime receivedDateTime,
                 ArrayList<Tag> tags) {
        this.subject = subject;
        this.sender = sender;
        this.receivedDateTime = receivedDateTime;
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
     * Add tag from string if not exist.
     *
     * @param keyword keyword of the tag
     */
    public void addTag(String keyword) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(keyword)) {
                return;
            }
        }
        this.tags.add(new Tag(keyword));
    }

    /**
     * Add tag from keywordPair if not exist.
     *
     * @param keywordPair keywordPair of the tag
     * @param relevance   relevance of the tag
     */
    public void addTag(EmailContentParser.KeywordPair keywordPair, int relevance) {
        for (Tag tag : tags) {
            if (tag.getKeywordPair().getKeyword().equals(keywordPair.getKeyword())) {
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
                return;
            }
        }
        this.tags.add(newTag);
    }

    /**
     * Colors the email body with the tag of highest relevance. Also, longer expression will have a higher
     * priority to be colored currently.
     *
     * @return email body after the coloring
     */
    public String colorBodyOnTag() {
        Tag highestTag = findHighestTag();
        if (highestTag == null) {
            return body;
        }
        String output = this.body;
        ArrayList<String> expressions = highestTag.getKeywordPair().getExpressions();
        expressions.sort((ex1, ex2) -> ex1.length() >= ex2.length() ? -1 : 1);
        output = addColorToExpressions(output, expressions);
        return output;
    }

    private String addColorToExpressions(String output, ArrayList<String> expressions) {
        for (String expression : expressions) {
            //Duke.getUI().showDebug(expression);
            Pattern colorPattern = Pattern.compile("(" + expression + ")", Pattern.CASE_INSENSITIVE);
            Matcher colorMatcher = colorPattern.matcher(output);
            output = colorMatcher.replaceAll("<font style=\"color:red\">" + expression + "</font>");
        }
        return output;
    }

    private Tag findHighestTag() {
        Tag highestTag = null;
        for (Tag tag : tags) {
            if (!tag.getKeywordPair().getKeyword().equals("Spam")
                    && (highestTag == null || tag.relevance > highestTag.relevance)) {
                highestTag = tag;
            }
        }
        return highestTag;
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
        return EmailStorage.getFolderDir() + File.separator + this.subject;
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
     *
     * @return string that will be displayed in GUI
     */
    public String toGuiString() {
        String guiStr = this.subject;
        if (tags.size() > 0) {
            guiStr += "\n";
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
        private EmailContentParser.KeywordPair keywordPair;
        private int relevance = INFINITY;

        public Tag(EmailContentParser.KeywordPair keywordPair, int relevance) {
            this.keywordPair = keywordPair;
            this.relevance = relevance;
        }

        public Tag(String keyword) {
            this.keywordPair = new EmailContentParser.KeywordPair(keyword);
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


            this.keywordPair = new EmailContentParser.KeywordPair(keyword, expressionList);
            this.relevance = relevance;
        }

        public EmailContentParser.KeywordPair getKeywordPair() {
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
