public class Todo extends Task{

    public Todo(String name) {
        super(name);
    }

    String status = "T";

    public static Todo createToDo(String[] arr) throws EmptyParameterException{
        if (arr.length < 2) {
            throw new EmptyParameterException("ERROR: The description of todo cannot be empty\n" +
                    "    Please ensure the correct format is used: todo <Description>\n");
        }

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
