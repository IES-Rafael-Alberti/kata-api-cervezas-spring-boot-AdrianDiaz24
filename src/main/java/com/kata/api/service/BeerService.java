package com.kata.api.service;

import com.kata.api.dto.BeerDTO;
import com.kata.api.entity.Beer;
import com.kata.api.entity.Brewery;
import com.kata.api.entity.Category;
import com.kata.api.entity.Style;
import com.kata.api.exception.ResourceNotFoundException;
import com.kata.api.mapper.BeerMapper;
import com.kata.api.repository.BeerRepository;
import com.kata.api.repository.BreweryRepository;
import com.kata.api.repository.CategoryRepository;
import com.kata.api.repository.StyleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final BreweryRepository breweryRepository;
    private final StyleRepository styleRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<BeerDTO> getAllBeers() {
        log.info("Obteniendo todas las cervezas");
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BeerDTO getBeerById(Integer id) {
        log.info("Obteniendo cerveza con id: {}", id);
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza no encontrada con id: " + id));
        return beerMapper.toDTO(beer);
    }

    @Transactional
    public BeerDTO createBeer(BeerDTO beerDTO) {
        log.info("Creando nueva cerveza: {}", beerDTO.getName());
        Beer beer = beerMapper.toEntity(beerDTO);

        // Cargar relaciones si existen IDs
        if (beerDTO.getBreweryId() != null) {
            Brewery brewery = breweryRepository.findById(beerDTO.getBreweryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cervecería no encontrada con id: " + beerDTO.getBreweryId()));
            beer.setBrewery(brewery);
        }

        if (beerDTO.getStyleId() != null) {
            Style style = styleRepository.findById(beerDTO.getStyleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estilo no encontrado con id: " + beerDTO.getStyleId()));
            beer.setStyle(style);
        }

        if (beerDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(beerDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + beerDTO.getCategoryId()));
            beer.setCategory(category);
        }

        Beer savedBeer = beerRepository.save(beer);
        log.info("Cerveza creada con id: {}", savedBeer.getId());
        return beerMapper.toDTO(savedBeer);
    }

    @Transactional
    public BeerDTO updateBeer(Integer id, BeerDTO beerDTO) {
        log.info("Actualizando cerveza con id: {}", id);
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza no encontrada con id: " + id));

        beerMapper.updateEntity(beerDTO, beer);

        // Actualizar relaciones si existen IDs
        if (beerDTO.getBreweryId() != null) {
            Brewery brewery = breweryRepository.findById(beerDTO.getBreweryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cervecería no encontrada con id: " + beerDTO.getBreweryId()));
            beer.setBrewery(brewery);
        } else {
            beer.setBrewery(null);
        }

        if (beerDTO.getStyleId() != null) {
            Style style = styleRepository.findById(beerDTO.getStyleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estilo no encontrado con id: " + beerDTO.getStyleId()));
            beer.setStyle(style);
        } else {
            beer.setStyle(null);
        }

        if (beerDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(beerDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + beerDTO.getCategoryId()));
            beer.setCategory(category);
        } else {
            beer.setCategory(null);
        }

        Beer updatedBeer = beerRepository.save(beer);
        log.info("Cerveza actualizada con id: {}", id);
        return beerMapper.toDTO(updatedBeer);
    }

    @Transactional
    public BeerDTO partialUpdateBeer(Integer id, BeerDTO beerDTO) {
        log.info("Actualización parcial de cerveza con id: {}", id);
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza no encontrada con id: " + id));

        // Actualizar solo los campos no nulos
        if (beerDTO.getName() != null) {
            beer.setName(beerDTO.getName());
        }
        if (beerDTO.getDescription() != null) {
            beer.setDescription(beerDTO.getDescription());
        }
        if (beerDTO.getAbv() != null) {
            beer.setAbv(beerDTO.getAbv());
        }
        if (beerDTO.getIbu() != null) {
            beer.setIbu(beerDTO.getIbu());
        }

        if (beerDTO.getBreweryId() != null) {
            Brewery brewery = breweryRepository.findById(beerDTO.getBreweryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cervecería no encontrada con id: " + beerDTO.getBreweryId()));
            beer.setBrewery(brewery);
        }

        if (beerDTO.getStyleId() != null) {
            Style style = styleRepository.findById(beerDTO.getStyleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estilo no encontrado con id: " + beerDTO.getStyleId()));
            beer.setStyle(style);
        }

        if (beerDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(beerDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + beerDTO.getCategoryId()));
            beer.setCategory(category);
        }

        Beer updatedBeer = beerRepository.save(beer);
        log.info("Cerveza parcialmente actualizada con id: {}", id);
        return beerMapper.toDTO(updatedBeer);
    }

    @Transactional
    public void deleteBeer(Integer id) {
        log.info("Eliminando cerveza con id: {}", id);
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cerveza no encontrada con id: " + id));
        beerRepository.delete(beer);
        log.info("Cerveza eliminada con id: {}", id);
    }
}

