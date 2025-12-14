package com.kata.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.api.dto.BeerDTO;
import com.kata.api.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerService beerService;

    private BeerDTO testBeerDTO;

    @BeforeEach
    void setUp() {
        testBeerDTO = BeerDTO.builder()
                .id(1)
                .name("Pilsen")
                .description("Cerveza tipo pilsen")
                .abv(5.0)
                .ibu(25.0)
                .breweryId(1)
                .styleId(1)
                .categoryId(1)
                .build();
    }

    @Test
    void testGetAllBeers() throws Exception {
        // Arrange
        List<BeerDTO> beers = Arrays.asList(testBeerDTO);
        when(beerService.getAllBeers()).thenReturn(beers);

        // Act & Assert
        mockMvc.perform(get("/api/beers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Pilsen")));

        verify(beerService, times(1)).getAllBeers();
    }

    @Test
    void testGetBeerById() throws Exception {
        // Arrange
        when(beerService.getBeerById(1)).thenReturn(testBeerDTO);

        // Act & Assert
        mockMvc.perform(get("/api/beers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Pilsen")))
                .andExpect(jsonPath("$.abv", is(5.0)));

        verify(beerService, times(1)).getBeerById(1);
    }

    @Test
    void testCreateBeer() throws Exception {
        // Arrange
        when(beerService.createBeer(any(BeerDTO.class))).thenReturn(testBeerDTO);

        // Act & Assert
        mockMvc.perform(post("/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Pilsen")));

        verify(beerService, times(1)).createBeer(any(BeerDTO.class));
    }

    @Test
    void testCreateBeer_InvalidData() throws Exception {
        // Arrange - Beer with invalid name (too short)
        BeerDTO invalidBeer = BeerDTO.builder()
                .name("P")  // Too short
                .abv(5.0)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBeer)))
                .andExpect(status().isBadRequest());

        verify(beerService, times(0)).createBeer(any(BeerDTO.class));
    }

    @Test
    void testUpdateBeer() throws Exception {
        // Arrange
        BeerDTO updatedBeer = BeerDTO.builder()
                .id(1)
                .name("Pilsen Premium")
                .description("Updated beer")
                .abv(5.2)
                .ibu(28.0)
                .breweryId(1)
                .styleId(1)
                .categoryId(1)
                .build();

        when(beerService.updateBeer(anyInt(), any(BeerDTO.class))).thenReturn(updatedBeer);

        // Act & Assert
        mockMvc.perform(put("/api/beers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pilsen Premium")))
                .andExpect(jsonPath("$.abv", is(5.2)));

        verify(beerService, times(1)).updateBeer(anyInt(), any(BeerDTO.class));
    }

    @Test
    void testPartialUpdateBeer() throws Exception {
        // Arrange
        BeerDTO partialUpdate = BeerDTO.builder()
                .abv(5.3)
                .build();

        BeerDTO resultBeer = BeerDTO.builder()
                .id(1)
                .name("Pilsen")
                .abv(5.3)
                .ibu(25.0)
                .build();

        when(beerService.partialUpdateBeer(anyInt(), any(BeerDTO.class))).thenReturn(resultBeer);

        // Act & Assert
        mockMvc.perform(patch("/api/beers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partialUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abv", is(5.3)));

        verify(beerService, times(1)).partialUpdateBeer(anyInt(), any(BeerDTO.class));
    }

    @Test
    void testDeleteBeer() throws Exception {
        // Arrange
        doNothing().when(beerService).deleteBeer(anyInt());

        // Act & Assert
        mockMvc.perform(delete("/api/beers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService, times(1)).deleteBeer(1);
    }
}

