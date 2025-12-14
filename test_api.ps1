# Script de prueba de la API Kata Cervezas
# Para Windows PowerShell

$BASE_URL = "http://localhost:8080/api"

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Pruebas API Kata Cervezas" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# ============================================================================
# BEERS - CRUD Completo
# ============================================================================

Write-Host "--- OBTENER TODAS LAS CERVEZAS ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/beers" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- OBTENER CERVEZA CON ID 1 ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/beers/1" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- CREAR NUEVA CERVEZA ---" -ForegroundColor Green
$beerBody = @{
    name = "Stout Artesanal"
    description = "Cerveza negra tostada"
    abv = 7.5
    ibu = 65.0
    breweryId = 1
    styleId = 3
    categoryId = 2
} | ConvertTo-Json

Invoke-RestMethod -Uri "$BASE_URL/beers" `
    -Method POST `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $beerBody | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- ACTUALIZAR CERVEZA COMPLETA (PUT) ---" -ForegroundColor Green
$updateBody = @{
    name = "Pilsen Gold"
    description = "Cerveza pilsen de primera calidad"
    abv = 5.3
    ibu = 30.0
    breweryId = 1
    styleId = 1
    categoryId = 1
} | ConvertTo-Json

Invoke-RestMethod -Uri "$BASE_URL/beers/1" `
    -Method PUT `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $updateBody | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- ACTUALIZAR CERVEZA PARCIALMENTE (PATCH) ---" -ForegroundColor Green
$patchBody = @{
    abv = 5.2
    ibu = 28.0
} | ConvertTo-Json

Invoke-RestMethod -Uri "$BASE_URL/beers/1" `
    -Method PATCH `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $patchBody | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- ELIMINAR CERVEZA ---" -ForegroundColor Green
$response = Invoke-RestMethod -Uri "$BASE_URL/beers/5" `
    -Method DELETE `
    -Headers @{"Content-Type" = "application/json"} `
    -StatusCodeVariable "statusCode"
Write-Host "Status Code: $statusCode" -ForegroundColor Yellow
Write-Host ""

# ============================================================================
# BREWERIES - Read Only
# ============================================================================

Write-Host "--- OBTENER TODAS LAS CERVECERÍAS ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/breweries" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- OBTENER CERVECERÍA CON ID 1 ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/breweries/1" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

# ============================================================================
# CATEGORIES - Read Only
# ============================================================================

Write-Host "--- OBTENER TODAS LAS CATEGORÍAS ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/categories" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- OBTENER CATEGORÍA CON ID 1 ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/categories/1" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

# ============================================================================
# STYLES - Read Only
# ============================================================================

Write-Host "--- OBTENER TODOS LOS ESTILOS ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/styles" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

Write-Host "--- OBTENER ESTILO CON ID 1 ---" -ForegroundColor Green
Invoke-RestMethod -Uri "$BASE_URL/styles/1" `
    -Method GET `
    -Headers @{"Content-Type" = "application/json"} | ConvertTo-Json | Write-Host
Write-Host ""

# ============================================================================
# CASOS DE ERROR
# ============================================================================

Write-Host "--- ERROR: CERVEZA NO ENCONTRADA (404) ---" -ForegroundColor Red
try {
    Invoke-RestMethod -Uri "$BASE_URL/beers/9999" `
        -Method GET `
        -Headers @{"Content-Type" = "application/json"}
} catch {
    $_.Exception.Response.StatusCode | Write-Host
    $_.Content | ConvertFrom-Json | ConvertTo-Json | Write-Host
}
Write-Host ""

Write-Host "--- ERROR: DATOS INVÁLIDOS (400) ---" -ForegroundColor Red
$invalidBody = @{
    name = "AB"
    abv = 150.0
} | ConvertTo-Json

try {
    Invoke-RestMethod -Uri "$BASE_URL/beers" `
        -Method POST `
        -Headers @{"Content-Type" = "application/json"} `
        -Body $invalidBody
} catch {
    $_.Exception.Response.StatusCode | Write-Host
    $_.Content | ConvertFrom-Json | ConvertTo-Json | Write-Host
}
Write-Host ""

Write-Host "================================" -ForegroundColor Cyan
Write-Host "Pruebas completadas" -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan

