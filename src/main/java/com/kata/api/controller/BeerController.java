package com.kata.api.controller;

import com.kata.api.dto.BeerDTO;
import com.kata.api.service.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/beers")
@RequiredArgsConstructor
@Tag(name = "Beer", description = "API para gestionar cervezas")
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    @Operation(summary = "Obtener todas las cervezas", description = "Retorna una lista de todas las cervezas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cervezas obtenida correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDTO.class)))
    })
    public ResponseEntity<List<BeerDTO>> getAllBeers() {
        log.info("GET /beers - Obteniendo todas las cervezas");
        return ResponseEntity.ok(beerService.getAllBeers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cerveza por ID", description = "Retorna los detalles de una cerveza específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada")
    })
    public ResponseEntity<BeerDTO> getBeerById(
            @Parameter(description = "ID de la cerveza")
            @PathVariable Integer id) {
        log.info("GET /beers/{} - Obteniendo cerveza", id);
        return ResponseEntity.ok(beerService.getBeerById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva cerveza", description = "Crea una nueva cerveza en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cerveza creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<BeerDTO> createBeer(
            @Valid @RequestBody BeerDTO beerDTO) {
        log.info("POST /beers - Creando nueva cerveza: {}", beerDTO.getName());
        BeerDTO created = beerService.createBeer(beerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cerveza", description = "Actualiza completamente una cerveza existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<BeerDTO> updateBeer(
            @Parameter(description = "ID de la cerveza")
            @PathVariable Integer id,
            @Valid @RequestBody BeerDTO beerDTO) {
        log.info("PUT /beers/{} - Actualizando cerveza", id);
        return ResponseEntity.ok(beerService.updateBeer(id, beerDTO));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualización parcial de cerveza", description = "Actualiza parcialmente una cerveza (solo los campos proporcionados)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cerveza actualizada parcialmente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BeerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<BeerDTO> partialUpdateBeer(
            @Parameter(description = "ID de la cerveza")
            @PathVariable Integer id,
            @RequestBody BeerDTO beerDTO) {
        log.info("PATCH /beers/{} - Actualización parcial de cerveza", id);
        return ResponseEntity.ok(beerService.partialUpdateBeer(id, beerDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cerveza", description = "Elimina una cerveza de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cerveza eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cerveza no encontrada")
    })
    public ResponseEntity<Void> deleteBeer(
            @Parameter(description = "ID de la cerveza")
            @PathVariable Integer id) {
        log.info("DELETE /beers/{} - Eliminando cerveza", id);
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}

