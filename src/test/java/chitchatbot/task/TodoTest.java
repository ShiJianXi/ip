package chitchatbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;

public class TodoTest {
    private Path path = Paths.get("data", "chat.txt");
    private Storage storage = new Storage(path);

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void createToDo_success() throws MissingParameterException {
        String[] inputArr = new String[]{"todo", "ToDoTest"};
        String actual = Todo.createToDo(inputArr, storage);
        String expected = "Got it. I've added this task:\n"
                + "  " + "[T][ ] ToDoTest" + "\n"
                + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.";

        assertEquals(expected, actual);
        String[] deleteInput = new String[]{"delete", String.valueOf(Task.getNoOfActivity())};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void createToDo_missingParameters_exceptionThrown() {
        try {
            String[] inputArr = new String[]{"todo"};
            String result = Todo.createToDo(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "Incorrect format:\n"
                    + "Please ensure the correct format is used: todo <Description>";
            assertEquals(expected, e.getMessage());
        }
    }
}
