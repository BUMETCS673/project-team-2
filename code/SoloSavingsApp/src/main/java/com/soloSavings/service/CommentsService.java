package com.soloSavings.service;

import com.soloSavings.model.Comments;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentsService {
    //Expenses
    public Comments findByCommentsId(Integer comments_id);
    //Income
    public void deleteByCommentsId(Integer comments_id);

    public List<Comments> allList(Integer user_id);

    public void add(Comments comments);


}
