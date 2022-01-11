package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> predicate) {
        StringBuilder output = new StringBuilder();
            try (BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(file))) {
                int data;
                while ((data = in.read()) != -1) {
                    if (predicate.test((char) data)) {
                        output.append((char) data);
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            }
        return output.toString();
    }
}
