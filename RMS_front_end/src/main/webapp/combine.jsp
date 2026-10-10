<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tomcat WAR Merger & Deployer</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f4f6f9; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .box { padding: 20px; border-radius: 5px; margin-top: 15px; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Tomcat WAR Auto-Merger</h1>
        <hr/>
<%!
    private void deleteRecursive(File f) {
        if (f.isDirectory()) {
            File[] children = f.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursive(child);
                }
            }
        }
        f.delete();
    }
%>
<%
    String realPath = application.getRealPath("/");
    File currentContext = realPath != null ? new File(realPath) : null;
    File webappsDir = currentContext != null ? currentContext.getParentFile() : null;

    File searchLocations[] = {
        currentContext,
        webappsDir,
        currentContext != null ? new File(currentContext, "..") : null
    };

    File foundPart1 = null;
    File foundPart2 = null;
    File targetDir = currentContext;

    for (File loc : searchLocations) {
        if (loc != null && loc.exists()) {
            File p1 = new File(loc, "api.part1");
            File p2 = new File(loc, "api.part2");
            if (p1.exists() && p2.exists()) {
                foundPart1 = p1;
                foundPart2 = p2;
                targetDir = loc;
                break;
            }
        }
    }

    if (foundPart1 != null && foundPart2 != null) {
        File target = new File(targetDir, "api.war");
        File apiFolder = new File(targetDir, "api");
        try {
            if (apiFolder.exists()) {
                deleteRecursive(apiFolder);
            }
            if (target.exists()) {
                target.delete();
            }
            
            FileOutputStream outStream = new FileOutputStream(target);
            FileInputStream in1 = new FileInputStream(foundPart1);
            FileInputStream in2 = new FileInputStream(foundPart2);
            
            byte[] buffer = new byte[16384];
            int bytesRead;
            while ((bytesRead = in1.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            while ((bytesRead = in2.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            
            in1.close();
            in2.close();
            outStream.close();
            
            foundPart1.delete();
            foundPart2.delete();
            
            out.println("<div class='box success'>");
            out.println("<h2>SUCCESS: api.war created successfully!</h2>");
            out.println("<p><strong>Target WAR Path:</strong> <code>" + target.getAbsolutePath() + "</code></p>");
            out.println("<p><strong>Total Size:</strong> " + String.format("%,d", target.length()) + " bytes</p>");
            out.println("<p>Old <code>api/</code> folder removed. Tomcat is auto-deploying the new <code>api.war</code> now!</p>");
            out.println("</div>");
        } catch (Exception e) {
            out.println("<div class='box error'>");
            out.println("<h2>ERROR: Failed to merge files</h2>");
            out.println("<pre>" + e.toString() + "</pre>");
            out.println("</div>");
        }
    } else {
        out.println("<div class='box error'>");
        out.println("<h2>Chunk files (api.part1 & api.part2) not detected yet</h2>");
        out.println("<p>Checked Context Directory: <code>" + (currentContext != null ? currentContext.getAbsolutePath() : "null") + "</code></p>");
        out.println("<h3>Files currently in context directory:</h3><ul>");
        if (currentContext != null && currentContext.exists()) {
            File[] files = currentContext.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    out.println("<li><code>" + f.getName() + "</code> " + (f.isDirectory() ? "[DIR]" : "(" + String.format("%,d", f.length()) + " bytes)") + "</li>");
                }
            } else {
                out.println("<li>(No files found)</li>");
            }
        }
        out.println("</ul></div>");
    }
%>
    </div>
</body>
</html>
