package chitchatbot.command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.task.Task;
import chitchatbot.task.Todo;
import chitchatbot.ui.Ui;

public class FindTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void findSimilarTask_success() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "find"};
        Todo.createToDo(taskInput, storage);
        int startingIndex = Task.getNoOfActivity();
        taskInput = new String[] {"todo", "find test"};
        Todo.createToDo(taskInput, storage);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("[T][ ] find");
        expected.add("[T][ ] find test");
        Find find = new Find(storage);
        ArrayList<String> similarTaskAL = new ArrayList<>();
        similarTaskAL.add("find");
        assertEquals(expected, find.findSimilarTask(similarTaskAL));
        String[] deleteInput = new String[] {"delete", String.valueOf(startingIndex)};
        Task.deleteTask(path, deleteInput);
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void findSimilarTask_noSimilarTask() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "find"};
        Todo.createToDo(taskInput, storage);
        int startingIndex = Task.getNoOfActivity();
        ArrayList<String> expected = new ArrayList<>();
        Find find = new Find(storage);
        ArrayList<String> similarTaskAL = new ArrayList<>();
        similarTaskAL.add("finding");
        assertEquals(expected, find.findSimilarTask(similarTaskAL));
        String[] deleteInput = new String[] {"delete", String.valueOf(startingIndex)};
        Task.deleteTask(path,deleteInput);
    }

    @Test
    public void executeFindCommand_success() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "find"};
        Todo.createToDo(taskInput, storage);
        int startingIndex = Task.getNoOfActivity();
        taskInput = new String[] {"todo", "find test"};
        Todo.createToDo(taskInput, storage);
        String expected ="1.[T][ ] find\n"
                + "2.[T][ ] find test";
        Find find = new Find(storage);
        String[] inputArr = new String[] {"find", "find"};
        assertEquals(expected, find.executeFindCommand(inputArr));
        String[] deleteInput = new String[] {"delete", String.valueOf(startingIndex)};
        Task.deleteTask(path, deleteInput);
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void executeFindCommand_missingParameters_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"find"};
            Find find = new Find(storage);
            find.executeFindCommand(inputArr);
            fail();
        } catch (MissingParameterException e) {
            String expected = "Missing parameters error: Please ensure the correct parameters is used:\n"
                    + "find <keyword>";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void executeFindCommand_noSimilarTask() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "find test"};
        Todo.createToDo(taskInput, storage);
        int startingIndex = Task.getNoOfActivity();
        String expected = "No similar task found!";
        Find find = new Find(storage);
        String[] inputArr = new String[] {"todo", "cannotbefound"};
        assertEquals(expected, find.executeFindCommand(inputArr));
        String[] deleteInput = new String[] {"delete", String.valueOf(startingIndex)};
        Task.deleteTask(path, deleteInput);
    }


}
