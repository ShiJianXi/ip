import java.util.StringJoiner;

public class Event extends Task{
    private String from;
    private String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    public static Event createEvent(String[] arr) {
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
