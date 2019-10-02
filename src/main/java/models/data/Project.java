package models.data;

public class Project implements IProject {
    private String description;
    private String members;

    public Project(String description, String members) {
        this.description = description;
        this.members = members;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getMembers() {
        return this.members;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }
}
