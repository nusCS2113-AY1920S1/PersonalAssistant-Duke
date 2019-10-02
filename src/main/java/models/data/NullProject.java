package models.data;

public class NullProject implements IProject {
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getMembers() {
        return null;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }
}
