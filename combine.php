<?php
// =========================================================
// SANKALP RMS - WAR RECOMBINER FOR CPANEL
// Merges api_part1.bin and api_part2.bin into api.war
// =========================================================

$part1 = 'api_part1.bin';
$part2 = 'api_part2.bin';
$output = 'api.war';

if (!file_exists($part1) || !file_exists($part2)) {
    die("<h3 style='color:red;'>Error: 'api_part1.bin' or 'api_part2.bin' missing! Please upload both files first.</h3>");
}

$fout = fopen($output, 'wb');
if (!$fout) {
    die("<h3 style='color:red;'>Error: Cannot write to $output. Check file permissions.</h3>");
}

$fin1 = fopen($part1, 'rb');
while (!feof($fin1)) {
    fwrite($fout, fread($fin1, 8192));
}
fclose($fin1);

$fin2 = fopen($part2, 'rb');
while (!feof($fin2)) {
    fwrite($fout, fread($fin2, 8192));
}
fclose($fin2);

fclose($fout);

$size = filesize($output);
echo "<h2 style='color:green;'>SUCCESS! Generated api.war (" . number_format($size / 1048576, 2) . " MB)</h2>";
echo "<p>Now restart Apache Tomcat in your cPanel dashboard!</p>";
?>
