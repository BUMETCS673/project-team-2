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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.save(user);

        //Then
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void testGetPasswordHash(){
        //When
        String expected = "passwordHash";
        when(userRepository.findPasswordHashByEmail(user.getEmail())).thenReturn(expected);
        String pw_hash = userService.getPasswordHash("test@gmail.com");

        //Then
        assertEquals(expected,pw_hash);
        verify(userRepository, times(1)).findPasswordHashByEmail(anyString());
    }

    @Test
    public void testGetUsersByEmail(){
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
//        when(userService.save(user)).thenReturn(user);

        //Then
        assertEquals(user,userService.getUserByEmail(user.getEmail()));
    }

    @Test
    public void testGetUserByName(){
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        //Then
        assertEquals(user,userService.getUserByName(user.getUsername()));
    }

    @Test
    public void testGetBalance(){
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getUser_id())).thenReturn(Optional.of(user));

        //Then
        assertEquals(10.00,userService.getBalance(user.getUser_id()));
    }

}
