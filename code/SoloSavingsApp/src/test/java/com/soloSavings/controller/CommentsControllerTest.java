package com.soloSavings.controller;

import com.soloSavings.model.Comments;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.CommentsService;
import com.soloSavings.service.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentsControllerTest {
    @Mock
    CommentsService commentsService;
    @Mock
    SecurityContext securityContext;
    @InjectMocks
    CommentsController commentsController;
    User user;
    Comments comments;
    Transaction transaction;
    @BeforeEach
    void setup(){
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(10.00)
                .last_updated(null)
                .build();
        transaction = Transaction.builder()
                .transaction_id(1)
                .user_id(user.getUser_id())
                .source("transaction-source")
                .transaction_type(TransactionType.CREDIT)
                .amount(100.00)
                .transaction_date(LocalDate.now())
                .build();
        comments = Comments.builder()
                .transaction_id(transaction.getTransaction_id())
                .user_id(user.getUser_id())
                .id(1)
                .content("comment-content")
                .build();
        doNothing().when(securityContext).setContext(any(
                org.springframework.security.core.context.SecurityContext.class));
        when(securityContext.getCurrentUser()).thenReturn(user);
        doNothing().when(securityContext).dispose();
    }
    @Test
    public void testAddComments(){
        doNothing().when(commentsService).add(any(Comments.class));
        ResponseEntity response = commentsController.addComments(comments, transaction.getTransaction_id());

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testFindComments(){
        when(commentsService.findByCommentsId(anyInt(),anyInt())).thenReturn(comments);

        ResponseEntity response = commentsController.findComments(comments.getId());

        assertEquals(comments,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testFindCommentsNull(){
        when(commentsService.findByCommentsId(anyInt(),anyInt())).thenReturn(null);

        ResponseEntity response = commentsController.findComments(comments.getId());

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteComments(){
        doNothing().when(commentsService).deleteByCommentsId(anyInt(),anyInt());

        ResponseEntity response = commentsController.delComments(comments.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testListComments(){
        List<Comments> commentsList = new ArrayList<>();
        commentsList.add(comments);

        when(commentsService.allListByTransactionId(anyInt())).thenReturn(commentsList);

        ResponseEntity response = commentsController.listComments(comments.getId());

        assertEquals(commentsList,response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testListCommentsNull(){
        List<Comments> commentsList = new ArrayList<>();
        when(commentsService.allListByTransactionId(anyInt())).thenReturn(commentsList);

        ResponseEntity<List<Comments>> response = commentsController.listComments(comments.getId());

        assertEquals(0,response.getBody().size());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
