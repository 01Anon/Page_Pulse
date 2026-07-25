package com.pagepulse.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditServiceTest {

    private AuditService auditService;

    @BeforeEach
    void setUp() {
        auditService = new AuditService();
    }

    @Test
    void testHappyPath_AllMetricsPresent() {
        String html = "<html><head>" +
                "<title>My Perfect Page</title>" +
                "<meta name='description' content='This is a great description.'>" +
                "</head><body>" +
                "<h1>Welcome to the site</h1>" +
                "<p>This page has exactly eight words in total.</p>" +
                "<img src='test1.png' alt='A valid image'>" +
                "</body></html>";
        
        Document doc = Jsoup.parse(html);

        assertEquals("My Perfect Page", auditService.extractTitle(doc));
        assertEquals("This is a great description.", auditService.extractMetaDescription(doc));
        assertEquals(0, auditService.countImagesMissingAlt(doc));
        // "Welcome to the site" (4) + "This page has exactly eight words in total." (8) = 12 words
        assertEquals(12, auditService.countWords(doc));
    }

    @Test
    void testFailureCase_MissingMetadataAndAlt() {
        String html = "<html><head></head><body>" +
                "<div>Just some text.</div>" +
                "<img src='no-alt.png'>" +
                "<img src='empty-alt.png' alt='   '>" +
                "</body></html>";
        
        Document doc = Jsoup.parse(html);

        assertEquals("No Title Found", auditService.extractTitle(doc));
        assertEquals("No Meta Description", auditService.extractMetaDescription(doc));
        assertEquals(2, auditService.countImagesMissingAlt(doc));
        assertEquals(3, auditService.countWords(doc)); // "Just some text."
    }

    @Test
    void testFailureCase_ScriptAndStyleExclusionForWordCount() {
        String html = "<html><body>" +
                "<script>const a = 'do not count me';</script>" +
                "<style>.hidden { display: none; }</style>" +
                "<p>Only count these words.</p>" +
                "</body></html>";
        
        Document doc = Jsoup.parse(html);

        // Word count should ignore script and style contents
        assertEquals(4, auditService.countWords(doc));
    }
}
