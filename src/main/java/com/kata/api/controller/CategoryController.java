package com.kata.api.controller;

import com.kata.api.dto.CategoryDTO;
import com.kata.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "API para obtener información de categorías")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Obtener todas las categorías", description = "Retorna una lista de todas las categorías registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class)))
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        log.info("GET /categories - Obteniendo todas las categorías");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Retorna los detalles de una categoría específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID de la categoría")
            @PathVariable Integer id) {
        log.info("GET /categories/{} - Obteniendo categoría", id);
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}

