package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    String status = "T";

    //A method to create a todo task
    public static String createToDo(String[] inputArr, Storage storage) {
        //Check if the user missed out the description of the test
        //Throw exception when required
        String result = "";
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
//            System.out.println(Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
//                    + Ui.indentation + "  " + newTask + "\n"
//                    + Ui.indentation + "Now you have "
//                    + Task.getNoOfActivity() + " tasks in the list.\n"));
            result = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                    + Ui.indentation + "  " + newTask + "\n"
                    + Ui.indentation + "Now you have "
                    + Task.getNoOfActivity() + " tasks in the list.\n");

            storage.appendToFile(newTask.toString());
            return result;
            //chitchatbot.ChitChatBot.appendToFile(newTask.toString(), file);
        } catch (MissingParameterException e) {
            System.out.println(Ui.printChat(Ui.indentation + e.getMessage()));
        }
        return result;
    }

    @Override
    public String toString() {
        String string = String.format("[%s]%s", status, super.toString());
        return string;
    }
}

