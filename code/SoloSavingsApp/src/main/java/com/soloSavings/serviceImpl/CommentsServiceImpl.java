package com.soloSavings.serviceImpl;

import com.soloSavings.model.Comments;
import com.soloSavings.repository.CommentsRepository;
import com.soloSavings.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentsRepository commentsRepository;
    @Override
    public Comments findByCommentsId(Integer comments_id) {
        return commentsRepository.findByCommentsId(comments_id);
    }

    @Override
    public void deleteByCommentsId(Integer comments_id) {
        commentsRepository.deleteById(comments_id);
    }

    @Override
    public List<Comments> allList(Integer user_id) {
        return commentsRepository.allList(user_id);
    }

    @Override
    public void add(Comments comments) {
        commentsRepository.save(comments);
    }

    @Override
    public List<Comments> allListByTransactionId(Integer transactionId) {
        return commentsRepository.allListByTransactionId(transactionId);
    }
}
