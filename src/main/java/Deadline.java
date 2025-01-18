import java.util.StringJoiner;

public class Deadline extends Task{

    protected String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    public static Deadline createDeadline(String[] arr) {
        String task = "";
        int byIndex = 0;
        StringJoiner by = new StringJoiner(" ");
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].equals("/by")) {
                byIndex = i;
                break;
            }
            task += arr[i];
            task += " ";
        }
        for (int i = byIndex + 1; i < arr.length; i++) {
            by.add(arr[i]);
        }

        Deadline newTask = new Deadline(task, by.toString());
        return newTask;
    }

    @Override
    public String toString() {
        return String.format("[%s]" + super.toString()
                + "(by: %s)", "D", this.by);
    }
}
