package com.brunohenrique.store.services;

import com.brunohenrique.store.domain.Shoe;
import com.brunohenrique.store.dtos.RequestShoe;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.repositories.ShoeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoeServiceTest {
    @Mock
    ShoeRepository shoeRepository;

    @InjectMocks
    ShoeService shoeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should list all shoes sucessfully")
    void listAllShoesCase1() {
        List<Shoe> listShoes = new ArrayList<>();
        listShoes.add(new Shoe("123e4567-e89b-12d3-a456-426655440000", "Qix Hexagon Black", "Descrição do Qix", "Qix", 450.59D, 43));
        listShoes.add(new Shoe("123e4567-e89b-12d3-a456-426655440012", "Adidas Campus 00s Black Core", "Descrição do Adidas", "Adidas", 699.99D, 39));

        when(shoeRepository.findAll()).thenReturn(listShoes);

        List<Shoe> expected = listShoes.stream()
                .sorted(Comparator.comparing(Shoe::getName))
                .collect(Collectors.toList());
        assertEquals(expected, shoeService.listAllShoes());
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void listAllShoesCase2(){
        when(shoeRepository.findAll()).thenThrow(new RuntimeException("Falha ao buscar calçados no repositório"));

        assertThrows(RuntimeException.class, () -> shoeService.listAllShoes());

        verify(shoeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should list shoe by id sucessfully")
    void listShoeByIdCase1() {
        String shoeId = "123e4567-e89b-12d3-a456-426655440000";

        Shoe expectedShoe = new Shoe(shoeId, "Air Max 97 Black", "Descrição", "Nike", 750D, 42);

        when(shoeRepository.findById(shoeId)).thenReturn(Optional.of(expectedShoe));

        Shoe result = shoeService.listShoeById(shoeId);

        verify(shoeRepository, times(1)).findById(shoeId);

        assertEquals(expectedShoe, result);
    }

    @Test
    @DisplayName("Should throw Exception when shoe not found")
    void listShoeByIdCase2(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(shoeRepository.findById(id)).thenThrow(new DataNotFoundException("Calçado não encontrado"));

        assertThrows(DataNotFoundException.class, () -> shoeService.listShoeById(id));

        verify(shoeRepository,  times(1)).findById(id);
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void listShoeByIdCase3(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(shoeRepository.findById(id)).thenThrow(new RuntimeException("Falha ao buscar o calçado no repositório"));

        assertThrows(RuntimeException.class, () -> shoeService.listShoeById(id));

        verify(shoeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should create shoe sucessfully")
    void createShoeCase1() {
        RequestShoe shoeTest = new RequestShoe("123e4567-e89b-12d3-a456-426655440000", "Qix Hexagon Black", "Descrição do Qix", "Qix", 450.59D, 42);

        shoeService.createShoe(shoeTest);

        verify(shoeRepository, times(1)).save(any(Shoe.class));
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void createShoeCase2(){
        RequestShoe shoeDTO = new RequestShoe("123e4567-e89b-12d3-a456-426655440000", "Air Max 97 Black", "Descrição do Air Max", "Nike", 750D, 41);
        Shoe shoeTest = new Shoe(shoeDTO);

        when(shoeRepository.save(shoeTest)).thenThrow(new RuntimeException("Falha ao criar o calçado no repositório"));

        assertThrows(RuntimeException.class, () -> shoeService.createShoe(shoeDTO));

        verify(shoeRepository, times(1)).save(shoeTest);
    }

    @Test
    @DisplayName("Should update shoe sucessfully")
    void updateShoeCase1() {
        RequestShoe shoeDTO = new RequestShoe("123e4567-e89b-12d3-a456-426655440000", "Air Max 97 Black", "Descrição do Air Max", "Nike", 750D, 41);
        Shoe oldShoe = new Shoe(shoeDTO.id(), "Air Max 97 Black", "Descrição Antiga", "Nike", 700D, 41);
        Shoe newShoe = new Shoe(shoeDTO);

        when(shoeRepository.findById(shoeDTO.id())).thenReturn(Optional.of(oldShoe));
        when(shoeRepository.save(any(Shoe.class))).thenReturn(newShoe);

        Shoe result = shoeService.updateShoe(shoeDTO);

        verify(shoeRepository, times(1)).findById(shoeDTO.id());
        verify(shoeRepository, times(1)).save(any(Shoe.class));

        assertEquals(newShoe, result);
    }

    @Test
    @DisplayName("Should throw Exception when shoe not found")
    void updateShoeCase2(){
        RequestShoe shoeDTO = new RequestShoe("123e4567-e89b-12d3-a456-426655440000", "Air Max 97 Black", "Descrição do Air Max", "Nike", 750D, 41);

        when(shoeRepository.findById(shoeDTO.id())).thenThrow(new DataNotFoundException("Calçado não encontrado"));

        assertThrows(DataNotFoundException.class, () -> shoeService.updateShoe(shoeDTO));
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void updateShoeCase3(){
        String id = "123e4567-e89b-12d3-a456-426655440000";

        when(shoeRepository.findById(id)).thenThrow(new RuntimeException("Falha ao buscar o calçado no repositório"));

        assertThrows(RuntimeException.class, () -> shoeService.listShoeById(id));

        verify(shoeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should delete shoe sucessfully")
    void deleteShoeCase1() {
        Shoe shoeTest = new Shoe("123e4567-e89b-12d3-a456-426655440000", "Qix Hexagon Black", "Descrição do Qix", "Qix", 450.59D, 42);

        shoeService.deleteShoe(shoeTest.getId());

        verify(shoeRepository, times(1)).deleteById(shoeTest.getId());
    }

    @Test
    @DisplayName("Should throw Exception when failure repository")
    void deleteShoeCase2(){
        String id = "123e4567-e89b-12d3-a456-426655440000";
        doThrow(new RuntimeException("Falha ao deletar o calçado do repositório")).when(shoeRepository).deleteById(id);

        assertThrows(RuntimeException.class, () -> shoeService.deleteShoe(id));

        verify(shoeRepository, times(1)).deleteById(id);
    }
}