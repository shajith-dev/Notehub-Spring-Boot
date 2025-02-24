package com.example.notehub.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Comment> getAllComments(Long noteId) {
        List<Comment> allComments = commentDAO.getAllComments(noteId);
        if (allComments == null || allComments.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, List<Comment>> commentsByParentId = new HashMap<>();

        List<Comment> rootComments = new ArrayList<>();

        for (Comment comment : allComments) {
            if (comment.getParentId() == null) {
                rootComments.add(comment);
            } else {
                commentsByParentId
                        .computeIfAbsent(comment.getParentId(), k -> new ArrayList<>())
                        .add(comment);
            }
        }

        for (Comment comment : allComments) {
            if (commentsByParentId.containsKey(comment.getCommentId())) {
                comment.setReplies(commentsByParentId.get(comment.getCommentId()));
            }
        }

        return rootComments;
    }


    public Comment editComment(Long noteId,Long commentId,String content){
        return commentDAO.editComment(noteId,commentId,content);
    }

}
