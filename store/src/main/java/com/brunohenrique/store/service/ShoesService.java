package com.brunohenrique.store.service;

import com.brunohenrique.store.domain.Shoe;
import com.brunohenrique.store.dtos.RequestShoe;
import com.brunohenrique.store.repository.ShoeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoesService {
    @Autowired
    ShoeRepository shoeRepository;

    public List<Shoe> listAllShoes(){
        return shoeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Shoe::getName))
                .collect(Collectors.toList());
    }

    public Optional<Shoe> listShoeById(String id){
        return shoeRepository.findById(id);
    }

    public void createShoe(RequestShoe data){
        Shoe newShoe = new Shoe(data);
        shoeRepository.save(newShoe);
    }

    public Shoe update(RequestShoe data){
        Optional<Shoe> optionalShoe = shoeRepository.findById(data.id());
        if(optionalShoe.isPresent()){
            Shoe newShoe = optionalShoe.get();
            newShoe.setName(data.name());
            newShoe.setDescription(data.description());
            newShoe.setPrice(data.price());
            shoeRepository.save(newShoe);
            return newShoe;
        }else{
            throw new EntityNotFoundException();
        }
    }

    public void delete(String id){
        shoeRepository.deleteById(id);}
}
