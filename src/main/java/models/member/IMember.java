package models.member;

public interface IMember {
    String getDetails();

    void updateDetails(String name, String phone, String email);

    void setIndexNumber(int indexNumber);

    int getIndexNumber();

    String getName();

    String getPhone();

    String getEmail();
}
