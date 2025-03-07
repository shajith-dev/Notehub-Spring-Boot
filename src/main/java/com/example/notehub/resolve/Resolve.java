package com.example.notehub.resolve;

import java.time.LocalDateTime;

public class Resolve {
    private Long resolveId;
    private Long submittedBy;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Long requestId;
    private String url;
    private Boolean approved;
    private String title;

    public Resolve(Long resolveId, Long submittedBy, LocalDateTime createdAt, Boolean isDeleted, Long requestId, String url, Boolean approved, String title) {
        this.resolveId = resolveId;
        this.submittedBy = submittedBy;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
        this.requestId = requestId;
        this.url = url;
        this.approved = approved;
        this.title = title;
    }

    public Long getResolveId() {
        return resolveId;
    }

    public void setResolveId(Long resolveId) {
        this.resolveId = resolveId;
    }

    public Long getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Long submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Resolve{" +
                "resolveId=" + resolveId +
                ", submittedBy=" + submittedBy +
                ", createdAt=" + createdAt +
                ", isDeleted=" + isDeleted +
                ", requestId=" + requestId +
                ", url='" + url + '\'' +
                ", approved=" + approved +
                ", title='" + title + '\'' +
                '}';
    }
}
