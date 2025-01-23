import java.util.Arrays;
import java.util.StringJoiner;

public class Event extends Task{
    private String from;
    private String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    //A method to create a event task
    public static Event createEvent(String[] arr) throws MissingParameterException{
        //Check for the various exceptions and  throw an exception when required
        if (arr.length < 2 || !Arrays.asList(arr).contains("/from")
                || !Arrays.asList(arr).contains("/to")
                || arr[1].equals("/from") || arr[1].equals("/to")
                || Arrays.asList(arr).indexOf("/from") > Arrays.asList(arr).indexOf("/to")) {
            throw new MissingParameterException("    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from <Date/Time> /to <Date/Time>\n");
        }
        String task = "";
        int fromIndex = 0;
        int toIndex = 0;
        StringJoiner from = new StringJoiner(" ");
        StringJoiner to = new StringJoiner(" ");
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].equals("/from")) {
                fromIndex = i;
                break;
            }

            task += arr[i];
            task += " ";
        }
        for (int i = fromIndex + 1; i < arr.length; i++) {
            if (arr[i].equals("/to")) {
                toIndex = i;
                break;
            }
            from.add(arr[i]);
        }
        for (int i = toIndex + 1; i < arr.length; i++) {
            to.add(arr[i]);
        }

        Event newTask = new Event(task, from.toString(), to.toString());
        return newTask;
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + "(from: %s to: %s)", from, to);
    }
}
