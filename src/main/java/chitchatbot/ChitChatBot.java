package chitchatbot;

import chitchatbot.command.Parser;
import chitchatbot.storage.Storage;

import java.nio.file.Path;

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

    public String getBotResponse(Parser parser) {
        return parser.parseCommand();
    }

}
