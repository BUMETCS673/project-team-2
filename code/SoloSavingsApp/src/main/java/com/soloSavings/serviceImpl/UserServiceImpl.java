package com.soloSavings.serviceImpl;

import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public String getPasswordHash(String email) {
        return userRepository.findPasswordHashByEmail(email);
    }

    @Override
    public User save(User user){
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword_hash(SecurityConfig.hashedPassword(user.getPassword_hash()));
        return userRepository.save(newUser);
    }
}
