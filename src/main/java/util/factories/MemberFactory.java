package util.factories;

import models.member.IMember;
import models.member.Member;
import models.member.NullMember;
import util.ParserHelper;
import util.ValidityHelper;

public class MemberFactory implements IArchDukeFactory<IMember> {
    private ParserHelper parserHelper;
    private ValidityHelper validityHelper;

    public MemberFactory() {
        this.parserHelper = new ParserHelper();
        this.validityHelper = new ValidityHelper();
    }

    /**
     * Method to create a new member.
     * @param input Input containing details of member to be added (name, phone, email).
     * @return Member with the relevant details. Index number is set later when adding to list.
     */
    public IMember create(String input) {
        String[] memberDetails = this.parserHelper.parseMemberDetails(input);
        boolean isNameCreated = false;
        String name = memberDetails[0];
        if (!("--").equals(name) && !("").equals(name)) {
            isNameCreated = true;
        }
        String phone = memberDetails[1];
        String email = memberDetails[2];
        int index = Integer.parseInt(memberDetails[3]);
        String role = memberDetails[4];
//        boolean invalidEmailFlag = !validityHelper.emailChecker(email) && !"--".equals(email);
//        boolean invalidPhoneFlag = !validityHelper.phoneChecker(phone) && !"--".equals(phone);
//        String error = "";
//        if (invalidPhoneFlag){
//            error += "Phone number is not a valid phone number! Please make sure the phone number "
//                    + "only has digits and a length of no more than 8 digits.";
//        }
//        if (invalidEmailFlag) {
//            String newLine = (!"".equals(error)) ? "\n\t" : "";
//            error += (newLine + "Email address is not a valid email address! Please adhere to standard "
//                    + "email address formats, such as archduke@emailprovider.com");
//        }
        String errorMessage = validityHelper.emailPhoneErrorMessage(email, phone);
        if (!"".equals(errorMessage)) {
            return new NullMember(errorMessage);
        } else if (!isNameCreated) {
            return new NullMember("Name cannot be empty! Please follow the add command format in user "
                                  + "guide! \"add member -n NAME\" is the minimum requirement for add member command");
        }
        return new Member(name, phone, email, index, role);
    }
}
