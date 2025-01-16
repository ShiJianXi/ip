

public class Deadline extends Task{

    protected String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[%s]" + super.toString()
                + "(by: %s)", "D", this.by);
    }
}
