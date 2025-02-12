package chitchatbot.storage;

import chitchatbot.task.Task;
import chitchatbot.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * A class that deals with the storage for that chat.txt
 */
public class Storage {
    private Path path;
    private File chatFile;


    /**
     * Constructs a storage class with the given relative path.
     *
     * @param path relative path where the chatbot.txt will be stored.
     */
    public Storage(Path path) {
        this.path = path;
        this.chatFile = new File(String.valueOf(path));
        boolean fileExist = this.chatFile.exists();
        assert fileExist == true : "File should exist";
    }

    /**
     * Initialises the chatbot.txt to read through all the text in the txt file
     * and reinitialise the number of task based on the number of activities in the txt file.
     *
     * @see Task
     */
    public void initStorage() {
        if (!chatFile.exists()) {
            tryCreateFile();
        } else {
            tryInitializeNumberOfActivities();
        }
    }

    private void tryInitializeNumberOfActivities() {
        try {
            int noOfActivity = Files.readAllLines(path).size();
            Task.setNoOfActivity(noOfActivity);
        } catch (IOException e) {
            System.out.println("An error occurred, unable to read file");
        }
    }

    private void tryCreateFile() {
        try {
            Files.createDirectories(path.getParent());
            chatFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred, unable to create file");
        }
    }

    /**
     * Append the given String message into the chatbot.txt file.
     *
     * @param message A String message to be appended into the file
     */
    public void appendToFile(String message) {
        writeMessageToFile(message);
    }

    private void writeMessageToFile(String message) {
        try {
            FileWriter fw = new FileWriter(chatFile, true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("An Error occurred, unable to write");
        }
    }

    /**
     * Returns a String that list all the activities within the chatbot.txt
     * and print to the user's screen in the form a chat UI.
     *
     * @see Ui
     */
    public String listTask() {
        try {
            return stringOfListOfTaskFromFile();
        } catch (FileNotFoundException e) {
            return "File error: File not found";
        }
    }

    private String stringOfListOfTaskFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(chatFile);
        StringJoiner toPrint = new StringJoiner("\n");
        int index = 0;
        while (scanner.hasNext()) {
            index++;
            String text = scanner.nextLine();
            text = index + "." + text;
            toPrint.add(text);
        }
        if (index == 0) {
            return "No task currently!";
        }
        return toPrint.toString();
    }

    /**
     * Return the chatbot.txt file.
     * This file contains the list of tasks that the user inputs previously.
     *
     * @return File where the text is stored at.
     */
    public File getFile() {
        return this.chatFile;
    }

    /**
     * Return the path where the chatbot.txt is stored at.
     *
     * @return Path, the path of the chatbot.txt
     */
    public Path getPath() {
        return this.path;
    }

}
