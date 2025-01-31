import java.util.UUID;

public class ToDoList {
    String Title;
    int Priority;
    boolean Completed;
    UUID UUIDtask = UUID.randomUUID();
    public ToDoList(int priority, String Title) {
        if (priority == 0){
            this.Priority = 2;
        }else {
            this.Priority = priority;
        }
        this.Title = Title;

    }
    public void Completed() {
        this.Completed = true;
    }
    @Override
    public String toString() {
        String priorityText;
        switch (Priority) {
            case 1:
                priorityText = "High";
                break;
            case 2:
                priorityText = "Middle";
                break;
            case 3:
                priorityText = "Low";
                break;
            default:
                priorityText = "Unknown";
        }
        return "Title: " + Title + " | Priority: " + priorityText + " | UUID: " + UUIDtask.toString();
    }
}
