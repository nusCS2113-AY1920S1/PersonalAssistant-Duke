package controllers;

import models.member.Member;
import util.ParserUtil;

public class MemberFactoryUtil {
    private Member newMember;

    /**
     * Method to create a new member.
     * @param input Input containing details of member to be added (name, phone, email).
     * @param memberListSize Integer containing the current size of the member list of the project.
     * @return Member with the relevant details. Index number is set later when adding to list.
     */
    public boolean memberIsCreated(String input, int memberListSize) {
        String[] memberDetails = ParserUtil.memberCreation(input);
        boolean nameCreatedFlag = false;
        String name = memberDetails[0];
        if (!("No name").equals(name)) {
            nameCreatedFlag = true;
        }
        String phone = memberDetails[1];
        String email = memberDetails[2];
        int indexNumber = memberListSize + 1;
        if (nameCreatedFlag) {
            this.newMember = new Member(name, phone, email, indexNumber);
            return true;
        } else {
            return false;
        }
    }

    public Member getNewMember() {
        return this.newMember;
    }
}
