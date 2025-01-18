import java.util.Arrays;
import java.util.StringJoiner;

public class Deadline extends Task{

    protected String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    public static Deadline createDeadline(String[] arr) throws MissingParameterException {
        if (arr.length < 2 || !Arrays.asList(arr).contains("/by") || arr[1].equals("/by")) {
            throw new MissingParameterException("    ERROR: There is missing parameters, " +
                    "please ensure the correct format is used:\n" +
                    "    deadline <Description> /by <Date/Time>\n");
        }
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
