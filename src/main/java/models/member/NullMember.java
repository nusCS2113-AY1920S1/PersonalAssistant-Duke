package models.member;

public class NullMember implements IMember {

    @Override
    public String getDetails() {
        return null;
    }

    @Override
    public void updateDetails(String name, String phone, String email) {

    }

    @Override
    public void setIndexNumber(int indexNumber) {

    }

    @Override
    public int getIndexNumber() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
