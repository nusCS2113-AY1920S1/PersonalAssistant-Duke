package seedu.duke.email;

import seedu.duke.email.entity.Email;

import java.util.ArrayList;
import java.util.HashMap;

public class EmailTags {

    private static HashMap<String, SubTagMap> tagMap = new HashMap<>();

    public EmailTags() {
        tagMap = new HashMap<>();
    }

    public static HashMap<String, SubTagMap> updateEmailTagList(EmailList emailList) {
        for (Email email : emailList) {
            ArrayList<Email.Tag> tags = email.getTags();
            for (Email.Tag rootTag : tags) {
                String rootTagName = rootTag.getKeywordPair().getKeyword();
                for (Email.Tag subTag : tags) {
                    String subTagName = subTag.getKeywordPair().getKeyword();

                    EmailList subEmailList = new EmailList();
                    SubTagMap subTagMap = new SubTagMap(subTagName, subEmailList);

                    if (tagMap.containsKey(rootTagName)) {
                        subTagMap = tagMap.get(rootTagName);
                    }
                    if (subTagMap.containsKey(subTagName)) {
                        subEmailList = subTagMap.get(rootTagName);
                    }

                    if (!subEmailList.contains(email)) {
                        subEmailList.add(email);
                        subTagMap.put(subTagName, subEmailList);
                        tagMap.put(rootTagName, subTagMap);
                    }
                }
            }
        }
        return tagMap;
    }

    public static String displayEmailTagList(ArrayList<String> tags) {
        String responseMsg = "";
        if (tags.size() > 2) {
            responseMsg = "[Input format error] Maximum of 2 tag names are allowed for email tag-"
                    + "searching.";
            return responseMsg;
        }

        if (tags.size() == 1) {
            String tagName = tags.get(0);
            if (!tagMap.containsKey(tagName)) {
                responseMsg = "[Input content error] The tag #" + tagName + " does not exists.";
                return responseMsg;
            }
            responseMsg += "Here is the email(s) tagged with #" + tagName + ": \n\n";
            EmailList emailList = tagMap.get(tagName).get(tagName);
            responseMsg += emailList.toString();
            return responseMsg;
        }

        if (tags.size() == 2) {
            String tagNameOne = tags.get(0);
            String tagNameTwo = tags.get(1);
            if (!tagMap.containsKey(tagNameOne) && !tagMap.containsKey(tagNameTwo)) {
                responseMsg = "[Input content error] The tags #" + tagNameOne + " and #" + tagNameTwo
                        + " does not exists.";
                return responseMsg;
            }
            if (!tagMap.containsKey(tagNameOne)) {
                responseMsg = "[Input content error] The tag #" + tagNameOne + " does not exists. ";
                responseMsg += "Here is the email(s) tagged with #" + tagNameTwo + ": \n\n";
                EmailList emailList = tagMap.get(tagNameTwo).get(tagNameTwo);
                responseMsg += emailList.toString();
                return responseMsg;
            }
            if (!tagMap.containsKey(tagNameTwo)) {
                responseMsg = "[Input content error] The tag #" + tagNameTwo + " does not exists. ";
                responseMsg += "Here is the email(s) tagged with #" + tagNameOne + ": \n\n";
                EmailList emailList = tagMap.get(tagNameOne).get(tagNameOne);
                responseMsg += emailList.toString();
                return responseMsg;
            }
            if (!tagMap.get(tagNameOne).containsKey(tagNameTwo)) {
                responseMsg = "No email is tagged with both #" + tagNameOne + " and #" + tagNameTwo + ": "
                        + "\n";
                responseMsg += "\nHere is the email(s) tagged with #" + tagNameOne + ": \n\n";
                EmailList emailListOne = tagMap.get(tagNameOne).get(tagNameOne);
                responseMsg += emailListOne.toString();
                responseMsg += "\n\nHere is the email(s) tagged with #" + tagNameTwo + ": \n\n";
                EmailList emailListTwo = tagMap.get(tagNameTwo).get(tagNameTwo);
                responseMsg += emailListTwo.toString();
                return responseMsg;
            }
            responseMsg = "Here is the email(s) tagged with both #" + tagNameOne + " and #" + tagNameTwo +
                    ": \n";
            EmailList emailList = tagMap.get(tagNameOne).get(tagNameTwo);
            responseMsg += emailList.toString();
        }
        return responseMsg;
    }

    public static class SubTagMap extends HashMap<String, EmailList> {

        public SubTagMap (String subTagName, EmailList subEmailList) {
        }
    }

}