package chitchatbot.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

import chitchatbot.exception.BotException;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.task.Task;

/**
 * Handle undo command that allows user to undo,
 * the previous command that modified the data in the file.
 */
public class Undo {
    private static int oldLen = 0;
    private static int newLen = 0;
    private Storage storage;
    private String[] inputArr;

    /**
     * Constructs a new Undo class.
     * @param storage The storage that the chat.txt is stored.
     * @param inputArr The user input.
     */
    public Undo(Storage storage, String[] inputArr) {
        this.storage = storage;
        this.inputArr = inputArr;
        newLen = Parser.getNumberOfPreviousCommands();
    }

    /**
     * Executes the undo command which undo the previous command,
     * that modified the chat.txt file.
     * @return A String to be printed to the user.
     * @throws BotException When the user input is incorrect or the user tries to undo multiple times.
     */
    public String executeUndo() throws BotException {
        checkCondition();
        try {
            String[] previousCommand = Parser.getPreviousCommand();
            String commandType = previousCommand[0];

            boolean isAddTask = commandType.equals("event")
                    || commandType.equals("deadline")
                    || commandType.equals("todo");
            boolean isDelete = commandType.equals("delete");
            boolean isMark = commandType.equals("mark");
            boolean isUnmark = commandType.equals("unmark");

            if (isAddTask) {
                undoAddTask();
            }

            if (isMark) {
                undoMarkCommand(previousCommand);
            }

            if (isUnmark) {
                undoUnmarkCommand(previousCommand);
            }

            if (isDelete) {
                undoDeleteCommand(previousCommand);
            }

            oldLen = newLen;
            return "Undo previous command: " + undoMessage(previousCommand);
        } catch (BotException e) {
            return e.getMessage();
        }
    }

    private void undoDeleteCommand(String[] previousCommand) {
        String previouslyDeleteTask = Task.getPreviouslyDeletedTask();
        int index = Integer.parseInt(previousCommand[1]);
        storage.appendToAnyPlaceInFile(previouslyDeleteTask, index);
    }

    private void undoUnmarkCommand(String[] previousCommand) {
        int index = Integer.parseInt(previousCommand[1]) - 1;
        Task.undoMarkUnmark(storage.getPath(), index, 2);
    }

    private void undoMarkCommand(String[] previousCommand) {
        int index = Integer.parseInt(previousCommand[1]) - 1;
        Task.undoMarkUnmark(storage.getPath(), index, 1);
    }

    private void undoAddTask() {
        try {
            int index = Task.getNoOfActivity() - 1;
            Path path = storage.getPath();
            List<String> lines = Files.readAllLines(path);
            Task.removeTaskFromChatFile(path, lines, index);
        } catch (IOException e) {
            System.out.println("Unable to read file");
        }
    }

    private String undoMessage(String[] previousCommand) {
        StringJoiner message = new StringJoiner(" ");
        for (String string : previousCommand) {
            message.add(string);
        }
        return message.toString();
    }

    private void checkCondition() throws BotException {
        if (this.inputArr.length > 1) {
            throw new MissingParameterException("Incorrect format: "
                    + "Please ensure the correct format is used Undo");
        }

        if (!(newLen > oldLen)) {
            throw new BotException("Only can undo the latest command that changed the data");
        }
    }
}
