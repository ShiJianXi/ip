package chitchatbot.exception;

/**
 * Custom exception to be thrown when user tried to mark a task that is already marked
 */
public class AlreadyMarkedException extends Exception{
    public AlreadyMarkedException(String message) {
        super(message);
    }
}
