package javacake.topics;

public abstract class Topic {
    private String name;

    public Topic(String name) {
        this.name = name;
    }
    public abstract String getName();
}
