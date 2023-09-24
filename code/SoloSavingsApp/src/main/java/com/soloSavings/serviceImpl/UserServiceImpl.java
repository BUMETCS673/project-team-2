package com.soloSavings.serviceImpl;

import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Double getBalance(Integer id) {
        User user = userRepository.findById(id).get();
        return user.getBalance_amount();
    }

    @Override
    public User save(User user){
        User newUser = new User();
        // expected username, email and plain_text_password given
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword_hash(SecurityConfig.hashedPassword(user.getPassword_hash()));
        return userRepository.save(newUser);
    }
}
