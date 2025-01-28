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

public class Find {
    private Storage storage;
    private File chatFile;
    public Find(Storage storage) {
        this.storage = storage;
        this.chatFile = storage.getFile();
    }

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

    public String executeFindCommand(String[] inputArr) throws MissingParameterException {
        if (inputArr.length < 2) {
            throw new MissingParameterException(Ui.printChat(Ui.indentation
                    + "Missing parameters error: Please ensure the correct parameters is used:\n"
                    + Ui.indentation + "find <keyword>\n"));
        }
        StringJoiner result = new StringJoiner("\n");
        ArrayList<String> lookingFor = new ArrayList<>();
        for (int i = 1; i < inputArr.length; i++) {
            lookingFor.add(inputArr[i]);
        }
        ArrayList<String> similarTask = this.findSimilarTask(lookingFor);

        if (similarTask.isEmpty()) {
            result.add(Ui.indentation + "No similar task found!\n");
            return result.toString();
        } else {
            for (int i = 0; i < similarTask.size(); i++) {
                if (i == similarTask.size() - 1) {
                    result.add(Ui.indentation + (i + 1) + "." + similarTask.get(i) + "\n");
                } else {
                    result.add(Ui.indentation + (i + 1) + "." + similarTask.get(i));
                }
            }
            return result.toString();
        }
    }

}
