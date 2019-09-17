package wallet.record;

public class Contact {

    private String name;
    private String detail;
    private String phoneNum;

    public Contact(String name, String detail, String phoneNum) {
        this.name = name;
        this.detail = detail;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

}
