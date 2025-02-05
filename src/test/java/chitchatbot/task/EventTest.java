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

public class EventTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @BeforeEach
    public void initStorage() {
        storage.initStorage();
    }

    @Test
    public void createEvent_success() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "eventTest",
                "/from", "28/01/2025", "1800",
                "/to", "29/01/2025", "1900"};
        String actual = Event.createEvent(inputArr, storage);
        String expected = "Got it. I've added this task:\n"
                + "  " + "[E][ ] eventTest(from: Jan 28 2025 18:00 to: Jan 29 2025 19:00)" + "\n"
                + "Now you have "
                + Task.getNoOfActivity() + " tasks in the list.";
        assertEquals(expected, actual);

        String[] deleteInput = new String[] {"delete", String.valueOf(Task.getNoOfActivity())};
        Task.deleteTask(path, deleteInput);
    }

    @Test
    public void createEvent_missingDescription_exceptionThrown() {
        String[] inputArr = new String[] {"event"};
        try {
            String result = Event.createEvent(inputArr, storage);
            fail();
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingDate_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingDateWithFrom_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTime_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTimeWithTo_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "/to"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingFrom_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "28/01/2025", "1800", "/to", "29/01/2025", "1900"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTo_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "29/01/2025", "1900"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "Missing parameters: \n"
                    + "Please ensure the correct format is used: \n"
                    + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_wrongDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "1800", "/to", "29 Jan 2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_wrongTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "06:00pm", "/to", "29 Jan 2025", "07:00pm"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_fromDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "1800", "/to", "29/01/2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_toDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "/to", "29 Jan 2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_fromTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "18:00", "/to", "29/01/2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_toTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "/to", "29/01/2025", "26:00"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "Incorrect format:\n"
                + "Please ensure the correct format is used:\n"
                + "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm";
        assertEquals(expected, result);
    }
}
