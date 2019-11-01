package duke.models.students;

public interface IStudent {

    void updateDetails(String name, String age, String address);

    int getIndexNumber();

    String getName();

    String getAge();

    String getAddress();

    String getDetails();

}
