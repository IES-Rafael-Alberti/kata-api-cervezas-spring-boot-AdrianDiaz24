#!/bin/bash

# Script de prueba de la API Kata Cervezas
# Usando curl para probar todos los endpoints

BASE_URL="http://localhost:8080/api"

echo "================================"
echo "Pruebas API Kata Cervezas"
echo "================================"
echo ""

# ============================================================================
# BEERS - CRUD Completo
# ============================================================================

echo "--- OBTENER TODAS LAS CERVEZAS ---"
curl -X GET "$BASE_URL/beers" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- OBTENER CERVEZA CON ID 1 ---"
curl -X GET "$BASE_URL/beers/1" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- CREAR NUEVA CERVEZA ---"
curl -X POST "$BASE_URL/beers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Stout Artesanal",
    "description": "Cerveza negra tostada",
    "abv": 7.5,
    "ibu": 65.0,
    "breweryId": 1,
    "styleId": 3,
    "categoryId": 2
  }' \
  -s | jq .
echo ""

echo "--- ACTUALIZAR CERVEZA COMPLETA (PUT) ---"
curl -X PUT "$BASE_URL/beers/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pilsen Gold",
    "description": "Cerveza pilsen de primera calidad",
    "abv": 5.3,
    "ibu": 30.0,
    "breweryId": 1,
    "styleId": 1,
    "categoryId": 1
  }' \
  -s | jq .
echo ""

echo "--- ACTUALIZAR CERVEZA PARCIALMENTE (PATCH) ---"
curl -X PATCH "$BASE_URL/beers/1" \
  -H "Content-Type: application/json" \
  -d '{
    "abv": 5.2,
    "ibu": 28.0
  }' \
  -s | jq .
echo ""

echo "--- ELIMINAR CERVEZA ---"
curl -X DELETE "$BASE_URL/beers/5" \
  -H "Content-Type: application/json" \
  -s -w "\nStatus: %{http_code}\n"
echo ""

# ============================================================================
# BREWERIES - Read Only
# ============================================================================

echo "--- OBTENER TODAS LAS CERVECERÍAS ---"
curl -X GET "$BASE_URL/breweries" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- OBTENER CERVECERÍA CON ID 1 ---"
curl -X GET "$BASE_URL/breweries/1" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

# ============================================================================
# CATEGORIES - Read Only
# ============================================================================

echo "--- OBTENER TODAS LAS CATEGORÍAS ---"
curl -X GET "$BASE_URL/categories" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- OBTENER CATEGORÍA CON ID 1 ---"
curl -X GET "$BASE_URL/categories/1" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

# ============================================================================
# STYLES - Read Only
# ============================================================================

echo "--- OBTENER TODOS LOS ESTILOS ---"
curl -X GET "$BASE_URL/styles" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- OBTENER ESTILO CON ID 1 ---"
curl -X GET "$BASE_URL/styles/1" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

# ============================================================================
# CASOS DE ERROR
# ============================================================================

echo "--- ERROR: CERVEZA NO ENCONTRADA (404) ---"
curl -X GET "$BASE_URL/beers/9999" \
  -H "Content-Type: application/json" \
  -s | jq .
echo ""

echo "--- ERROR: DATOS INVÁLIDOS (400) ---"
curl -X POST "$BASE_URL/beers" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "AB",
    "abv": 150.0
  }' \
  -s | jq .
echo ""

echo "================================"
echo "Pruebas completadas"
echo "================================"

