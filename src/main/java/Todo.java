public class Todo extends Task{

    public Todo(String name) {
        super(name);
    }
    String status = "T";

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}
