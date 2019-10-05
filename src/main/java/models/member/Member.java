package models.member;

public class Member {
    private String name;
    private String phone;
    private String email;
    private int indexNumber;
    private int totalCredit;

    public Member (String name, String phone, String email, int indexNumber) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.totalCredit = 0;
        this.indexNumber = indexNumber;
    }

}
