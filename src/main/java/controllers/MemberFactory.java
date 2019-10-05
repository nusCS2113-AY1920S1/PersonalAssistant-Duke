package controllers;

import models.member.Member;

public class MemberFactory {

    public static Member createMember(String input) {
        String name = "No name";
        String phone = "No phone number";
        String email = "No email address";

        int nameFlag = -1;

        String [] memberDetails = input.split(" ");
        for (int i = 0; i < memberDetails.length; i++) {
            String s = memberDetails[i];
            System.out.println(s);
            switch (s.substring(0,2)) {
                case "n/":
                    System.out.println(s.substring(2));
                    name = s.substring(2);
                    break;
                case "p/":
                    System.out.println(s.substring(2));
                    phone = s.substring(2);
                    break;
                case "e/":
                    email = s.substring(2);
                    break;
            }
        }
        return new Member(name, phone, email, 0);
    }
}
