package util.factories;

import models.member.IMember;
import models.member.Member;
import models.member.NullMember;
import util.ParserHelper;

public class MemberFactory implements IArchDukeFactory<IMember> {
    private ParserHelper parserHelper;

    public MemberFactory() {
        this.parserHelper = new ParserHelper();
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
        if (!("--").equals(name)) {
            isNameCreated = true;
        }
        String phone = memberDetails[1];
        String email = memberDetails[2];
        int index = Integer.parseInt(memberDetails[3]);
        String role = memberDetails[4];
        if (isNameCreated) {
            return new Member(name, phone, email, index, role);
        } else {
            return new NullMember();
        }
    }
}
