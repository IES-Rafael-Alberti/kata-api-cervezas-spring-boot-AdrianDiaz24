package com.kata.api.service;

import com.kata.api.dto.StyleDTO;
import com.kata.api.entity.Style;
import com.kata.api.exception.ResourceNotFoundException;
import com.kata.api.mapper.StyleMapper;
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
public class StyleService {

    private final StyleRepository styleRepository;
    private final StyleMapper styleMapper;

    @Transactional(readOnly = true)
    public List<StyleDTO> getAllStyles() {
        log.info("Obteniendo todos los estilos");
        return styleRepository.findAll()
                .stream()
                .map(styleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StyleDTO getStyleById(Integer id) {
        log.info("Obteniendo estilo con id: {}", id);
        Style style = styleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estilo no encontrado con id: " + id));
        return styleMapper.toDTO(style);
    }
}

