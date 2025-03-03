package com.example.notehub.request;

import java.time.LocalDateTime;

public class Request {
    private Long requestId;
    private String title;
    private Long subjectId;
    private Long requestedBy;
    private LocalDateTime createdAt;
    private Boolean resolved;
    private Long resolvedBy;
    private Boolean isDeleted;
    private String description;

    public Request(Long requestId, String title, Long subjectId, Long requestedBy, LocalDateTime createdAt, Boolean resolved, Long resolvedBy, Boolean isDeleted, String description) {
        this.requestId = requestId;
        this.title = title;
        this.subjectId = subjectId;
        this.requestedBy = requestedBy;
        this.createdAt = createdAt;
        this.resolved = resolved;
        this.resolvedBy = resolvedBy;
        this.isDeleted = isDeleted;
        this.description = description;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    public Long getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(Long resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", title='" + title + '\'' +
                ", subjectId=" + subjectId +
                ", requestedBy=" + requestedBy +
                ", createdAt=" + createdAt +
                ", resolved=" + resolved +
                ", resolvedBy=" + resolvedBy +
                ", isDeleted=" + isDeleted +
                ", description='" + description + '\'' +
                '}';
    }
}

