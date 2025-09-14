// File: src/main/java/com/example/dao/UserDao.java
package com.example.dao;

import com.example.model.User;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findByEmail(String email);
}