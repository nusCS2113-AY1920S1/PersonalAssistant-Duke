package seedu.duke.email;

import seedu.duke.email.entity.Email;

import java.util.ArrayList;
import java.util.HashMap;

public class EmailTags {
    private static TagMap tagMap = new TagMap();
    private static ArrayList<String> emailTagList = new ArrayList<>();

    public static ArrayList<String> updateEmailTagList(EmailList emailList) {
        for (Email email : emailList) {
            ArrayList<Email.Tag> tags = email.getTags();
            for (Email.Tag tag : tags) {
                String tagName = tag.getKeywordPair().getKeyword();
                if (! emailTagList.contains(tagName)) {
                    emailTagList.add(tagName);
                }
            }
        }
        return emailTagList;
    }

    public static ArrayList<String> getEmailTagList() {
        return emailTagList;
    }

    /**
     * Construct a HashMap to store the index of emails under the category of tags.
     *
     * @param emailList emailList from model.
     * @return HashMap of tags with their associated emails.
     */
    public static HashMap<String, SubTagMap> updateTagMap(EmailList emailList) {
        updateEmailTagList(emailList);
        tagMap.clear();
        for (int index = 0; index < emailList.size(); index ++) {
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
     * @param index index of email
     * @param tags list of all tags in the email
     * @param rootTagName each tags in the email
     */
    public static void updateRootTagMap(int index, ArrayList<Email.Tag> tags, String rootTagName) {
        for (Email.Tag subTag : tags) {
            String subTagName = subTag.getKeywordPair().getKeyword();
            updateSubTagMap(index, rootTagName, subTagName);
        }
    }

    /**
     * Inserts the email at the under its tag category in the HashMap.
     *
     * @param index index of email
     * @param rootTagName root tag name
     * @param subTagName sub tag name
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
        if (!indexList.contains(index)) {
            indexList.add(index);
            subTagMap.put(subTagName, indexList);
            tagMap.put(rootTagName, subTagMap);
        }
    }

    /**
     * Display the tagged emails given the tagName.
     *
     * @param tags tag(s) input by users.
     * @return String of tagged emails.
     */
    public static String getTaggedEmailList(ArrayList<String> tags, EmailList emailList) {
        String responseMsg = "";

        // more than two input tags
        if (tags.size() > 2) {
            responseMsg = "[Input format error] Maximum of 2 tag names are allowed for email tag-"
                    + "searching.";
            return responseMsg;
        }

        // one input tag
        if (tags.size() == 1) {
            String tagName = tags.get(0);
            if (!tagMap.containsKey(tagName)) {
                responseMsg = "[Input content error] The tag #" + tagName + " does not exists.";
                return responseMsg;
            }
            responseMsg += displayRootEmailTag(tagName, emailList);
            return responseMsg;
        }

        // two input tags
        if (tags.size() == 2) {
            String tagNameOne = tags.get(0);
            String tagNameTwo = tags.get(1);
            // both tags do not exist
            if (!tagMap.containsKey(tagNameOne) && !tagMap.containsKey(tagNameTwo)) {
                responseMsg = "[Input content error] The tags #" + tagNameOne + " and #" + tagNameTwo
                        + " does not exists.";
                return responseMsg;
            }
            // first tag does not exist, returns only list of emails with the second tag
            if (!tagMap.containsKey(tagNameOne)) {
                responseMsg = "[Input content error] The tag #" + tagNameOne + " does not exists. ";
                responseMsg += "Here are the email(s) tagged with #" + tagNameTwo + ": " + System.lineSeparator()
                        + System.lineSeparator();
                ArrayList<Integer> indexList = tagMap.get(tagNameTwo).get(tagNameTwo);
                responseMsg += emailList.toString(indexList);
                return responseMsg;
            }
            // second tag does not exist, returns only list of emails with the first tag
            if (!tagMap.containsKey(tagNameTwo)) {
                responseMsg = "[Input content error] The tag #" + tagNameTwo + " does not exists. ";
                responseMsg += "Here are the email(s) tagged with #" + tagNameOne + ": "
                        + System.lineSeparator() + System.lineSeparator();
                ArrayList<Integer> indexList =  tagMap.get(tagNameOne).get(tagNameOne);
                responseMsg += emailList.toString(indexList);
                return responseMsg;
            }
            // both tags exist, but do not co-exist, returns list of emails with only the first tag and
            // second tag respectively
            if (!tagMap.get(tagNameOne).containsKey(tagNameTwo)) {
                responseMsg = "No email is tagged with both #" + tagNameOne + " and #" + tagNameTwo + ": "
                        + System.lineSeparator();
                responseMsg += System.lineSeparator() + "Here are the email(s) tagged with #" + tagNameOne
                        + ": " + System.lineSeparator() + System.lineSeparator();
                ArrayList<Integer> indexListOne =  tagMap.get(tagNameOne).get(tagNameOne);
                responseMsg += emailList.toString(indexListOne);
                responseMsg += System.lineSeparator() + System.lineSeparator() + "Here are the email(s) "
                        + "tagged with #" + tagNameTwo + ": " + System.lineSeparator() + System.lineSeparator();
                ArrayList<Integer> indexListTwo = tagMap.get(tagNameTwo).get(tagNameTwo);
                responseMsg += emailList.toString(indexListTwo);
                return responseMsg;
            }
            // both tags exist and co-exist, returns list of emails with both tags
            responseMsg = "Here are the email(s) tagged with both #" + tagNameOne + " and #" + tagNameTwo
                    + ": " + System.lineSeparator() + System.lineSeparator();
            ArrayList<Integer> indexList = tagMap.get(tagNameOne).get(tagNameTwo);
            responseMsg += emailList.toString(indexList);
        }
        return responseMsg;
    }

    /**
     * Displays list of emails with tags co-exist with a given a single tag name.
     *
     * @param tagName tagName input by user.
     * @return String of emails with tags co-exist with a given a single tagName.
     */
    public static String displayRootEmailTag(String tagName, EmailList emailList) {
        String responseMsg = "Here are the email(s) tagged with #" + tagName + ": " + System.lineSeparator()
                + System.lineSeparator();
        SubTagMap subTagMap = tagMap.get(tagName);
        // for each tag co-exist with the given tag name, display the list of emails with both tags
        for (HashMap.Entry<String, ArrayList<Integer>> entry : subTagMap.entrySet()) {
            String subTagName = entry.getKey();
            ArrayList<Integer> indexList = entry.getValue();
            responseMsg += "[" + subTagName + "]" + System.lineSeparator() + emailList.toString(indexList)
                    + System.lineSeparator();
        }
        return responseMsg;
    }

    public static class SubTagMap extends HashMap<String, ArrayList<Integer>> {
        private String subTagName;
        private ArrayList<Integer> indexList;

        public SubTagMap(String subTagName, ArrayList<Integer> indexList) {
            this.subTagName = subTagName;
            this.indexList = indexList;
        }
    }

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

}