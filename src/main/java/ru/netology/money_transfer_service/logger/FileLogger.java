package ru.netology.money_transfer_service.logger;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class FileLogger implements ILogger {
    private static final String LINE_SEPARATOR = "\r\n";
    private final File file;

    public FileLogger(String filePath) {
        file = new File(filePath);
        checkFile();
    }

    public void log(boolean isError, String message) {
        try (final FileWriter writer = new FileWriter(file, true)) {
            writer.write(message + LINE_SEPARATOR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile() {
        if (!file.exists()) {
            final String parent = file.getParent();
            try {
                if (!(new File(parent).exists())) {
                    Files.createDirectories(Paths.get(file.getParent()));
                }
                Files.createFile(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}