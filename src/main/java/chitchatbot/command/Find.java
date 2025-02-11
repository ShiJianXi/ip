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
     * A task is considered similar as long as it contains all the key-words
     * The given keywords will be in the form of ArrayList<String>.
     * <p>
     * An empty ArrayList<String> will be returned if no similar task within the txt file.
     * @param descriptions The descriptions in the form of ArrayList<String>.
     * @return ArrayList<String> of all the similar task.
     */
    public ArrayList<String> findSimilarTask(ArrayList<String> descriptions) {
        ArrayList<String> originalDescription = new ArrayList<>(List.copyOf(descriptions));
        ArrayList<String> result = new ArrayList<>();
        return getListOfTaskWithKeyWord(descriptions, result, originalDescription);
    }

    private ArrayList<String> getListOfTaskWithKeyWord(ArrayList<String> descriptions,
                                                       ArrayList<String> result,
                                                       ArrayList<String> originalDescription) {
        try {
            scanChatFile(descriptions, result, originalDescription);
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("File not found error");
        }
        return result;
    }

    private void scanChatFile(ArrayList<String> descriptions,
                              ArrayList<String> result,
                              ArrayList<String> originalDescription) throws FileNotFoundException {
        Scanner sc = new Scanner(chatFile);
        while (sc.hasNext()) {
            String task = sc.nextLine();
            String[] taskArr = task.split(" ");
            findKeyWordInTaskArr(descriptions, taskArr, result, task);
            descriptions = new ArrayList<>(originalDescription);
        }
    }

    private static void findKeyWordInTaskArr(ArrayList<String> descriptions,
                                             String[] taskArr,
                                             ArrayList<String> result,
                                             String task) {
        for (int i = 1; i < taskArr.length; i++) {
            if (containAllKeyWords(descriptions, taskArr, result, task, i)) break;
        }
    }

    private static boolean containAllKeyWords(ArrayList<String> descriptions, String[] taskArr,
                                              ArrayList<String> result, String task, int i) {
        if (descriptions.contains(taskArr[i])) {
            String text = taskArr[i];

            descriptions.remove(text);
            if (descriptions.isEmpty()) {
                result.add(task);
                return true;
            }
        }
        return false;
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
        checkInputArrLength(inputArr);
        ArrayList<String> lookingFor = addKeyWordsIntoList(inputArr);
        ArrayList<String> similarTask = this.findSimilarTask(lookingFor);
        return parseSimilarTaskIntoString(similarTask);
    }

    private static String parseSimilarTaskIntoString(ArrayList<String> similarTask) {
        StringJoiner result = new StringJoiner("\n");
        if (similarTask.isEmpty()) {
            return "No similar task found!";
        } else {
            for (int i = 0; i < similarTask.size(); i++) {
                result.add((i + 1) + "." + similarTask.get(i));
            }
            return result.toString();
        }
    }

    private static ArrayList<String> addKeyWordsIntoList(String[] inputArr) {
        ArrayList<String> lookingFor = new ArrayList<>();

        for (int i = 1; i < inputArr.length; i++) {
            lookingFor.add(inputArr[i]);
        }
        return lookingFor;
    }

    private static void checkInputArrLength(String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2) {
            throw new MissingParameterException("Missing parameters error: Please ensure the correct parameters is used:\n"
                    + "find <keyword>");
        }
    }

}
