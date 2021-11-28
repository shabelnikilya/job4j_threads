package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private int index;

    @Override
    public void run() {
        String[] process = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                index = index == 3 ? 0 : index;
                System.out.print("\r load: " + process[index++]);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
