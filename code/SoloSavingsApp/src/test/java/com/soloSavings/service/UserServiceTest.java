package com.soloSavings.service;

import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setup(){
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(10.00)
                .last_updated(null)
                .build();
    }

    @Test
    public void testSave(){
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        User userSaved = userService.save(user);

        //Then
        assertEquals(user,userSaved);

    }

    @Test
    public void testGetPasswordHash(){
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getUser_id())).thenReturn(Optional.of(user));
        User userSaved = userService.save(user);
    }

    @Test
    public void testGetUsersByEmail(){

    }

    @Test
    public void testGetUserByName(){

    }

    @Test
    public void testGetBalance(){

    }

}
