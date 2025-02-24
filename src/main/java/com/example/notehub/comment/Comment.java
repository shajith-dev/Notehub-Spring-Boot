package com.example.notehub.comment;

public class Comment {
    private Long commentId;
    private Long createdBy;
    private Long noteId;
    private Long parentId;
    private Boolean isDeleted;
    private String content;

    public Comment(Long commentId, Long createdBy, Long noteId, Long parentId, Boolean isDeleted, String content) {
        this.commentId = commentId;
        this.createdBy = createdBy;
        this.noteId = noteId;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
        this.content = content;
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

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", createdBy=" + createdBy +
                ", noteId=" + noteId +
                ", parentId=" + parentId +
                ", isDeleted=" + isDeleted +
                ", content='" + content + '\'' +
                '}';
    }
}
