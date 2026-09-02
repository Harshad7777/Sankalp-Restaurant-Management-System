# Setup Tomcat 10.1 script
$tomcatUrl = "https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.34/bin/apache-tomcat-10.1.34-windows-x64.zip"
$zipFile = "tomcat10.zip"
$destDir = "tomcat10"

Write-Host "Downloading Apache Tomcat 10.1..."
Invoke-WebRequest -Uri $tomcatUrl -OutFile $zipFile

Write-Host "Extracting Tomcat..."
if (Test-Path $destDir) { Remove-Item -Recurse -Force $destDir }
Expand-Archive -Path $zipFile -DestinationPath $destDir -Force
Remove-Item -Force $zipFile

$tomcatHome = Get-ChildItem -Path $destDir | Where-Object { $_.PSIsContainer } | Select-Object -First 1
$webappsDir = Join-Path -Path $tomcatHome.FullName -ChildPath "webapps"

Write-Host "Clearing default Tomcat webapps in $webappsDir..."
Get-ChildItem -Path $webappsDir | Remove-Item -Recurse -Force

Write-Host "Deploying backend WAR to $webappsDir\api.war..."
if (Test-Path "api.war") { Copy-Item "api.war" -Destination "$webappsDir\api.war" -Force }
elseif (Test-Path "RMS_backend.war") { Copy-Item "RMS_backend.war" -Destination "$webappsDir\api.war" -Force }

Write-Host "Deploying frontend WAR to $webappsDir\ROOT.war..."
if (Test-Path "ROOT.war") { Copy-Item "ROOT.war" -Destination "$webappsDir\ROOT.war" -Force }
elseif (Test-Path "RMS_frontend.war") { Copy-Item "RMS_frontend.war" -Destination "$webappsDir\ROOT.war" -Force }

Write-Host "Deployment Complete!"
Write-Host "Tomcat Directory: $($tomcatHome.FullName)"
