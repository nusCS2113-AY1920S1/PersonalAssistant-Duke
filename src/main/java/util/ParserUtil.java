package util;

public class ParserUtil {
    /**
     *  Parses the string input to extract the name, phone number and email.
     * @param input Contains the name, phone number and email.
     * @return An array consisting on name in index 0, phone number in index 1 and email in index 2.
     */
    public static String[] parseMemberDetails(String input) {
        String[] memberDetails = new String[4];
        memberDetails[0] = "No name";
        memberDetails[1] = "No phone number";
        memberDetails[2] = "No email address";
        memberDetails[3] = "0";
        String[] tempInput = input.split(" ");
        for (String s : tempInput) {
            if (s.length() >= 2) {
                switch (s.substring(0, 2)) {
                case "n/":
                    memberDetails[0] = s.substring(2);
                    break;
                case "i/":
                    memberDetails[1] = s.substring(2);
                    break;
                case "e/":
                    memberDetails[2] = s.substring(2);
                    break;
                case "x/":
                    memberDetails[3] = s.substring(2);
                    break;
                default:
                    break;
                }
            }
        }
        return memberDetails;
    }
}
