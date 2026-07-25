const API_URL = 'http://localhost:8080/api/audit';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('audit-form');
    const input = document.getElementById('url-input');
    const resultsSection = document.getElementById('results-section');
    const loader = document.getElementById('loader');
    const errorMessage = document.getElementById('error-message');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        let url = input.value.trim();

        // Auto-prefix with https if no protocol is provided
        if (!url.startsWith('http://') && !url.startsWith('https://')) {
            url = 'https://' + url;
            input.value = url;
        }

        // Reset UI state before each request
        errorMessage.classList.add('hidden');
        resultsSection.classList.add('hidden');
        loader.classList.remove('hidden');

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ url: url })
            });

            const data = await response.json();
            loader.classList.add('hidden');

            if (!response.ok || data.error) {
                // Show backend error message or HTTP error
                showError(data.message || 'An error occurred while auditing the URL.');
                return;
            }

            renderResults(url, data);

        } catch (error) {
            loader.classList.add('hidden');
            showError('Network error. Make sure the backend server is running on port 8080.');
        }
    });

    /**
     * Displays an error message panel below the search bar.
     */
    function showError(message) {
        errorMessage.textContent = message;
        errorMessage.classList.remove('hidden');
    }

    /**
     * Populates the results section with data from the audit response.
     */
    function renderResults(url, data) {
        // Target URL badge
        document.getElementById('target-display').textContent = url;

        // HTTP Status with color-coded indicator
        const statusEl = document.getElementById('val-status');
        const statusInd = document.getElementById('ind-status');
        statusEl.textContent = data.statusCode;
        statusInd.className = 'metric-indicator';

        if (data.statusCode >= 200 && data.statusCode < 300) {
            statusInd.classList.add('indicator-success');
            statusEl.style.color = 'var(--success)';
        } else if (data.statusCode >= 300 && data.statusCode < 400) {
            statusInd.classList.add('indicator-warning');
            statusEl.style.color = 'var(--warning)';
        } else {
            statusInd.classList.add('indicator-error');
            statusEl.style.color = 'var(--error)';
        }

        // Response time
        document.getElementById('val-time').textContent = data.responseTimeMs;

        // H1 count
        document.getElementById('val-h1').textContent = data.h1Count;

        // Images missing alt — highlight if > 0
        const altEl = document.getElementById('val-alt');
        altEl.textContent = data.imagesMissingAlt;
        altEl.style.color = data.imagesMissingAlt > 0 ? 'var(--warning)' : 'inherit';

        // Word count with locale formatting (e.g. 1,234)
        document.getElementById('val-words').textContent = data.wordCount.toLocaleString();

        // Text details
        document.getElementById('val-title').textContent = data.pageTitle;
        document.getElementById('val-desc').textContent = data.metaDescription;

        // Reveal results section with animation
        resultsSection.classList.remove('hidden');
    }
});
