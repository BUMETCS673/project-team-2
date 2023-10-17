package com.soloSavings.service;

import com.soloSavings.model.Comments;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.CommentsRepository;
import com.soloSavings.serviceImpl.CommentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentsServiceTest {

    @Mock
    CommentsRepository commentsRepository;
    @InjectMocks
    CommentsServiceImpl commentsService;
    Transaction transaction;
    Comments comments;
    @BeforeEach
    public void setup(){
        transaction = Transaction.builder()
                .transaction_id(1)
                .user_id(1)
                .source("transaction-source")
                .transaction_type(TransactionType.CREDIT)
                .amount(100.00)
                .transaction_date(LocalDate.now())
                .build();
        comments = Comments.builder()
                .transaction_id(transaction.getTransaction_id())
                .user_id(1)
                .id(1)
                .content("comment-content")
                .build();
    }
    @Test
    public void testFindByCommentsId(){
        when(commentsRepository.findByCommentsId(anyInt(),anyInt()))
                .thenReturn(comments);

        Comments actual = commentsService.findByCommentsId(1,1);

        assertEquals(comments,actual);
    }

    @Test
    public void testDeleteByCommentsId(){
        when(commentsRepository.findByCommentsId(anyInt(),anyInt()))
                .thenReturn(comments);
        doNothing().when(commentsRepository).deleteById(anyInt());

        commentsService.deleteByCommentsId(1,1);

        verify(commentsRepository, times(1)).findByCommentsId(anyInt(),anyInt());
        verify(commentsRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testDeleteByCommentsIdNull(){
        when(commentsRepository.findByCommentsId(anyInt(),anyInt()))
                .thenReturn(null);
        doNothing().when(commentsRepository).deleteById(anyInt());

        commentsService.deleteByCommentsId(1,1);

        verify(commentsRepository, times(1)).findByCommentsId(anyInt(),anyInt());
        verify(commentsRepository, times(0)).deleteById(anyInt());
    }

    @Test
    public void testAllList(){
        List<Comments> list = new ArrayList<>();
        list.add(comments);

        when(commentsRepository.allList(anyInt()))
                .thenReturn(list);

        List<Comments> actual = commentsService.allList(1);

        assertEquals(list,actual);
        assertEquals(1,actual.size());
    }

    @Test
    public void testAdd(){
        when(commentsRepository.save(any(Comments.class))).thenReturn(comments);

        commentsService.add(comments);

        verify(commentsRepository, times(1)).save(any(Comments.class));
    }

    @Test
    public void testAllListByTransactionId(){
        List<Comments> list = new ArrayList<>();
        list.add(comments);

        when(commentsRepository.allListByTransactionId(anyInt())).thenReturn(list);

        List<Comments> actualList = commentsService.allListByTransactionId(1);

        assertEquals(list,actualList);
        assertEquals(list.size(),actualList.size());

    }
}
