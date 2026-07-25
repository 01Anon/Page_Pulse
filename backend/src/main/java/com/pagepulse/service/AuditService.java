package com.pagepulse.service;

import com.pagepulse.dto.AuditResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Core service that fetches a target URL, parses its HTML content,
 * and extracts structured SEO and health metrics.
 */
@Service
public class AuditService {

    private static final int TIMEOUT_MS = 15000;

    /**
     * Performs a full audit on the given URL.
     *
     * @param targetUrl the URL string to audit
     * @return AuditResponse containing all extracted metrics
     * @throws MalformedURLException   if the URL format is invalid
     * @throws SocketTimeoutException  if the connection or read times out
     * @throws IOException             if a network-level error occurs
     * @throws IllegalArgumentException if the response is not HTML
     */
    public AuditResponse audit(String targetUrl) throws IOException {
        // Step 1: Validate URL format before making any network call
        validateUrl(targetUrl);

        // Step 2: Fetch the page and measure response time
        long startTime = System.currentTimeMillis();

        Connection.Response response = Jsoup.connect(targetUrl)
                .userAgent("PagePulse/1.0 (URL Auditor)")
                .timeout(TIMEOUT_MS)
                .followRedirects(true)
                .ignoreHttpErrors(true)
                .execute();

        long responseTimeMs = System.currentTimeMillis() - startTime;
        int statusCode = response.statusCode();

        // Step 3: Check if the response is actually HTML
        String contentType = response.contentType();
        if (contentType == null || !contentType.toLowerCase().contains("text/html")) {
            return new AuditResponse()
                    .setError(true)
                    .setMessage("Non-HTML response. Content-Type: " + contentType)
                    .setStatusCode(statusCode)
                    .setResponseTimeMs(responseTimeMs);
        }

        // Step 4: Parse HTML into a DOM tree
        Document doc = response.parse();

        // Step 5: Extract all metrics
        String pageTitle = extractTitle(doc);
        String metaDescription = extractMetaDescription(doc);
        int h1Count = doc.select("h1").size();
        int imagesMissingAlt = countImagesMissingAlt(doc);
        int wordCount = countWords(doc);

        // Step 6: Build and return the audit report
        return new AuditResponse()
                .setError(false)
                .setStatusCode(statusCode)
                .setResponseTimeMs(responseTimeMs)
                .setPageTitle(pageTitle)
                .setMetaDescription(metaDescription)
                .setH1Count(h1Count)
                .setImagesMissingAlt(imagesMissingAlt)
                .setWordCount(wordCount);
    }

    /**
     * Validates that the given string is a well-formed HTTP or HTTPS URL.
     */
    private void validateUrl(String targetUrl) throws MalformedURLException {
        URL url = new URL(targetUrl);
        String protocol = url.getProtocol();
        if (!"http".equals(protocol) && !"https".equals(protocol)) {
            throw new MalformedURLException("Only HTTP and HTTPS protocols are supported.");
        }
    }

    /**
     * Extracts the page title from the document.
     */
    String extractTitle(Document doc) {
        String title = doc.title();
        return (title != null && !title.isBlank()) ? title.trim() : "No Title Found";
    }

    /**
     * Extracts the meta description content, if present.
     */
    String extractMetaDescription(Document doc) {
        Element meta = doc.selectFirst("meta[name=description]");
        if (meta != null) {
            String content = meta.attr("content");
            if (!content.isBlank()) {
                return content.trim();
            }
        }
        return "No Meta Description";
    }

    /**
     * Counts the number of <img> tags that have no alt attribute
     * or an empty/whitespace-only alt attribute.
     */
    int countImagesMissingAlt(Document doc) {
        Elements images = doc.select("img");
        int count = 0;
        for (Element img : images) {
            String alt = img.attr("alt");
            if (alt == null || alt.isBlank()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the approximate number of words in the visible body text.
     * Strips scripts, styles, and noscript blocks before counting.
     */
    int countWords(Document doc) {
        // Clone so we don't mutate the original document
        Document clone = doc.clone();
        clone.select("script, style, noscript").remove();

        String bodyText = clone.body() != null ? clone.body().text() : "";
        if (bodyText.isBlank()) {
            return 0;
        }

        // Split on whitespace and count tokens
        String[] words = bodyText.trim().split("\\s+");
        return words.length;
    }
}
