package chitchatbot.storage;

import chitchatbot.task.Task;
import chitchatbot.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringJoiner;


public class Storage {
    private Path path;
    private File chatFile;

    public Storage (Path path) {
        this.path = path;
        this.chatFile = new File(String.valueOf(path));
    }

    public void initStorage() {
        if (!chatFile.exists()) {
            try {
                Files.createDirectories(path.getParent());
                chatFile.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred, unable to create file");
            }
        } else {
            try {
                int noOfActivity = Files.readAllLines(path).size();
                Task.setNoOfActivity(noOfActivity);
            } catch (IOException e) {
                System.out.println("An error occurred, unable to read file");
            }
        }
    }

    public void appendToFile(String message) {
        try {
            FileWriter fw = new FileWriter(chatFile, true);
            fw.write(message + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("An Error occurred, unable to write");
        }
    }

    public void listTask() {
        try {
            Scanner scanner = new Scanner(chatFile);
            StringJoiner toPrint = new StringJoiner("\n");
            int index = 0;
            while (scanner.hasNext()) {
                index++;
                String text = scanner.nextLine();
                text = "    " + index + "." + text;
                toPrint.add(text);
            }

            System.out.println(Ui.printChat(toPrint + "\n"));
        } catch (FileNotFoundException e) {
            System.out.println("File error: File not found");
        }
    }

    public File getFile() {
        return this.chatFile;
    }

    public Path getPath() {
        return this.path;
    }

}
