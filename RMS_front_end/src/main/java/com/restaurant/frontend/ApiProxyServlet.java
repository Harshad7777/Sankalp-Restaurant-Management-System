package com.restaurant.frontend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Lightweight reverse-proxy servlet for /api/* requests when the JSP frontend (ROOT.war)
 * is hosted separately from the Spring Boot backend (e.g., on Render Free Web Services).
 * Controlled via the API_PROXY_TARGET environment variable.
 */
@WebServlet(name = "ApiProxyServlet", urlPatterns = "/api/*")
public class ApiProxyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Set<String> HOP_BY_HOP_HEADERS = Set.of(
            "host", "connection", "keep-alive", "proxy-authenticate",
            "proxy-authorization", "te", "trailers", "transfer-encoding",
            "upgrade", "content-length"
    );

    private transient HttpClient httpClient;
    private String proxyTarget;

    @Override
    public void init() throws ServletException {
        String envTarget = System.getenv("API_PROXY_TARGET");
        if (envTarget == null || envTarget.isBlank()) {
            envTarget = getServletContext().getInitParameter("apiProxyTarget");
        }
        if (envTarget != null && !envTarget.isBlank()) {
            this.proxyTarget = envTarget.replaceAll("/+$", "");
        }
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (proxyTarget == null || proxyTarget.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "API_PROXY_TARGET is not configured on the frontend service.");
            return;
        }

        String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "";
        String query = req.getQueryString();
        String targetUrl = proxyTarget + "/api" + pathInfo + (query != null ? "?" + query : "");

        HttpRequest.BodyPublisher bodyPublisher;
        String method = req.getMethod().toUpperCase();
        if ("GET".equals(method) || "HEAD".equals(method) || "OPTIONS".equals(method)) {
            bodyPublisher = HttpRequest.BodyPublishers.noBody();
        } else {
            byte[] bodyBytes = req.getInputStream().readAllBytes();
            bodyPublisher = bodyBytes.length > 0
                    ? HttpRequest.BodyPublishers.ofByteArray(bodyBytes)
                    : HttpRequest.BodyPublishers.noBody();
        }

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .timeout(Duration.ofSeconds(60))
                .method(method, bodyPublisher);

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (HOP_BY_HOP_HEADERS.contains(name.toLowerCase())) {
                continue;
            }
            Enumeration<String> values = req.getHeaders(name);
            while (values.hasMoreElements()) {
                builder.header(name, values.nextElement());
            }
        }

        try {
            HttpResponse<InputStream> backendResp =
                    httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofInputStream());

            resp.setStatus(backendResp.statusCode());

            for (Map.Entry<String, List<String>> entry : backendResp.headers().map().entrySet()) {
                String name = entry.getKey();
                if (name == null || HOP_BY_HOP_HEADERS.contains(name.toLowerCase())) {
                    continue;
                }
                for (String value : entry.getValue()) {
                    resp.addHeader(name, value);
                }
            }

            try (InputStream in = backendResp.body(); OutputStream out = resp.getOutputStream()) {
                in.transferTo(out);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Backend request interrupted.");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "Failed to reach backend service: " + e.getMessage());
        }
    }
}
