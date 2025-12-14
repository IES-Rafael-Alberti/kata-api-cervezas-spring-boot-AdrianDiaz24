package com.kata.api.controller;

import com.kata.api.dto.BreweryDTO;
import com.kata.api.service.BreweryService;
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
@RequestMapping("/breweries")
@RequiredArgsConstructor
@Tag(name = "Brewery", description = "API para obtener información de cervecerías")
public class BreweryController {

    private final BreweryService breweryService;

    @GetMapping
    @Operation(summary = "Obtener todas las cervecerías", description = "Retorna una lista de todas las cervecerías registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cervecerías obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BreweryDTO.class)))
    })
    public ResponseEntity<List<BreweryDTO>> getAllBreweries() {
        log.info("GET /breweries - Obteniendo todas las cervecerías");
        return ResponseEntity.ok(breweryService.getAllBreweries());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cervecería por ID", description = "Retorna los detalles de una cervecería específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cervecería encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BreweryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cervecería no encontrada")
    })
    public ResponseEntity<BreweryDTO> getBreweryById(
            @Parameter(description = "ID de la cervecería")
            @PathVariable Integer id) {
        log.info("GET /breweries/{} - Obteniendo cervecería", id);
        return ResponseEntity.ok(breweryService.getBreweryById(id));
    }
}

