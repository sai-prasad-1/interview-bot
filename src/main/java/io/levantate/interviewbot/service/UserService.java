package io.levantate.interviewbot.service;
import io.levantate.interviewbot.DTO.User.RegisterDTO;
import io.levantate.interviewbot.models.User;
import io.levantate.interviewbot.repository.UserRepository;
import io.levantate.interviewbot.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return jwtTokenUtil.generateToken(user.getUsername(), newUser.getId());
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Login successful, generate JWT token
            return jwtTokenUtil.generateToken(user.getUsername(), user.getId());
        }
        return null; // Login failed
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
