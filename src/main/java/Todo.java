import java.io.File;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    String status = "T";

    //A method to create a todo task
    public static void createToDo(String[] inputArr, File file) {
        //Check if the user missed out the description of the test
        //Throw exception when required
        try {
            if (inputArr.length < 2) {
                throw new MissingParameterException("ERROR: The description of todo cannot be empty\n" +
                        "    Please ensure the correct format is used: todo <Description>\n");
            }

            String task = "";
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i].equals("/by")) {
                    break;
                }
                task += inputArr[i];
                task += " ";
            }

            Todo newTask = new Todo(task);
            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Got it. I've added this task:\n"
                    + ChitChatBot.indentation + "  " + newTask + "\n"
                    + ChitChatBot.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n"));
            //return newTask;
            ChitChatBot.appendToFile(newTask.toString(), file);
        } catch (MissingParameterException e) {
            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + e.getMessage()));
        }
    }

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}

