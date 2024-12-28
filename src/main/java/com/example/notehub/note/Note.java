package com.example.notehub.note;

public class Note {
    private Long noteId;
    private Long createdBy;
    private Boolean isDeleted;
    private String title;
    private String description;
    private String url;
    private Long likes;
    private Long subjectId;

    public Note(){}

    public Note(Long noteId, Long createdBy, Boolean isDeleted, String title, String description, String url, Long likes, Long subjectId) {
        this.noteId = noteId;
        this.createdBy = createdBy;
        this.isDeleted = isDeleted;
        this.title = title;
        this.description = description;
        this.url = url;
        this.likes = likes;
        this.subjectId = subjectId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", createdBy=" + createdBy +
                ", isDeleted=" + isDeleted +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", likes=" + likes +
                ", subjectId=" + subjectId +
                '}';
    }
}
