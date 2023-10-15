package com.soloSavings.controller;

import com.soloSavings.model.Comments;
import com.soloSavings.service.CommentsService;
import com.soloSavings.service.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    CommentsService commentsService;
    @Autowired
    SecurityContext securityContext;

    @RequestMapping(value = "/add/{transactionId}", method = RequestMethod.POST)
    public ResponseEntity<?> addComments (HttpServletRequest request, @RequestBody Comments comments,
                                          @PathVariable("transactionId") Integer transactionId){
        securityContext.setContext(SecurityContextHolder.getContext());
        comments.setUser_id(securityContext.getCurrentUser().getUser_id());
        comments.setTransaction_id(transactionId);
        commentsService.add(comments);
        securityContext.dispose();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<Comments> findComments (@RequestParam Integer comments_id){
        securityContext.setContext(SecurityContextHolder.getContext());
        Comments foundComment = commentsService.findByCommentsId(comments_id, securityContext.getCurrentUser().getUser_id());
        if (foundComment != null) {
            securityContext.dispose();
            return new ResponseEntity<>(foundComment, HttpStatus.OK);
        } else {
            securityContext.dispose();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/del/{commentsId}", method = RequestMethod.POST)
    public ResponseEntity<?> delComments (@PathVariable("commentsId") Integer comments_id){
        securityContext.setContext(SecurityContextHolder.getContext());
        commentsService.deleteByCommentsId(comments_id, securityContext.getCurrentUser().getUser_id());
        securityContext.dispose();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping( "/list/{transactionId}")
    @ResponseBody
    public ResponseEntity<List<Comments>> listComments (HttpServletRequest request,
                                                        @PathVariable("transactionId") Integer transactionId){
        securityContext.setContext(SecurityContextHolder.getContext());
        //List<Comments> commentsList = commentsService.allList(securityContext.getCurrentUser().getUser_id());
        List<Comments> commentsList = commentsService.allListByTransactionId(transactionId);
        if (commentsList != null && !commentsList.isEmpty()) {
            securityContext.dispose();
            return new ResponseEntity<>(commentsList, HttpStatus.OK);
        } else {
            //securityContext.dispose();
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(commentsList, HttpStatus.OK);
        }
    }
}
