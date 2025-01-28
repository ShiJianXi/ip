package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TodoTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void createToDo_success() throws MissingParameterException {
        String[] inputArr = new String[] {"todo", "ToDoTest"};
        String actual = Todo.createToDo(inputArr, storage);
        String expected = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + "[T][ ] ToDoTest " + "\n"
                + Ui.indentation + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.\n");

        assertEquals(expected, actual);
        String[] deleteInput = new String[] {"delete", String.valueOf(Task.getNoOfActivity())};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void createToDo_missingParameters_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"todo"};
            String result = Todo.createToDo(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "Missing parameters error: The description of todo cannot be empty\n" +
                    "    Please ensure the correct format is used: todo <Description>\n";
            assertEquals(expected, e.getMessage());
        }
    }
}
