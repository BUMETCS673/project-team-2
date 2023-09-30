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
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Double getBalance(Integer id) {
        User user = userRepository.findById(id).get();
        return user.getBalance_amount();
    }

    @Override
    public UserDetails getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with username: " + username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword_hash(),
                new ArrayList<>());

    }

    @Override
    public UserDetails getUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findUserByEmail(email);
        if(null == user)
            throw new UsernameNotFoundException("User could not be found with email " + email);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword_hash(),
                new ArrayList<>());
    }

    @Override
    public String getPasswordHash(String email) {
        return userRepository.findPasswordHashByEmail(email);
    }

    @Override
    public User save(User user){
        User newUser = new User();
        User existing = userRepository.findUserByEmail(user.getEmail());
        if(null != existing) {
            throw new NonUniqueResultException("Email {}" + user.getEmail() + " already registered");
        }
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword_hash(SecurityConfig.hashedPassword(user.getPassword_hash()));
        return userRepository.save(newUser);
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
