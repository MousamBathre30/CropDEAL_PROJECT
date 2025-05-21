package com.cropdealer.UserMicroservice.controller;

import com.cropdealer.UserMicroservice.entity.User;
import com.cropdealer.UserMicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){

        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/role/{role}")
    public List<User> getUserBYRole(@PathVariable String role){
        return userService.getUserByRole(role);
    }
}
