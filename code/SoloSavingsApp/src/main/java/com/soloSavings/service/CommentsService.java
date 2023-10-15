package com.soloSavings.service;

import com.soloSavings.model.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentsService {
    //Expenses
    public Comments findByCommentsId(Integer user_id, Integer comments_id);
    //Income
    public void deleteByCommentsId(Integer user_id, Integer comments_id);

    public List<Comments> allList(Integer user_id);

    public void add(Comments comments);


}
