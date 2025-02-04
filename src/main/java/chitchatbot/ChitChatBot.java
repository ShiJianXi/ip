package chitchatbot;

import chitchatbot.command.Parser;
import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * chitchatbot.Main program that runs the chitchatbot
 */
public class ChitChatBot {
    private Path path;
    private Storage storage;

    /**
     * Constructs the ChitChatBot with the given path.
     * Will initialise the storage during execution
     * to ensure that the file will be created if it is missing
     * and if the file exist, the number of task will be reinitialised correctly.
     *
     * @param path The relative path where the txt file will be stored at.
     * @see Storage
     */
    public ChitChatBot(Path path) {
        this.path = path;
        this.storage = new Storage(path);
        storage.initStorage();
    }

    /**
     * Runs the ChitChatBot to start parsing the user's input
     * to execute the task based on the user's input.
     *
     * @see Ui
     * @see Parser
     */
//    public void run() {
//        Scanner sc = new Scanner(System.in);
//        Ui.greetUser();
//        while (sc.hasNext()) {
//            String input = sc.nextLine();
//            String[] inputArr = input.split(" ");
//
//            Parser parser = new Parser(inputArr, storage);
//            parser.parseCommand();
//        }
//    }

//    public static void main(String[] args) throws MissingParameterException {
//
//        //Get the path to create where the chat.txt supposed to be
//        Path path = Paths.get("data", "chat.txt");
//        ChitChatBot chitChatBot = new ChitChatBot(path);
//        chitChatBot.run();
//
//    }

    public String getResponse(Parser parser) {
        return parser.parseCommand();
    }

}
