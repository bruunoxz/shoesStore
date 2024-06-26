package com.brunohenrique.store.services;

import com.brunohenrique.store.domain.Shoe;
import com.brunohenrique.store.dtos.RequestShoe;
import com.brunohenrique.store.exceptions.DataAlreadyRegistered;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeService {
    @Autowired
    ShoeRepository shoeRepository;

    public List<Shoe> listAllShoes(){
        return shoeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Shoe::getName))
                .collect(Collectors.toList());
    }

    public Shoe listShoeById(String id){
        return shoeRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Calçado não encontrado"));
    }

    public void createShoe(RequestShoe data){
        Shoe newShoe = new Shoe(data);
        shoeRepository.save(newShoe);
    }

    public Shoe updateShoe(RequestShoe data){
        return shoeRepository.findById(data.id()).
                map(shoe -> {
                    shoe.setName(data.name());
                    shoe.setDescription(data.description());
                    shoe.setBrand(data.brand());
                    shoe.setPrice(data.price());
                    return shoeRepository.save(shoe);
                }).orElseThrow(()->new DataNotFoundException("Calçado não encontrado"));
    }

    public void deleteShoe(String id){shoeRepository.deleteById(id);}
}
