
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ChitChatBot {
//    public static String indentation = "    ";

    public static void main(String[] args) throws MissingParameterException {

        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";

        //Get the path to create where the chat.txt supposed to be
        Path path = Paths.get("data", "chat.txt");
        Storage storage = new Storage(path);

        storage.initStorage();

        //Greet the user
        System.out.println(Ui.printChat(Ui.indentation + "Hello! I'm "
                + name + "\n"
                + Ui.indentation + "What can i do for you?" + "\n"));

        //Takes in user input
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");
            Action action = null;

            //Handle the case where the user entered invalid inputs
            try {
                action = Action.valueOf(inputArr[0]);
            } catch (IllegalArgumentException e) {
                System.out.println(Ui.printChat(Ui.indentation + "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                        + Ui.indentation + "Please use the correct queries:\n"
                        + Ui.indentation + "todo <description>\n"
                        + Ui.indentation + "deadline <description> /by <Date/Time>\n"
                        + Ui.indentation + "event <description> /from <Date/Time> /to <Date/Time>\n"
                        + Ui.indentation + "or list to show all the task\n"));
            }

            if (action == Action.bye) {

                System.out.println(Ui.printChat(Ui.indentation
                        + "Bye. Hope to see you again soon!\n"));
                break;

            } else if (action == Action.list) {

                storage.listTask();

            } else if (action == Action.mark) {

                Task.markAsDone(path, inputArr);

            } else if (action == Action.unmark) {

                Task.markAsNotDone(path, inputArr);

            } else if (action == Action.todo) {

                Todo.createToDo(inputArr, storage);

            } else if (action == Action.deadline) {

                Deadline.createDeadline(inputArr, storage);

            } else if (action == Action.event) {

                Event.createEvent(inputArr, storage);

            } else if (action == Action.delete) {

                Task.deleteTask(path, inputArr);

            }
        }
    }


//    //Format for printing of message
//    public static String printChat(String message) {
//        String line = "_____________________________________________________";
//        String indentation = "    ";
//        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
//    }

}
