package chitchatbot.exception;

/**
 * An exception that will be throw when the user's input has missing parameters
 */
public class MissingParameterException extends BotException {
    public MissingParameterException(String message) {
        super(message);
    }
}
