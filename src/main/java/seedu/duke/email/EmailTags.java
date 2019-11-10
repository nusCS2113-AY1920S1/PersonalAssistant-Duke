package seedu.duke.email;

import seedu.duke.email.entity.Email;
import seedu.duke.email.parser.EmailCommandParseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class EmailTags {
    private static TagMap tagMap = new TagMap();
    private static ArrayList<String> emailTagList = new ArrayList<>();

    /**
     * Updates the list of tags exist in the emails.
     *
     * @param emailList email list from model
     * @return list of tags exist in emails
     */
    public static ArrayList<String> updateEmailTagList(EmailList emailList) {
        emailTagList.clear();
        for (Email email : emailList) {
            ArrayList<Email.Tag> tags = email.getTags();
            for (Email.Tag tag : tags) {
                String tagName = tag.getKeywordPair().getKeyword();
                if (!emailTagList.contains(tagName)) {
                    emailTagList.add(tagName);
                }
            }
        }
        return emailTagList;
    }

    /**
     * Gets list of tags exist in the emails.
     *
     * @return list of tags exist in the emails.
     */
    public static ArrayList<String> getEmailTagList() {
        return emailTagList;
    }

    /**
     * Construct a HashMap to store the index of emails under the category of tags.
     *
     * @param emailList emailList from model.
     * @return HashMap of tags with their associated emails.
     * @see TagMap
     */
    public static HashMap<String, SubTagMap> updateTagMap(EmailList emailList) {
        tagMap.clear();
        for (int index = 0; index < emailList.size(); index++) {
            Email email = emailList.get(index);
            updateTagMapFromEachEmail(index, email);
        }
        return tagMap;
    }

    /**
     * Updates TagMap from each emails by processing each tags in the email.
     *
     * @param index index of email
     * @param email email
     */
    public static void updateTagMapFromEachEmail(int index, Email email) {
        ArrayList<Email.Tag> tags = email.getTags();
        for (Email.Tag rootTag : tags) {
            String rootTagName = rootTag.getKeywordPair().getKeyword();
            updateRootTagMap(index, tags, rootTagName);
        }
    }

    /**
     * Updates root tag map
     *
     * @param index       index of email
     * @param tags        list of all tags in the email
     * @param rootTagName each tags in the email
     */
    public static void updateRootTagMap(int index, ArrayList<Email.Tag> tags, String rootTagName) {
        for (Email.Tag subTag : tags) {
            String subTagName = subTag.getKeywordPair().getKeyword();
            updateSubTagMap(index, rootTagName, subTagName);
        }
    }

    /**
     * Inserts the email under its tag category in the HashMap.
     *
     * @param index       index of email
     * @param rootTagName root tag name
     * @param subTagName  sub tag name
     */
    public static void updateSubTagMap(int index, String rootTagName, String subTagName) {
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        SubTagMap subTagMap = new SubTagMap(subTagName, indexList);
        if (tagMap.containsKey(rootTagName)) {
            subTagMap = tagMap.get(rootTagName);
            if (subTagMap.containsKey(subTagName)) {
                indexList = subTagMap.get(subTagName);
            }
        }
        // add the index of the email tagged with both rootTag and subTag to the indexList
        if (!indexList.contains(index)) {
            indexList.add(index);
            subTagMap.put(subTagName, indexList);
            tagMap.put(rootTagName, subTagMap);
        }
    }

    public static String filterByEmailTag(ArrayList<String> tags, EmailList emailList) {
        String responseMsg = "";
        try {
            responseMsg = getTaggedEmailList(tags, emailList);
        } catch (EmailTagParseException e) {
            responseMsg = e.getMessage();
        }
        return responseMsg;
    }


    /**
     * Gets list of tagged emails given one or two tags from user input.
     *
     * @param tags tag(s) input by users.
     * @return list of tagged emails.
     */
    public static String getTaggedEmailList(ArrayList<String> tags, EmailList emailList) throws EmailTagParseException {
        if (tags.size() == 1) {
            String tagName = tags.get(0);
            return getRootTaggedEmailList(tagName, emailList);
        }

        String tagOne = tags.get(0);
        String tagTwo = tags.get(1);
        // both tags do not exist
        if (!isOneTagExist(tagOne) && !isOneTagExist(tagTwo)) {
            throw new EmailTagParseException("[Input content error] The tags #" + tagOne + " and #" + tagTwo
                            + " does not exists.");
        }
        // first tag does not exist, returns only list of emails with the second tag
        if (!isOneTagExist(tagOne)) {
            ArrayList<Integer> indexList = tagMap.get(tagTwo).get(tagTwo);
            return getOneTagMessage(emailList, tagOne, tagTwo, indexList);
        }
        // second tag does not exist, returns only list of emails with the first tag
        if (!isOneTagExist(tagTwo)) {
            ArrayList<Integer> indexList = tagMap.get(tagOne).get(tagOne);
            return getOneTagMessage(emailList, tagTwo, tagOne, indexList);
        }
        // both tags exist, but do not co-exist, returns list of emails with only the first tag and
        // second tag respectively
        if (!isBothTagCoexist(tagOne, tagTwo)) {
            ArrayList<Integer> indexListOne = tagMap.get(tagOne).get(tagOne);
            ArrayList<Integer> indexListTwo = tagMap.get(tagTwo).get(tagTwo);
            return getTagsNotCoexistMessage(emailList, tagOne, tagTwo, indexListOne, indexListTwo);
        }
        // both tags exist and co-exist, returns list of emails with both tags
        ArrayList<Integer> indexList = tagMap.get(tagOne).get(tagTwo);
        return getTagsCoexistMessage(emailList, tagOne, tagTwo, indexList);
    }

    /**
     * Checks the existence of a tag.
     * A tag exists if there is an email with the tag. If none of the emails has the tag, tag does not exist.
     *
     * @param tag tag name
     * @return true if the tag exists
     */
    public static Boolean isOneTagExist(String tag){
        if (tagMap.containsKey(tag)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if both tags co-exist.
     * Both tags co-exist if there is an email tagged with both tags at the same time.
     * Two tags exist but not co-exist means that no email has both tags at the same time, but some emails
     * have one of the tags each.
     *
     * @param tagOne first tag name
     * @param tagTwo second tag name
     * @return true if both tags co-exist
     */
    public static Boolean isBothTagCoexist(String tagOne, String tagTwo){
        if (!tagMap.containsKey(tagOne) || !tagMap.containsKey(tagTwo)) {
            return false;
        }
        if (tagMap.get(tagOne).containsKey(tagTwo)) {
            return true;
        }
        return false;
    }

    /**
     * Constructs response message for two tags input, only one of the tags exists.
     *
     * @param emailList email list from model
     * @param tagNameNotExist tag name that does not exist
     * @param tagNameExist tag name that exists
     * @param indexList index list of tagged emails
     * @return
     */
    public static String getOneTagMessage(EmailList emailList, String tagNameNotExist, String tagNameExist,
                                        ArrayList<Integer> indexList) {
        String responseMsg = "[Input content error] The tag #" + tagNameNotExist + " does not exists. ";
        responseMsg += "Here are the email(s) tagged with #" + tagNameExist + ": "
                + System.lineSeparator() + System.lineSeparator();
        responseMsg += emailList.toString(indexList);
        return responseMsg;
    }

    /**
     * Constructs response message for two tags input, both tags co-exist.
     *
     * @param emailList email list from model
     * @param tagNameOne first tag name
     * @param tagNameTwo second tag name
     * @param indexList index list of tagged emails
     * @return
     */
    public static String getTagsCoexistMessage(EmailList emailList, String tagNameOne, String tagNameTwo, ArrayList<Integer> indexList) {
        String responseMsg = "Here are the email(s) tagged with both #" + tagNameOne + " and #" + tagNameTwo
                + ": " + System.lineSeparator() + System.lineSeparator();
        responseMsg += emailList.toString(indexList);
        return responseMsg;
    }

    /**
     * Constructs response message for two tags input, both tags do not co-exist.
     *
     * @param emailList email list from model
     * @param tagNameOne first tag name
     * @param tagNameTwo second tag name
     * @param indexListOne index list of emails with first tag
     * @param indexListTwo index list of emails with second tag
     * @return
     */
    public static String getTagsNotCoexistMessage(EmailList emailList, String tagNameOne, String tagNameTwo,
                                                ArrayList<Integer> indexListOne,
                                                  ArrayList<Integer> indexListTwo) {
        String responseMsg = "No email is tagged with both #" + tagNameOne + " and #" + tagNameTwo + ": "
                + System.lineSeparator();
        responseMsg += System.lineSeparator() + "Here are the email(s) tagged with #" + tagNameOne
                + ": " + System.lineSeparator() + System.lineSeparator();
        responseMsg += emailList.toString(indexListOne);
        responseMsg += System.lineSeparator() + System.lineSeparator() + "Here are the email(s) "
                + "tagged with #" + tagNameTwo + ": " + System.lineSeparator() + System.lineSeparator();
        responseMsg += emailList.toString(indexListTwo);
        return responseMsg;
    }

    /**
     * Gets list of tagged emails given a single tag name, namely rootTagName.
     *
     * @param rootTagName tag input by user.
     * @return list of emails with tags co-exist with a given a single tagName.
     */
    public static String getRootTaggedEmailList(String rootTagName, EmailList emailList) throws EmailTagParseException {
        if (!isOneTagExist(rootTagName)) {
            throw new EmailTagParseException("[Input content error] The tag #" + rootTagName + " does not "
                    + "exists.");
        }
        String responseMsg = "Here are the email(s) tagged with #" + rootTagName + ": " + System.lineSeparator()
                + System.lineSeparator();
        SubTagMap subTagMap = tagMap.get(rootTagName);
        // For each tag that co-exists with rootTagName, namely subTagName, gets the list of emails tagged
        // with both the rootTagName and subTagName.
        for (HashMap.Entry<String, ArrayList<Integer>> entry : subTagMap.entrySet()) {
            String subTagName = entry.getKey();
            ArrayList<Integer> indexList = entry.getValue();
            responseMsg += "[" + subTagName + "]" + System.lineSeparator() + emailList.toString(indexList)
                    + System.lineSeparator();
        }
        return responseMsg;
    }

    /**
     * A structure that stores subTagName and its indexlist of emails.
     * Each entry of SubTagMap is a pair of subTagName and indexList.
     * SubTagName is the tag that co-exist with the rootTagName.
     * IndexList is the list of indexes of emails tagged with both rootTag and subTag.
     * @see TagMap
     */
    public static class SubTagMap extends HashMap<String, ArrayList<Integer>> {
        private String subTagName;
        private ArrayList<Integer> indexList;

        public SubTagMap(String subTagName, ArrayList<Integer> indexList) {
            this.subTagName = subTagName;
            this.indexList = indexList;
        }
    }

    /**
     * A structure that stores each tags and its subTagMap.
     * Each entry of TagMap is a pair of rootTagName and subTagMap.
     * @see SubTagMap
     */
    public static class TagMap extends HashMap<String, SubTagMap> {
        private String rootTagName;
        private SubTagMap subTagMap;

        public TagMap() {
            super();
        }

        public TagMap(String rootTagName, SubTagMap subTagMap) {
            this.rootTagName = rootTagName;
            this.subTagMap = subTagMap;
        }
    }

    /**
     * An type of exception to handle the unexpected user/file input related to email tags. The message
     * contains more specific information.
     */
    public static class EmailTagParseException extends EmailCommandParseHelper.EmailParseException {
        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public EmailTagParseException(String msg) {
            super(msg);
        }
    }
}