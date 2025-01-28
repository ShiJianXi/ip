package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);
    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void deleteTask_success() throws MissingParameterException, IOException {
        String[] taskInput = new String[] {"todo", "delete task test"};
        Todo.createToDo(taskInput, storage);
        int numberOfTask = Task.getNoOfActivity();

        String expected = Ui.printChat(Ui.indentation + "Noted. I've removed this task:\n" +
                Ui.indentation + "  " + "[T][ ] delete task test " + "\n"
                + Ui.indentation + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.\n");
        String[] testInput = new String[] {"delete", String.valueOf(numberOfTask)};

        assertEquals(expected, Task.deleteTask(path, testInput));
    }

    @Test
    public void deleteTask_missingParameter_exceptionThrown() throws IOException {
        String[] testInput = new String[] {"delete"};
        String expected = Ui.printChat(Ui.indentation
                + "Missing parameters error: Incorrect format for delete queries:\n"
                + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n");
        try {
            Task.deleteTask(path, testInput);
            fail();
        } catch (MissingParameterException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void deleteTask_wrongNumberOfParameter_exceptionThrown() throws IOException {
        String[] testInput = new String[] {"delete", "3", "4"};
        String expected = Ui.printChat(Ui.indentation
                + "Missing parameters error: Incorrect format for delete queries:\n"
                + Ui.indentation + "Please ensure the correct format is used: delete <Task number>\n");
        try {
            Task.deleteTask(path, testInput);
            fail();
        } catch (MissingParameterException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void deleteTask_noNumber_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"delete", "a"};
        assertEquals("", Task.deleteTask(path, testInput));
    }

    @Test
    public void deleteTask_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"delete", "99999999999999999999"};
        assertEquals("", Task.deleteTask(path, testInput));
    }



    @Test
    public void markAsDone_success() throws MissingParameterException {
        String[] arr = new String[] {"todo", "markAsDone Test"};
        Todo.createToDo(arr, storage);
        int numberOfTask = Task.getNoOfActivity();

        String[] inputArr = new String[] {"mark", String.valueOf(numberOfTask)};
        String expected = Ui.printChat(Ui.indentation + "Nice! I've marked this task as done:\n"
                + Ui.indentation + "  " + "[T][X] markAsDone Test " + "\n");
        assertEquals(expected, Task.markAsDone(path, inputArr));

        //Delete the added String in chat.txt
        String[] deleteArr = new String[] {"delete", String.valueOf(numberOfTask)};
        String deleteExpected = Ui.printChat(Ui.indentation + "Noted. I've removed this task:\n" +
                Ui.indentation + "  " + "[T][X] markAsDone Test " + "\n"
                + Ui.indentation + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.\n");
        assertEquals(deleteExpected, Task.deleteTask(path, deleteArr));
    }

    @Test
    public void markAsDone_missingParameters_exceptionThrown() {
        String[] testInput = new String[] {"delete"};
        try {
            Task.markAsDone(path, testInput);
            fail();
        } catch (MissingParameterException e){
            String expected = Ui.indentation + "ERROR: Missing parameters\n"
                    + Ui.indentation + "Please ensure the correct format is used: mark <Task Number>\n";

            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void markAsDone_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"mark", "9999999999999999999"};
        assertEquals("", Task.markAsDone(path, testInput));
    }

    @Test
    public void markAsDone_notNumber_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"mark", "a"};
        assertEquals("", Task.markAsDone(path, testInput));
    }

    @Test
    public void markAsDone_alreadyMark_exceptionThrown() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "markAsDone alreadyMark test"};
        Todo.createToDo(taskInput, storage);
        int numberOfTask = Task.getNoOfActivity();
        String[] markInput = new String[] {"mark", String.valueOf(numberOfTask)};
        Todo.markAsDone(path, markInput);

        assertEquals("", Task.markAsDone(path, markInput));
        String[] deleteInput = new String[] {"delete", String.valueOf(numberOfTask)};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void markAsNotDone_success() throws MissingParameterException {
        String[] arr = new String[] {"todo", "markAsNotDone Test"};
        Todo.createToDo(arr, storage);
        int numberOfTask = Task.getNoOfActivity();
        String[] markArr = new String[] {"mark", String.valueOf(numberOfTask)};
        Task.markAsDone(path, markArr);

        String[] testArr = new String[] {"unmark", String.valueOf(numberOfTask)};

        String expected = Ui.printChat(Ui.indentation + "OK, I've marked this task as not done yet:\n"
                + Ui.indentation + "  " + "[T][ ] markAsNotDone Test " + "\n");

        assertEquals(expected, Task.markAsNotDone(path, testArr));

        //Delete the added String in chat.txt
        String[] deleteArr = new String[] {"delete", String.valueOf(numberOfTask)};
        String deleteExpected = Ui.printChat(Ui.indentation + "Noted. I've removed this task:\n" +
                Ui.indentation + "  " + "[T][ ] markAsNotDone Test " + "\n"
                + Ui.indentation + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.\n");
        assertEquals(deleteExpected, Task.deleteTask(path, deleteArr));
    }

    @Test
    public void markAsNotDone_missingParameters_exceptionThrown() {
        String[] testInput = new String[] {"unmark"};
        String expected = Ui.indentation + "ERROR: Missing parameters\n"
                + Ui.indentation + "Please ensure the correct format is used: " +
                "unmark <Task Number>\n";
        try {
            Task.markAsNotDone(path, testInput);
            fail();
        } catch (MissingParameterException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void markAsNotDone_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"unmark", "999999999999999999999999"};
        assertEquals("", Task.markAsNotDone(path, testInput));
    }

    @Test
    public void markAsNotDone_noNumber_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"unmark", "a"};
        assertEquals("", Task.markAsNotDone(path, testInput));
    }

    @Test
    public void markAsDone_notYetMark_exceptionThrown() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "markAsNotDone not yet mark test"};
        Todo.createToDo(taskInput, storage);
        int numberOfTask = Task.getNoOfActivity();
        String[] markInput = new String[] {"unmark", String.valueOf(numberOfTask)};
        Todo.markAsNotDone(path, markInput);

        assertEquals("", Task.markAsNotDone(path, markInput));

        String[] deleteInput = new String[] {"delete", String.valueOf(numberOfTask)};
        Task.deleteTask(path, deleteInput);
    }


}
