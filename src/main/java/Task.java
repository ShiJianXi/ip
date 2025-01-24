import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public static void markAsDone(Path path, File file, int index) {
        try {
            Scanner sc = new Scanner(file);
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

            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Nice! I've marked this task as done:\n"
                    + ChitChatBot.indentation + "  " + newString + "\n"));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
        } catch (AlreadyMarkedException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        }
    }

    public static void markAsNotDone(Path path, File file, int index) {
        try {
            Scanner sc = new Scanner(file);
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

            System.out.println(ChitChatBot.printChat(ChitChatBot.indentation + "Nice! I've marked this task as not done yet:\n"
                    + ChitChatBot.indentation + "  " + newString + "\n"));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
        } catch (AlreadyMarkedException e) {
            System.out.println(ChitChatBot.printChat(e.getMessage()));
        }
    }


    public void toggleIsDone() {
        this.isDone = !isDone;
    }

    public int getIndex() {
        return this.index;
    }

    //A method to delete a task from the task array
    public static ArrayList<Task> deleteTask(ArrayList<Task> taskArr, int index) {
        taskArr.remove(index);
        noOfActivity--;
        return taskArr;
    }

    @Override
    public String toString() {
        String string = "[%s] %s";
        String toReturn = String.format(string, isDone == true ? "X" : " ", this.name);
        return toReturn;
    }
}
