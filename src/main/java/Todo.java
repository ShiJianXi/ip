public class Todo extends Task{

    public Todo(String name) {
        super(name);
    }

    String status = "T";

    //A method to create a todo task
    public static Todo createToDo(String[] arr) throws MissingParameterException {
        //Check if the user missed out the description of the test
        //Throw exception when required
        if (arr.length < 2) {
            throw new MissingParameterException("ERROR: The description of todo cannot be empty\n" +
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
