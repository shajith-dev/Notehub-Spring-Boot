package com.example.notehub.comment;

import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private Long commentId;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long noteId;
    private Long parentId;
    private Boolean isDeleted;
    private String content;
    private List<Comment> replies;

    public Comment(Long commentId, Long createdBy, LocalDateTime createdAt, Long noteId, Long parentId, Boolean isDeleted, String content, List<Comment> replies) {
        this.commentId = commentId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.noteId = noteId;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
        this.content = content;
        this.replies = replies;
    }

    public Comment(Long commentId, Long createdBy, Long noteId, Long parentId, Boolean isDeleted, String content, List<Comment> replies) {
        this.commentId = commentId;
        this.createdBy = createdBy;
        this.noteId = noteId;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
        this.content = content;
        this.replies = replies;
    }

    public Comment(){}

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", noteId=" + noteId +
                ", parentId=" + parentId +
                ", isDeleted=" + isDeleted +
                ", content='" + content + '\'' +
                ", replies=" + replies +
                '}';
    }
}
