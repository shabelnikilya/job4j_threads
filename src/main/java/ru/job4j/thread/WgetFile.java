package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

public class WgetFile implements Runnable {

    private final String url;
    private final int speed;

    public WgetFile(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int pause;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                Instant start = Instant.now();
                out.write(dataBuffer, 0, bytesRead);
                Instant finish = Instant.now();
                pause = speed - Duration.between(start, finish).toMillisPart();
                if (pause > 0) {
                    Thread.sleep(pause);
                }
            }
        } catch (IOException | InterruptedException io) {
            io.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new WgetFile(url, speed));
        wget.start();
        wget.join();
    }
}
