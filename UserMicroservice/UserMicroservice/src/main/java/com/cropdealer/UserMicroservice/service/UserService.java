package com.cropdealer.UserMicroservice.service;

import com.cropdealer.UserMicroservice.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    List<User> getUserByRole(String role);
}
