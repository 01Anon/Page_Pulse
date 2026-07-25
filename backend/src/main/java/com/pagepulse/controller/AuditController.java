package com.pagepulse.controller;

import com.pagepulse.dto.AuditRequest;
import com.pagepulse.dto.AuditResponse;
import com.pagepulse.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing the /api/audit endpoint.
 * Accepts a URL and delegates to AuditService for processing.
 */
@RestController
@RequestMapping("/api")
public class AuditController {

    private final AuditService auditService;

    // Constructor injection (recommended by Spring — no need for @Autowired)
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * POST /api/audit
     * Accepts a JSON body with a "url" field and returns the audit report.
     *
     * @param request validated request body containing the target URL
     * @return JSON audit report with HTTP metrics, SEO data, and content stats
     */
    @PostMapping("/audit")
    public ResponseEntity<AuditResponse> auditUrl(@Valid @RequestBody AuditRequest request) throws Exception {
        AuditResponse response = auditService.audit(request.getUrl());
        return ResponseEntity.ok(response);
    }
}
