<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sankalp RMS - WAR Recombiner</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #0f172a; color: #f8fafc; padding: 40px; }
        .card { background: #1e293b; padding: 30px; border-radius: 12px; max-width: 650px; margin: 0 auto; box-shadow: 0 10px 25px rgba(0,0,0,0.5); border: 1px solid #334155; }
        h1 { color: #f59e0b; margin-top: 0; font-size: 1.6rem; }
        .success { color: #10b981; font-weight: bold; font-size: 1.2rem; }
        .error { color: #ef4444; font-weight: bold; }
        code { background: #0f172a; padding: 4px 8px; border-radius: 4px; color: #38bdf8; }
    </style>
</head>
<body>
<div class="card">
    <h1>Sankalp RMS — WAR Recombiner</h1>
<%
    String realPath = application.getRealPath("/");
    File appDir = new File(realPath);
    File parentDir = appDir.getParentFile();
    
    // Try multiple search locations for api_part1.bin and api_part2.bin
    File part1 = new File(appDir, "api_part1.bin");
    File part2 = new File(appDir, "api_part2.bin");
    File targetWar = new File(appDir, "api.war");

    if (!part1.exists() && parentDir != null) {
        File p1 = new File(parentDir, "api_part1.bin");
        File p2 = new File(parentDir, "api_part2.bin");
        if (p1.exists() && p2.exists()) {
            part1 = p1;
            part2 = p2;
            targetWar = new File(parentDir, "api.war");
        }
    }

    try {
        if (!part1.exists() || !part2.exists()) {
            out.println("<p class='error'>Error: api_part1.bin or api_part2.bin not found!</p>");
            out.println("<p>Checked path: <code>" + part1.getAbsolutePath() + "</code></p>");
            out.println("<p>Please upload <code>api_part1.bin</code> and <code>api_part2.bin</code> to the same directory as <code>combine.jsp</code>.</p>");
        } else {
            FileOutputStream fos = new FileOutputStream(targetWar);
            FileInputStream fis1 = new FileInputStream(part1);
            byte[] buffer = new byte[16384];
            int bytesRead;
            while ((bytesRead = fis1.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis1.close();

            FileInputStream fis2 = new FileInputStream(part2);
            while ((bytesRead = fis2.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fis2.close();
            fos.close();

            out.println("<p class='success'>SUCCESS! Created api.war (" + String.format("%.2f", targetWar.length() / (1024.0 * 1024.0)) + " MB)</p>");
            out.println("<p>Target Path: <code>" + targetWar.getAbsolutePath() + "</code></p>");
            out.println("<p>Now restart Apache Tomcat in your cPanel dashboard!</p>");
        }
    } catch (Exception e) {
        out.println("<p class='error'>Exception occurred: " + e.getMessage() + "</p>");
        out.println("<pre style='color:#f87171;'>");
        e.printStackTrace(new PrintWriter(out));
        out.println("</pre>");
    }
%>
</div>
</body>
</html>
