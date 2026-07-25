package com.pagepulse.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for incoming audit requests.
 * Carries the target URL submitted by the client.
 */
public class AuditRequest {

    @NotBlank(message = "URL must not be blank")
    private String url;

    // Default constructor (required by Jackson)
    public AuditRequest() {}

    public AuditRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
