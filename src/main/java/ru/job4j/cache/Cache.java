package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public Base getBase(int id) {
        return memory.get(id);
    }

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(
                        model.getId(),
                        (key, oldValue) -> {
                        if (oldValue.getVersion() != model.getVersion()) {
                            throw new OptimisticException("Versions are not equal");
                            }
                        Base newValue = new Base(key, oldValue.getVersion() + 1);
                        newValue.setName(model.getName());
                        return newValue;
                        })
                != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }
}
