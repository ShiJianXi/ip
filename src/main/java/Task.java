import java.util.ArrayList;

public class Task {
    private String name;
    private boolean isDone;
    private int index;
    private static int noOfActivity = 0;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
        noOfActivity++;
        this.index = noOfActivity;
    }

    public static int getNoOfActivity() {
        return noOfActivity;
    }

    public void markAsDone() throws AlreadyMarkedException {
        if (this.isDone) {
            throw new AlreadyMarkedException("    ERROR: This task is already marked as done\n");
        }
        this.isDone = true;
    }

    public void markAsNotDone() throws AlreadyMarkedException{
        if (this.isDone == false) {
            throw new AlreadyMarkedException("    ERROR: This task is not yet marked as done\n");
        }
        this.isDone = false;
    }

    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public int getIndex() {
        return this.index;
    }

    //A method to delete a task from the task array
    public static ArrayList<Task> deleteTask(ArrayList<Task> taskArr, int index) {
        taskArr.remove(index);
        noOfActivity--;
        return taskArr;
    }

    @Override
    public String toString() {
        String string = "[%s] %s";
        String toReturn = String.format(string, isDone == true ? "X" : " ", this.name);
        return toReturn;
    }
}
