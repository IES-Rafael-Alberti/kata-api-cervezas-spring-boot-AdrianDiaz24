#!/bin/bash

# Script de prueba de la API Kata Cervezas usando httpie
# Requiere: pip install httpie

BASE_URL="http://localhost:8080/api"

echo "================================"
echo "Pruebas API Kata Cervezas - httpie"
echo "================================"
echo ""

# ============================================================================
# BEERS - CRUD Completo
# ============================================================================

echo "--- OBTENER TODAS LAS CERVEZAS ---"
http GET $BASE_URL/beers
echo ""

echo "--- OBTENER CERVEZA CON ID 1 ---"
http GET $BASE_URL/beers/1
echo ""

echo "--- CREAR NUEVA CERVEZA ---"
http POST $BASE_URL/beers \
  name="IPA Craft" \
  description="India Pale Ale artesanal" \
  abv:=6.5 \
  ibu:=60.0 \
  breweryId:=1 \
  styleId:=2 \
  categoryId:=2
echo ""

echo "--- ACTUALIZAR CERVEZA COMPLETA (PUT) ---"
http PUT $BASE_URL/beers/1 \
  name="Pilsen Gold" \
  description="Cerveza pilsen premium" \
  abv:=5.3 \
  ibu:=30.0 \
  breweryId:=1 \
  styleId:=1 \
  categoryId:=1
echo ""

echo "--- ACTUALIZAR CERVEZA PARCIALMENTE (PATCH) ---"
http PATCH $BASE_URL/beers/1 \
  abv:=5.2 \
  ibu:=28.0
echo ""

echo "--- ELIMINAR CERVEZA ---"
http DELETE $BASE_URL/beers/5
echo ""

# ============================================================================
# BREWERIES - Read Only
# ============================================================================

echo "--- OBTENER TODAS LAS CERVECERÍAS ---"
http GET $BASE_URL/breweries
echo ""

echo "--- OBTENER CERVECERÍA CON ID 1 ---"
http GET $BASE_URL/breweries/1
echo ""

# ============================================================================
# CATEGORIES - Read Only
# ============================================================================

echo "--- OBTENER TODAS LAS CATEGORÍAS ---"
http GET $BASE_URL/categories
echo ""

echo "--- OBTENER CATEGORÍA CON ID 1 ---"
http GET $BASE_URL/categories/1
echo ""

# ============================================================================
# STYLES - Read Only
# ============================================================================

echo "--- OBTENER TODOS LOS ESTILOS ---"
http GET $BASE_URL/styles
echo ""

echo "--- OBTENER ESTILO CON ID 1 ---"
http GET $BASE_URL/styles/1
echo ""

# ============================================================================
# CASOS DE ERROR
# ============================================================================

echo "--- ERROR: CERVEZA NO ENCONTRADA (404) ---"
http GET $BASE_URL/beers/9999
echo ""

echo "--- ERROR: DATOS INVÁLIDOS (400) ---"
http POST $BASE_URL/beers \
  name="AB" \
  abv:=150.0
echo ""

echo "================================"
echo "Pruebas completadas"
echo "================================"

