package com.ticket.ticket.entities;

import java.io.Serializable;
import java.util.Map;

public class CachedResponse<T> implements Serializable{
    private T body;
    private int status;
    private Map<String, String> headers;

    // Constructors, getters, setters
    public CachedResponse() {}

    public CachedResponse(T body, int status, Map<String, String> headers) {
        this.body = body;
        this.status = status;
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
