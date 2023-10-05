package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Comments;
import com.soloSavings.model.Transaction;
import com.soloSavings.service.CommentsService;
import com.soloSavings.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    CommentsService commentsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addComments (HttpServletRequest request, @RequestBody Comments comments){
            Integer userId=(Integer)request.getSession().getAttribute("userId");
            comments.setUser_id(userId);
            commentsService.add(comments);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<Comments> findComments (@RequestParam Integer comments_id){
        Comments foundComment = commentsService.findByCommentsId(comments_id);
        if (foundComment != null) {
            return new ResponseEntity<>(foundComment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/del/{commentsId}", method = RequestMethod.POST)
    public ResponseEntity<?> delComments (@PathVariable("commentsId") Integer comments_id){
        commentsService.deleteByCommentsId(comments_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping( "/list")
    @ResponseBody
    public ResponseEntity<List<Comments>> listComments (HttpServletRequest request){
        Integer userId=(Integer)request.getSession().getAttribute("userId");
        List<Comments> commentsList = commentsService.allList(userId);
        if (commentsList != null && !commentsList.isEmpty()) {
            return new ResponseEntity<>(commentsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
