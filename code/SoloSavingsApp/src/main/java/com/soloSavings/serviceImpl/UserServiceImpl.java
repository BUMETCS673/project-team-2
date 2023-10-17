package com.soloSavings.serviceImpl;

import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TransactionServiceImpl transactionServiceImpl;
    @Override
    public Double getBalance(Integer id) {
        User user = userRepository.findById(id).get();
        return user.getBalance_amount();
    }

    @Override
    public User getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with username: " + username);
        return user;

    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findUserByEmail(email);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with email " + email);
        return user;
    }


    @Override
    public void setUserNewPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with username " + username);
        String hashedPassword = SecurityConfig.hashedPassword(newPassword);
        userRepository.updatePasswordByUsername(hashedPassword, username);
    }

    @Override
    public String getPasswordHash(String email) {
        return userRepository.findPasswordHashByEmail(email);
    }

    @Override
    public void save(User user){
        User existing = userRepository.findUserByEmail(user.getEmail());
        if(null != existing) {
            throw new NonUniqueResultException("Email {}" + user.getEmail() + " already registered");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword_hash(SecurityConfig.hashedPassword(user.getPassword_hash()));
        userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with username " + username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword_hash(),
                new ArrayList<>());
    }
}
