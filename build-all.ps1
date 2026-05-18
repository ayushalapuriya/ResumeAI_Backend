Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "          ResumeAI Microservices - Local Build Script     " -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host ""

$services = @(
    "SERVICE-REGISTRY",
    "RESUMEAI-GATEWAY",
    "AUTH-SERVICE",
    "RESUME-SERVICE",
    "TEMPLATE-SERVICE",
    "RESUME-SECTION-SERVICE",
    "AICONTENT-SERVICE",
    "NOTIFICATION-SERVICE"
)

$successCount = 0
$failCount = 0

foreach ($service in $services) {
    $servicePath = "$service"
    Write-Host "----------------------------------------------------------" -ForegroundColor Gray
    Write-Host ">>> Building service: $service..." -ForegroundColor Yellow
    
    if (-not (Test-Path $servicePath)) {
        Write-Error "Directory not found: $servicePath"
        $failCount++
        continue
    }

    Push-Location $servicePath
    
    # Check if maven wrapper exists, otherwise use standard maven
    if (Test-Path "mvnw.cmd") {
        Write-Host "Executing build using Maven Wrapper (mvnw.cmd)..." -ForegroundColor DarkGray
        & .\mvnw.cmd clean package "-Dmaven.test.skip=true"
    } else {
        Write-Host "Maven Wrapper not found. Executing build using system maven (mvn)..." -ForegroundColor DarkGray
        & mvn clean package "-Dmaven.test.skip=true"
    }
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SUCCESS: $service built successfully!" -ForegroundColor Green
        $successCount++
    } else {
        Write-Host "ERROR: $service compilation failed!" -ForegroundColor Red
        $failCount++
    }
    
    Pop-Location
}

Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host "Build Summary:" -ForegroundColor Cyan
Write-Host "  Successful builds: $successCount" -ForegroundColor Green
$failColor = "Gray"
if ($failCount -gt 0) { $failColor = "Red" }
Write-Host "  Failed builds:     $failCount" -ForegroundColor $failColor
Write-Host "==========================================================" -ForegroundColor Cyan

if ($failCount -gt 0) {
    Exit 1
}
