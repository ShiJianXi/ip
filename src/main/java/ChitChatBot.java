
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ChitChatBot {
    public static String indentation = "    ";

    public static void main(String[] args) throws MissingParameterException {

        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";

        ArrayList<Task> Tasks = new ArrayList<>();

        //Get the path to create where the chat.txt supposed to be
        Path path = Paths.get("data", "chat.txt");

        File chatFile = new File(String.valueOf(path));

        //If chat.txt does not exist, create the file
        if (!chatFile.exists()) {
            try {
                Files.createDirectories(path.getParent());
                chatFile.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred, unable to create file");
            }
        } else {
            try {
                int noOfActivity = Files.readAllLines(path).size();
                Task.setNoOfActivity(noOfActivity);
            } catch (IOException e) {
                System.out.println("An error occurred, unable to read file");
                ;
            }
        }


        //Greet the user
        System.out.println(printChat(indentation + "Hello! I'm "
                + name + "\n"
                + indentation + "What can i do for you?" + "\n"));

        //Takes in user input
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] inputArr = input.split(" ");
            Action action = null;

            //Handle the case where the user entered invalid inputs
            try {
                action = Action.valueOf(inputArr[0]);
            } catch (IllegalArgumentException e) {
                System.out.println(printChat(indentation + "OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                        + indentation + "Please use the correct queries:\n"
                        + indentation + "todo <description>\n"
                        + indentation + "deadline <description> /by <Date/Time>\n"
                        + indentation + "event <description> /from <Date/Time> /to <Date/Time>\n"
                        + indentation + "or list to show all the task\n"));
            }

            if (action == Action.bye) {

                System.out.println(printChat(indentation
                        + "Bye. Hope to see you again soon!\n"));
                break;

            } else if (action == Action.list) {

                listTask(chatFile);

            } else if (action == Action.mark) {

                Task.markAsDone(path, chatFile, inputArr);

            } else if (action == Action.unmark) {

                Task.markAsNotDone(path, chatFile, inputArr);

            } else if (action == Action.todo) {

                Todo.createToDo(inputArr, chatFile);

            } else if (action == Action.deadline) {

                Deadline.createDeadline(inputArr, chatFile);

            } else if (action == Action.event) {

                Event.createEvent(inputArr, chatFile);

            } else if (action == Action.delete) {

                try {
                    if (inputArr.length > 2) {
                        throw new MissingParameterException(printChat(indentation
                                + "ERROR: Incorrect format for delete queries:\n"
                                + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                    }

                    int index = Integer.parseInt(inputArr[1]) - 1;
                    Task.deleteTask(path, chatFile, index);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(printChat(indentation + "ERROR: Missing parameters\n"
                            + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(printChat(indentation + "ERROR: This task doesn't exist\n"
                            + indentation + "You can only delete an existing task\n"));
                } catch (NumberFormatException e) {
                    System.out.println(printChat(indentation + "ERROR: Wrong parameters\n"
                            + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                } catch (MissingParameterException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    //Format for printing of message
    public static String printChat(String message) {
        String line = "_____________________________________________________";
        String indentation = "    ";
        return String.format(indentation + line + "\n" + "%s" + indentation + line, message);
    }

    //A method to append to a file
    public static void appendToFile(String message, File file) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("An Error occurred, unable to write");
        }
    }

    private static void listTask(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringJoiner toPrint = new StringJoiner("\n");
            int index = 0;
            while (scanner.hasNext()) {
                index++;
                String text = scanner.nextLine();
                text = "    " + index + "." + text;
                toPrint.add(text);
            }

            System.out.println(printChat(toPrint + "\n"));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        }
    }

}
