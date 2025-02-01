package com.example.notehub.dto;

import java.util.List;

public class PagedResult<T> {
    private List<T> results;
    private boolean hasMore;

    public PagedResult(List<T> results, boolean hasMore) {
        this.results = results;
        this.hasMore = hasMore;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
