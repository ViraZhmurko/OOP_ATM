package com.atm.repository;

import com.atm.domain.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public User findById(String id) {
        return users.get(id);
    }

    public void save(User user) {
        users.put(user.getId(), user);
    }
}