package chitchatbot.task;

import chitchatbot.exception.AlreadyMarkedException;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.ui.Ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Task {
    private String name;
    private boolean isDone;
    private int index;
    private static int noOfActivity = 0;


    public Task(String name) {

        this.name = name;
        this.isDone = false;
        noOfActivity++;
        this.index = noOfActivity;

    }

    public static int getNoOfActivity() {
        return noOfActivity;
    }

    public static void setNoOfActivity(int value) {
        noOfActivity = value;
    }


    public static String markAsDone(Path path, String[] inputArr) {
        String result = "";
        try {
            if (inputArr.length < 2) {
                throw new MissingParameterException(Ui.indentation + "ERROR: Missing parameters\n"
                        + Ui.indentation + "Please ensure the correct format is used: mark <Task Number>\n");
            }
            int index = Integer.parseInt(inputArr[1]) - 1;

            String text = Files.readAllLines(path).get(index);

            char[] charArr = text.toCharArray();
            if (charArr[4] == 'X') {
                throw new AlreadyMarkedException("    ERROR: This task is already marked as done\n");
            } else {
                charArr[4] = 'X';
            }
            String newString = String.valueOf(charArr);

            List<String> lines = Files.readAllLines(path);

            lines.set(index, newString);

            Files.write(path, lines);

//            System.out.println(Ui.printChat(Ui.indentation + "Nice! I've marked this task as done:\n"
//                    + Ui.indentation + "  " + newString + "\n"));
            result = Ui.printChat(Ui.indentation + "Nice! I've marked this task as done:\n"
                    + Ui.indentation + "  " + newString + "\n");
            return result;

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
        } catch (AlreadyMarkedException e) {
            System.out.println(Ui.printChat(e.getMessage()));
        } catch (IndexOutOfBoundsException e) {
            if (noOfActivity == 0) {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, no task in the list, " +
                        "please add task first\n"));
            } else if (noOfActivity == 1) {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, " +
                        "only 1 task in the list\n"));
            } else {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to mark, this task doesn't exist, " +
                        "please pick a task from 1 to "
                        + Task.getNoOfActivity() + " to mark.\n"));
            }
        } catch (NumberFormatException e) {
            System.out.println(Ui.printChat(Ui.indentation + "ERROR: " +
                    "Please enter the number of the task that you want to mark\n"));
        } catch (MissingParameterException e) {
            System.out.println(Ui.printChat(e.getMessage()));
        }
        return result;
    }

    public static void markAsNotDone(Path path, String[] inputArr) {
        try {
            if (inputArr.length < 2) {
                throw new MissingParameterException(Ui.indentation + "ERROR: Missing parameters\n"
                        + Ui.indentation + "Please ensure the correct format is used: " +
                        "unmark <Task Number>\n");
            }
            int index = Integer.parseInt(inputArr[1]) - 1;

            String text = Files.readAllLines(path).get(index);

            char[] charArr = text.toCharArray();
            if (charArr[4] == ' ') {
                throw new AlreadyMarkedException("    ERROR: This task is not yet marked as done\n");
            } else {
                charArr[4] = ' ';
            }
            String newString = String.valueOf(charArr);

            List<String> lines = Files.readAllLines(path);

            lines.set(index, newString);

            Files.write(path, lines);

            System.out.println(Ui.printChat(Ui.indentation + "OK, I've marked this task as not done yet:\n"
                    + Ui.indentation + "  " + newString + "\n"));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
        } catch (AlreadyMarkedException e) {
            System.out.println(Ui.printChat(e.getMessage()));
        } catch (IndexOutOfBoundsException e) {
            if (Task.getNoOfActivity() == 0) {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, no task in the list, " +
                        "please add and mark task first\n"));
            } else if (Task.getNoOfActivity() == 1) {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, " +
                        "only 1 task in the list\n"));
            } else {
                System.out.println(Ui.printChat(Ui.indentation + "Unable to unmark, This task doesn't exist, " +
                        "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.\n"));
            }
        } catch (NumberFormatException e) {
            System.out.println(Ui.printChat(Ui.indentation + "ERROR: " +
                    "Please enter the number of the task that you want to unmark\n"));
        } catch (MissingParameterException e) {
            System.out.println(Ui.printChat(e.getMessage()));
        }
    }


    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public int getIndex() {
        return this.index;
    }

    //A method to delete a task from the file
    public static void deleteTask(Path path, String[] inputArr) {
        try {
            if (inputArr.length > 2) {
                throw new MissingParameterException(Ui.printChat(Ui.indentation
                        + "ERROR: Incorrect format for delete queries:\n"
                        + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
            }

            int index = Integer.parseInt(inputArr[1]) - 1;

            List<String> lines = Files.readAllLines(path);
            String toRemove = lines.get(index);
            lines.remove(index);
            Files.write(path, lines);
            noOfActivity--;
            System.out.println(Ui.printChat(Ui.indentation + "Noted. I've removed this task:\n" +
                    Ui.indentation + "  " + toRemove + "\n"
                    + Ui.indentation + "Now you have " + Task.getNoOfActivity()
                    + " tasks in the list.\n"));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Ui.printChat(Ui.indentation + "ERROR: Missing parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(Ui.printChat(Ui.indentation + "ERROR: This task doesn't exist\n"
                    + Ui.indentation + "You can only delete an existing task\n"));
        } catch (NumberFormatException e) {
            System.out.println(Ui.printChat(Ui.indentation + "ERROR: Wrong parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n"));
        } catch (MissingParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        String string = "[%s] %s";
        return String.format(string, isDone ? "X" : " ", this.name);
    }
}
