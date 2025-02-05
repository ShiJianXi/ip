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

        String expected = "Noted. I've removed this task:\n"
                + "  " + "[T][ ] delete task test" + "\n"
                + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.";
        String[] testInput = new String[] {"delete", String.valueOf(numberOfTask)};

        assertEquals(expected, Task.deleteTask(path, testInput));
    }

    @Test
    public void deleteTask_missingParameter_exceptionThrown() throws IOException {
        String[] testInput = new String[] {"delete"};
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used: delete <Task number>";
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
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used: delete <Task number>";
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
        String expected = "Incorrect format:\n"
                + "Please ensure the ID number of the task is provided";
        assertEquals(expected, Task.deleteTask(path, testInput));
    }

    @Test
    public void deleteTask_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"delete", "999"};
        String expected = "This task doesn't exist:\n"
                + "You can only delete an existing task";
        assertEquals(expected, Task.deleteTask(path, testInput));
    }



    @Test
    public void markAsDone_success() throws MissingParameterException {
        String[] arr = new String[] {"todo", "markAsDone Test"};
        Todo.createToDo(arr, storage);
        int numberOfTask = Task.getNoOfActivity();

        String[] inputArr = new String[] {"mark", String.valueOf(numberOfTask)};
        String expected = "Nice! I've marked this task as done:\n"
                + "  " + "[T][X] markAsDone Test";
        assertEquals(expected, Task.markAsDone(path, inputArr));

        //Delete the added String in chat.txt
        String[] deleteArr = new String[] {"delete", String.valueOf(numberOfTask)};
        String deleteExpected = "Noted. I've removed this task:\n"
                + "  " + "[T][X] markAsDone Test" + "\n"
                + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.";
        assertEquals(deleteExpected, Task.deleteTask(path, deleteArr));
    }

    @Test
    public void markAsDone_missingParameters_exceptionThrown() {
        String[] testInput = new String[] {"delete"};
        try {
            Task.markAsDone(path, testInput);
            fail();
        } catch (MissingParameterException e){
            String expected = "Missing parameters:\n"
                    + "Please ensure the correct format is used: mark <Task Number>";

            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void markAsDone_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"mark", "999"};
        String expected;
        if (Task.getNoOfActivity() == 0) {
            expected = "Unable to mark, no task in the list, "
                    + "please add task first";
        } else if (Task.getNoOfActivity() == 1) {
            expected = "Unable to mark, this task doesn't exist, "
                    + "only 1 task in the list";
        } else {
            expected = "Unable to mark, this task doesn't exist, "
                    + "please pick a task from 1 to "
                    + Task.getNoOfActivity() + " to mark.";
        }
        assertEquals(expected, Task.markAsDone(path, testInput));
    }

    @Test
    public void markAsDone_notNumber_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"mark", "a"};
        String expected = "Please enter the ID number of the task that you want to mark.";
        assertEquals(expected, Task.markAsDone(path, testInput));
    }

    @Test
    public void markAsDone_alreadyMark_exceptionThrown() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "markAsDone alreadyMark test"};
        Todo.createToDo(taskInput, storage);
        int numberOfTask = Task.getNoOfActivity();
        String[] markInput = new String[] {"mark", String.valueOf(numberOfTask)};
        Todo.markAsDone(path, markInput);
        String expected = "This task is already marked as done!";
        assertEquals(expected, Task.markAsDone(path, markInput));
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

        String expected = "OK, I've marked this task as not done yet:\n"
                + "  " + "[T][ ] markAsNotDone Test";

        assertEquals(expected, Task.markAsNotDone(path, testArr));

        //Delete the added String in chat.txt
        String[] deleteArr = new String[] {"delete", String.valueOf(numberOfTask)};
        String deleteExpected = "Noted. I've removed this task:\n"
                + "  " + "[T][ ] markAsNotDone Test" + "\n"
                + "Now you have " + (Task.getNoOfActivity() - 1)
                + " tasks in the list.";
        assertEquals(deleteExpected, Task.deleteTask(path, deleteArr));
    }

    @Test
    public void markAsNotDone_missingParameters_exceptionThrown() {
        String[] testInput = new String[] {"unmark"};
        String expected = "Missing parameters:\n"
                + "Please ensure the correct format is used: "
                + "unmark <Task Number>";
        try {
            Task.markAsNotDone(path, testInput);
            fail();
        } catch (MissingParameterException e) {
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void markAsNotDone_indexOutOfBound_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"unmark", "999"};
        String expected;
        if (Task.getNoOfActivity() == 0) {
            expected = "Unable to unmark, no task in the list, "
                    + "please add and mark task first";
        } else if (Task.getNoOfActivity() == 1) {
            expected = "Unable to unmark, this task doesn't exist, "
                    + "only 1 task in the list";
        } else {
            expected = "Unable to unmark, this task doesn't exist, "
                    + "please pick a task from 1 to " + Task.getNoOfActivity() + " to unmark.";
        }
        assertEquals(expected, Task.markAsNotDone(path, testInput));
    }

    @Test
    public void markAsNotDone_noNumber_exceptionThrown() throws MissingParameterException {
        String[] testInput = new String[] {"unmark", "a"};
        String expected = "Please enter the ID number of the task that you want to unmark.";
        assertEquals(expected, Task.markAsNotDone(path, testInput));
    }

    @Test
    public void markAsDone_notYetMark_exceptionThrown() throws MissingParameterException {
        String[] taskInput = new String[] {"todo", "markAsNotDone not yet mark test"};
        Todo.createToDo(taskInput, storage);
        int numberOfTask = Task.getNoOfActivity();
        String[] markInput = new String[] {"unmark", String.valueOf(numberOfTask)};
        Todo.markAsNotDone(path, markInput);
        String expected = "This task is not yet marked as done!";
        assertEquals(expected, Task.markAsNotDone(path, markInput));

        String[] deleteInput = new String[] {"delete", String.valueOf(numberOfTask)};
        Task.deleteTask(path, deleteInput);
    }


}
