package UserCode.Tasks;

public class TemplateTask {
    String taskType;
    String condition;
    String action;

    public TemplateTask(String t, String c, String a) {
        this.taskType = t;
        this.condition = c;
        this.action = a;
    }

    public String getTaskType() { return this.taskType; }

    public String getCondition() { return this.condition; }

    public String getAction() { return this.action; }
}
