public class Todo extends Task{

    public Todo(String name) {
        super(name);
    }

    String status = "T";

    public static Todo createToDo(String[] arr) {
        String task = "";
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].equals("/by")) {
                break;
            }
            task += arr[i];
            task += " ";
        }

        Todo newTask = new Todo(task);
        return newTask;
    }

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}
