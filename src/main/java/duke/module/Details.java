package duke.module;

public interface Details {

    String getDetails();

    void updateDetails(String name, String age, String address);

    void getIndexNumber();

    String getParticulars();

    String updateParticulars();

    void updateParticulars(String contact, String gender, String email, String DOB);

    String getPersonalBest();

    String getDietaryPlan();

    String getNotes();
}
