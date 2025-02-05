package chitchatbot.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

/**
 * A class to deal with the Find command
 */
public class Find {
    private Storage storage;
    private File chatFile;

    /**
     * Constructs the Find object with the given storage.
     * This storage will then be used to access the txt file to get the data.
     *
     * @param storage
     * @see Storage
     */
    public Find(Storage storage) {
        this.storage = storage;
        this.chatFile = storage.getFile();
    }

    /**
     * Returns an ArrayList<String> that contains all the similar task as of the given words.
     * The given keywords will be in the form of ArrayList<String>.
     * <p>
     * An empty ArrayList<String> will be returned if no similar task within the txt file.
     * @param description The descriptions in the form of ArrayList<String>.
     * @return ArrayList<String> of all the similar task.
     */
    public ArrayList<String> findSimilarTask(ArrayList<String> description) {
        ArrayList<String> originalDescription = new ArrayList<>(List.copyOf(description));
        ArrayList<String> result = new ArrayList<>();
        try {
            Scanner sc = new Scanner(chatFile);
            while (sc.hasNext()) {
                String task = sc.nextLine();
                String[] taskArr = task.split(" ");
                for (int i = 1; i < taskArr.length; i++) {
                    if (description.contains(taskArr[i])) {
                        String text = taskArr[i];

                        description.remove(text);
                        if (description.isEmpty()) {
                            result.add(task);
                            break;
                        }
                    }
                }
                description = new ArrayList<>(originalDescription);
            }

            return result;
        } catch (FileNotFoundException e) {
            System.out.println("File not found error");
        }
        return result;
    }

    /**
     * Returns the String of all the similar task in the chat UI format
     * which will be printed to the user's screen.
     * <p>
     * A String in the chat UI format indicating no similar task found will be printed if no similar task
     * is found within the txt file.
     * @param inputArr The user's input in the form of String[]
     * @return A String showing all the similar task based on the keywords in the chat UI format.
     * @throws MissingParameterException If the user's input has mising parameters
     * @see Ui
     */
    public String executeFindCommand(String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2) {
            throw new MissingParameterException("Missing parameters error: Please ensure the correct parameters is used:\n"
                    + "find <keyword>");
        }

        StringJoiner result = new StringJoiner("\n");
        ArrayList<String> lookingFor = new ArrayList<>();

        for (int i = 1; i < inputArr.length; i++) {
            lookingFor.add(inputArr[i]);
        }

        ArrayList<String> similarTask = this.findSimilarTask(lookingFor);

        if (similarTask.isEmpty()) {
            return "No similar task found!";
        } else {
            for (int i = 0; i < similarTask.size(); i++) {
                result.add((i + 1) + "." + similarTask.get(i));
            }
            return result.toString();
        }
    }

}
