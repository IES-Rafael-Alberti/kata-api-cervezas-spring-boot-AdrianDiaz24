package com.kata.api.service;

import com.kata.api.dto.CategoryDTO;
import com.kata.api.entity.Category;
import com.kata.api.exception.ResourceNotFoundException;
import com.kata.api.mapper.CategoryMapper;
import com.kata.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        log.info("Obteniendo todas las categorías");
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Integer id) {
        log.info("Obteniendo categoría con id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return categoryMapper.toDTO(category);
    }
}

