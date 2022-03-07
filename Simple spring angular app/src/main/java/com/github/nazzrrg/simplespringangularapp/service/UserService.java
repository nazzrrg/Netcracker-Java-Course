package com.github.nazzrrg.simplespringangularapp.controller;

import com.github.nazzrrg.simplespringangularapp.repo.UserRepository;
import com.github.nazzrrg.simplespringangularapp.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void create(@RequestBody User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
