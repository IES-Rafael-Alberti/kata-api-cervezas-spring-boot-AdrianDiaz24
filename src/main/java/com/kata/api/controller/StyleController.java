package com.kata.api.controller;

import com.kata.api.dto.StyleDTO;
import com.kata.api.service.StyleService;
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
@RequestMapping("/styles")
@RequiredArgsConstructor
@Tag(name = "Style", description = "API para obtener información de estilos de cerveza")
public class StyleController {

    private final StyleService styleService;

    @GetMapping
    @Operation(summary = "Obtener todos los estilos", description = "Retorna una lista de todos los estilos de cerveza registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estilos obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StyleDTO.class)))
    })
    public ResponseEntity<List<StyleDTO>> getAllStyles() {
        log.info("GET /styles - Obteniendo todos los estilos");
        return ResponseEntity.ok(styleService.getAllStyles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estilo por ID", description = "Retorna los detalles de un estilo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estilo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StyleDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estilo no encontrado")
    })
    public ResponseEntity<StyleDTO> getStyleById(
            @Parameter(description = "ID del estilo")
            @PathVariable Integer id) {
        log.info("GET /styles/{} - Obteniendo estilo", id);
        return ResponseEntity.ok(styleService.getStyleById(id));
    }
}

