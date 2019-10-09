package util.constant;

public class SubstringParser {
    /**
     *  Parses the string input to extract the name, phone number and email.
     * @param input Contains the name, phone number and email.
     * @return An array consisting on name in index 0, phone number in index 1 and email in index 2.
     */
    public static String[] memberCreation(String input) {
        String[] memberDetails = new String[3];
        memberDetails[0] = "No name";
        memberDetails[1] = "No phone number";
        memberDetails[2] = "No email address";
        String[] tempInput = input.split(" ");
        for (int i = 0; i < tempInput.length; i++) {
            String s = tempInput[i];
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
            default:
                break;
            }
        }
        return memberDetails;
    }
}
