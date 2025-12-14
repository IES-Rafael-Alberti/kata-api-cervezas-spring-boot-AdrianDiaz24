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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private BeerMapper beerMapper;

    @Mock
    private BreweryRepository breweryRepository;

    @Mock
    private StyleRepository styleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BeerService beerService;

    private Beer testBeer;
    private BeerDTO testBeerDTO;
    private Brewery testBrewery;
    private Style testStyle;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        testBrewery = Brewery.builder()
                .id(1)
                .name("Test Brewery")
                .build();

        testCategory = Category.builder()
                .id(1)
                .name("Test Category")
                .build();

        testStyle = Style.builder()
                .id(1)
                .name("Test Style")
                .category(testCategory)
                .build();

        testBeer = Beer.builder()
                .id(1)
                .name("Test Beer")
                .description("A test beer")
                .abv(5.0)
                .ibu(25.0)
                .brewery(testBrewery)
                .style(testStyle)
                .category(testCategory)
                .build();

        testBeerDTO = BeerDTO.builder()
                .id(1)
                .name("Test Beer")
                .description("A test beer")
                .abv(5.0)
                .ibu(25.0)
                .breweryId(1)
                .styleId(1)
                .categoryId(1)
                .build();
    }

    @Test
    void testGetAllBeers() {
        // Arrange
        List<Beer> beers = Arrays.asList(testBeer);
        List<BeerDTO> beerDTOs = Arrays.asList(testBeerDTO);

        when(beerRepository.findAll()).thenReturn(beers);
        when(beerMapper.toDTO(testBeer)).thenReturn(testBeerDTO);

        // Act
        List<BeerDTO> result = beerService.getAllBeers();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Beer");
        verify(beerRepository, times(1)).findAll();
    }

    @Test
    void testGetBeerById_Success() {
        // Arrange
        when(beerRepository.findById(1)).thenReturn(Optional.of(testBeer));
        when(beerMapper.toDTO(testBeer)).thenReturn(testBeerDTO);

        // Act
        BeerDTO result = beerService.getBeerById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Test Beer");
        verify(beerRepository, times(1)).findById(1);
    }

    @Test
    void testGetBeerById_NotFound() {
        // Arrange
        when(beerRepository.findById(9999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> beerService.getBeerById(9999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cerveza no encontrada");
    }

    @Test
    void testCreateBeer_Success() {
        // Arrange
        when(beerMapper.toEntity(testBeerDTO)).thenReturn(testBeer);
        when(breweryRepository.findById(1)).thenReturn(Optional.of(testBrewery));
        when(styleRepository.findById(1)).thenReturn(Optional.of(testStyle));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(beerRepository.save(any(Beer.class))).thenReturn(testBeer);
        when(beerMapper.toDTO(testBeer)).thenReturn(testBeerDTO);

        // Act
        BeerDTO result = beerService.createBeer(testBeerDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void testCreateBeer_InvalidBrewery() {
        // Arrange
        when(beerMapper.toEntity(testBeerDTO)).thenReturn(testBeer);
        when(breweryRepository.findById(9999)).thenReturn(Optional.empty());

        // Act & Assert
        testBeerDTO.setBreweryId(9999);
        assertThatThrownBy(() -> beerService.createBeer(testBeerDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cervecería no encontrada");
    }

    @Test
    void testUpdateBeer_Success() {
        // Arrange
        when(beerRepository.findById(1)).thenReturn(Optional.of(testBeer));
        when(breweryRepository.findById(1)).thenReturn(Optional.of(testBrewery));
        when(styleRepository.findById(1)).thenReturn(Optional.of(testStyle));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(beerRepository.save(any(Beer.class))).thenReturn(testBeer);
        when(beerMapper.toDTO(testBeer)).thenReturn(testBeerDTO);

        // Act
        BeerDTO result = beerService.updateBeer(1, testBeerDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void testUpdateBeer_NotFound() {
        // Arrange
        when(beerRepository.findById(9999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> beerService.updateBeer(9999, testBeerDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDeleteBeer_Success() {
        // Arrange
        when(beerRepository.findById(1)).thenReturn(Optional.of(testBeer));

        // Act
        beerService.deleteBeer(1);

        // Assert
        verify(beerRepository, times(1)).delete(testBeer);
    }

    @Test
    void testDeleteBeer_NotFound() {
        // Arrange
        when(beerRepository.findById(9999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> beerService.deleteBeer(9999))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testPartialUpdateBeer_Success() {
        // Arrange
        BeerDTO updateDTO = BeerDTO.builder()
                .abv(6.0)
                .ibu(35.0)
                .build();

        when(beerRepository.findById(1)).thenReturn(Optional.of(testBeer));
        when(beerRepository.save(any(Beer.class))).thenReturn(testBeer);
        when(beerMapper.toDTO(testBeer)).thenReturn(testBeerDTO);

        // Act
        BeerDTO result = beerService.partialUpdateBeer(1, updateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(beerRepository, times(1)).save(any(Beer.class));
    }
}

