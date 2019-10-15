package javacake.topics;

public class SubListTopic extends Topic {

    private String name;

    SubListTopic(String nameOfTopic) {
        name = nameOfTopic;
    }

    @Override
    public String getName() {
        return name;
    }
}
