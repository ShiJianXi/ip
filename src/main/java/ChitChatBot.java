
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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String name = "ChitChatBot";

        ArrayList<Task> Tasks = new ArrayList<>();

        //Get the path to create where the chat.txt supposed to be
        Path path = Paths.get("data","chat.txt");
        boolean fileExist = Files.exists(path);

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
                System.out.println(Task.getNoOfActivity());
            } catch (IOException e) {
                System.out.println("An error occurred, unable to read file");;
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
            } catch (IllegalArgumentException e){
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
                try {
                    //Handle the case whereby the user did not enter a task number to mark
                    if (inputArr.length < 2) {
                        throw new MissingParameterException(indentation + "ERROR: Missing parameters\n"
                                + indentation + "Please ensure the correct format is used: mark <Task Number>\n");
                    }

                    int index = Integer.parseInt(inputArr[1]) - 1;
                    Task.markAsDone(path, chatFile, index);

                } catch (IndexOutOfBoundsException e) {
                    if (Task.getNoOfActivity() == 0) {
                        System.out.println(printChat(indentation + "Unable to mark, no task in the list, " +
                                "please add task first\n"));
                    } else if (Task.getNoOfActivity() == 1) {
                        System.out.println(printChat(indentation + "Unable to mark, this task doesn't exist, " +
                                "only 1 task in the list\n"));
                    } else {
                        System.out.println(printChat(indentation + "Unable to mark, this task doesn't exist, " +
                                "please pick a task from 1 to "
                                + Task.getNoOfActivity() + " to mark.\n"));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(printChat(indentation + "ERROR: " +
                            "Please enter the number of the task that you want to mark\n"));
                } catch (MissingParameterException e) {
                    System.out.println(printChat(e.getMessage()));
                }

            } else if (action == Action.unmark) {

                try {
                    //Handle the case whereby the user did not enter a task number to unmark
                    if (inputArr.length < 2) {
                        throw new MissingParameterException(indentation + "ERROR: Missing parameters\n"
                                + indentation + "Please ensure the correct format is used: " +
                                "unmark <Task Number>\n");
                    }
                    int index = Integer.parseInt(inputArr[1]) - 1;
                    Task.markAsNotDone(path, chatFile, index);

                } catch (IndexOutOfBoundsException e) {
                    if (Task.getNoOfActivity() == 0) {
                        System.out.println(printChat(indentation + "Unable to unmark, no task in the list, " +
                                "please add and mark task first\n"));
                    } else if (Task.getNoOfActivity() == 1) {
                        System.out.println(printChat(indentation + "Unable to unmark, This task doesn't exist, " +
                                "only 1 task in the list\n"));
                    } else {
                        System.out.println(printChat(indentation + "Unable to unmark, This task doesn't exist, " +
                                "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.\n"));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(printChat(indentation + "ERROR: " +
                            "Please enter the number of the task that you want to unmark\n"));
                } catch (MissingParameterException e) {
                    System.out.println(printChat(e.getMessage()));
                }

            } else if (action == Action.todo) {

                try {
                    Task newTask = Todo.createToDo(inputArr);
                    Tasks.add(newTask);

                    int noOfTasks = Task.getNoOfActivity();
                    System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                            + indentation + "  " + newTask + "\n"
                            + indentation + "Now you have "
                            + noOfTasks + " tasks in the list.\n"));

                    //Append to chat.txt
                    appendToFile(newTask.toString(), chatFile);

                } catch (MissingParameterException e) {
                    System.out.println(printChat(indentation + e.getMessage()));
                }

            } else if (action == Action.deadline) {
                try {
                    Task newTask = Deadline.createDeadline(inputArr);
                    Tasks.add(newTask);
                    int noOfTasks = Task.getNoOfActivity();
                    System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                            + indentation + "  " + newTask
                            + "\n" + indentation + "Now you have "
                            + noOfTasks + " tasks in the list.\n"));

                    //Append deadline task to chat.txt
                    appendToFile(newTask.toString(), chatFile);
                } catch (MissingParameterException e) {
                    System.out.println(printChat(e.getMessage()));
                }


            } else if (action == Action.event) {
                try {
                    Task newTask = Event.createEvent(inputArr);
                    Tasks.add(newTask);
                    int noOfTasks = Task.getNoOfActivity();
                    System.out.println(printChat(indentation + "Got it. I've added this task:\n"
                            + indentation + "  " + newTask + "\n"
                            + indentation + "Now you have "
                            + noOfTasks + " tasks in the list.\n"));

                    //Append event task to chat.txt
                    appendToFile(newTask.toString(), chatFile);

                } catch (MissingParameterException e) {
                    System.out.println(printChat(e.getMessage()));
                }
            } else if (action == Action.delete) {

                try {
                    if (inputArr.length > 2) {
                        throw new MissingParameterException(printChat(indentation
                                + "ERROR: Incorrect format for delete queries:\n"
                                + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                    }
                    int index = Integer.parseInt(inputArr[1]) - 1;
                    Task toRemove = Tasks.get(index);
                    Task.deleteTask(Tasks, index);
                    System.out.println(printChat(indentation + "Noted. I've removed this task:\n" +
                            indentation + "  " + toRemove + "\n"
                            + indentation + "Now you have " + Task.getNoOfActivity()
                            + " tasks in the list.\n"));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(printChat(indentation + "ERROR: Missing parameters\n"
                            + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(printChat(indentation + "ERROR: This task doesn't exist\n"
                            + indentation + "You can only delete an existing task\n"));
                } catch (NumberFormatException e) {
                    System.out.println(printChat(indentation + "ERROR: Wrong parameters\n"
                            + indentation + "Please ensure the correct format is used: delete <Task number>\n"));
                } catch (MissingParameterException e){
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
    private static void appendToFile(String message, File file) {
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


//    private static void unmarkTask(Path path, File file, int index) {
//        try {
//            Scanner sc = new Scanner(file);
//            String text = Files.readAllLines(path).get(index);
//
//            char[] charArr = text.toCharArray();
//            if (charArr[4] == ' ') {
//                throw new AlreadyMarkedException("    ERROR: This task is not yet marked as done\n");
//            } else {
//                charArr[4] = ' ';
//            }
//            String newString = String.valueOf(charArr);
//
//            List<String> lines = Files.readAllLines(path);
//
//            lines.set(index, newString);
//
//            Files.write(path, lines);
//
//            System.out.println(printChat(indentation + "Nice! I've marked this task as not done yet:\n"
//                    + indentation + "  " + newString + "\n"));
//
//        } catch (FileNotFoundException e) {
//            System.out.println("ERROR: File not found");
//        } catch (IOException e) {
//            System.out.println("ERROR: Unable to read file");
//        } catch (AlreadyMarkedException e) {
//            System.out.println(printChat(e.getMessage()));
//        }
//    }
}
