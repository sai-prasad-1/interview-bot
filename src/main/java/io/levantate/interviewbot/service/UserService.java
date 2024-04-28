package io.levantate.interviewbot.service;
import io.levantate.interviewbot.DTO.User.RegisterDTO;
import io.levantate.interviewbot.models.User;
import io.levantate.interviewbot.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        return user; // Login failed
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
