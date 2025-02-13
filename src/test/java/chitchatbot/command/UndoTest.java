package chitchatbot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import chitchatbot.exception.BotException;
import chitchatbot.storage.Storage;
import chitchatbot.task.Deadline;
import chitchatbot.task.Event;
import chitchatbot.task.Task;
import chitchatbot.task.Todo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UndoTest {
    private Path path = Paths.get("data", "chat.txt");
    private Storage storage = new Storage(path);
    private static final String[] UNDO_INPUT = new String[] {"undo"};
    private static final String REPEAT_UNDO_EXCEPTION = "Only can undo the latest command that changed the data";

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }


    @Test
    @Order(1)
    public void executeUndo_NoCommand_Success() {
        Undo noCommandUndo = new Undo(storage, UNDO_INPUT);
        String expected = REPEAT_UNDO_EXCEPTION;
        try {
            String actual = noCommandUndo.executeUndo();
            fail();
        } catch (BotException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void executeUndo_Todo_Success() throws BotException {
        String[] input = new String[] {"todo", "undo test"};
        String expected = "Undo previous command: todo undo test";
        Todo.createToDo(input, storage);
        Undo undoTest = new Undo(storage, UNDO_INPUT);
        String actual = undoTest.executeUndo();
        assertEquals(expected, actual);
        try {
            Undo undoAgain = new Undo(storage, UNDO_INPUT);
            undoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            expected = REPEAT_UNDO_EXCEPTION;
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void executeUndo_Deadline_Success() throws BotException {
        String[] input = new String[] {"deadline", "undo deadline test",
                "/by", "13/02/2025", "1800"};
        String expected = "Undo previous command: deadline undo deadline test /by 13/02/2025 1800";
        Deadline.createDeadline(input, storage);
        Undo undoDeadlineTest = new Undo(storage, UNDO_INPUT);
        String actual = undoDeadlineTest.executeUndo();
        assertEquals(expected, actual);

        try {
            Undo undoAgain = new Undo(storage, UNDO_INPUT);
            undoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            expected = REPEAT_UNDO_EXCEPTION;
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void executeUndo_Event_Success() throws BotException {
        String[] input = new String[] {"event", "undo event test",
                "/from", "13/02/2025", "1200",
                "/to", "13/02/2025", "1800"};
        String expected = "Undo previous command: event undo event test /from 13/02/2025 1200 /to 13/02/2025 1800";
        Event.createEvent(input, storage);
        Undo undoEventTest = new Undo(storage, UNDO_INPUT);
        String actual = undoEventTest.executeUndo();
        assertEquals(expected, actual);
        try {
            Undo undoAgain = new Undo(storage, UNDO_INPUT);
            undoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            expected = REPEAT_UNDO_EXCEPTION;
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void executeUndo_ListCommand_Success() {
        Undo noCommandUndo = new Undo(storage, UNDO_INPUT);
        String expected = REPEAT_UNDO_EXCEPTION;
        try {
            storage.listTask();
            String actual = noCommandUndo.executeUndo();
            fail();
        } catch (BotException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(6)
    public void executeUndo_FindCommand_Success() {
        Undo noCommandUndo = new Undo(storage, UNDO_INPUT);
        String expected = REPEAT_UNDO_EXCEPTION;
        try {
            Find findCommand = new Find(storage);
            ArrayList<String> findTask = new ArrayList<>();
            findTask.add("test");
            findCommand.findSimilarTask(findTask);
            String actual = noCommandUndo.executeUndo();
            fail();
        } catch (BotException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(7)
    public void executeUndo_InvalidInput() {
        try {
            Undo invalidInputUndo = new Undo(storage, new String[] {"undo", "test"});
            invalidInputUndo.executeUndo();
            fail();
        } catch (BotException e) {
            String expected = "Incorrect format: "
                    + "Please ensure the correct format is used Undo";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    @Order(8)
    public void executeUndo_MarkTask_Success() throws IOException, BotException {
        String[] taskInput = new String[] {"todo", "undo mark test"};
        Todo.createToDo(taskInput, storage);
        int taskIndex = Task.getNoOfActivity();
        String[] markInput = new String[] {"mark", String.valueOf(taskIndex)};
        Task.markAsDone(path, markInput);
        String expectedTask = "[T][ ] undo mark test";
        Undo undoMark = new Undo(storage, UNDO_INPUT);
        undoMark.executeUndo();
        String actualTask = Files.readAllLines(path).get(taskIndex - 1);
        assertEquals(expectedTask, actualTask);

        try {
            Undo undoAgain = new Undo(storage, UNDO_INPUT);
            undoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            String expected = REPEAT_UNDO_EXCEPTION;
            assertEquals(expected, e.getMessage());
        }

        String[] deleteInput = new String[] {"delete", String.valueOf(taskIndex)};
        Task.deleteTask(path, deleteInput);

    }

    @Test
    @Order(9)
    public void executeUndo_UnmarkTask_Success() throws IOException, BotException {
        String[] taskInput = new String[] {"todo", "undo unmark test"};
        Todo.createToDo(taskInput, storage);
        int taskIndex = Task.getNoOfActivity();
        String[] markInput = new String[] {"mark", String.valueOf(taskIndex)};
        Task.markAsDone(path, markInput);
        String[] unmarkInput = new String[] {"unmark", String.valueOf(taskIndex)};
        Task.markAsNotDone(path, unmarkInput);
        String expectedTask = "[T][X] undo unmark test";
        Undo undoUnmark = new Undo(storage, UNDO_INPUT);
        undoUnmark.executeUndo();
        String actualTask = Files.readAllLines(path).get(taskIndex - 1);
        assertEquals(expectedTask, actualTask);

        try {
            Undo undoAgain = new Undo(storage, UNDO_INPUT);
            undoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            String expected = REPEAT_UNDO_EXCEPTION;
            assertEquals(expected, e.getMessage());
        }

        String[] deleteInput = new String[] {"delete", String.valueOf(taskIndex)};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    @Order(10)
    public void executeUndo_UndoDelete_Success() throws BotException, IOException {
        String[] taskInput = new String[] {"todo", "undo delete test"};
        Todo.createToDo(taskInput, storage);
        int taskIndex = Task.getNoOfActivity();
        String[] deleteInput = new String[] {"delete", String.valueOf(taskIndex)};
        Task.deleteTask(path, deleteInput);
        String expected = "[T][ ] undo delete test";
        Undo undoDelete = new Undo(storage, UNDO_INPUT);
        undoDelete.executeUndo();
        String undoTask = Files.readAllLines(path).get(taskIndex - 1);
        assertEquals(expected, undoTask);

        try {
            Undo undoUndoAgain = new Undo(storage, UNDO_INPUT);
            undoUndoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            String expectedRepeat = REPEAT_UNDO_EXCEPTION;
            assertEquals(expectedRepeat, e.getMessage());
        }

        Task.deleteTask(path, deleteInput);
    }

    @Test
    @Order(11)
    public void executeUndo_UndoDeleteMiddleTask_Success() throws BotException, IOException {
        String[] taskInput = new String[] {"todo", "undo delete test"};
        String[] anotherTaskInput = new String[] {"todo", "another test"};
        Todo.createToDo(taskInput, storage);
        int taskIndex = Task.getNoOfActivity();
        Todo.createToDo(anotherTaskInput, storage);

        String[] deleteInput = new String[] {"delete", String.valueOf(taskIndex)};
        Task.deleteTask(path, deleteInput);
        String expected = "[T][ ] undo delete test";
        Undo undoDelete = new Undo(storage, UNDO_INPUT);
        undoDelete.executeUndo();
        String undoTask = Files.readAllLines(path).get(taskIndex - 1);
        assertEquals(expected, undoTask);

        try {
            Undo undoUndoAgain = new Undo(storage, UNDO_INPUT);
            undoUndoAgain.executeUndo();
            fail();
        } catch (BotException e) {
            String expectedRepeat = REPEAT_UNDO_EXCEPTION;
            assertEquals(expectedRepeat, e.getMessage());
        }

        Task.deleteTask(path, deleteInput);
        Task.deleteTask(path, deleteInput);
    }

    
}
