package com.kata.api.service;

import com.kata.api.dto.BreweryDTO;
import com.kata.api.entity.Brewery;
import com.kata.api.exception.ResourceNotFoundException;
import com.kata.api.mapper.BreweryMapper;
import com.kata.api.repository.BreweryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreweryService {

    private final BreweryRepository breweryRepository;
    private final BreweryMapper breweryMapper;

    @Transactional(readOnly = true)
    public List<BreweryDTO> getAllBreweries() {
        log.info("Obteniendo todas las cervecerías");
        return breweryRepository.findAll()
                .stream()
                .map(breweryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BreweryDTO getBreweryById(Integer id) {
        log.info("Obteniendo cervecería con id: {}", id);
        Brewery brewery = breweryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cervecería no encontrada con id: " + id));
        return breweryMapper.toDTO(brewery);
    }
}

