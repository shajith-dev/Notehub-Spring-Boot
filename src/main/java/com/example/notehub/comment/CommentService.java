package com.example.notehub.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    public Comment createComment(Comment comment){
        return commentDAO.createComment(comment);
    }

    public void deleteComment(Long commentId){
        commentDAO.deleteComment(commentId);
    }

    public List<Comment> getAllComments(Long noteId){
        return commentDAO.getAllComments(noteId);
    }

    public Comment editComment(Long noteId,Long commentId,String content){
        return commentDAO.editComment(noteId,commentId,content);
    }

}
