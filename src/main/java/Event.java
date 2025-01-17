public class Event extends Task{
    private String from;
    private String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]" + super.toString() + "(from: %s to: %s)", from, to);
    }
}
