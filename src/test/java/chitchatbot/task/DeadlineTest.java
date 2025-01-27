package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @Test
    public void createDeadline_success() throws MissingParameterException {
        String[] inputArr = new String[] {"deadline", "test", "/by", "27/01/2025", "1800"};
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        Deadline newTask = new Deadline("test ", LocalDate.parse("27/01/2025",
                dateFormatter), LocalTime.parse("1800", timeFormatter));

        String expected = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + newTask
                + "\n" + Ui.indentation + "Now you have "
                + (Task.getNoOfActivity() + 1) + " tasks in the list.\n");

        assertEquals(expected, Deadline.createDeadline(inputArr, storage));
    }

    @Test
    public void createDeadline_missingDescription_exceptionThrown() {
        try {
            String[] inputArr = new String[] {"deadline"};
            Deadline.createDeadline(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "    ERROR: There is missing parameters, " +
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
            String expected = "    ERROR: There is missing parameters, " +
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
            String expected = "    ERROR: There is missing parameters, " +
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
