package javacake.topics;

public class MainListTopic extends Topic {

    private String name;

    MainListTopic(String nameOfTopic) {
        name = nameOfTopic;
    }

    @Override
    public  String getName() {
        return name;
    }

}
