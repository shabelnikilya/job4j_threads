package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) {
        try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(file))) {
            out.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
