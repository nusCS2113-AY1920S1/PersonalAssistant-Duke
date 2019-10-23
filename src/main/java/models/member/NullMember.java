package models.member;

public class NullMember implements IMember {

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public void updateDetails(String name, String phone, String email) {
        /**
         * Empty method
         */
    }

    @Override
    public void setIndexNumber(int indexNumber) {
        /**
         * Empty method
         */
    }

    @Override
    public int getIndexNumber() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String setRole(String input) {
        return null;
    }

}
