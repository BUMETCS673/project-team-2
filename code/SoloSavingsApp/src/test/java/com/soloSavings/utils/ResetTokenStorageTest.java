package com.soloSavings.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResetTokenStorageTest {
    @InjectMocks
    ResetTokenStorage tokenStorage;
    @Test
    public void testRetrieveUsernameNull(){
        Assertions.assertNull(tokenStorage.retrieveUsername(""));
    }

    @Test
    public void testRetrieveUsername(){
        String username = "username";
        String token = "token";
        tokenStorage.storeToken("username2","token2");
        tokenStorage.storeToken(username,token);

        String actual = tokenStorage.retrieveUsername(token);

        assertEquals(username,actual);
    }

    @Test
    public void testRemoveTokenRecord(){
        String username = "username";
        String token = "token";
        tokenStorage.storeToken(username,token);

        String actual = tokenStorage.retrieveUsername(token);
        assertEquals(username,actual);

        tokenStorage.removeTokenRecord(username);
        Assertions.assertNull(tokenStorage.retrieveUsername(token));
    }
}
