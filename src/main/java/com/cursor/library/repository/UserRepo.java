package com.cursor.library.repository;

import com.cursor.library.models.User;
import com.cursor.library.models.enums.UserPermission;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepo {
    private final Map<String, User> users = new HashMap<>();

    public List<User> getAll() {
        return new ArrayList<>(users.values()); }

    public User saveUser(
            final String username,
            final String password,
            final Set<UserPermission> permissions) {
        final User newUser = new User(UUID.randomUUID().toString(), username, password, permissions);
        return users.put(newUser.getId(), newUser); }

    public User findById(String userId) {
        return users.get(userId);
    }

    public Optional<User> findByUsername(String username) {
        return users.values().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
