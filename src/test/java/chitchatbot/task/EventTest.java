package chitchatbot.task;

import chitchatbot.exception.MissingParameterException;
import chitchatbot.storage.Storage;
import chitchatbot.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventTest {
    Path path = Paths.get("data", "chat.txt");
    Storage storage = new Storage(path);

    @Test
    public void createEvent_success() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "eventTest",
                "/from", "28/01/2025", "1800",
                "/to", "29/01/2025", "1900"};
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalDate fromDate = LocalDate.parse("28/01/2025", dateFormatter);
        LocalTime fromTime = LocalTime.parse("1800", timeFormatter);
        LocalDate toDate = LocalDate.parse("29/01/2025", dateFormatter);
        LocalTime toTime = LocalTime.parse("1900", timeFormatter);
        Event newTask = new Event("eventTest ", fromDate, fromTime, toDate, toTime);
        String expected = Ui.printChat(Ui.indentation + "Got it. I've added this task:\n"
                + Ui.indentation + "  " + newTask + "\n"
                + Ui.indentation + "Now you have "
                + (Task.getNoOfActivity() + 1) + " tasks in the list.\n");
        assertEquals(expected, Event.createEvent(inputArr, storage));
    }

    @Test
    public void createEvent_missingDescription_exceptionThrown() {
        String[] inputArr = new String[] {"event"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingDate_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingDateWithFrom_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTime_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTimeWithTo_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "/to"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingFrom_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "28/01/2025", "1800", "/to", "29/01/2025", "1900"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_missingTo_exceptionThrown() {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "29/01/2025", "1900"};
        try {
            String result = Event.createEvent(inputArr, storage);
        } catch (MissingParameterException e) {
            String expected = "    ERROR: Missing parameters\n" +
                    "    Please ensure the correct format is used: " +
                    "event <Description> /from dd/mm/yyyy HHmm /to dd/mm/yyyy HHmm\n";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void createEvent_wrongDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "1800", "/to", "29 Jan 2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_wrongTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "06:00pm", "/to", "29 Jan 2025", "07:00pm"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_fromDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28 Jan 2025", "1800", "/to", "29/01/2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_toDateFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "/to", "29 Jan 2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_fromTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "18:00", "/to", "29/01/2025", "1900"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void createEvent_toTimeFormat() throws MissingParameterException {
        String[] inputArr = new String[] {"event", "event exception test", "/from", "28/01/2025", "1800", "/to", "29/01/2025", "26:00"};
        String result = Event.createEvent(inputArr, storage);
        String expected = "";
        assertEquals(expected, result);
    }
}
