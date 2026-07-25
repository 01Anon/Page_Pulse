package com.pagepulse.dto;

/**
 * Data Transfer Object for the audit report returned to the client.
 * Contains all extracted metrics from the target URL.
 */
public class AuditResponse {

    private boolean error;
    private String message;
    private int statusCode;
    private long responseTimeMs;
    private String pageTitle;
    private String metaDescription;
    private int h1Count;
    private int imagesMissingAlt;
    private int wordCount;

    // Default constructor
    public AuditResponse() {}

    // --- Builder-style setters for clean construction ---

    public AuditResponse setError(boolean error) {
        this.error = error;
        return this;
    }

    public AuditResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public AuditResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public AuditResponse setResponseTimeMs(long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
        return this;
    }

    public AuditResponse setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
        return this;
    }

    public AuditResponse setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
        return this;
    }

    public AuditResponse setH1Count(int h1Count) {
        this.h1Count = h1Count;
        return this;
    }

    public AuditResponse setImagesMissingAlt(int imagesMissingAlt) {
        this.imagesMissingAlt = imagesMissingAlt;
        return this;
    }

    public AuditResponse setWordCount(int wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    // --- Getters (required by Jackson for serialization) ---

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public int getH1Count() {
        return h1Count;
    }

    public int getImagesMissingAlt() {
        return imagesMissingAlt;
    }

    public int getWordCount() {
        return wordCount;
    }
}
