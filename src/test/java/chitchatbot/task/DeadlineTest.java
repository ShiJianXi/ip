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

public class DeadlineTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void createDeadline_success() throws MissingParameterException {
        String[] inputArr = new String[] {"deadline", "test", "/by", "27/01/2025", "1800"};

        String actual = Deadline.createDeadline(inputArr, storage);
        String expected = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + "[D][ ] test (by: Jan 27 2025 18:00)"
                + "\n" + Ui.indentation + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.\n");

        assertEquals(expected, actual);
        String[] deleteInput = new String[] {"delete", String.valueOf(Task.getNoOfActivity())};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void createDeadline_withoutTime_success() throws MissingParameterException {
        String[] inputArr = new String[] {"deadline", "test without time", "/by", "27/01/2025"};

        String actual = Deadline.createDeadline(inputArr, storage);
        String expected = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + "[D][ ] test without time (by: Jan 27 2025)"
                + "\n" + Ui.indentation + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.\n");

        assertEquals(expected, actual);
        String[] deleteInput = new String[] {"delete", String.valueOf(Task.getNoOfActivity())};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void createDeadline_missingDescription_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"deadline"};
            Deadline.createDeadline(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "    Missing parameter error: There is missing parameters, " +
                    "please ensure the correct format is used:\n" +
                    "    deadline <Description> /by dd/mm/yyyy\n" +
                    "    OR deadline <Description /by dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createDeadline_missingDate_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"deadline", "test deadline", "/by"};
            Deadline.createDeadline(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "    Missing parameter error: There is missing parameters, " +
                    "please ensure the correct format is used:\n" +
                    "    deadline <Description> /by dd/mm/yyyy\n" +
                    "    OR deadline <Description /by dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createDeadline_missingBy_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"deadline", "test deadline", "27/01/2025"};
            Deadline.createDeadline(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "    Missing parameter error: There is missing parameters, " +
                    "please ensure the correct format is used:\n" +
                    "    deadline <Description> /by dd/mm/yyyy\n" +
                    "    OR deadline <Description /by dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createDeadline_wrongDateFormat() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "27-01-2025"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }

    @Test
    public void createDeadline_wrongDateFormat2() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "27 Jan 2025"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }

    @Test
    public void createDeadline_wrongDateFormat3() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "Today"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }

    @Test
    public void createDeadline_wrongTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "27-01-2025", "2900"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }

    @Test
    public void createDeadline_wrongTimeFormat2() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "27-01-2025", "18:00"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }

    @Test
    public void createDeadline_wrongTimeFormat3() throws MissingParameterException {
        String[] inputArr = new String[]{"deadline", "test" + "deadline", "/by", "27-01-2025", "09:00am"};
        String result = Deadline.createDeadline(inputArr, storage);
        //Since the exception is catch internally, the resulting string will be an empty string
        assertEquals("", result);
    }


}
