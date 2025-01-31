package com.example.notehub.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes/{noteId}/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment createComment(@PathVariable("noteId") Long noteId, @RequestBody Comment comment){
        comment.setNoteId(noteId);
        return commentService.createComment(comment);
    }

    @DeleteMapping(path = "/{commentId}")
    public void deleteComment(@PathVariable("noteId") Long noteId, @PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
    }

    @GetMapping
    public List<Comment> getAllComments(@PathVariable("noteId") Long noteId){
        return commentService.getAllComments(noteId);
    }

    @PutMapping(path = "/{commentId}")
    public Comment editComment(@PathVariable("noteId") Long noteId, @PathVariable("commentId") Long commentId, Comment comment){
        return commentService.editComment(noteId,commentId,comment.getContent());
    }

}
