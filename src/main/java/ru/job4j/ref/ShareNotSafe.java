package ru.job4j.ref;

public class ShareNotSafe {

    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> cache.findAll().get(0).setName("Ilya")
        );
        first.start();
        first.join();
        System.out.println(cache.findAll());
    }
}
